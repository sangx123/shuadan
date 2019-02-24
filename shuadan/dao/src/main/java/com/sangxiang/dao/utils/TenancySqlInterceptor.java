package com.sangxiang.dao.utils;

import com.sangxiang.base.model.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})

@Slf4j
public class TenancySqlInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        addOnTenancy(invocation);
        return invocation.proceed();
    }

    private void transSelect(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Object paratemers = invocation.getArgs()[1];

        BoundSql oSql;
        if(invocation.getArgs().length == 6) {
            oSql = (BoundSql) invocation.getArgs()[5];
        } else {
            oSql = ms.getBoundSql(paratemers);
        }

        //log.debug(String.format("::: thread id: [%s]", Thread.currentThread().getId()));
        //log.debug(String.format("::: SQL Old: [%s]", oSql.getSql()));
        String tSql = TenancySqlTransition.transit(oSql.getSql());
        //log.debug(String.format("::: SQL New: [%s]", tSql));

        BoundSql newSql = new BoundSql(ms.getConfiguration(), tSql, oSql.getParameterMappings(), oSql.getParameterObject());
        //反射获取 BoundSql 中的 additionalParameters 属性
        try {
            Field additionalParameters = BoundSql.class.getDeclaredField("additionalParameters");
            additionalParameters.setAccessible(true);
            Map<String, Object> aParameters = (Map<String, Object>) additionalParameters.get(oSql);
            Optional.ofNullable(aParameters).ifPresent(aparams -> {
                for (String key : aparams.keySet()) {
                    newSql.setAdditionalParameter(key, aparams.get(key));
                }
            });
        } catch (NoSuchFieldException e) {
            //log.error("::: Error: ", e);
        }

        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), new BoundSqlSource(newSql), ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.cache(ms.getCache());
        builder.useCache(ms.isUseCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.keyColumn(StringUtils.join(ms.getKeyColumns(), ","));
        builder.keyProperty(StringUtils.join(ms.getKeyProperties(), ","));
        MappedStatement stmt =  builder.build();
        invocation.getArgs()[0] = stmt;
        if (invocation.getMethod().getName().equalsIgnoreCase("query")) {
            if (invocation.getArgs().length > 4) {
                invocation.getArgs()[5] = stmt.getBoundSql(invocation.getArgs()[1]);
            }
        }
    }

    private void transUpdate(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Object paratemers = invocation.getArgs()[1];
        try {
            Method orgIdGetter = paratemers.getClass().getMethod("getOrgId");
            Long orgIdInParam = (Long)orgIdGetter.invoke(paratemers);
            if(orgIdInParam == null || orgIdInParam.equals(0)) {
                Method orgIdSetter = paratemers.getClass().getMethod("setOrgId", Long.class);
                orgIdSetter.invoke(paratemers, TenancyContext.getInstance().getOrgId());
            }

        } catch (NoSuchMethodException e) {
            try {
                if (BaseEntity.class.isAssignableFrom(paratemers.getClass())) {
                    Method orgIdSetter = paratemers.getClass().getSuperclass().getMethod("setOrgId", Long.class);
                    orgIdSetter.invoke(paratemers, TenancyContext.getInstance().getOrgId());
                }
                // insertList支持
                if (ms.getId().contains("insertList")) {
                    Map<String, Object> paramsMap = (Map<String, Object>) paratemers;
                    if (paramsMap != null && paramsMap.size() > 0) {
                        for (Map.Entry<String, Object> param : paramsMap.entrySet()) {
                            if (param.getKey().equals("list") || param.getKey().equals("collection")) {
                                List paramValues = (ArrayList) param.getValue();
                                Iterator paramIt = paramValues.iterator();
                                while (paramIt.hasNext()) {
                                    Object itObject = paramIt.next();
                                    if (BaseEntity.class.isAssignableFrom(itObject.getClass())) {
                                        Method orgIdSetter = itObject.getClass().getSuperclass().getMethod("setOrgId", Long.class);
                                        orgIdSetter.invoke(itObject, TenancyContext.getInstance().getOrgId());
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (HashMap.class.isAssignableFrom(paratemers.getClass())) {
                        Map<String, Object> paramsMap = (Map<String, Object>) paratemers;
                        if (paramsMap != null && paramsMap.size() > 0) {
                            if (!paramsMap.containsKey("orgId")) {
                                paramsMap.put("orgId", TenancyContext.getInstance().getOrgId());
                            }
                        }
                    }
                }

            } catch (NoSuchMethodException em) {
                //log.info("::: Error: o org id parameter ");
            }
        }
        BoundSql oSql = ms.getBoundSql(paratemers);
        //log.debug(String.format("::: SQL Old: [%s]", oSql.getSql()));
        String tSql = TenancySqlTransition.transit(oSql.getSql());
        //log.debug(String.format("::: SQL New: [%s]", tSql));

        BoundSql newSql = new BoundSql(ms.getConfiguration(), tSql, oSql.getParameterMappings(), oSql.getParameterObject());
        //反射获取 BoundSql 中的 additionalParameters 属性
        try {
            Field additionalParameters = BoundSql.class.getDeclaredField("additionalParameters");
            additionalParameters.setAccessible(true);
            Map<String, Object> aParameters = (Map<String, Object>) additionalParameters.get(oSql);
            Optional.ofNullable(aParameters).ifPresent(aparams -> {
                for (String key : aparams.keySet()) {
                    newSql.setAdditionalParameter(key, aparams.get(key));
                }
            });
        } catch (NoSuchFieldException e) {
            //log.error("::: Error: ", e);
        }
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), new BoundSqlSource(newSql), ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.cache(ms.getCache());
        builder.useCache(ms.isUseCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.keyColumn(StringUtils.join(ms.getKeyColumns(),","));
        builder.keyProperty(StringUtils.join(ms.getKeyProperties(), ","));
        MappedStatement stmt =  builder.build();
        invocation.getArgs()[0] = stmt;
    }

    private void addOnTenancy(Invocation invocation) throws Throwable {
        //log.debug(String.format("::: Current OrgID: [%d]", TenancyContext.getInstance().getOrgId()));
        if (invocation.getMethod().getName().equalsIgnoreCase("update")) {
            transUpdate(invocation);
        }
        if (invocation.getMethod().getName().equalsIgnoreCase("query")) {
            transSelect(invocation);
        }
    }

    private class BoundSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

//    public static void main(String[] args)  throws Throwable {
//        TenancyContext.getInstance().setOrgId(1L);
////        String sql = "insert sys_area (area_name) values ('aaa') on duplicate key update area_name = '111'";
////        String sql = "update t_operate_data_for_work a inner join t_loop_work b on a.loop_work_id = b.id set a.audit_result = 2 where b.work_status=1 and b.execute_deadline < '2018-08-13 12:00:00'";
//        String sql = "UPDATE t_operate_data_for_work SET audit_result = 2 WHERE org_id= 1 and loop_work_id IN (SELECT id FROM t_loop_work  WHERE work_status=1 AND execute_deadline < '2018-08-13 12:00:00')";
//        try {
//            String nsql = TenancySqlTransition.transit(sql);
//            System.out.println(nsql);
//        } catch (JSQLParserException e) {
//            e.printStackTrace();
//        }
//    }
}

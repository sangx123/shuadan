package com.sangxiang.dao.utils;

import lombok.Data;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TenancySqlTransition {

    @Data
    private static class TableNamePair {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        private String alias;
        public TableNamePair(String name, String alias) {
            this.name = name;
            this.alias = alias;
        }
    }

    public static class MyTablesNamesFinder extends TablesNamesFinder {
        protected List<TableNamePair> tableList = new ArrayList<TableNamePair>();

        protected List<String> excludeTables = Arrays.asList("sys_app_version", "sys_district", "sys_org", "sys_user_token","sys_industry", "a_client_action", "t_task_template", "t_task_template_option",
                "a_plan_statistics", "a_shop_statistics", "a_task_statistics", "a_user_statistics");

        @Override
        public void visit(Table table) {
            if (!excludeTables.contains(table.getName())) {
                tableList.add(new TableNamePair(table.getName(), table.getAlias() == null? null : table.getAlias().getName()));
            }
        }

        @Override
        public void visit(Insert insert) {
            insert.getTable().accept(this);
        }

        @Override
        public void visit(Update update) {
            for (Table table : update.getTables()) {
                table.accept(this);
            }

            if (update.getJoins() != null) {
                for (Join join : update.getJoins()) {
                    if (join.isInner()) {
                        join.getRightItem().accept(this);
                    };
                }
            }
        }

        @Override
        public void visit(PlainSelect plainSelect) {
            if (plainSelect.getFromItem() != null) {
                plainSelect.getFromItem().accept(this);
            }

            if (plainSelect.getJoins() != null) {
                for (Join join : plainSelect.getJoins()) {
                    if (join.isInner()) {
                        join.getRightItem().accept(this);
                    }
                }
            }

        }

        @Override
        public void visit(Delete delete) {
            delete.getTable().accept(this);
        }

        public List<String> getAddOnTableAliases(Statement statement) {
            statement.accept(this);
            List<String> aliasList = new ArrayList<>();
            List<String> nameList = new ArrayList<>();
            for(TableNamePair pair : tableList) {
                if (StringUtils.isNotBlank(pair.getAlias())) {
                    aliasList.add(pair.getAlias());
                }
                nameList.add(pair.getName());
            }
            if (aliasList.size() > 0) {
                return aliasList;
            }
            if (nameList.size() > 0) {
                return aliasList;
            }
            // 不做处理，返回原来的sql
            return null;
        }

        /**
         * 检查当前表名是否在tableList中
         * @param statement
         * @param tableName
         * @return
         */
        public boolean checkTableAvailable(Statement statement, String tableName) {
            statement.accept(this);
            for (TableNamePair pair : tableList) {
                if (pair.getName().equals(tableName)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static String transit(String fullSql) throws Throwable {
        long orgId = TenancyContext.getInstance().getOrgId();
        if (orgId == 0) {
            // 判断是否有商户id存在，如果不存在就不修改sql，这里主要是为了兼容后台Job，Job运行的时候是没办法分商户的。
            return fullSql;
        }
        if(fullSql.contains(";")) {
            String[] subSqls = fullSql.split(";");
            StringBuilder transitedFullSql = new StringBuilder();
            for (String sql : subSqls) {
                transitedFullSql.append(transitSubSql(orgId, sql));
                transitedFullSql.append(";");
            }
            return transitedFullSql.toString();

        } else {
            return transitSubSql(orgId, fullSql);
        }

    }

    private static String transitSubSql(long orgId, String sql) throws Throwable {
        Statement stmt = CCJSqlParserUtil.parse(sql);
        if (stmt instanceof Delete) {
            Delete delete = (Delete) stmt;

            MyTablesNamesFinder tablesNamesFinder = new MyTablesNamesFinder();
            List<String> tableAliases = tablesNamesFinder.getAddOnTableAliases(delete);
            if (tableAliases == null) {
                return delete.toString();
            }
            String fromAlias = "";
            if (delete.getJoins() != null && delete.getJoins().size() > 0) {
                fromAlias = delete.getTable().getAlias().getName();
            }
            Expression where = delete.getWhere();
            if (where instanceof BinaryExpression) {
                EqualsTo equalsTo = new EqualsTo();
                if (StringUtils.isNotBlank(fromAlias)) {
                    equalsTo.setLeftExpression(new Column(String.format("%s.%s", fromAlias, "org_id")));
                } else {
                    equalsTo.setLeftExpression(new Column("org_id"));
                }
                equalsTo.setRightExpression(new LongValue(orgId));
                AndExpression andExpression = new AndExpression(equalsTo, where);
                delete.setWhere(andExpression);
            }
            return delete.toString();
        }
        if (stmt instanceof Insert) {
            Insert insert = (Insert) stmt;
            for (int i = 0, len = insert.getColumns().size(); i < len; i++) {
                if (insert.getColumns().get(i).getColumnName().equalsIgnoreCase("org_id")) {
//                    ((ExpressionList) insert.getItemsList()).getExpressions().set(i, new LongValue(orgId));
//                    if (insert.isUseDuplicate()) {
//                        for (int j = 0, sz = insert.getDuplicateUpdateColumns().size(); j < sz; j++) {
//                            if (insert.getDuplicateUpdateColumns().get(j).getColumnName().equalsIgnoreCase("org_id")) {
//                                insert.getDuplicateUpdateExpressionList().set(j, new LongValue(orgId));
//                            }
//                        }
//                    }
                    return insert.toString();
                }
            }
            MyTablesNamesFinder tablesNamesFinder = new MyTablesNamesFinder();
            List<String> tableAliases = tablesNamesFinder.getAddOnTableAliases(insert);
            if (tableAliases == null) {
                return insert.toString();
            }
            insert.getColumns().add(new Column("org_id"));

            if (insert.getItemsList() instanceof MultiExpressionList) {
                List<ExpressionList> expressionLists = ((MultiExpressionList) insert.getItemsList()).getExprList();
                for (ExpressionList expressionList : expressionLists) {
                    expressionList.getExpressions().add(new LongValue(orgId));
                }
            } else {
                ((ExpressionList) insert.getItemsList()).getExpressions().add(new LongValue(orgId));
            }

            if (insert.isUseDuplicate()) {
                insert.getDuplicateUpdateColumns().add(new Column("org_id"));
                insert.getDuplicateUpdateExpressionList().add(new LongValue(orgId));
            }
            return insert.toString();
        }
        if (stmt instanceof Update) {
            Update updateStatement = (Update) stmt;

            MyTablesNamesFinder tablesNamesFinder = new MyTablesNamesFinder();
            List<String> tableAliases = tablesNamesFinder.getAddOnTableAliases(updateStatement);
            if (tableAliases == null) {
                return updateStatement.toString();
            }
            //获得where条件表达式
            Expression where = updateStatement.getWhere();
//            if (where instanceof BinaryExpression) {
            if (tableAliases.size() > 0) {
                Expression condition = where;
                for (String alias : tableAliases) {
                    EqualsTo equalsTo = new EqualsTo();
                    equalsTo.setLeftExpression(new Column(alias + ".org_id"));
                    equalsTo.setRightExpression(new LongValue(orgId));
                    condition = new AndExpression(equalsTo, condition);
                }
                updateStatement.setWhere(condition);
            } else {
                EqualsTo equalsTo = new EqualsTo();
                equalsTo.setLeftExpression(new Column("org_id"));
                equalsTo.setRightExpression(new LongValue(orgId));
                AndExpression andExpression = new AndExpression(equalsTo, where);
                updateStatement.setWhere(andExpression);
            }
//            }
            return updateStatement.toString();
        }
        if (stmt instanceof Select) {
            Select select = (Select) stmt;
            // 支持union
            if (select.getSelectBody() instanceof SetOperationList) {
                SetOperationList setOperationList = (SetOperationList) select.getSelectBody();
                List<SelectBody> plainSelects = setOperationList.getSelects();
                for (SelectBody plainSelect : plainSelects) {
                    List<String> tableAliases = new ArrayList<String>();
                    MyTablesNamesFinder tablesNamesFinder = new MyTablesNamesFinder();
                    PlainSelect ps = (PlainSelect) plainSelect;
                    Table formTable = (Table) ps.getFromItem();
                    // 如果当前操作的from表名在可操作org_id的表名单中，则拼接org_id字段
                    if (tablesNamesFinder.checkTableAvailable(select, formTable.getName())) {
                        if (formTable.getAlias() != null) {
                            tableAliases.add(formTable.getAlias().getName());
                        }

                    }
                    // 如果当前操作的join表名在可操作org_id的表名单中，则拼接org_id字段
                    if (CollectionUtils.isNotEmpty(ps.getJoins())) {
                        for (Join joinTable : ps.getJoins()) {
                            Table rightItmes = (Table) joinTable.getRightItem();
                            if (rightItmes != null && tablesNamesFinder.checkTableAvailable(select, rightItmes.getName())) {
                                if (rightItmes.getAlias() != null) {
                                    tableAliases.add(rightItmes.getAlias().getName());
                                }
                            }
                        }
                    }
                    joinSelectSql(tableAliases, orgId, ps);
                }
            } else {
                MyTablesNamesFinder tablesNamesFinder = new MyTablesNamesFinder();
                List<String> tableAliases = tablesNamesFinder.getAddOnTableAliases(select);
                PlainSelect ps = (PlainSelect) select.getSelectBody();
                joinSelectSql(tableAliases, orgId, ps);
            }
            return select.toString();
        }
        throw new RuntimeException("SQL parse error: incorrect sql !");
    }

    /**
     * 拼接查询sql
     * @param tableAliases
     * @param orgId
     * @param ps
     */
    private static void joinSelectSql(List<String> tableAliases, Long orgId, PlainSelect ps) {
        if (tableAliases == null) {
            return;
        }
        if (tableAliases.size() > 0) {
            for (String table : tableAliases) {
                EqualsTo equalsTo = new EqualsTo();
                equalsTo.setLeftExpression(new Column(table + ".org_id"));
                equalsTo.setRightExpression(new LongValue(orgId));
                if (ps.getWhere() != null) {
                    AndExpression andExpression = new AndExpression(equalsTo, ps.getWhere());
                    ps.setWhere(andExpression);
                } else {
                    ps.setWhere(equalsTo);
                }
            }
        } else {
            EqualsTo equalsTo = new EqualsTo();
            equalsTo.setLeftExpression(new Column("org_id"));
            equalsTo.setRightExpression(new LongValue(orgId));
            if (ps.getWhere() != null) {
                AndExpression andExpression = new AndExpression(equalsTo, ps.getWhere());
                ps.setWhere(andExpression);
            } else {
                ps.setWhere(equalsTo);
            }
        }
    }

}

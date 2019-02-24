package com.sangxiang.dao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sangxiang.base.service.impl.BaseServiceImpl;
import com.sangxiang.dao.mapper.SysUserMapper;
import com.sangxiang.dao.mapper.TaobaoTaskMapper;
import com.sangxiang.dao.mapper.UserMoneyHistoryMapper;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.model.TaobaoTask;
import com.sangxiang.dao.model.Task;
import com.sangxiang.dao.model.UserMoneyHistory;
import com.sangxiang.dao.service.TaobaoTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class TaobaoTaskServiceImpl extends BaseServiceImpl<TaobaoTask> implements TaobaoTaskService {
    @Autowired
    TaobaoTaskMapper taobaoTaskMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    UserMoneyHistoryMapper userMoneyHistoryMapper;

    @Transactional
    @Override
    public int createTaobaoTask(TaobaoTask model, SysUser sysUser) {
        //先要扣除用户的金钱
        //创建任务
        //增加用户的金币变化
        UserMoneyHistory userMoneyHistory=new UserMoneyHistory();
        userMoneyHistory.setCreateTime(new Date());
        userMoneyHistory.setMoneyBefore(sysUser.getMoney());
        userMoneyHistory.setUserId(sysUser.getId());
        sysUser.setMoney(sysUser.getMoney()==null?0:sysUser.getMoney()-model.getTotalPrice());
        sysUserMapper.updateByPrimaryKey(sysUser);
        taobaoTaskMapper.insertUseGeneratedKeys(model);
        //插入收支历史记录
        userMoneyHistory.setMoneyAfter(sysUser.getMoney());
        userMoneyHistory.setMoneyAdd(userMoneyHistory.getMoneyAfter()-userMoneyHistory.getMoneyBefore());
        userMoneyHistory.setType(UserMoneyHistory.taskpub);
        userMoneyHistory.setDescription("发布任务");
        userMoneyHistoryMapper.insertUseGeneratedKeys(userMoneyHistory);

        return model.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo<TaobaoTask> findPage(Integer pageNum, Integer pageSize, int state) {
        Example example = new Example(TaobaoTask.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", state);
        //倒序
        example.orderBy("createTime").desc();
        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<TaobaoTask> list = this.selectByExample(example);

        return new PageInfo<>(list);
    }

}

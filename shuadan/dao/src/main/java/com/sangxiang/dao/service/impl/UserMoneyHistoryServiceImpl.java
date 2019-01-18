package com.sangxiang.dao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sangxiang.base.service.impl.BaseServiceImpl;
import com.sangxiang.dao.mapper.TaskMapper;
import com.sangxiang.dao.mapper.UserMoneyHistoryMapper;
import com.sangxiang.dao.mapper.UserTaskMapper;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.model.Task;
import com.sangxiang.dao.model.UserMoneyHistory;
import com.sangxiang.dao.model.UserTask;
import com.sangxiang.dao.service.UserMoneyHistoryService;
import com.sangxiang.dao.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class UserMoneyHistoryServiceImpl extends BaseServiceImpl<UserMoneyHistory>  implements UserMoneyHistoryService {

    @Autowired
    UserMoneyHistoryMapper userMoneyHistoryMapper;

    @Override
    public PageInfo<UserMoneyHistory> findPage(Integer pageNum, Integer pageSize, int userId) {
        Example example = new Example(UserMoneyHistory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        //倒序
        example.orderBy("createTime").desc();

        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<UserMoneyHistory> list = this.selectByExample(example);
        return new PageInfo<>(list);
    }
}

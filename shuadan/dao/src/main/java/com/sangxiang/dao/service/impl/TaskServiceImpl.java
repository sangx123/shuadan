package com.sangxiang.dao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sangxiang.base.service.impl.BaseServiceImpl;
import com.sangxiang.dao.mapper.SysRoleMapper;
import com.sangxiang.dao.mapper.TaskMapper;
import com.sangxiang.dao.model.SysRole;
import com.sangxiang.dao.model.Task;
import com.sangxiang.dao.service.SysRoleService;
import com.sangxiang.dao.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TaskServiceImpl extends BaseServiceImpl<Task> implements TaskService {
    @Autowired
    TaskMapper taskMapper;

    @Override
    public void createTask(Task task) {
        taskMapper.insertUseGeneratedKeys(task);
    }
    @Transactional(readOnly = true)
    @Override
    public PageInfo<Task> findPage(Integer pageNum, Integer pageSize, int state) {
        Example example = new Example(Task.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("state", state);
        //倒序
        example.orderBy("createTime").desc();

        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Task> logList = this.selectByExample(example);

        return new PageInfo<>(logList);
    }


}

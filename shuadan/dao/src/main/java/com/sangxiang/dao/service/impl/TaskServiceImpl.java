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
    public int createTask(Task task) {
         taskMapper.insertUseGeneratedKeys(task);
         return task.getId();
    }

    @Override
    public Task queryById(int id) {
        return taskMapper.queryById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo<Task> findPage(Integer pageNum, Integer pageSize, int state) {
//        Example example = new Example(Task.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("state", state);
//
//        //倒序
//        example.orderBy("createTime").desc();

        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Task> list = taskMapper.getEnableTask();
        return new PageInfo<>(list);
    }

    @Override
    public List<Task> getShanhuTask(int userid, int status) {
        return taskMapper.getShanhuTask(userid,status);
    }

    @Override
    public PageInfo<Task> findUserPublishTaskList(Integer pageNumber, Integer pageSize, Integer state, int userId) {
        PageHelper.startPage(pageNumber,pageSize);
        List<Task> list = taskMapper.getUserPublishTask(userId,state);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Task> findAllUserPublishTaskList(Integer pageNumber, Integer pageSize, int userId) {
        PageHelper.startPage(pageNumber,pageSize);
        List<Task> list = taskMapper.getAllUserPublishTask(userId);
        return new PageInfo<>(list);
    }


}

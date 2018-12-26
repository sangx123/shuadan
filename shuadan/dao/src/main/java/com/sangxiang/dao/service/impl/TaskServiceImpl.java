package com.sangxiang.dao.service.impl;

import com.sangxiang.base.service.impl.BaseServiceImpl;
import com.sangxiang.dao.mapper.SysRoleMapper;
import com.sangxiang.dao.mapper.TaskMapper;
import com.sangxiang.dao.model.SysRole;
import com.sangxiang.dao.model.Task;
import com.sangxiang.dao.service.SysRoleService;
import com.sangxiang.dao.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends BaseServiceImpl<Task> implements TaskService {
    @Autowired
    TaskMapper taskMapper;

    @Override
    public void createTask(Task task) {
        taskMapper.insertUseGeneratedKeys(task);
    }
}

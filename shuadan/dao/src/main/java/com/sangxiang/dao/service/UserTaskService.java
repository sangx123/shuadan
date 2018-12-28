package com.sangxiang.dao.service;

import com.github.pagehelper.PageInfo;
import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.base.service.BaseService;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.model.Task;
import com.sangxiang.dao.model.UserTask;

public interface UserTaskService extends BaseService<UserTask> {
    void createUserTask(Task task,SysUser user);
    PageInfo<Task> getUserTask(int userid, int pageNum, int pageSize);
}
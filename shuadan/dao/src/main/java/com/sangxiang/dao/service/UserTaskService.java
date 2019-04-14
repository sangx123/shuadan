package com.sangxiang.dao.service;

import com.github.pagehelper.PageInfo;
import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.base.service.BaseService;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.model.Task;
import com.sangxiang.dao.model.UserTask;

import java.util.List;

public interface UserTaskService extends BaseService<UserTask> {
    void createUserTask(Task task,SysUser user);
    void createUserTask(Task task,int userId);
    PageInfo<Task> getUserTask(int userid,int state,int pageNum, int pageSize);
    List<Task> getUserTask(int userid, int status);
    UserTask hasApplyTask(int userId, int id);
}
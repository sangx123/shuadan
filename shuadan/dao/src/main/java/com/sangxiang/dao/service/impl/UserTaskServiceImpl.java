package com.sangxiang.dao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sangxiang.base.service.BaseService;
import com.sangxiang.base.service.impl.BaseServiceImpl;
import com.sangxiang.dao.mapper.TaskMapper;
import com.sangxiang.dao.mapper.UserTaskMapper;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.model.Task;
import com.sangxiang.dao.model.UserTask;
import com.sangxiang.dao.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserTaskServiceImpl extends BaseServiceImpl<UserTask>  implements UserTaskService {

    @Autowired
    UserTaskMapper userTaskMapper;

    @Autowired
    TaskMapper taskMapper;

    @Transactional
    @Override
    public void createUserTask(Task task, SysUser user) {
        task.setWorkerNum(task.getWorkerNum()-1);
        taskMapper.updateByPrimaryKey(task);
        UserTask userTask=new UserTask();
        userTask.setTaskid(task.getId());
        userTask.setUserid(user.getId());
        userTask.setState(0);
        userTaskMapper.insertUseGeneratedKeys(userTask);
    }
    @Transactional(readOnly = true)
    @Override
    public PageInfo<Task> getUserTask(int userid,int pageNum,int pageSize) {
        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Task> list = userTaskMapper.queryUserTask(userid);
       return new PageInfo<>(list);
    }
}

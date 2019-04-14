package com.sangxiang.dao.service;

import com.github.pagehelper.PageInfo;
import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.base.service.BaseService;
import com.sangxiang.dao.model.Task;

import java.util.List;

public interface TaskService extends BaseService<Task> {
    /**
     * 创建任务
     * @param
     */
    int createTask(Task task);


    /**
     *
     * @param id
     * @return
     */
    Task queryById(int id);
    /**
     * 分页查询任务列表
     * @param pageNum
     * @param pageSize
     * @param state
     * @return
     */
    PageInfo<Task> findPage(Integer pageNum, Integer pageSize,int state);
    List<Task> getShanhuTask(int userid, int status);

    PageInfo<Task> findUserPublishTaskList(Integer pageNumber, Integer pageSize, Integer state, int userId);

    PageInfo<Task> findAllUserPublishTaskList(Integer pageNumber, Integer pageSize, int userId);
}
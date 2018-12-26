package com.sangxiang.dao.service;

import com.github.pagehelper.PageInfo;
import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.base.service.BaseService;
import com.sangxiang.dao.model.Task;

public interface TaskService extends BaseService<Task> {
    /**
     * 创建任务
     * @param
     */
    void createTask(Task task);

    /**
     * 分页查询任务列表
     * @param pageNum
     * @param pageSize
     * @param state
     * @return
     */
    PageInfo<Task> findPage(Integer pageNum, Integer pageSize,int state);

}
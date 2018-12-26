package com.sangxiang.dao.service;

import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.base.service.BaseService;
import com.sangxiang.dao.model.Task;

public interface TaskService extends BaseService<Task> {
    /**
     * 创建任务
     * @param
     */
    void createTask(Task task);
}
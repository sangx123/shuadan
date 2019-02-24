package com.sangxiang.dao.service;

import com.github.pagehelper.PageInfo;
import com.sangxiang.base.service.BaseService;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.model.TaobaoTask;

import java.util.List;

public interface TaobaoTaskService extends BaseService<TaobaoTask> {

    /**
     * 创建淘宝任务
     * @param
     * @param userModel
     */
    int createTaobaoTask(TaobaoTask task, SysUser userModel);

    /**
     * 分页查询任务列表
     * @param pageNum
     * @param pageSize
     * @param state
     * @return
     */
    PageInfo<TaobaoTask> findPage(Integer pageNum, Integer pageSize, int state);

}

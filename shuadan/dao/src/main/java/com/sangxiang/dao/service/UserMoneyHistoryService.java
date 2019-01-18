package com.sangxiang.dao.service;

import com.github.pagehelper.PageInfo;
import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.base.service.BaseService;
import com.sangxiang.dao.model.UserMoneyHistory;

public interface UserMoneyHistoryService extends BaseService<UserMoneyHistory> {
    /**
     * 分页查询任务列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<UserMoneyHistory> findPage(Integer pageNum, Integer pageSize,int userId);
}
package com.sangxiang.dao.mapper;

import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.dao.model.Task;
import com.sangxiang.dao.model.UserTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserTaskMapper extends MyMapper<UserTask> {
    List<Task> queryUserTask(@Param("userid")int userid,@Param("state")int state);
}
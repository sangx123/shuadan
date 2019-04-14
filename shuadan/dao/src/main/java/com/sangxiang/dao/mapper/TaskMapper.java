package com.sangxiang.dao.mapper;

import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.dao.model.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper extends MyMapper<Task> {
   Task queryById(int id);
   List<Task> getShanhuTask(@Param("userid")int userid, @Param("state")int state);

   List<Task> getEnableTask();
   List<Task> getUserPublishTask(@Param("userid")int userid, @Param("state")int state);
   List<Task> getAllUserPublishTask(@Param("userid")int userid);
}
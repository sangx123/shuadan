package com.sangxiang.dao.mapper;

import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.dao.model.UserAlipayTixian;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserAlipayTixianMapper extends MyMapper<UserAlipayTixian> {
    List<UserAlipayTixian> getTixianFromDay(@Param("userId")int userId, @Param("startDate")Date startDate, @Param("endDate")Date endDate);
    List<UserAlipayTixian> getTixianByUser(@Param("userId")int userId,@Param("status") int status);
}
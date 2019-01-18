package com.sangxiang.dao.model;

import com.sangxiang.base.model.BaseEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class UserAlipayTixian extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Float userTixianMoney;

    private String userTixianName;

    private String userTixianAccount;

    private Date userTixianTime;

    private Float userTixianLeftMoney;

    private Integer userTixianStatus;

    private String userTixianDescription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getUserTixianMoney() {
        return userTixianMoney;
    }

    public void setUserTixianMoney(Float userTixianMoney) {
        this.userTixianMoney = userTixianMoney;
    }

    public String getUserTixianName() {
        return userTixianName;
    }

    public void setUserTixianName(String userTixianName) {
        this.userTixianName = userTixianName == null ? null : userTixianName.trim();
    }

    public String getUserTixianAccount() {
        return userTixianAccount;
    }

    public void setUserTixianAccount(String userTixianAccount) {
        this.userTixianAccount = userTixianAccount == null ? null : userTixianAccount.trim();
    }

    public Date getUserTixianTime() {
        return userTixianTime;
    }

    public void setUserTixianTime(Date userTixianTime) {
        this.userTixianTime = userTixianTime;
    }

    public Float getUserTixianLeftMoney() {
        return userTixianLeftMoney;
    }

    public void setUserTixianLeftMoney(Float userTixianLeftMoney) {
        this.userTixianLeftMoney = userTixianLeftMoney;
    }

    public Integer getUserTixianStatus() {
        return userTixianStatus;
    }

    public void setUserTixianStatus(Integer userTixianStatus) {
        this.userTixianStatus = userTixianStatus;
    }

    public String getUserTixianDescription() {
        return userTixianDescription;
    }

    public void setUserTixianDescription(String userTixianDescription) {
        this.userTixianDescription = userTixianDescription == null ? null : userTixianDescription.trim();
    }
}
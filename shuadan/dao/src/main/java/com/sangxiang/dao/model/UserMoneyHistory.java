package com.sangxiang.dao.model;

import com.sangxiang.base.model.BaseEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

public class UserMoneyHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer type;

    private Float moneyBefore;

    private Float moneyAfter;

    private Date createTime;

    private String description;

    private Float moneyAdd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Float getMoneyBefore() {
        return moneyBefore;
    }

    public void setMoneyBefore(Float moneyBefore) {
        this.moneyBefore = moneyBefore;
    }

    public Float getMoneyAfter() {
        return moneyAfter;
    }

    public void setMoneyAfter(Float moneyAfter) {
        this.moneyAfter = moneyAfter;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Float getMoneyAdd() {
        return moneyAdd;
    }

    public void setMoneyAdd(Float moneyAdd) {
        this.moneyAdd = moneyAdd;
    }

    @Transient
    public static int chongzhi=1;

    @Transient
    public static int tixian=2;

    @Transient
    public static int taskget=3;

    @Transient
    public static int taskpub=4;

    private Integer userId;
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
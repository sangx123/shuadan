package com.sangxiang.dao.model;

import com.sangxiang.base.model.BaseEntity;

import javax.persistence.Id;

public class SysPermission  extends BaseEntity {
    @Id
    private Integer id;

    private String permission;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }
}
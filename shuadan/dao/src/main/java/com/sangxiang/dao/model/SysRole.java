package com.sangxiang.dao.model;

import com.sangxiang.base.model.BaseEntity;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

public class SysRole extends BaseEntity {
    @Id
    private Integer id;

    private String role;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
    @Transient
    private List<SysPermission> permissionList;

    public List<SysPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<SysPermission> permissionList) {
        this.permissionList = permissionList;
    }
}
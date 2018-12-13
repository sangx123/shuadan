package com.sangxiang.base.model;

import lombok.Data;

import javax.persistence.Column;

/**
 * 基础信息
 *
 */
@Data
public abstract class BaseEntity implements java.io.Serializable{
    @Column(name = "org_id")
    private Long orgId;
}

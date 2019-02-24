package com.sangxiang.dao.model;

import com.sangxiang.base.model.BaseEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class TaobaoTask extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userid;

    private Date createTime;

    private String sousuoguanjianzi;

    private String zhangguiming;

    private String shangpinlianjie;

    private Float shangpinjiage;

    private Integer shuadanshuliang;

    private Float shuadanyongjin;

    private String renqunbiaoqian;

    private Boolean jialiao;

    private Boolean liulanqita;

    private Boolean tingliushichang;

    private Boolean daituhaoping;

    private Boolean huobisanjia;

    private Boolean zhenshiqianshou;

    private Float totalPrice;

    private Boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSousuoguanjianzi() {
        return sousuoguanjianzi;
    }

    public void setSousuoguanjianzi(String sousuoguanjianzi) {
        this.sousuoguanjianzi = sousuoguanjianzi == null ? null : sousuoguanjianzi.trim();
    }

    public String getZhangguiming() {
        return zhangguiming;
    }

    public void setZhangguiming(String zhangguiming) {
        this.zhangguiming = zhangguiming == null ? null : zhangguiming.trim();
    }

    public String getShangpinlianjie() {
        return shangpinlianjie;
    }

    public void setShangpinlianjie(String shangpinlianjie) {
        this.shangpinlianjie = shangpinlianjie == null ? null : shangpinlianjie.trim();
    }

    public Float getShangpinjiage() {
        return shangpinjiage;
    }

    public void setShangpinjiage(Float shangpinjiage) {
        this.shangpinjiage = shangpinjiage;
    }

    public Integer getShuadanshuliang() {
        return shuadanshuliang;
    }

    public void setShuadanshuliang(Integer shuadanshuliang) {
        this.shuadanshuliang = shuadanshuliang;
    }

    public Float getShuadanyongjin() {
        return shuadanyongjin;
    }

    public void setShuadanyongjin(Float shuadanyongjin) {
        this.shuadanyongjin = shuadanyongjin;
    }

    public String getRenqunbiaoqian() {
        return renqunbiaoqian;
    }

    public void setRenqunbiaoqian(String renqunbiaoqian) {
        this.renqunbiaoqian = renqunbiaoqian == null ? null : renqunbiaoqian.trim();
    }

    public Boolean getJialiao() {
        return jialiao;
    }

    public void setJialiao(Boolean jialiao) {
        this.jialiao = jialiao;
    }

    public Boolean getLiulanqita() {
        return liulanqita;
    }

    public void setLiulanqita(Boolean liulanqita) {
        this.liulanqita = liulanqita;
    }

    public Boolean getTingliushichang() {
        return tingliushichang;
    }

    public void setTingliushichang(Boolean tingliushichang) {
        this.tingliushichang = tingliushichang;
    }

    public Boolean getDaituhaoping() {
        return daituhaoping;
    }

    public void setDaituhaoping(Boolean daituhaoping) {
        this.daituhaoping = daituhaoping;
    }

    public Boolean getHuobisanjia() {
        return huobisanjia;
    }

    public void setHuobisanjia(Boolean huobisanjia) {
        this.huobisanjia = huobisanjia;
    }

    public Boolean getZhenshiqianshou() {
        return zhenshiqianshou;
    }

    public void setZhenshiqianshou(Boolean zhenshiqianshou) {
        this.zhenshiqianshou = zhenshiqianshou;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
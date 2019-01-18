package com.sangxiang.model.UserCenter;

public class UserOverView {
    //用户余额
    private Float money;
    //发布中的任务数量
    private int pubTaskNum;
    //接手中的任务数量
    private int getTaskNum;

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public int getPubTaskNum() {
        return pubTaskNum;
    }

    public void setPubTaskNum(int pubTaskNum) {
        this.pubTaskNum = pubTaskNum;
    }

    public int getGetTaskNum() {
        return getTaskNum;
    }

    public void setGetTaskNum(int getTaskNum) {
        this.getTaskNum = getTaskNum;
    }

    public int getTixianTaskNum() {
        return tixianTaskNum;
    }

    public void setTixianTaskNum(int tixianTaskNum) {
        this.tixianTaskNum = tixianTaskNum;
    }

    //提现中的任务数量
    private int tixianTaskNum;
}

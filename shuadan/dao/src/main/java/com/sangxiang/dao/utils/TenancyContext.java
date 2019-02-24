package com.sangxiang.dao.utils;

/**
 * 这是一个单例，使用静态内部类实现。
 */
public class TenancyContext {

    private ThreadLocal<Long> orgId = new ThreadLocal<>();

    private TenancyContext() { }

    private static class TenancyThreadMapInstance {
        private static final TenancyContext instance = new TenancyContext();
    }

    public static TenancyContext getInstance() {
        return TenancyThreadMapInstance.instance;
    }

    public long getOrgId() {
        return orgId.get() == null ? 0L : orgId.get().longValue();
    }

    public void setOrgId(Long v) {
        orgId.set(v);
    }
}

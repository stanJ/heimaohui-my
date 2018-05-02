package com.xa3ti.blackcat.base.util.cache;


public class CacheFactory {
    private static final ExpiredCache<VeriCode> expiredcache = new ExpiredCache<VeriCode>(50*1000);

    public static ExpiredCache<VeriCode> getExpiredcache() {
        return expiredcache;
    }
}
package com.xa3ti.blackcat.base.util.cache;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

public class ExpiredCache<V> {

    private ConcurrentMap<String, V> map;

    private ConcurrentMap<String, Long[]> expireMap;

    private int capacity;

    public ExpiredCache(int capacity) {
        this.capacity = capacity;
        this.map = new ConcurrentLinkedHashMap.Builder<String, V>().maximumWeightedCapacity(capacity).build();
        this.expireMap = new ConcurrentLinkedHashMap.Builder<String, Long[]>().maximumWeightedCapacity(capacity)
                .build();
    }

    public V get(String k) {
        Long[] expire = expireMap.get(k);
        if (expire != null && expire.length == 2) {
            if (expire[1] * 1000 < (getNowTime() - expire[0])) {
                remove(k);
            }
        } else {
            remove(k);
        }
        return map.get(k);
    }

    public int size() {
        return this.map.size();
    }

    public void put(String k, V v, long expireSecond) {
        Long[] expire = new Long[] { getNowTime(), expireSecond };
        this.map.put(k, v);
        this.expireMap.put(k, expire);

    }

    public V remove(String k) {
        this.expireMap.remove(k);
        return this.map.remove(k);
    }

    public void clear() {
        this.map.clear();
        this.expireMap.clear();
    }

    public boolean containsKey(String k) {
        return get(k) != null;
    }

    public Set<String> keySet() {
        return this.map.keySet();
    }

    private long getNowTime() {
        return System.currentTimeMillis();
    }

    public int getCapacity() {
        return this.capacity;
    }
}

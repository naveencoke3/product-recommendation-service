package com.naveen.sample.productrecommendationservice.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheStrategyImpl<K, V> implements CacheStrategy<K, V> {
    private final Map<K, V> map;

    public CacheStrategyImpl(int capacity) {
        this.map = new LinkedHashMap<>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public synchronized V get(K key) { return map.get(key); }

    @Override
    public synchronized void put(K key, V value) { map.put(key, value); }
}

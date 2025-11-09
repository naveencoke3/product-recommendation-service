package com.naveen.sample.productrecommendationservice.cache;

public interface CacheStrategy<K,V> {
    V get(K key);
    void put(K key, V value);
}

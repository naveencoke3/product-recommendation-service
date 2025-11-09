package com.naveen.sample.productrecommendationservice.cache;

public class KeyBasedLRUCache<K, V> extends CacheStrategyImpl<K,V> {

    public KeyBasedLRUCache(int capacity) {
        super(capacity);
    }
}

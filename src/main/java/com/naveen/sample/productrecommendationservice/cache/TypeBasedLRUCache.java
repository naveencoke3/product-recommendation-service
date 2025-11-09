package com.naveen.sample.productrecommendationservice.cache;

public class TypeBasedLRUCache<K, V> extends CacheStrategyImpl<K,V> {

    public TypeBasedLRUCache(int capacity) {
        super(capacity);
    }
}

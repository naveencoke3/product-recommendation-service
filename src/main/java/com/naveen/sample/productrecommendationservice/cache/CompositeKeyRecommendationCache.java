package com.naveen.sample.productrecommendationservice.cache;

public class CompositeKeyRecommendationCache<K,V> extends CacheStrategyImpl<K,V> {

    public CompositeKeyRecommendationCache(int capacity) {
        super(capacity);
    }
}

package com.naveen.sample.productrecommendationservice.config;

import com.naveen.sample.productrecommendationservice.cache.CacheStrategy;
import com.naveen.sample.productrecommendationservice.cache.CompositeKeyRecommendationCache;
import com.naveen.sample.productrecommendationservice.cache.KeyBasedLRUCache;
import com.naveen.sample.productrecommendationservice.cache.TypeBasedLRUCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {
    @Value("${app.cache.strategy:KEY}")
    private String strategy;

    @Value("${app.cache.capacity:100}")
    private int capacity;

    @Bean(name = "productCache")
    public CacheStrategy<String, Object> productCache() {
        return strategy.equalsIgnoreCase("TYPE") ? new TypeBasedLRUCache<>(capacity) : new KeyBasedLRUCache<>(capacity);
    }

    @Bean(name = "recommendationCache")
    public CacheStrategy<String, Object> recommendationCache() {
        return new CompositeKeyRecommendationCache<>(capacity);
    }
}

package com.naveen.sample.productrecommendationservice.cache;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class TypeBasedLRUCacheTest {

    @Test
    void shouldEvictWhenCapacityExceeded() {
        TypeBasedLRUCache<String, Integer> cache = new TypeBasedLRUCache<>(2);
        cache.put("TYPE::toy::1", 1);
        cache.put("TYPE::toy::2", 2);
        cache.put("TYPE::toy::3", 3);

        // oldest toy should be evicted
        assertThat(cache.get("TYPE::toy::1")).isNull();
        assertThat(cache.get("TYPE::toy::2")).isNotNull();
    }
}

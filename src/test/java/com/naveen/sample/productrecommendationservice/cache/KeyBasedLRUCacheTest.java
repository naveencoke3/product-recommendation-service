package com.naveen.sample.productrecommendationservice.cache;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class KeyBasedLRUCacheTest {
    @Test
    void shouldEvictOldestWhenCapacityExceeded() {
        KeyBasedLRUCache<String, Integer> cache = new KeyBasedLRUCache<>(2);
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);

        assertThat(cache.get("A")).isNull(); // evicted
        assertThat(cache.get("B")).isEqualTo(2);
        assertThat(cache.get("C")).isEqualTo(3);
    }

    @Test
    void shouldReturnValueIfPresent() {
        KeyBasedLRUCache<String, Integer> cache = new KeyBasedLRUCache<>(2);
        cache.put("X", 10);
        assertThat(cache.get("X")).isEqualTo(10);
    }
}

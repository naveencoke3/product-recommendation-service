package com.naveen.sample.productrecommendationservice.service;

import com.naveen.sample.productrecommendationservice.cache.CacheStrategy;
import com.naveen.sample.productrecommendationservice.dto.RecommendationFilter;
import com.naveen.sample.productrecommendationservice.model.Product;
import com.naveen.sample.productrecommendationservice.repository.ProductRepository;
import com.naveen.sample.productrecommendationservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock(name = "productCache")
    private CacheStrategy<String, Object> productCache;

    @Mock(name = "recommendationCache")
    private CacheStrategy<String, Object> recommendationCache;

    private ProductServiceImpl service;

    private Product toyCar;

    @BeforeEach
    void setup() {
        toyCar = new Product("1", "Toy Car", "toy", "kids", 500, "5-10", Map.of());
        service = new ProductServiceImpl(productRepository, productCache, recommendationCache);
        Mockito.reset(productRepository, productCache, recommendationCache);
    }

    @Test
    void getProductById_shouldReturnCachedProductIfPresent() {
        when(productCache.get("1")).thenReturn(toyCar);
        Product p = service.getProductById("1");

        assertThat(p).isEqualTo(toyCar);
        verify(productRepository, never()).findById(any());
    }

    @Test
    void getProductById_shouldFetchAndCacheWhenNotPresent() {
        when(productCache.get("1")).thenReturn(null);
        when(productRepository.findById("1")).thenReturn(Optional.of(toyCar));

        Product p = service.getProductById("1");

        assertThat(p).isEqualTo(toyCar);
        verify(productCache).put(eq("1"), any());
    }

    @Test
    void getProductsByType_shouldReturnCachedListIfPresent() {
        when(productCache.get("TYPE::toy")).thenReturn(List.of(toyCar));

        List<Product> result = service.getProductsByType("toy");

        assertThat(result).containsExactly(toyCar);
        verify(productRepository, never()).findByType("toy");
    }

    @Test
    void getProductsByType_shouldFetchAndCacheWhenMissing() {
        when(productCache.get("TYPE::toy")).thenReturn(null);
        when(productRepository.findByType("toy")).thenReturn(List.of(toyCar));

        List<Product> result = service.getProductsByType("toy");

        assertThat(result).containsExactly(toyCar);
        verify(productCache).put("TYPE::toy", List.of(toyCar));
    }

    @Test
    void recommend_shouldFilterByPriceAndAge() {
        Product expensive = new Product("2", "Drone", "toy", "kids", 10000, "12-18", Map.of());
        when(productRepository.findAll()).thenReturn(List.of(toyCar, expensive));
        when(recommendationCache.get(any())).thenReturn(null);

        RecommendationFilter filter = new RecommendationFilter(0L, 1000L, "toy", null, 8);
        List<Product> result = service.recommend(filter);

        assertThat(result).containsExactly(toyCar);
        verify(recommendationCache).put(anyString(), eq(result));
    }
}

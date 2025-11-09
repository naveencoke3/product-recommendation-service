package com.naveen.sample.productrecommendationservice.service.impl;

import com.naveen.sample.productrecommendationservice.cache.CacheStrategy;
import com.naveen.sample.productrecommendationservice.dto.RecommendationFilter;
import com.naveen.sample.productrecommendationservice.model.Product;
import com.naveen.sample.productrecommendationservice.repository.ProductRepository;
import com.naveen.sample.productrecommendationservice.service.ProductService;
import com.naveen.sample.productrecommendationservice.util.AgeRangeParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CacheStrategy<String, Object> productCache; // injected via config/factory
    private final CacheStrategy<String, Object> recommendationCache; // composite-key

    @Override
    public Product getProductById(String id) {
        Product fromCache = (Product) productCache.get(id);
        if (fromCache != null) return fromCache;
        Product p = productRepository.findById(id).orElse(null);
        if (p != null) productCache.put(id, p);
        return p;
    }

    @Override
    public List<Product> getProductsByType(String type) {
        String key = "TYPE::" + type;
        List<Product> fromCache = (List<Product>) productCache.get(key);
        if (fromCache != null) return fromCache;
        List<Product> products = productRepository.findByType(type);
        productCache.put(key, products);
        return products;
    }

    @Override
    public List<Product> recommend(RecommendationFilter filter) {
        String compositeKey = buildKey(filter);
        List<Product> cached = (List<Product>) recommendationCache.get(compositeKey);
        if (cached != null) return cached;

        List<Product> candidates = productRepository.findAll().stream()
                .filter(p -> applyPriceFilter(p, filter))
                .filter(p -> applyOptional(p.type(), filter.type()))
                .filter(p -> applyOptional(p.category(), filter.category()))
                .filter(p -> applyAgeFilter(p.recommendedAgeGroup(), filter.age()))
                .collect(Collectors.toList());

        recommendationCache.put(compositeKey, candidates);
        return candidates;
    }

    private boolean applyPriceFilter(Product p, RecommendationFilter f) {
        if (f.minPrice() != null && p.price() < f.minPrice()) return false;
        if (f.maxPrice() != null && p.price() > f.maxPrice()) return false;
        return true;
    }

    private boolean applyOptional(String value, String expected) {
        return expected == null || expected.isBlank() || Objects.equals(value, expected);
    }

    private boolean applyAgeFilter(String range, Integer age) {
        if (age == null) return true;
        return AgeRangeParser.ageInRange(range, age);
    }

    private String buildKey(RecommendationFilter f) {
        return String.format("min=%s|max=%s|type=%s|cat=%s|age=%s",
                f.minPrice(), f.maxPrice(), f.type(), f.category(), f.age());
    }
}

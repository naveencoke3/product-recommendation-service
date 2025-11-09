package com.naveen.sample.productrecommendationservice.dto;

public record RecommendationFilter(
        Long minPrice,
        Long maxPrice,
        String type,
        String category,
        Integer age
) {}

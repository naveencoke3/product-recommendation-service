package com.naveen.sample.productrecommendationservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("products")
public record Product(
        @Id String id,
        String name,
        String type,
        String category,
        double price,
        String recommendedAgeGroup,
        Map<String, String> attributes
) {}
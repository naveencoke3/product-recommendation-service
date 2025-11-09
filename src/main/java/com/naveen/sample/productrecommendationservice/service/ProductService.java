package com.naveen.sample.productrecommendationservice.service;

import com.naveen.sample.productrecommendationservice.dto.RecommendationFilter;
import com.naveen.sample.productrecommendationservice.model.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(String id);
    List<Product> getProductsByType(String type);
    List<Product> recommend(RecommendationFilter filter);
}

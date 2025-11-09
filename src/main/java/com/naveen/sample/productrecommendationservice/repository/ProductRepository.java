package com.naveen.sample.productrecommendationservice.repository;

import com.naveen.sample.productrecommendationservice.model.Product;

import java.util.List;

public interface ProductRepository extends IMongoRepository<Product> {
    List<Product> findByType(String type);
}

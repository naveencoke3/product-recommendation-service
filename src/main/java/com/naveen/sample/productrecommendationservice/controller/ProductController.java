package com.naveen.sample.productrecommendationservice.controller;

import com.naveen.sample.productrecommendationservice.dto.RecommendationFilter;
import com.naveen.sample.productrecommendationservice.model.Product;
import com.naveen.sample.productrecommendationservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Product>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(productService.getProductsByType(type));
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<Product>> recommend(
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer age
    ) {
        RecommendationFilter filter = new RecommendationFilter(minPrice, maxPrice, type, category, age);
        return ResponseEntity.ok(productService.recommend(filter));
    }
}

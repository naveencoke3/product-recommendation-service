package com.naveen.sample.productrecommendationservice.integration;

import com.naveen.sample.productrecommendationservice.model.Product;
import com.naveen.sample.productrecommendationservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
class ProductControllerIntegrationTest {

    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:7.0.2");

    @DynamicPropertySource
    static void setMongoUri(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
        productRepository.save(new Product(
                "P1001", "Apple iPhone 16", "ELECTRONICS",
                "SMARTPHONE", 99900, "18-45", Map.of("color", "Black", "storage", "128GB")
        ));
        productRepository.save(new Product(
                "P2001", "LEGO Classic Set", "TOY",
                "BLOCKS", 4999, "5-12", Map.of("pieces", "1200")
        ));
    }

    @Test
    void testGetProductById() throws Exception {
        mockMvc.perform(get("/api/products/P1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apple iPhone 16"));
    }

    @Test
    void testGetByType() throws Exception {
        mockMvc.perform(get("/api/products/type/ELECTRONICS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("P1001"));
    }

    @Test
    void testRecommendations() throws Exception {
        mockMvc.perform(get("/api/products/recommendations")
                        .param("minPrice", "4000")
                        .param("maxPrice", "10000")
                        .param("type", "TOY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("P2001"));
    }
}

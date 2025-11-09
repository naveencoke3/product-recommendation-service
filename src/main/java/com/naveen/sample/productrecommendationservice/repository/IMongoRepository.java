package com.naveen.sample.productrecommendationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IMongoRepository<T> extends MongoRepository<T, String> {
}

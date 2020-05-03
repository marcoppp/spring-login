package com.example.demo2.repository;

import com.example.demo2.model.*;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {}

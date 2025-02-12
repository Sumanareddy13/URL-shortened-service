package com.example.URL.Shortener.Service.repository;

import com.example.URL.Shortener.Service.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<Url, String> {
    Optional<Url> findByLongUrl(String longUrl);
}

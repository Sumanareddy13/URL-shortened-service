package com.example.URL.Shortener.Service.repository;

import com.example.URL.Shortener.Service.model.Url;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    @BeforeEach
    void setUp() {
        urlRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        urlRepository.deleteAll();
    }

    @Test
    void testSaveAndFindByLongUrl() {
        String shortUrl = "abcd1234";
        String longUrl = "https://example.com";
        Url url = new Url(shortUrl, longUrl, Instant.now(), Instant.now().plusSeconds(60 * 60 * 24 * 30));

        urlRepository.save(url);

        Optional<Url> retrievedUrl = urlRepository.findByLongUrl(longUrl);

        assertTrue(retrievedUrl.isPresent());
        assertEquals(shortUrl, retrievedUrl.get().getId());
        assertEquals(longUrl, retrievedUrl.get().getLongUrl());
    }

    @Test
    void testFindById() {
        String shortUrl = "abcd1234";
        String longUrl = "https://example.com";
        Url url = new Url(shortUrl, longUrl, Instant.now(), Instant.now().plusSeconds(60 * 60 * 24 * 30));

        urlRepository.save(url);

        Optional<Url> retrievedUrl = urlRepository.findById(shortUrl);

        assertTrue(retrievedUrl.isPresent());
        assertEquals(longUrl, retrievedUrl.get().getLongUrl());
    }

    @Test
    void testFindById_NotFound() {
        Optional<Url> retrievedUrl = urlRepository.findById("nonexistent");

        assertFalse(retrievedUrl.isPresent());
    }
}

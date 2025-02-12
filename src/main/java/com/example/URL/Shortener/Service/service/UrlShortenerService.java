package com.example.URL.Shortener.Service.service;

import com.example.URL.Shortener.Service.model.Url;
import com.example.URL.Shortener.Service.repository.UrlRepository;
import org.springframework.util.DigestUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UrlShortenerService {

    private final UrlRepository repository;

    public UrlShortenerService(UrlRepository repository) {
        this.repository = repository;
    }

    public String shortenUrl(String longUrl) {
        // Check if the URL already exists in the repository
        Optional<Url> existingMapping = repository.findByLongUrl(longUrl);
        if (existingMapping.isPresent()) {
            return existingMapping.get().getId();
        }

        // Generate a short URL using MD5 hash of the long URL
        String shortUrlKey = DigestUtils.md5DigestAsHex(longUrl.getBytes()).substring(0, 8);

        // Create the URL object and save to repository
        Url url = new Url(shortUrlKey, longUrl, Instant.now(), Instant.now().plusSeconds(60 * 60 * 24 * 30));
        repository.save(url);

        return shortUrlKey;
    }

    public String getLongUrl(String shortUrlKey) {
        return repository.findById(shortUrlKey)
                .map(Url::getLongUrl)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));
    }

}

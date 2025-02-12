package com.example.URL.Shortener.Service.controller;

import com.example.URL.Shortener.Service.service.UrlShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService){
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody Map<String, String> requestBody) {
        String longUrl = requestBody.get("longUrl");
        return urlShortenerService.shortenUrl(longUrl);
    }


    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl){
        String longUrl = urlShortenerService.getLongUrl(shortUrl);
        return ResponseEntity.status(302).location(URI.create(longUrl)).build();
    }
}

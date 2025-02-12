package com.example.URL.Shortener.Service.controller;

import com.example.URL.Shortener.Service.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.FOUND;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerControllerTest {

    @Mock
    private UrlShortenerService urlShortenerService;

    @InjectMocks
    private UrlShortenerController urlShortenerController;

    @Test
    void testShortenUrl() {
        String longUrl = "https://example.com";
        String shortUrl = "abcd1234";

        when(urlShortenerService.shortenUrl(longUrl)).thenReturn(shortUrl);

        String result = urlShortenerController.shortenUrl(Map.of("longUrl", longUrl));

        assertEquals(shortUrl, result);
    }

    @Test
    void testRedirectToLongUrl_Success() {
        String shortUrl = "abcd1234";
        String longUrl = "https://example.com";

        when(urlShortenerService.getLongUrl(shortUrl)).thenReturn(longUrl);

        ResponseEntity<Void> response = urlShortenerController.redirectToLongUrl(shortUrl);

        assertEquals(FOUND, response.getStatusCode());
        assertEquals(URI.create(longUrl), response.getHeaders().getLocation());
    }

    @Test
    void testRedirectToLongUrl_NotFound() {
        String shortUrl = "unknown";

        when(urlShortenerService.getLongUrl(shortUrl)).thenThrow(new RuntimeException("Short URL not found"));

        assertThrows(RuntimeException.class, () -> urlShortenerController.redirectToLongUrl(shortUrl));
    }
}
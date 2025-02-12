package com.example.URL.Shortener.Service.service;

import com.example.URL.Shortener.Service.model.Url;
import com.example.URL.Shortener.Service.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.DigestUtils;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlShortenerService urlShortenerService;

    @Test
    void testShortenUrl() {
        String longUrl = "https://example.com";
        String expectedShortUrl = DigestUtils.md5DigestAsHex(longUrl.getBytes()).substring(0, 8);

        when(urlRepository.findByLongUrl(longUrl)).thenReturn(Optional.empty());
        when(urlRepository.save(any(Url.class))).thenReturn(new Url(expectedShortUrl, longUrl, Instant.now(), Instant.now().plusSeconds(60 * 60 * 24 * 30)));

        String result = urlShortenerService.shortenUrl(longUrl);
        assertNotNull(result);
        assertEquals(expectedShortUrl, result);
    }

    @Test
    void testShortenUrl_AlreadyExists() {
        String longUrl = "https://example.com";
        String expectedShortUrl = DigestUtils.md5DigestAsHex(longUrl.getBytes()).substring(0, 8);
        Url existingUrl = new Url(expectedShortUrl, longUrl, Instant.now(), Instant.now().plusSeconds(60 * 60 * 24 * 30));

        when(urlRepository.findByLongUrl(longUrl)).thenReturn(Optional.of(existingUrl));

        String result = urlShortenerService.shortenUrl(longUrl);
        assertEquals(expectedShortUrl, result);
    }

    @Test
    void testGetLongUrl_Success() {
        String shortUrl = "7f4c9195";
        String longUrl = "https://example.com";
        Url url = new Url(shortUrl, longUrl, Instant.now(), Instant.now().plusSeconds(60 * 60 * 24 * 30));

        when(urlRepository.findById(shortUrl)).thenReturn(Optional.of(url));

        String result = urlShortenerService.getLongUrl(shortUrl);
        assertEquals(longUrl, result);
    }

    @Test
    void testGetLongUrl_NotFound() {
        String shortUrl = "unknown";

        when(urlRepository.findById(shortUrl)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> urlShortenerService.getLongUrl(shortUrl));
        assertEquals("Short URL not found", exception.getMessage());
    }
}
package com.example.URL.Shortener.Service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "urls")
public class Url {

    @Id
    private String id;

    private String longUrl;
    private Instant createdDate;
    private Instant expiryDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Url(String id, String longUrl, Instant createdDate, Instant expiryDate) {
        this.id = id;
        this.longUrl = longUrl;
        this.createdDate = createdDate;
        this.expiryDate = expiryDate;
    }

    public Url() {
    }
}

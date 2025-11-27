package com.urlshortner.services;

public interface CacheService {
    String getURL(String s);

    void cacheURL(String shortenUrl, String s);

    void deleteURL(String byIdOnlyShortUrl);
}

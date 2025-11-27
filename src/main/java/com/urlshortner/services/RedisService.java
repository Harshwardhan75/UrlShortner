package com.urlshortner.services;

public interface RedisService {
    void cacheURL(String shortURL,String url);
    String getURL(String shortURL);
    void deleteURL(String shortURL);
}

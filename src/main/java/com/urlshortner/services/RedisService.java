package com.urlshortner.services;

import com.urlshortner.entity.URL;

public interface RedisService {
    void cacheURL(URL url);
    URL getURL(String shortURL);
    void deleteURL(String shortURL);
}

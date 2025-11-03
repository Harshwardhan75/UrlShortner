package com.urlshortner.services.implementation;

import com.urlshortner.cache.UrlCache;
import com.urlshortner.entity.URL;
import com.urlshortner.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImplementation implements RedisService {

    @Autowired
    private UrlCache urlCache;

    @Override
    public void cacheURL(URL url) {
        urlCache.cacheURL(url);
    }

    @Override
    public URL getURL(String shortURL) {
        return urlCache.getURL(shortURL);
    }

    @Override
    public void deleteURL(String shortURL) {
        urlCache.deleteURL(shortURL);
    }
}

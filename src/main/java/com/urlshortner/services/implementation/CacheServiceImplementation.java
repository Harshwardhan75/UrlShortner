package com.urlshortner.services.implementation;

import com.urlshortner.services.CacheService;
import com.urlshortner.services.LeastRecentlyUsedService;
import com.urlshortner.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImplementation implements CacheService {

    @Autowired
    private LeastRecentlyUsedService lru;

    @Autowired
    private RedisService redisService;

    @Override
    public String getURL(String shortURL) {
        String s = lru.get(shortURL);
        if(s!=null && !s.isEmpty())
            return s;

        s = redisService.getURL(shortURL);
        if(s!=null && !s.isEmpty())
            return s;

        return null;
    }

    @Override
    public void cacheURL(String shortUrl, String longURL) {
        lru.add(shortUrl,longURL);
        redisService.cacheURL(shortUrl,longURL);
    }

    @Override
    public void deleteURL(String shortURL) {
        lru.delete(shortURL);
        redisService.deleteURL(shortURL);
    }
}

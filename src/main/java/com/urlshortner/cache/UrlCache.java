package com.urlshortner.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urlshortner.entity.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class UrlCache {

    private final String KEY = "URL";
    @Autowired
    private RedisTemplate<String, URL> redisTemplate;

    //  SAVE URL
    public void cacheURL(URL url) {
//        System.out.println("Cache Stored "+ url.getShortenUrl());
        redisTemplate.opsForHash().put(KEY, url.getShortenUrl(), url);
        redisTemplate.expire(KEY, 5, TimeUnit.MINUTES);
    }

    //  GET URL
    public URL getURL(String shortUrl) {
//        System.out.println("Cache Hit For: "+ shortUrl);
//        long start = System.currentTimeMillis();
        URL url = (URL) redisTemplate.opsForHash().get(KEY, shortUrl);
//        System.out.println("Redis get took: " + (System.currentTimeMillis() - start) + " ms");
//        System.out.println(url);
        return url;
    }

//    DELETE URL
    public void deleteURL(String shortUrl){
        redisTemplate.opsForHash().delete(KEY,shortUrl);
    }
}

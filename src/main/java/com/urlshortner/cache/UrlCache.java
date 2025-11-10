package com.urlshortner.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
        try {
            redisTemplate.opsForHash().put(KEY, url.getShortenUrl(), url);
            redisTemplate.expire(KEY, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            System.out.println("Exception ******************"+e.toString());
        }
    }

    //  GET URL
    public URL getURL(String shortUrl) {
//        System.out.println("Cache Hit For: "+ shortUrl);
//        long start = System.currentTimeMillis();
        try {
            URL url = (URL) redisTemplate.opsForHash().get(KEY,shortUrl);
            return url;
        }catch (Exception e){
            System.out.println("Error Aya hain ************************************");
            System.out.println(e.toString());
            return null;
        }
//        System.out.println("Redis get took: " + (System.currentTimeMillis() - start) + " ms");
//        System.out.println(url);
//        return url;
    }

    //    DELETE URL
    public void deleteURL(String shortUrl) {
        try {
            redisTemplate.opsForHash().delete(KEY, shortUrl);
        } catch (Exception e) {
            System.out.println("Exception ******************* "+e.toString());
        }
    }
}

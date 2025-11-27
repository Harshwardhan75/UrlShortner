package com.urlshortner.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.urlshortner.entity.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class UrlCache {

    private final String KEY = "URL";
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //  SAVE URL
    public void cacheURL(String shortURL,String url) {
        try {
            redisTemplate.opsForHash().put(KEY, shortURL, url);
            redisTemplate.expire(KEY, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            logger.info("Error: {}",e.getMessage());
        }
    }

    //  GET URL
    public String getURL(String shortUrl) {
        try {
//            System.out.println("Redis Cache Hit");
            String url = (String) redisTemplate.opsForHash().get(KEY,shortUrl);
            return url;
        }catch (Exception e){
            logger.info("Error: {}",e.getMessage());
            return null;
        }
    }

    //    DELETE URL
    public void deleteURL(String shortUrl) {
        try {
            redisTemplate.opsForHash().delete(KEY, shortUrl);
        } catch (Exception e) {
            logger.info("Error: {}",e.getMessage());
        }
    }
}

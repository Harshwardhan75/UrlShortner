package com.urlshortner.services.implementation;

import com.urlshortner.entity.URL;
import com.urlshortner.entity.User;
import com.urlshortner.repository.URLRepository;
import com.urlshortner.services.AnalyticsService;
import com.urlshortner.services.RedisService;
import com.urlshortner.services.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;

@Slf4j
@Service
public class UrlServiceImplementation implements UrlService {

    @Autowired
    private URLRepository urlRepository;

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private RedisService redisService;

    @Override
    public URL addURL(String urlString, User user) {
        URL url = new URL();
        url.setOriginalUrl(urlString);
        url.setActive(true);
        url.setUser(user);
        url.setExpiredAt(LocalDateTime.now().plusHours(1));
        return urlRepository.save(url);
    }

    @Override
    public URL updateShortUrl(URL url, String shortUrl) {
        url.setShortenUrl(shortUrl);
        url = this.urlRepository.save(url);
        return url;
    }

    @Override
    public boolean updateURL(int urlId){
        urlRepository.incrementUserCountByURLId(urlId);
        return true;
    }

    @Override
    public URL generateURL(String urlString, User user){
        URL url = this.addURL(urlString,user);
        Base64.Encoder encoder = Base64.getUrlEncoder();
        String encode = encoder.encodeToString((url.getUrlId() + " " + user.getUserId()).getBytes());
        String shortUrl = "/shorten/"+encode;
        url = this.updateShortUrl(url,shortUrl);
        return url;
    }

    @Override
    public String originalURL(String shortURL, HttpServletRequest request){
//        System.out.println("Harshwardhan:  "+shortURL);
        Base64.Decoder decoder = Base64.getUrlDecoder();
        byte[] decode = decoder.decode(shortURL);
        String[] urlArr = new String(decode).split(" ");
        URL url = urlRepository.findById(Integer.parseInt(urlArr[0])).get();
        if(!url.isActive()){
            return null;
        }

        redisService.cacheURL(url);

        String agent = request.getHeader("User-Agent");
        boolean status = analyticsService.addAnalytics(url,agent);
//        System.out.println("Analytics Status***********: "+status);
        String result = url.getOriginalUrl();
        return result;
    }

    @Override
    public URL getUrl(int urlId) {
        return this.urlRepository.findById(urlId).get();
    }

    @Override
    public boolean updateActive(Integer urlId, int value) {
        this.urlRepository.updateIsActive(urlId,value==0?false:true);
        if(value == 0){ //false hai redis cache se bhi nikalo bhai isse agar ho toh
            redisService.deleteURL(urlRepository.getByIdOnlyShortUrl(urlId));
        }
        return true;
    }
}

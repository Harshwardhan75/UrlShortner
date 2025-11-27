package com.urlshortner.controller;

import com.urlshortner.entity.URL;
import com.urlshortner.services.AnalyticsService;
import com.urlshortner.services.CacheService;
import com.urlshortner.services.RedisService;
import com.urlshortner.services.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shorten")
@CrossOrigin(origins = "*")
public class AccessController {

    @Autowired
    private UrlService urlService;

//    @Autowired
//    private RedisService redisService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private AnalyticsService analyticsService;


    @GetMapping("/{shortURL}")
//    @ResponseBody
    public String redirectToOriginalURL(@PathVariable String shortURL, HttpServletRequest request) {
        String url = cacheService.getURL("/shorten/" + shortURL);

        if (url != null && !url.isEmpty()) {
            String[] s = url.split("& &");
            String originalurl = "https://"+s[0];
            String agent = request.getHeader("User-Agent");
            boolean status = analyticsService.addAnalytics(s[1], agent);
            return "redirect:" + originalurl;
        }
        String originalURL = urlService.originalURL(shortURL, request);
        originalURL = "https://" + originalURL;
        return "redirect:" + originalURL;
    }

}

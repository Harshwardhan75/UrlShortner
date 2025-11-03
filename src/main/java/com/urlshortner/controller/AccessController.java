package com.urlshortner.controller;

import com.urlshortner.entity.URL;
import com.urlshortner.services.AnalyticsService;
import com.urlshortner.services.RedisService;
import com.urlshortner.services.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shorten")
@CrossOrigin(origins = "*")
public class AccessController {

    @Autowired
    private UrlService urlService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AnalyticsService analyticsService;


    @GetMapping("/{shortURL}")
    public String redirectToOriginalURL(@PathVariable String shortURL, HttpServletRequest request) {

        URL url = redisService.getURL("/shorten/" + shortURL);

        if (url != null) {
            String originalurl = "https://" + url.getOriginalUrl();
//            Currently direct DB update afterwards replace it with kafka
            String agent = request.getHeader("User-Agent");
            boolean status = analyticsService.addAnalytics(url, agent);
            return "redirect:" + originalurl;
        }

        String originalURL = urlService.originalURL(shortURL, request);
        originalURL = "https://" + originalURL;
        return "redirect:" + originalURL;
    }

}

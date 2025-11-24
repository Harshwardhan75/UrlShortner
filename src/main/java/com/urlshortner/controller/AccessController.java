package com.urlshortner.controller;

import com.urlshortner.entity.URL;
import com.urlshortner.services.AnalyticsService;
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

    @Autowired
    private RedisService redisService;

    @Autowired
    private AnalyticsService analyticsService;


    @GetMapping("/{shortURL}")
//    @ResponseBody
    public String redirectToOriginalURL(@PathVariable String shortURL, HttpServletRequest request) {
        long start = System.currentTimeMillis();
//        System.out.println("Inside Access Controller");
        URL url = redisService.getURL("/shorten/" + shortURL);

        if (url != null) {
            String originalurl = "https://" + url.getOriginalUrl();
            System.out.println("Cache Hit");
            String agent = request.getHeader("User-Agent");
            boolean status = analyticsService.addAnalytics(url, agent);
//            System.out.println("Cache Hit Under: "+(System.currentTimeMillis()-start));
            return "redirect:" + originalurl;
        }
//        System.out.println("Not a Cache Hit");
        String originalURL = urlService.originalURL(shortURL, request);
        originalURL = "https://" + originalURL;
        return "redirect:" + originalURL;
    }

}

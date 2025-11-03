package com.urlshortner.services;

import com.urlshortner.entity.URL;
import com.urlshortner.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UrlService {

    URL addURL(String url, User user);

    URL updateShortUrl(URL url, String shortUrl);

    boolean updateURL(int urlId);

    URL generateURL(String urlString, User user);

    String originalURL(String shortURL, HttpServletRequest request);

    URL getUrl(int urlId);

    boolean updateActive(Integer urlId, int value);
}

package com.urlshortner.services;

public interface LeastRecentlyUsedService {
    boolean add(String shortURL,String longURL);
    String get(String shortURL);

    void delete(String shortURL);
}

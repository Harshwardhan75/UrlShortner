package com.urlshortner.services;

import com.urlshortner.entity.Analytics;
import com.urlshortner.entity.URL;
import com.urlshortner.entity.User;

import java.util.List;

public interface UserService {

    boolean checkExist(String email);

    URL generateShortURL(String url, String email);

    User getUser(String name);

    List<Analytics> getAnalytics(int urlId, String email);

    boolean updateUser(String userId, String userName, String password);

    boolean updateUrlActive(Integer urlId,int value);
}

package com.urlshortner.services.implementation;

import com.urlshortner.entity.Analytics;
import com.urlshortner.entity.URL;
import com.urlshortner.entity.User;
import com.urlshortner.exception.UnAuthorizedUrlAnalyticsAccessException;
import com.urlshortner.repository.UserRepository;
import com.urlshortner.services.AnalyticsService;
import com.urlshortner.services.UrlService;
import com.urlshortner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UrlService urlService;

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean checkExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public URL generateShortURL(String urlString, String email) {
        User user = this.userRepository.findByEmail(email).get();
        return urlService.generateURL(urlString,user);
    }

    @Override
    public User getUser(String name) {
        return userRepository.findByEmail(name).get();
    }

    @Override
    public List<Analytics> getAnalytics(int urlId, String email){
        User user = this.getUser(email);
        URL url = urlService.getUrl(urlId);
        if(user.getUserId()!=url.getUser().getUserId()){
            throw new UnAuthorizedUrlAnalyticsAccessException("This URL doesn't Belongs to your Account");
        }
        return analyticsService.getAnalytics(urlId);
    }

    @Override
    public boolean updateUser(String userId, String userName, String password) {
        try {
            this.userRepository.updateUser(userId, userName, passwordEncoder.encode(password));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateUrlActive(Integer urlId,int value) {
        return urlService.updateActive(urlId,value);
    }
}

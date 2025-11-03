package com.urlshortner.services;

import com.urlshortner.entity.Analytics;
import com.urlshortner.entity.URL;

import java.util.List;

public interface AnalyticsService {

    boolean addAnalytics(URL url,String device);

    List<Analytics> getAnalytics(int urlId);

    Analytics saveAnalytics(Analytics analytics);
}

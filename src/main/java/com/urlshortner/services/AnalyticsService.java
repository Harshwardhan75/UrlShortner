package com.urlshortner.services;

import com.urlshortner.entity.Analytics;
import com.urlshortner.entity.URL;

import java.util.List;

public interface AnalyticsService {

    boolean addAnalytics(String urlId,String device);

    List<Analytics> getAnalytics(int urlId);

    Analytics saveAnalytics(Analytics analytics);

    void batchUpdate(List<Analytics> logs);
}

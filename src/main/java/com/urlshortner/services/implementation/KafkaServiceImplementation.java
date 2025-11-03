package com.urlshortner.services.implementation;

import com.urlshortner.entity.Analytics;
import com.urlshortner.entity.URL;
import com.urlshortner.services.AnalyticsService;
import com.urlshortner.services.KafkaService;
import com.urlshortner.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImplementation implements KafkaService {

    @Autowired
    private KafkaTemplate<String,Analytics> kafkaTemplate;

//    @Value("${topicName}")
    private final String topicName = "TOPIC-URL";

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private UrlService urlService;

    @Override
    public boolean push(Analytics analytics) {
        try{
            this.kafkaTemplate.send(topicName,analytics);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @KafkaListener(topics = topicName, groupId = "GROUP-1")
    @Override
    public void update(Analytics analytics){
        URL url = analytics.getUrl();
        analytics = analyticsService.saveAnalytics(analytics);
        urlService.updateURL(analytics.getUrl().getUrlId());
    }
}

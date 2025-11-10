package com.urlshortner.services.implementation;

import com.urlshortner.entity.Analytics;
import com.urlshortner.services.AnalyticsService;
import com.urlshortner.services.KafkaService;
import com.urlshortner.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaServiceImplementation implements KafkaService {

    //    @Value("${topicName}")
    private final String topicName = "TOPIC-URL";
    private final List<Analytics> logs = new ArrayList<>();
    @Autowired
    private KafkaTemplate<String, Analytics> kafkaTemplate;
    @Autowired
    private AnalyticsService analyticsService;
    @Autowired
    private UrlService urlService;

    @Override
    public boolean push(Analytics analytics) {
        try {
            kafkaTemplate.send(topicName, analytics)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            System.out.println("Message failed to produce: " + ex.getMessage());
                        } else {
                            System.out.println("Message produced successfully: " + analytics);
                        }
                    });
            return true;
        } catch (Exception e) {
            System.out.println("Kafka Push Error: "+e.toString());
            return false;
        }
    }

    @KafkaListener(topics = topicName, groupId = "GROUP-1")
    @Override
    public void update(Analytics analytics) {
//        URL url = analytics.getUrl();
//        analytics = analyticsService.saveAnalytics(analytics);
//        urlService.updateURL(analytics.getUrl().getUrlId());
        logs.add(analytics);
        System.out.println(logs);
    }

    @Scheduled(fixedDelay = 60000)
    public void update() {
        synchronized (logs) {
            if(!logs.isEmpty())
                analyticsService.batchUpdate(logs);
            logs.clear();
        }
    }
}

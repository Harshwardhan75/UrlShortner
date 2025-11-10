package com.urlshortner.services.implementation;

import com.urlshortner.entity.Analytics;
import com.urlshortner.entity.URL;
import com.urlshortner.repository.AnalyticsRepository;
import com.urlshortner.services.AnalyticsService;
import com.urlshortner.services.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnalyticsServiceImplementation implements AnalyticsService {

    @Autowired
    private AnalyticsRepository analyticsRepository;

    @Autowired
    @Lazy
    private KafkaService kafkaService;

    @Override
    public boolean addAnalytics(URL url, String agent) {
        String device = generateDevice(agent);
        Analytics analytics = new Analytics();
        analytics.setUrl(url);
        analytics.setDevice(device);
        analytics.setAccessTime(LocalDateTime.now());

        boolean status = kafkaService.push(analytics);
        System.out.println("Kafka Producer Status***********: "+status);
        return true;
    }

    @Override
    public List<Analytics> getAnalytics(int urlId) {
        List<Analytics> list = analyticsRepository.findAllByUrlId(urlId);
        return list;
    }

    @Override
    public Analytics saveAnalytics(Analytics analytics){
        return this.analyticsRepository.save(analytics);
    }

    @Override
    public void batchUpdate(List<Analytics> logs) {
        analyticsRepository.saveAll(logs);
    }

    private String generateDevice(String agent) {
        String device = "unknown";

        if (agent != null) {
            agent = agent.toLowerCase();

            if (agent.contains("windows")) {
                device = "Windows PC";
            } else if (agent.contains("macintosh") || agent.contains("mac os")) {
                device = "Mac";
            } else if (agent.contains("iphone")) {
                device = "iPhone";
            } else if (agent.contains("ipad") || agent.contains("tablet")) {
                device = "Tablet";
            } else if (agent.contains("android")) {
                if (agent.contains("mobile")) {
                    device = "Android Mobile";
                } else {
                    device = "Android Tablet";
                }
            } else if (agent.contains("linux")) {
                device = "Linux";
            }
        }
        return device;
    }
}

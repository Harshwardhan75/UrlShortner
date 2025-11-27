package com.urlshortner.services.implementation;

import com.urlshortner.entity.Analytics;
import com.urlshortner.services.AnalyticsService;
import com.urlshortner.services.KafkaService;
import com.urlshortner.services.UrlService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.Acknowledgment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaServiceImplementation implements KafkaService {

    //    @Value("${topicName}")
    private final String topicName = "TOPIC-URL";
    private final List<Analytics> logs = new ArrayList<>();
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private AnalyticsService analyticsService;
    @Autowired
    private UrlService urlService;

    @Override
    public boolean push(String analytics) {
        try {
            kafkaTemplate.send(topicName, analytics);
            return true;
        } catch (Exception e) {
//            System.out.println("Kafka Push Error: "+e.toString());
            return false;
        }
    }

    @KafkaListener(topics = topicName, groupId = "GROUP-1", containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void update(String messages) {
        try {
                String[] s = messages.split("& &");
                Analytics analytics1 = new Analytics();
                //        System.out.println(analytics);
                analytics1.setAccessTime(LocalDateTime.parse(s[0]));
                analytics1.setDevice(s[1]);
                analytics1.setUrlId(Integer.parseInt(s[2]));

                logs.add(analytics1);
        } catch (Exception e) {

        }
    }

    @Scheduled(fixedDelay = 60000*5)
    public void update() {
        synchronized (logs){
            if(!logs.isEmpty())
                analyticsService.batchUpdate(logs);
            logs.clear();
        }
    }
}

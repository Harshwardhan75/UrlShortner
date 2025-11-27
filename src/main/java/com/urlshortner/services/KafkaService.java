package com.urlshortner.services;

import com.urlshortner.entity.Analytics;
import org.springframework.kafka.annotation.KafkaListener;

public interface KafkaService {

    boolean push(String analytics);

    @KafkaListener()
    void update(String analytics);
}

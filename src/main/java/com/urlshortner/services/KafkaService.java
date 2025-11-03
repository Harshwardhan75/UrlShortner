package com.urlshortner.services;

import com.urlshortner.entity.Analytics;
import com.urlshortner.entity.URL;
import org.springframework.kafka.annotation.KafkaListener;

public interface KafkaService {

    public boolean push(Analytics analytics);


    @KafkaListener()
    void update(Analytics analytics);
}

//package com.urlshortner.components;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
//import org.springframework.stereotype.Component;
//
////@Component
//public class KafkaListenerStartUp {
//    @Autowired
//    private KafkaListenerEndpointRegistry registry;
//
//    @PostConstruct
//    public void startKafkaListeners() {
//        registry.getListenerContainers().forEach(container -> {
//            try {
//                if (!container.isRunning()) {
//                    container.start();
//                    System.out.println("✅ Kafka Listener started: " + container.getListenerId());
//                }
//            } catch (Exception e) {
//                System.err.println("❌ Failed to start Kafka listener: " + container.getListenerId());
//                e.printStackTrace();
//            }
//        });
//    }
//}

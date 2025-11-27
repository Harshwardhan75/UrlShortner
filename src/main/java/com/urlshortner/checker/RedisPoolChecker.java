//package com.urlshortner.checker;
//
//import jakarta.annotation.PostConstruct;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class RedisPoolChecker {
//
//    @Autowired
//    private GenericObjectPoolConfig<?> poolConfig;
//
//    @PostConstruct
//    public void printRedisPool() {
//        System.out.println("Redis pool max-active: " + poolConfig.getMaxTotal());
//        System.out.println("Redis pool max-idle: " + poolConfig.getMaxIdle());
//        System.out.println("Redis pool min-idle: " + poolConfig.getMinIdle());
//    }
//}

//package com.urlshortner.checker;
//
//import org.apache.catalina.connector.Connector;
//import org.apache.catalina.core.StandardThreadExecutor;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RedisPoolConfig {
//
//    @Value("${spring.data.redis.lettuce.pool.max-active}")
//    private Integer MAX_TOTAL;
//    @Value("${spring.data.redis.lettuce.pool.max-idle}")
//    private Integer MAX_IDLE;
//    @Value("${spring.data.redis.lettuce.pool.min-idle}")
//    private Integer MIN_IDLE;
//
//
//
//    @Bean(name = "myRedisPoolConfig")
//    public GenericObjectPoolConfig<?> redisPoolConfig() {
//        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
//        poolConfig.setMaxTotal(MAX_TOTAL);
//        poolConfig.setMaxIdle(MAX_IDLE);
//        poolConfig.setMinIdle(MIN_IDLE);
//        return poolConfig;
//    }
//
//    @Bean
//    public TomcatServletWebServerFactory tomcatFactory() {
//        return new TomcatServletWebServerFactory() {
//            @Override
//            protected void customizeConnector(Connector connector) {
//                super.customizeConnector(connector);
//
//                StandardThreadExecutor executor = new StandardThreadExecutor();
//                executor.setNamePrefix("tomcat-exec-");
//                executor.setMaxThreads(1000);   // your desired max
//                executor.setMinSpareThreads(50);
//                executor.setMaxQueueSize(2000); // accept-count queue
//
//                connector.getProtocolHandler().setExecutor(executor);
//            }
//        };
//    }
//}
//package com.urlshortner.checker;
//
//import com.zaxxer.hikari.HikariDataSource;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class DbPoolChecker {
//
//    @Autowired
//    private HikariDataSource hikariDataSource;
//
//    @PostConstruct
//    public void printDbPool() {
//        System.out.println("Hikari max pool size: " + hikariDataSource.getMaximumPoolSize());
//        System.out.println("Active connections: " + hikariDataSource.getHikariPoolMXBean().getActiveConnections());
//        System.out.println("Idle connections: " + hikariDataSource.getHikariPoolMXBean().getIdleConnections());
//        System.out.println("Total connections: " + hikariDataSource.getHikariPoolMXBean().getTotalConnections());
//    }
//}

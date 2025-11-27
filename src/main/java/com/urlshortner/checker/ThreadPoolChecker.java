//package com.urlshortner.checker;
//
//import jakarta.annotation.PostConstruct;
//import org.apache.catalina.connector.Connector;
//import org.apache.catalina.startup.Tomcat;
//import org.apache.coyote.AbstractProtocol;
//import org.apache.tomcat.util.threads.ThreadPoolExecutor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
//import org.springframework.boot.web.server.WebServer;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.Executor;
//
//@Component
//public class ThreadPoolChecker {
//
//    @Autowired
//    private org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext webServerAppCtxt;
//
//    @PostConstruct
//    public void printActiveThreads() {
//        WebServer webServer = webServerAppCtxt.getWebServer();
//        if (webServer instanceof TomcatWebServer tomcatWebServer) {
//            Connector connector = tomcatWebServer.getTomcat().getConnector();
//
//            ThreadPoolExecutor executor = (ThreadPoolExecutor) connector.getProtocolHandler().getExecutor();
//
//            System.out.println("Core Threads: " + executor.getCorePoolSize());
//            System.out.println("Current Pool Size: " + executor.getPoolSize());
//            System.out.println("Active Threads: " + executor.getActiveCount());
//            System.out.println("Largest Pool Size: " + executor.getLargestPoolSize());
//        }
//    }
//
//    @Scheduled(fixedRate = 2000)
//    public void printTomcatThreads() {
//        WebServer webServer = webServerAppCtxt.getWebServer();
//        if (webServer instanceof TomcatWebServer tomcatWebServer) {
//            Connector connector = tomcatWebServer.getTomcat().getConnector();
//            Executor executor = ((AbstractProtocol<?>) connector.getProtocolHandler()).getExecutor();
//            if (executor instanceof ThreadPoolExecutor tpe) {
//                System.out.println("Active Threads: " + tpe.getActiveCount());
//                System.out.println("Current Pool Size: " + tpe.getPoolSize());
//                System.out.println("Largest Pool Size: " + tpe.getLargestPoolSize());
//            }
//        }
//    }
//
//}

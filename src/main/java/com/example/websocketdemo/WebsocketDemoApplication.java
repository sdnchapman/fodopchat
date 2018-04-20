package com.example.websocketdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;
 
@SpringBootApplication
public class WebsocketDemoApplication extends SpringBootServletInitializer {
 
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebsocketDemoApplication.class);
    }
 
    public static void main(String[] args) {
        SpringApplication.run(WebsocketDemoApplication.class, args);
    }
}
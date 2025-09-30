package com.sise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.TimeZone;

@Configuration
public class TimeZoneConfig implements WebMvcConfigurer {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Lima"));
        System.setProperty("user.timezone", "America/Lima");
    }

    @Bean
    public ZoneId zoneId() {
        return ZoneId.of("America/Lima");
    }
}

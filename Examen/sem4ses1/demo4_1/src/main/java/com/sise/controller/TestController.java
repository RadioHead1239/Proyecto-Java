package com.sise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
public class TestController {

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        LocalDateTime now = LocalDateTime.now();
        ZoneId zoneId = ZoneId.of("America/Lima");
        String formattedTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        return String.format("Test funcionando - Usuario autenticado correctamente<br>" +
                           "Fecha y hora actual (Perú): %s<br>" +
                           "Zona horaria: %s", 
                           formattedTime, zoneId.toString());
    }
}

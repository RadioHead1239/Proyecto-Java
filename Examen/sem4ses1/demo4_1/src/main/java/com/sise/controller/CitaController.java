package com.sise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CitaController {

    @GetMapping("/cita")
    public String citas() {
        return "views/cita";
    }
}

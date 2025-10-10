package com.sise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CitaController {

    @GetMapping("/cita")
    public String citas(Model model) {
        return "views/cita";
    }
}
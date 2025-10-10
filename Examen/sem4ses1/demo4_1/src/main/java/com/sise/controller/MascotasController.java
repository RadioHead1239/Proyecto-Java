package com.sise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MascotasController {

    @GetMapping("/mascota")
    public String mascotas(Model model) {
        return "views/mascota";
    }
}
package com.sise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VentaController {

    @GetMapping("/venta")
    public String ventas() {
        return "views/venta";
    }
}

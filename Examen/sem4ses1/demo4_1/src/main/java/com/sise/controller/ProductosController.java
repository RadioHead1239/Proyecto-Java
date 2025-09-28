package com.sise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductosController {

    @GetMapping("/producto")
    public String productos() {
        return "views/producto";
    }
}

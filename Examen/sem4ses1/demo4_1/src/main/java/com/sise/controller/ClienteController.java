package com.sise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteController {

    @GetMapping("/cliente")
    public String clientes() {
        return "views/cliente";
    }
}

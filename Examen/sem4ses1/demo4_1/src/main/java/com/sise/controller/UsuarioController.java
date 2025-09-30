package com.sise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    @GetMapping("/usuario")
    public String usuarios(Model model) {
        return "views/usuario";
    }
}

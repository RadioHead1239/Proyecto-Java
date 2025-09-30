package com.sise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sise.dto.LoginDTO;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "views/login"; 
    }
}

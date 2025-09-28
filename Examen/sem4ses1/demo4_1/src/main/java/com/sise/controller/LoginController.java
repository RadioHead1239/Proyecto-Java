package com.sise.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sise.dto.LoginDTO;
import com.sise.dto.UsuarioDTO;
import com.sise.iservice.IUsuarioService;

@Controller
public class LoginController {

    private final IUsuarioService usuarioService;

    public LoginController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "views/login"; 
    }

    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO, Model model) {
        Optional<UsuarioDTO> usuarioOpt = usuarioService.login(loginDTO.getCorreo(), loginDTO.getPassword());

        if (usuarioOpt.isPresent()) {
            UsuarioDTO usuario = usuarioOpt.get();
            model.addAttribute("usuario", usuario);

            return "redirect:/dashboard";  
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "views/login";
        }
    }
}

package com.sise.web.controller;

import com.sise.model.Usuario;
import com.sise.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UsuarioViewController {

    private final UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        model.addAttribute("roles", Usuario.Rol.values());
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuario());
        }
        return "usuarios/list";
    }

    @PostMapping("/usuarios")
    public String crear(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result,
            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.usuario", result);
            redirect.addFlashAttribute("usuario", usuario);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "createUsuarioModal");
            return "redirect:/usuarios";
        }
        usuarioService.save(usuario);
        redirect.addFlashAttribute("success", "Usuario registrado correctamente");
        return "redirect:/usuarios";
    }

    @PutMapping("/usuarios/{id}")
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.usuario", result);
            redirect.addFlashAttribute("usuario", usuario);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "editUsuarioModal");
            return "redirect:/usuarios";
        }
        usuario.setId(id);
        usuarioService.save(usuario);
        redirect.addFlashAttribute("success", "Usuario actualizado correctamente");
        return "redirect:/usuarios";
    }

    @DeleteMapping("/usuarios/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        usuarioService.delete(id);
        redirect.addFlashAttribute("success", "Usuario eliminado");
        return "redirect:/usuarios";
    }
}

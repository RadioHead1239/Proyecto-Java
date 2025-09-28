package com.sise.web.controller;

import com.sise.model.Servicio;
import com.sise.service.ServicioService;
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
public class ServicioViewController {

    private final ServicioService servicioService;

    @GetMapping("/servicios")
    public String listar(Model model) {
        model.addAttribute("servicios", servicioService.findAll());
        if (!model.containsAttribute("servicio")) {
            model.addAttribute("servicio", new Servicio());
        }
        return "servicios/list";
    }

    @PostMapping("/servicios")
    public String crear(@Valid @ModelAttribute("servicio") Servicio servicio, BindingResult result,
            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.servicio", result);
            redirect.addFlashAttribute("servicio", servicio);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "createServicioModal");
            return "redirect:/servicios";
        }
        servicioService.save(servicio);
        redirect.addFlashAttribute("success", "Servicio registrado correctamente");
        return "redirect:/servicios";
    }

    @PutMapping("/servicios/{id}")
    public String actualizar(@PathVariable Integer id, @Valid @ModelAttribute("servicio") Servicio servicio,
            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.servicio", result);
            redirect.addFlashAttribute("servicio", servicio);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "editServicioModal");
            return "redirect:/servicios";
        }
        servicio.setId(id);
        servicioService.save(servicio);
        redirect.addFlashAttribute("success", "Servicio actualizado correctamente");
        return "redirect:/servicios";
    }

    @DeleteMapping("/servicios/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        servicioService.delete(id);
        redirect.addFlashAttribute("success", "Servicio eliminado");
        return "redirect:/servicios";
    }
}

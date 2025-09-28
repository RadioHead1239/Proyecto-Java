package com.sise.web.controller;

import com.sise.model.Cliente;
import com.sise.model.Mascota;
import com.sise.service.ClienteService;
import com.sise.service.MascotaService;
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
public class MascotaViewController {

    private final MascotaService mascotaService;
    private final ClienteService clienteService;

    @GetMapping("/mascotas")
    public String listar(Model model) {
        model.addAttribute("mascotas", mascotaService.findAll());
        model.addAttribute("clientes", clienteService.findAll());
        if (!model.containsAttribute("mascota")) {
            Mascota mascota = new Mascota();
            mascota.setCliente(new Cliente());
            model.addAttribute("mascota", mascota);
        }
        model.addAttribute("especies", Mascota.Especie.values());
        return "mascotas/list";
    }

    @PostMapping("/mascotas")
    public String crear(@Valid @ModelAttribute("mascota") Mascota mascota, BindingResult result,
            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.mascota", result);
            redirect.addFlashAttribute("mascota", mascota);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "createMascotaModal");
            return "redirect:/mascotas";
        }
        mascotaService.save(mascota);
        redirect.addFlashAttribute("success", "Mascota registrada correctamente");
        return "redirect:/mascotas";
    }

    @PutMapping("/mascotas/{id}")
    public String actualizar(@PathVariable Integer id, @Valid @ModelAttribute("mascota") Mascota mascota,
            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.mascota", result);
            redirect.addFlashAttribute("mascota", mascota);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "editMascotaModal");
            return "redirect:/mascotas";
        }
        mascota.setId(id);
        mascotaService.save(mascota);
        redirect.addFlashAttribute("success", "Mascota actualizada correctamente");
        return "redirect:/mascotas";
    }

    @DeleteMapping("/mascotas/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        mascotaService.delete(id);
        redirect.addFlashAttribute("success", "Mascota eliminada");
        return "redirect:/mascotas";
    }
}

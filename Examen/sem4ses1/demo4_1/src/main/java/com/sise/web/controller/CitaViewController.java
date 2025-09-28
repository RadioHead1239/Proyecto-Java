package com.sise.web.controller;

import com.sise.model.Cita;
import com.sise.model.Cliente;
import com.sise.model.Mascota;
import com.sise.model.Servicio;
import com.sise.model.Usuario;
import com.sise.service.CitaService;
import com.sise.service.ClienteService;
import com.sise.service.MascotaService;
import com.sise.service.ServicioService;
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
public class CitaViewController {

    private final CitaService citaService;
    private final ClienteService clienteService;
    private final MascotaService mascotaService;
    private final ServicioService servicioService;
    private final UsuarioService usuarioService;

    @GetMapping("/citas")
    public String listar(Model model) {
        model.addAttribute("citas", citaService.findAll());
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("mascotas", mascotaService.findAll());
        model.addAttribute("servicios", servicioService.findAll());
        model.addAttribute("usuarios", usuarioService.findAll());
        if (!model.containsAttribute("cita")) {
            Cita cita = new Cita();
            cita.setCliente(new Cliente());
            cita.setMascota(new Mascota());
            cita.setServicio(new Servicio());
            cita.setUsuario(new Usuario());
            model.addAttribute("cita", cita);
        }
        model.addAttribute("estados", Cita.Estado.values());
        return "citas/list";
    }

    @PostMapping("/citas")
    public String crear(@Valid @ModelAttribute("cita") Cita cita, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.cita", result);
            redirect.addFlashAttribute("cita", cita);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "createCitaModal");
            return "redirect:/citas";
        }
        citaService.save(cita);
        redirect.addFlashAttribute("success", "Cita registrada correctamente");
        return "redirect:/citas";
    }

    @PutMapping("/citas/{id}")
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute("cita") Cita cita, BindingResult result,
            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.cita", result);
            redirect.addFlashAttribute("cita", cita);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "editCitaModal");
            return "redirect:/citas";
        }
        cita.setId(id);
        citaService.save(cita);
        redirect.addFlashAttribute("success", "Cita actualizada correctamente");
        return "redirect:/citas";
    }

    @DeleteMapping("/citas/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        citaService.delete(id);
        redirect.addFlashAttribute("success", "Cita eliminada");
        return "redirect:/citas";
    }
}

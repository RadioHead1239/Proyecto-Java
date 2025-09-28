package com.sise.web.controller;

import com.sise.model.Cliente;
import com.sise.service.ClienteService;
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
public class ClienteViewController {

    private final ClienteService clienteService;

    @GetMapping("/clientes")
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.findAll());
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }
        return "clientes/list";
    }

    @PostMapping("/clientes")
    public String crear(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result,
            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.cliente", result);
            redirect.addFlashAttribute("cliente", cliente);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "createClienteModal");
            return "redirect:/clientes";
        }
        clienteService.save(cliente);
        redirect.addFlashAttribute("success", "Cliente registrado correctamente");
        return "redirect:/clientes";
    }

    @PutMapping("/clientes/{id}")
    public String actualizar(@PathVariable Integer id, @Valid @ModelAttribute("cliente") Cliente cliente,
            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.cliente", result);
            redirect.addFlashAttribute("cliente", cliente);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "editClienteModal");
            return "redirect:/clientes";
        }
        cliente.setId(id);
        clienteService.save(cliente);
        redirect.addFlashAttribute("success", "Cliente actualizado correctamente");
        return "redirect:/clientes";
    }

    @DeleteMapping("/clientes/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        clienteService.delete(id);
        redirect.addFlashAttribute("success", "Cliente eliminado");
        return "redirect:/clientes";
    }
}

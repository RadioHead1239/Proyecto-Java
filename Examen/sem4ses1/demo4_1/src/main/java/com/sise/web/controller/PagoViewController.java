package com.sise.web.controller;

import com.sise.model.Pago;
import com.sise.service.CitaService;
import com.sise.service.PagoService;
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
public class PagoViewController {

    private final PagoService pagoService;
    private final CitaService citaService;

    @GetMapping("/pagos")
    public String listar(Model model) {
        model.addAttribute("pagos", pagoService.findAll());
        model.addAttribute("citas", citaService.findAll());
        if (!model.containsAttribute("pago")) {
            model.addAttribute("pago", new Pago());
        }
        return "pagos/list";
    }

    @PostMapping("/pagos")
    public String crear(@Valid @ModelAttribute("pago") Pago pago, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.pago", result);
            redirect.addFlashAttribute("pago", pago);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "createPagoModal");
            return "redirect:/pagos";
        }
        pagoService.save(pago);
        redirect.addFlashAttribute("success", "Pago registrado correctamente");
        return "redirect:/pagos";
    }

    @PutMapping("/pagos/{id}")
    public String actualizar(@PathVariable Integer id, @Valid @ModelAttribute("pago") Pago pago, BindingResult result,
            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.pago", result);
            redirect.addFlashAttribute("pago", pago);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "editPagoModal");
            return "redirect:/pagos";
        }
        pago.setId(id);
        pagoService.save(pago);
        redirect.addFlashAttribute("success", "Pago actualizado correctamente");
        return "redirect:/pagos";
    }

    @DeleteMapping("/pagos/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        pagoService.delete(id);
        redirect.addFlashAttribute("success", "Pago eliminado");
        return "redirect:/pagos";
    }
}

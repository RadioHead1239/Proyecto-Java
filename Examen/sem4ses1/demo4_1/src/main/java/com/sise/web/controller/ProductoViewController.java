package com.sise.web.controller;

import com.sise.model.Producto;
import com.sise.service.ProductoService;
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
public class ProductoViewController {

    private final ProductoService productoService;

    @GetMapping("/productos")
    public String listar(Model model) {
        model.addAttribute("productos", productoService.findAll());
        if (!model.containsAttribute("producto")) {
            model.addAttribute("producto", new Producto());
        }
        return "productos/list";
    }

    @PostMapping("/productos")
    public String crear(@Valid @ModelAttribute("producto") Producto producto, BindingResult result,
            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.producto", result);
            redirect.addFlashAttribute("producto", producto);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "createProductoModal");
            return "redirect:/productos";
        }
        productoService.save(producto);
        redirect.addFlashAttribute("success", "Producto registrado correctamente");
        return "redirect:/productos";
    }

    @PutMapping("/productos/{id}")
    public String actualizar(@PathVariable Integer id, @Valid @ModelAttribute("producto") Producto producto,
            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.producto", result);
            redirect.addFlashAttribute("producto", producto);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "editProductoModal");
            return "redirect:/productos";
        }
        producto.setId(id);
        productoService.save(producto);
        redirect.addFlashAttribute("success", "Producto actualizado correctamente");
        return "redirect:/productos";
    }

    @DeleteMapping("/productos/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        productoService.delete(id);
        redirect.addFlashAttribute("success", "Producto eliminado");
        return "redirect:/productos";
    }
}

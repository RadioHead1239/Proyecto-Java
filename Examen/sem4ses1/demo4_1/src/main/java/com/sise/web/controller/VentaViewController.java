package com.sise.web.controller;

import com.sise.model.Cliente;
import com.sise.model.DetalleVenta;
import com.sise.model.Producto;
import com.sise.model.Venta;
import com.sise.service.ClienteService;
import com.sise.service.ProductoService;
import com.sise.service.VentaService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class VentaViewController {

    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final ProductoService productoService;

    @GetMapping("/ventas")
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.findAll());
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("productos", productoService.findAll());
        if (!model.containsAttribute("venta")) {
            Venta venta = new Venta();
            venta.setCliente(new Cliente());
            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(new Producto());
            detalle.setCantidad(1);
            detalle.setSubtotal(0.0);
            venta.setDetalles(new ArrayList<>());
            venta.getDetalles().add(detalle);
            model.addAttribute("venta", venta);
        }
        return "ventas/list";
    }

    @PostMapping("/ventas")
    public String crear(@Valid @ModelAttribute("venta") Venta venta, BindingResult result,
            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.venta", result);
            redirect.addFlashAttribute("venta", venta);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "createVentaModal");
            return "redirect:/ventas";
        }
        prepararTotales(venta);
        ventaService.save(venta);
        redirect.addFlashAttribute("success", "Venta registrada correctamente");
        return "redirect:/ventas";
    }

    @PutMapping("/ventas/{id}")
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute("venta") Venta venta, BindingResult result,
            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.venta", result);
            redirect.addFlashAttribute("venta", venta);
            redirect.addFlashAttribute("error", "Verifique los datos del formulario");
            redirect.addFlashAttribute("modal", "editVentaModal");
            return "redirect:/ventas";
        }
        venta.setId(id);
        prepararTotales(venta);
        ventaService.save(venta);
        redirect.addFlashAttribute("success", "Venta actualizada correctamente");
        return "redirect:/ventas";
    }

    @DeleteMapping("/ventas/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        ventaService.delete(id);
        redirect.addFlashAttribute("success", "Venta eliminada");
        return "redirect:/ventas";
    }

    private void prepararTotales(Venta venta) {
        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            return;
        }
        double total = 0;
        for (DetalleVenta detalle : venta.getDetalles()) {
            if (detalle.getProducto() != null && detalle.getProducto().getId() != null) {
                productoService.findById(detalle.getProducto().getId()).ifPresent(detalle::setProducto);
            }
            double precio = detalle.getProducto() != null && detalle.getProducto().getPrecio() != null
                    ? detalle.getProducto().getPrecio() : 0;
            int cantidad = detalle.getCantidad() != null ? detalle.getCantidad() : 0;
            double subtotal = precio * cantidad;
            detalle.setSubtotal(subtotal);
            total += subtotal;
        }
        venta.setTotal(BigDecimal.valueOf(total));
    }
}

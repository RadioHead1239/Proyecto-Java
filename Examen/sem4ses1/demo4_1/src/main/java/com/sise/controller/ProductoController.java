package com.sise.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sise.dto.ProductoDTO;
import com.sise.iservice.IProductoService;

@Controller
public class ProductoController {

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/producto")
    public String productos(Model model) {
        return "views/producto";
    }

    @GetMapping("/api/productos")
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        try {
            List<ProductoDTO> productos = productoService.findAll();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/productos/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        try {
            Optional<ProductoDTO> producto = productoService.findById(id);
            return producto.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/api/productos")
    public ResponseEntity<ProductoDTO> createProducto(@RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO savedProducto = productoService.save(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProducto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/api/productos/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        try {
            Optional<ProductoDTO> existingProducto = productoService.findById(id);
            if (existingProducto.isPresent()) {
                productoDTO.setId(id);
                ProductoDTO updatedProducto = productoService.save(productoDTO);
                return ResponseEntity.ok(updatedProducto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/api/productos/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        try {
            boolean deleted = productoService.deleteById(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

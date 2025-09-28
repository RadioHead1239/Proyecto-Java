package com.sise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagoController {

    @GetMapping("/pago")
    public String pagos() {
        return "views/pago";
    }
}

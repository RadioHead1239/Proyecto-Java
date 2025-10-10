package com.sise.api;

import com.sise.dto.EmailRequest;
import com.sise.dto.EmailResponse;
import com.sise.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class EmailApi {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar-email")
    public ResponseEntity<EmailResponse> enviarEmail(@RequestBody EmailRequest emailRequest) {
        try {
            System.out.println("=== INICIANDO ENVÍO DE EMAIL ===");
            System.out.println("To: " + emailRequest.getTo());
            System.out.println("Subject: " + emailRequest.getSubject());
            System.out.println("Venta ID: " + emailRequest.getVenta().getId());
            System.out.println("PDF Name: " + emailRequest.getPdfName());
            System.out.println("PDF Base64 length: " + (emailRequest.getPdfBase64() != null ? emailRequest.getPdfBase64().length() : "null"));
            
            emailService.enviarComprobantePorEmail(
                emailRequest.getTo(),
                emailRequest.getSubject(),
                emailRequest.getVenta(),
                emailRequest.getPdfBase64(),
                emailRequest.getPdfName()
            );
            
            System.out.println("=== EMAIL ENVIADO EXITOSAMENTE ===");
            return ResponseEntity.ok(new EmailResponse(true, "Email enviado exitosamente"));
        } catch (Exception e) {
            System.err.println("=== ERROR AL ENVIAR EMAIL ===");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new EmailResponse(false, "Error al enviar email", e.getMessage()));
        }
    }
}

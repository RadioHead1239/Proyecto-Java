package com.sise.service;

import com.sise.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final String EMAIL_FROM = "victorevangelistacucho@gmail.com";

    public void enviarComprobantePorEmail(String to, String subject, EmailRequest.VentaInfo venta, 
                                         String pdfBase64, String pdfName) throws MessagingException {
        
        System.out.println("=== EMAILSERVICE: INICIANDO ENVÍO ===");
        System.out.println("From: " + EMAIL_FROM);
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("PDF Size: " + pdfBase64.length() + " characters");
        
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(EMAIL_FROM);
            helper.setTo(to);
            helper.setSubject(subject);
            
            // Contenido HTML
            String htmlContent = crearContenidoHTML(venta);
            helper.setText(htmlContent, true);
            
            // Adjuntar PDF
            byte[] pdfBytes = java.util.Base64.getDecoder().decode(pdfBase64);
            System.out.println("PDF Bytes length: " + pdfBytes.length);
            helper.addAttachment(pdfName, new ByteArrayResource(pdfBytes), "application/pdf");
            
            System.out.println("=== ENVIANDO EMAIL... ===");
            mailSender.send(message);
            System.out.println("=== EMAIL ENVIADO EXITOSAMENTE ===");
            
        } catch (Exception e) {
            System.err.println("=== ERROR EN EMAILSERVICE ===");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private String crearContenidoHTML(EmailRequest.VentaInfo venta) {
        return "<html>" +
            "<body style=\"font-family: Arial, sans-serif; line-height: 1.6; color: #333;\">" +
                "<div style=\"max-width: 600px; margin: 0 auto; padding: 20px;\">" +
                    "<div style=\"background: linear-gradient(135deg, #4facfe, #00f2fe); color: white; padding: 20px; border-radius: 10px 10px 0 0; text-align: center;\">" +
                        "<h1 style=\"margin: 0; font-size: 24px;\">VetCare</h1>" +
                        "<p style=\"margin: 5px 0 0 0; font-size: 14px;\">Veterinaria y Clínica Animal</p>" +
                    "</div>" +
                    
                    "<div style=\"background: #f8f9fa; padding: 20px; border-radius: 0 0 10px 10px;\">" +
                        "<h2 style=\"color: #2c3e50; margin-top: 0;\">Comprobante de Pago</h2>" +
                        
                        "<p>Estimado/a cliente,</p>" +
                        
                        "<p>Adjunto encontrará el comprobante de pago correspondiente a su compra:</p>" +
                        
                        "<div style=\"background: white; padding: 15px; border-radius: 5px; margin: 15px 0; border-left: 4px solid #4facfe;\">" +
                            "<p style=\"margin: 5px 0;\"><strong>N° Comprobante:</strong> " + venta.getId() + "</p>" +
                            "<p style=\"margin: 5px 0;\"><strong>Fecha:</strong> " + venta.getFecha() + "</p>" +
                            "<p style=\"margin: 5px 0;\"><strong>Cliente:</strong> " + venta.getCliente() + "</p>" +
                            "<p style=\"margin: 5px 0;\"><strong>Estado:</strong> " + venta.getEstado() + "</p>" +
                        "</div>" +
                        
                        "<p>Este comprobante no tiene validez fiscal ante SUNAT.</p>" +
                        
                        "<p>Gracias por confiar en VetCare para el cuidado de sus mascotas.</p>" +
                        
                        "<div style=\"text-align: center; margin-top: 20px; padding-top: 20px; border-top: 1px solid #ddd;\">" +
                            "<p style=\"color: #666; font-size: 12px; margin: 0;\">" +
                                "VetCare - Cuidando a tus mascotas<br>" +
                                "Av. Javier Prado Este 123, San Isidro, Lima<br>" +
                                "Tel: (01) 234-5678 | Email: info@vetcare.com" +
                            "</p>" +
                        "</div>" +
                    "</div>" +
                "</div>" +
            "</body>" +
            "</html>";
    }
}

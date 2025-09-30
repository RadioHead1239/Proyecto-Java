// Utilidades para VetCare
class VetCareUtils {
    
    // Formatear moneda a soles peruanos
    static formatCurrency(amount) {
        if (amount === null || amount === undefined) return 'S/ 0.00';
        const num = parseFloat(amount);
        return new Intl.NumberFormat('es-PE', {
            style: 'currency',
            currency: 'PEN',
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        }).format(num);
    }

    // Parser de imágenes separadas por punto y coma
    static parseImages(imageString) {
        if (!imageString) return [];
        return imageString.split(';').filter(img => img.trim() !== '');
    }

    // Crear carousel de imágenes para modal
    static createImageCarousel(images, id) {
        if (!images || images.length === 0) {
            return '<div class="text-center text-muted"><i class="bi bi-image fs-1"></i><br>Sin imágenes</div>';
        }

        let carouselHTML = `
            <div id="carousel-${id}" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-indicators">
        `;
        
        images.forEach((_, index) => {
            carouselHTML += `<button type="button" data-bs-target="#carousel-${id}" data-bs-slide-to="${index}" ${index === 0 ? 'class="active" aria-current="true"' : ''}></button>`;
        });
        
        carouselHTML += `
                </div>
                <div class="carousel-inner">
        `;
        
        images.forEach((image, index) => {
            carouselHTML += `
                <div class="carousel-item ${index === 0 ? 'active' : ''}">
                    <img src="${image}" class="d-block w-100" alt="Imagen ${index + 1}" style="height: 400px; object-fit: cover;">
                </div>
            `;
        });
        
        carouselHTML += `
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carousel-${id}" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carousel-${id}" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        `;
        
        return carouselHTML;
    }

    // Crear modal para ver imágenes
    static showImageModal(title, images) {
        const modalId = 'imageModal_' + Date.now();
        const carouselHTML = this.createImageCarousel(images, modalId);
        
        const modalHTML = `
            <div class="modal fade" id="${modalId}" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header bg-success text-white">
                            <h5 class="modal-title">
                                <i class="bi bi-images me-2"></i>${title}
                            </h5>
                            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            ${carouselHTML}
                        </div>
                    </div>
                </div>
            </div>
        `;
        
        // Remover modal anterior si existe
        const existingModal = document.getElementById(modalId);
        if (existingModal) {
            existingModal.remove();
        }
        
        // Agregar modal al DOM
        document.body.insertAdjacentHTML('beforeend', modalHTML);
        
        // Mostrar modal
        const modal = new bootstrap.Modal(document.getElementById(modalId));
        modal.show();
        
        // Limpiar modal cuando se cierre
        document.getElementById(modalId).addEventListener('hidden.bs.modal', function() {
            this.remove();
        });
    }

    // Generar PDF de comprobante de venta
    static generateSaleReceipt(venta, detalles) {
        // Verificar si jsPDF está disponible
        if (typeof window.jsPDF === 'undefined') {
            console.error('jsPDF no está cargado');
            return;
        }

        const { jsPDF } = window.jsPDF;
        const doc = new jsPDF();

        // Configuración del documento
        const pageWidth = doc.internal.pageSize.getWidth();
        const margin = 20;
        let yPosition = margin;

        // Encabezado de la clínica veterinaria
        doc.setFontSize(20);
        doc.setFont('helvetica', 'bold');
        doc.text('CLÍNICA VETERINARIA VETCARE', pageWidth / 2, yPosition, { align: 'center' });
        yPosition += 10;

        doc.setFontSize(12);
        doc.setFont('helvetica', 'normal');
        doc.text('CUIDAMOS A TUS MASCOTAS CON AMOR Y PROFESIONALISMO', pageWidth / 2, yPosition, { align: 'center' });
        yPosition += 15;

        // Línea separadora
        doc.line(margin, yPosition, pageWidth - margin, yPosition);
        yPosition += 10;

        // Información del comprobante
        doc.setFontSize(14);
        doc.setFont('helvetica', 'bold');
        doc.text('COMPROBANTE DE VENTA', pageWidth / 2, yPosition, { align: 'center' });
        yPosition += 15;

        // Datos de la venta
        doc.setFontSize(10);
        doc.setFont('helvetica', 'normal');
        
        const fecha = new Date(venta.fechaCreacion || new Date()).toLocaleDateString('es-PE');
        doc.text(`Fecha: ${fecha}`, margin, yPosition);
        doc.text(`N° Venta: ${venta.id || 'N/A'}`, pageWidth - margin - 40, yPosition);
        yPosition += 10;

        doc.text(`Cliente: ${venta.cliente?.nombre || 'N/A'} ${venta.cliente?.apellido || ''}`, margin, yPosition);
        yPosition += 10;

        if (venta.cliente?.telefono) {
            doc.text(`Teléfono: ${venta.cliente.telefono}`, margin, yPosition);
            yPosition += 10;
        }

        // Línea separadora
        doc.line(margin, yPosition, pageWidth - margin, yPosition);
        yPosition += 10;

        // Encabezados de la tabla
        doc.setFont('helvetica', 'bold');
        doc.text('DESCRIPCIÓN', margin, yPosition);
        doc.text('CANT.', margin + 80, yPosition);
        doc.text('P.UNIT.', margin + 110, yPosition);
        doc.text('SUBTOTAL', margin + 150, yPosition);
        yPosition += 8;

        // Línea separadora
        doc.line(margin, yPosition, pageWidth - margin, yPosition);
        yPosition += 8;

        // Detalles de productos/servicios
        doc.setFont('helvetica', 'normal');
        let total = 0;

        if (detalles && detalles.length > 0) {
            detalles.forEach(detalle => {
                const productoNombre = detalle.producto?.nombre || 'Producto';
                const cantidad = detalle.cantidad || 1;
                const precioUnitario = parseFloat(detalle.precioUnitario || detalle.precio || 0);
                const subtotal = parseFloat(detalle.subtotal || precioUnitario * cantidad);

                total += subtotal;

                // Asegurar que el texto no se salga de la página
                if (yPosition > 250) {
                    doc.addPage();
                    yPosition = margin;
                }

                doc.text(productoNombre.substring(0, 30), margin, yPosition);
                doc.text(cantidad.toString(), margin + 80, yPosition);
                doc.text(this.formatCurrency(precioUnitario), margin + 110, yPosition);
                doc.text(this.formatCurrency(subtotal), margin + 150, yPosition);
                yPosition += 8;
            });
        } else {
            doc.text('Sin detalles disponibles', margin, yPosition);
            yPosition += 8;
        }

        // Línea separadora
        doc.line(margin, yPosition, pageWidth - margin, yPosition);
        yPosition += 10;

        // Total
        doc.setFont('helvetica', 'bold');
        doc.text('TOTAL:', margin + 110, yPosition);
        doc.text(this.formatCurrency(total), margin + 150, yPosition);
        yPosition += 15;

        // Información adicional
        doc.setFontSize(8);
        doc.setFont('helvetica', 'normal');
        doc.text('* Este comprobante no es válido para efectos tributarios (Sin IGV)', margin, yPosition);
        yPosition += 5;
        doc.text('* Gracias por confiar en nuestros servicios veterinarios', margin, yPosition);
        yPosition += 5;
        doc.text('* Para consultas: info@vetcare.com', margin, yPosition);

        // Pie de página
        yPosition = doc.internal.pageSize.getHeight() - 20;
        doc.line(margin, yPosition, pageWidth - margin, yPosition);
        yPosition += 5;
        doc.setFontSize(8);
        doc.text('VetCare - Clínica Veterinaria | Cuidamos a tus mascotas con amor y profesionalismo', pageWidth / 2, yPosition, { align: 'center' });

        // Guardar el PDF
        const nombreArchivo = `comprobante_venta_${venta.id || 'N/A'}_${fecha.replace(/\//g, '-')}.pdf`;
        doc.save(nombreArchivo);
    }

    // Formatear fecha
    static formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString('es-PE', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
    }

    // Formatear fecha y hora
    static formatDateTime(dateString) {
        const date = new Date(dateString);
        return date.toLocaleString('es-PE', {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    }

    // Obtener clase de badge según stock
    static getStockBadgeClass(stock, stockMinimo) {
        if (stock <= 0) return 'danger';
        if (stock <= stockMinimo) return 'warning';
        return 'success';
    }

    // Obtener texto de stock
    static getStockText(stock, stockMinimo) {
        if (stock <= 0) return 'Sin Stock';
        if (stock <= stockMinimo) return 'Stock Bajo';
        return 'En Stock';
    }
}

// Funciones globales para compatibilidad
window.formatCurrency = VetCareUtils.formatCurrency;
window.parseImages = VetCareUtils.parseImages;
window.showImageModal = VetCareUtils.showImageModal;
window.generateSaleReceipt = VetCareUtils.generateSaleReceipt;

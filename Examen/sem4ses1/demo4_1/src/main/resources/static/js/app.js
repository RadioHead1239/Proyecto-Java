document.addEventListener('DOMContentLoaded', () => {
    const bodyModal = document.body.dataset.openModal;
    if (bodyModal) {
        const modalEl = document.getElementById(bodyModal);
        if (modalEl) {
            const modal = new bootstrap.Modal(modalEl);
            modal.show();
        }
    }

    document.querySelectorAll('[data-modal-target]').forEach(button => {
        const targetId = button.dataset.modalTarget;
        const modalEl = document.getElementById(targetId);
        if (!modalEl) {
            return;
        }
        button.addEventListener('click', () => {
            const form = modalEl.querySelector('form');
            if (form && button.dataset.action) {
                form.setAttribute('action', button.dataset.action);
            }
            const methodInput = form ? form.querySelector('input[name="_method"]') : null;
            if (methodInput && button.dataset.method) {
                methodInput.value = button.dataset.method;
            }
            modalEl.querySelectorAll('[data-field]').forEach(field => {
                const value = button.dataset[field.dataset.field];
                if (field.tagName === 'INPUT' || field.tagName === 'TEXTAREA' || field.tagName === 'SELECT') {
                    if (field.type === 'checkbox') {
                        field.checked = value === 'true' || value === true;
                    } else {
                        field.value = value ?? '';
                    }
                }
            });
        });
    });
});

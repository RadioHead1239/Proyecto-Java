async function loadClientes() {
  const res = await fetch('/api/clientes');
  const data = await res.json();

  const tbody = document.getElementById('tbodyClientes');
  tbody.innerHTML = '';
  data.forEach(c => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${c.nombre} ${c.apellido}</td>
      <td>${c.telefono || '-'}</td>
      <td>${c.correo || '-'}</td>
      <td>${c.fechaRegistro}</td>
      <td class="text-end">
        <button class="btn btn-sm btn-outline-secondary" onclick="editarCliente(${c.id})">✏️</button>
        <button class="btn btn-sm btn-outline-danger" onclick="eliminarCliente(${c.id})">🗑️</button>
      </td>
    `;
    tbody.appendChild(tr);
  });
}

async function registrarCliente(cliente) {
  await fetch('/api/clientes', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(cliente)
  });
  loadClientes();
}

async function eliminarCliente(id) {
  await fetch(`/api/clientes/${id}`, { method: 'DELETE' });
  loadClientes();
}

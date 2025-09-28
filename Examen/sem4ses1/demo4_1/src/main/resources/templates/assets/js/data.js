 async function loadDashboard() {
  try {
    const res = await fetch('/api/dashboard');
    const data = await res.json();

    // ===== KPIs =====
    document.getElementById('kpiClientes').textContent = data.totalClientes;
    document.getElementById('kpiMascotas').textContent = data.totalMascotas;
    document.getElementById('kpiCitas').textContent = data.citasHoy;
    document.getElementById('kpiIngresos').textContent = `S/ ${data.ingresosHoy}`;

    // ===== Citas próximas =====
    const tbodyCitas = document.getElementById('tbodyCitas');
    tbodyCitas.innerHTML = '';
    data.proximasCitas.forEach(c => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${c.fechaCita?.replace('T',' ') || '-'}</td>
        <td>${c.cliente}</td>
        <td>${c.mascota}</td>
        <td>${c.servicio}</td>
        <td>${c.estado}</td>`;
      tbodyCitas.appendChild(tr);
    });

    // ===== Últimas ventas =====
    const tbodyVentas = document.getElementById('tbodyVentas');
    tbodyVentas.innerHTML = '';
    data.ultimasVentas.forEach((v,i) => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${i+1}</td>
        <td>${v.cliente}</td>
        <td>${v.fecha?.replace('T',' ') || '-'}</td>
        <td><strong>S/ ${v.total}</strong></td>`;
      tbodyVentas.appendChild(tr);
    });

    // ===== Gráficos =====
    initCharts(data.ventasSemanales, data.distribucionMascotas);

  } catch (e) {
    console.error("Error cargando dashboard:", e);
  }
}

function initCharts(ventasSemanales, distribucionMascotas) {
  // Ventas por semana
  const ctx1 = document.getElementById('ventasChart');
  new Chart(ctx1, {
    type: 'line',
    data: {
      labels: ventasSemanales.map(v => v.semana),
      datasets: [{
        label: 'Ventas S/',
        data: ventasSemanales.map(v => v.total),
        tension:.35,
        borderColor: '#0066cc',
        fill: false
      }]
    }
  });

  const ctx2 = document.getElementById('especiesChart');
  new Chart(ctx2, {
    type: 'doughnut',
    data: {
      labels: distribucionMascotas.map(m => m.especie),
      datasets: [{
        data: distribucionMascotas.map(m => m.cantidad),
        backgroundColor: ['#00bcd4','#ff9800','#8bc34a']
      }]
    },
    options: {
      plugins: { legend: { position: 'bottom' } }
    }
  });
}

document.addEventListener('DOMContentLoaded', loadDashboard);



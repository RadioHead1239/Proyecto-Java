document.addEventListener("DOMContentLoaded", () => {
  const content = document.getElementById("content");
  document.querySelectorAll("[data-view]").forEach(link => {
    link.addEventListener("click", e => {
      e.preventDefault();
      const view = link.getAttribute("data-view");
      fetch("vistas/" + view)
        .then(res => res.text())
        .then(html => content.innerHTML = html)
        .catch(err => content.innerHTML = "<p>Error cargando vista.</p>");
    });
  });
});

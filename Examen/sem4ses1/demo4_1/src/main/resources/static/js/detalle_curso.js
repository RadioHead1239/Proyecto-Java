$(document).ready(function () {

    obtenerCurso($('#idCurso').html());

});

function obtenerCurso(id) {
    $.ajax({
        url: "/back/curso/obtener?id=" + id,
        success: function( result ) {
            if (result.id > 0) {
                $('.noExisteCurso').hide();
                $('#idNombre').html(result.nombre);
                $('#idDescripcion').html(result.descripcion);
                $('#idCiclo').html(result.ciclo);
                if (result.estado == 1) {
                    $('#idEstado').html('<span class="text-active">Activo</span>');
                } else {
                    $('#idEstado').html('<span class="text-inactive">Inactivo</span>');
                }
            } else {
                $('.existeCurso').hide();
            }
        }
    });
}
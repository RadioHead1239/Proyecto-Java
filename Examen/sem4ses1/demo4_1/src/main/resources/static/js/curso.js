console.log("Hola esto es la consola de mi navegador y estoy en curso.js");

$(document).ready(function () {

    console.log("Hola esto es la consola de mi navegador y esto en mi primer documento leído");

    $('#botonHolaMundo').click(function() {
        var numero1 = parseInt($('#numero1').val());
        var numero2 = parseInt($('#numero2').val());
        //var suma = numero1 + numero2;
        //alert("La suma es: " + suma);
        $('#resultado').html("El resultado de la suma es: " + sumar(numero1, numero2));
    });

    $('#mensajePrueba').click(function () {
        alert($(this).html());
    });

    $('#Prueba').click(function () {
        alert($(this).html());
    });

    $('#datos').click(function () {
        alert($(this).data('contenido'));
    });

});

function sumar(numero1, numero2) {
    return numero1 + numero2;
}
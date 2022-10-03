//alert("Mensaje de prueba.");
var username = new URL(location.href).searchParams.get("username");
var user;

$(document).ready(function () {

    $(function () {
        $('[data-bs-toggle="tooltip"]').tooltip();
    });

    getCliente().then(function () {

        //Botón mi perfil
        $("#btn-mi-perfil").attr("href", "profile.html?username=" + username);

        //Muestra el saldo disponible en la cuenta
        $("#user-saldo").html(user.saldo.toFixed(2) + "$");

        //Mostrar productos
        getProductos(false, "ASC");

        //Ordenar los productos por categorías
        $("#ordenar-genero").click(ordenarProductos);

    });

});

//PEDIR CLIENTE
async function getCliente() {

    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletClientePedir",
        data: $.param({
            username: username
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                user = parsedResult;
            } else {
                console.log("Error recuperando los datos del cliente.");
            }
        }

    });

}

//LISTAR PRODUCTOS
function getProductos(ordenar, orden) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletProductoListar",
        data: $.param({
            ordenar: ordenar,
            orden: orden
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                mostrarProductos(parsedResult);
            } else {
                console.log("Error recuperando los datos de los productos.");
            }
        }
    });
}

//MOSTRAR PRODUCTOS
function mostrarProductos(productos) {

    let contenido = "";

    $.each(productos, function (index, producto) {

        producto = JSON.parse(producto);
        
        let precio = producto.precio_producto;

        if (producto.cantidad_producto > 0) {

            contenido += '<tr><th scope="row">' + producto.id_producto + '</th>' +
                    '<td>' + producto.nombre_producto + '</td>' +
                    '<td>' + producto.categoria_producto + '</td>' +
                    '<td>' + producto.descripcion_producto + '</td>' +
                    '<td>$' + precio + ' </td>' +
                    '<td>' + producto.cantidad_producto + ' kg</td>' +
                    '<td><button onclick="comprarProducto(' + producto.id_producto + ',' + precio + ');" class="btn btn-success" ';
            if (user.saldo < precio) {   
                contenido += ' disabled ';
            }

            contenido += '>Comprar</button></td></tr>'

        }
    });
    $("#productos-tbody").html(contenido);
    
}

//
function ordenarProductos() {

    if ($("#icono-ordenar").hasClass("fa-sort")) {
        getProductos(true, "ASC");
        $("#icono-ordenar").removeClass("fa-sort");
        $("#icono-ordenar").addClass("fa-sort-down");
    } else if ($("#icono-ordenar").hasClass("fa-sort-down")) {
        getProductos(true, "DESC");
        $("#icono-ordenar").removeClass("fa-sort-down");
        $("#icono-ordenar").addClass("fa-sort-up");
    } else if ($("#icono-ordenar").hasClass("fa-sort-up")) {
        getProductos(false, "ASC");
        $("#icono-ordenar").removeClass("fa-sort-up");
        $("#icono-ordenar").addClass("fa-sort");
    }
}

//
function comprarProducto(id_producto, precio_producto) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletProductoReservar",
        data: $.param({
            id_producto: id_producto,
            username: username
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                restarDinero(precio_producto).then(function () {
                    location.reload();
                })
            } else {
                console.log("Error en la reserva del producto.");
            }
        }
    });
}

async function restarDinero(precio_producto) {

    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletClienteRestarDinero",
        data: $.param({
            username: username,
            saldo: parseFloat(user.saldo - precio_producto)
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                console.log("Saldo actualizado.");
            } else {
                console.log("Error en el proceso de pago.");
            }
        }
    });
    
}

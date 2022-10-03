var username = new URL(location.href).searchParams.get("username");
var user;

$(document).ready(function () {

    fillCliente().then(function () {

        $("#user-saldo").html("$" + user.saldo.toFixed());
        getCompras(user.username);
        
    });

    $("#reservar-btn").attr("href", `home.html?username=${username}`);

    $("#form-modificar").on("submit", function (event) {

        event.preventDefault();
        modificarCliente();
    
    });

    $("#aceptar-eliminar-cuenta-btn").click(function () {

        eliminarCuenta().then(function () {
            location.href = "index.html";
        })
    })

});

//FILL CLIENTE
async function fillCliente() {
    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletClientePedir",
        data: $.param({
            username: username,
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                user = parsedResult;

                $("#input-username").val(parsedResult.username);
                $("#input-contrasena").val(parsedResult.contrasena);
                $("#input-nombre").val(parsedResult.nombre);
                $("#input-apellido").val(parsedResult.apellido);
                $("#input-email").val(parsedResult.email);
                $("#input-departamento").val(parsedResult.departamento);
                $("#input-ciudad").val(parsedResult.ciudad);
                $("#input-direccion").val(parsedResult.direccion);
                $("#input-telefono").val(parsedResult.telefono);
                $("#input-saldo").val(parsedResult.saldo.toFixed(2));

            } else {
                console.log("Error recuperando los datos del cliente.");
            }
        }
    });
}

//GET COMPRAS
function getCompras(username) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletCompraListar",
        data: $.param({
            username: username
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {

                mostrarHistorial(parsedResult);

            } else {
                console.log("Error recuperando los datos de las compras.");
            }
        }
    });
}

//MOSTRAR HISTORIAL DE COMPRAS
function mostrarHistorial(productos) {
    let contenido = "";
    if (productos.length >= 1) {
        $.each(productos, function (index, producto) {
            producto = JSON.parse(producto);

            contenido += '<tr><th scope="row">' + producto.id_producto + '</th>' +
                    '<td>' + producto.nombre_producto + '</td>' +
                    '<td>' + producto.categoria_producto + '</td>' +
                    '<td>' + producto.fecha_compra + '</td>' +
                    '<td><button id="devolver-btn" onclick= "devolverProducto(' + producto.id_producto
                    + ');" class="btn btn-danger">Devolver producto</button></td></tr>';
        });
        $("#historial-tbody").html(contenido);
        $("#historial-table").removeClass("d-none");
        $("#historial-vacio").addClass("d-none");

    } else {
        $("#historial-vacio").removeClass("d-none");
        $("#historial-table").addClass("d-none");
    }
}

//DEVOLVER COMPRA (CANCELAR COMPRA)
function devolverProducto(id_producto) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletProductoDevolver",
        data: $.param({
            username: username,
            id_producto: id_producto,
        }),
        success: function (result) {

            if (result != false) {

                location.reload();

            } else {
                console.log("Error devolviendo el producto.");
            }
        }
    });

}

//MODIFICAR CLIENTE
function modificarCliente() {

    let username = $("#input-username").val();
    let contrasena = $("#input-contrasena").val();
    let nombre = $("#input-nombre").val();
    let apellido = $("#input-apellido").val();
    let email = $("#input-email").val();
    let departamento = $("#input-departamento").val();
    let ciudad = $("#input-ciudad").val();
    let direccion = $("#input-direccion").val();
    let telefono = $("#input-telefono").val();
    let saldo = $("#input-saldo").val();
    
    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletClienteModificar",
        data: $.param({
            username: username,
            contrasena: contrasena,
            nombre: nombre,
            apellido: apellido,
            email: email,
            departamento: departamento,
            ciudad: ciudad,
            direccion: direccion,
            telefono: telefono,
            saldo: saldo,
        }),
        success: function (result) {

            if (result != false) {
                $("#modificar-error").addClass("d-none");
                $("#modificar-exito").removeClass("d-none");
            } else {
                $("#modificar-error").removeClass("d-none");
                $("#modificar-exito").addClass("d-none");
            }

            setTimeout(function () {
                location.reload();
            }, 3000);

        }
    });

}

//ELIMINAR CUENTA
async function eliminarCuenta() {

    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletClienteEliminar",
        data: $.param({
            username: username
        }),
        success: function (result) {

            if (result != false) {

                console.log("Cliente eliminado.")

            } else {
                console.log("Error eliminando el cliente.");
            }
        }
    });
    
}

$(document).ready(function () {

    //Iniciar sesión
    $("#form-login").submit(function(event) {

        event.preventDefault();
        autenticarCliente();

    });
    
    //Registro de usuarios
    $("#form-register").submit(function (event) {

        event.preventDefault();
        registrarUsuario();

    });    

});

/* Función autenticar usuarios */
function autenticarCliente() {

    //#id's del form login.html
    let username = $("#usuario").val();
    let contrasena = $("#contrasena").val();

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletClienteLogin",
        data: $.param({
            username: username,
            contrasena: contrasena
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);
            if (parsedResult != false) {
                $("#login-error").addClass("d-none");
                let username = parsedResult['username'];
                document.location.href = "home.html?username=" + username;
            } else {
                $("#login-error").removeClass("d-none");
            }
        }
        
    });
    
}

/* Función registrar usuarios */
function registrarUsuario() {

    //id's del form register.html
    let username = $("#input-username").val();
    let contrasena = $("#input-contrasena").val();
    let contrasenaConfirmacion = $("#input-contrasena-repeat").val();
    let nombre = $("#input-nombre").val();
    let apellido = $("#input-apellido").val();
    let email = $("#input-email").val();
    let departamento = $("#input-departamento").val();
    let ciudad = $("#input-ciudad").val();
    let direccion = $("#input-direccion").val();
    let telefono = $("#input-telefono").val();
    let saldo = $("#input-saldo").val();

    if (contrasena == contrasenaConfirmacion) {

        $.ajax({
            type: "GET",
            dataType: "html",
            url: "./ServletClienteRegister",
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
                saldo: saldo
            }),
            success: function (result) {
                let parsedResult = JSON.parse(result);

                if (parsedResult != false) {
                    $("#register-error").addClass("d-none");
                    let username = parsedResult['username'];
                    document.location.href = "home.html?username=" + username;
                } else {
                    $("#register-error").removeClass("d-none");
                    $("#register-error").html("Error en el registro del usuario");
                }
                
            }
        });
    } else {
        $("#register-error").removeClass("d-none");
        $("#register-error").html("Las contraseñas no coinciden");
    }
    
}

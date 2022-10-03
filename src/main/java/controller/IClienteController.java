package controller;

import java.util.Map;

public interface IClienteController {
    
    /* Método para iniciar sesión */
    public String login(String username, String contrasena);

    /* Método para regístrarse */
    public String register(String username, String contrasena, String nombre, 
            String apellido, String email, String departamento, String ciudad, 
            String direccion, String telefono, double saldo);

    /* Método pedir */
    public String pedir(String username);

    /* Método restar dinero */
    public String restarDinero(String username, double nuevoSaldo);
    
    /* Método modificar */
    public String modificar(String username, String nuevaContrasena, String nuevoNombre, 
            String nuevosApellidos, String nuevoEmail, String nuevoDepartamento, String nuevoCiudad, 
            String nuevoDireccion, String nuevoTelefono, double nuevoSaldo);
    
    /* Método ver copias */
    public String verCopias(String username);

    /* Método devolver productos */
    public String devolverProductos(String username, Map<Integer, Integer> cantidad_producto);

    /* Método eliminar */
    public String eliminar(String username);   
    
}

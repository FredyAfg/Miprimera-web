package beans;

public class Clientes {

    //CAMPOS BD
    private String username;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String email;
    private String departamento;
    private String ciudad;
    private String direccion;
    private String telefono;
    private double saldo;

    //MÉTODO CONSTRUCTOR
    public Clientes(String username, String contrasena, String nombre, String apellido, String email, String departamento, String ciudad, String direccion, String telefono, double saldo) {
        this.username = username;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.saldo = saldo;
    }

    //GETTER AND SETTER
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    //TOSTRING
    @Override
    public String toString() {
        return "Clientes{" + "username=" + username + ", "
                + "contrasena=" + contrasena + ", "
                + "nombre=" + nombre + ", "
                + "apellido=" + apellido + ", "
                + "email=" + email + ", "
                + "departamento=" + departamento + ", "
                + "ciudad=" + ciudad + ", "
                + "direccion=" + direccion + ", "
                + "telefono=" + telefono + ", "
                + "saldo=" + saldo + '}';
    }
    
}

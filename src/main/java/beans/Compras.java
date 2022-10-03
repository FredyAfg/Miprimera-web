package beans;

import java.sql.Date;

public class Compras {
    
    /**
    private int id;
    private String username;
    private Date fechaAlquiler;
    private boolean novedad;
    private String genero;
    */
    
    //CAMPOS BD
    private int id_producto;
    private String username;
    private Date fecha_compra;
    private String categoria_producto;

    public Compras(int id_producto, String username, Date fecha_compra, String categoria_producto) {
        this.id_producto = id_producto;
        this.username = username;
        this.fecha_compra = fecha_compra;
        this.categoria_producto = categoria_producto;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(Date fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public String getCategoria_producto() {
        return categoria_producto;
    }

    public void setCategoria_producto(String categoria_producto) {
        this.categoria_producto = categoria_producto;
    }

    @Override
    public String toString() {
        return "Compras{" + "id_producto=" + id_producto + ", "
                + "username=" + username + ", "
                + "fecha_compra=" + fecha_compra + ", "
                + "categoria_producto=" + categoria_producto + '}';
    }
    
}

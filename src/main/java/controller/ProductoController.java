package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import beans.Productos;
import connection.DBConnection;

public class ProductoController implements IProductoController {

    //LISTAR PRODUCTOS
    @Override
    public String listar(boolean ordenar, String orden) {

        Gson gson = new Gson();

        DBConnection conn = new DBConnection();

        String sql = "SELECT * FROM productos";

        if (ordenar == true) {
            sql += " ORDER BY categoria_producto " + orden;
        }

        List<String> productos = new ArrayList<String>();

        try {

            Statement st = conn.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                int id_producto = rs.getInt("id_producto");
                String nombre_producto = rs.getString("nombre_producto");
                String categoria_producto = rs.getString("categoria_producto");
                String descripcion_producto = rs.getString("descripcion_producto");
                double precio_producto = rs.getDouble("precio_producto");
                int cantidad_producto = rs.getInt("cantidad_producto");

                Productos producto = new Productos(id_producto, nombre_producto, categoria_producto,
                        descripcion_producto, precio_producto, cantidad_producto);

                productos.add(gson.toJson(producto));

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }

        return gson.toJson(productos);

    }

    //ALQUILAR
    @Override
    public String alquilar(int id_producto, String username) {

        Timestamp fecha_compra = new Timestamp(new Date().getTime());
        
        DBConnection conn = new DBConnection();
        
        String sql = "INSERT INTO compras VALUES ('" + id_producto + "', '" + username + "', '" + fecha_compra + "')";

        try {
            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql);

            String modificar = modificar(id_producto);

            if (modificar.equals("true")) {
                return "true";
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            conn.desconectar();
        }
        return "false";
    }
    
    //MODIFICAR
    @Override
    public String modificar(int id_producto) {

        DBConnection conn = new DBConnection();
        String sql = "UPDATE productos SET cantidad_producto = (cantidad_producto - 1) WHERE id_producto = " + id_producto;

        try {
            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            conn.desconectar();
        }

        return "false";

    }

    //DEVOLVER
    @Override
    public String devolver(int id_producto, String username) {

        DBConnection conn = new DBConnection();
        
        String sql = "DELETE FROM compras WHERE id_producto= " + id_producto + " AND username = '" 
                + username + "' LIMIT 1";

        try {
            Statement st = conn.getConnection().createStatement();
            st.executeQuery(sql);

            this.sumarCantidad(id_producto);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            conn.desconectar();
        }

        return "false";
    }    
    
    //SUMAR CANTIDAD
    @Override
    public String sumarCantidad(int id_producto) {

        DBConnection conn = new DBConnection();

        String sql = "UPDATE productos SET cantidad_producto = (SELECT cantidad__producto FROM productos where id_producto = " 
                + id_producto + ") + 1 WHERE id_producto = " + id_producto;

        try {
            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            conn.desconectar();
        }

        return "false";

    }

}

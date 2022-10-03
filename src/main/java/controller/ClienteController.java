package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

import beans.Clientes;
import connection.DBConnection;

public class ClienteController implements IClienteController {

    //INICIAR SESIÓN - CLIENTES
    @Override
    public String login(String username, String contrasena) {

        //Crear un objeto de la clase Gson
        Gson gson = new Gson();

        //Crear un objeto de la clase DBConnection para onectarnos con la base de datos
        DBConnection conn = new DBConnection();

        //Sentencia SQL
        String sql = "SELECT * FROM "
                + "clientes "
                + "WHERE username = '" + username
                + "' AND + contrasena = '" + contrasena + "'";

        //Cuando se establece la conexión
        try {

            Statement st = conn.getConnection().createStatement();

            ResultSet rs = st.executeQuery(sql);

            //Proceso de buscar dentro de la conexión
            while (rs.next()) {
                //parametroEntrada = campoDB 
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String departamento = rs.getString("departamento");
                String ciudad = rs.getString("ciudad");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                double saldo = rs.getDouble("saldo");

                //Crear un objeto de la clase Clientes que almacenara toda la info
                Clientes cliente = new Clientes(username, contrasena, nombre, apellido, email, departamento, ciudad, direccion, telefono, saldo);

                //Retornar los datos
                return gson.toJson(cliente);

            }

        } //Error en el proceso de conexión
        catch (Exception ex) {
            System.out.println("¡ERROR!: " + ex.getMessage());
        } //Finalizar el proceso de conexión
        finally {
            conn.desconectar();
        }

        return "false";

    }

    //CREAR UN NUEVO CLIENTE
    @Override
    public String register(String username, String contrasena, String nombre,
            String apellido, String email, String departamento, String ciudad, String direccion,
            String telefono, double saldo) {

        Gson gson = new Gson();

        DBConnection conn = new DBConnection();

        String sql = "INSERT INTO clientes VALUES('" + username + "', '" + contrasena + "', '" + nombre
                + "', '" + apellido + "', '" + email + "', '" + departamento + "', '" + ciudad + "', '" + direccion
                + "', " + telefono + ", " + saldo + ")";

        try {
            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql);

            Clientes cliente = new Clientes(username, contrasena, nombre, apellido, email, departamento, ciudad, direccion, telefono, saldo);

            st.close();

            return gson.toJson(cliente);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }

        return "false";

    }

    //PEDIR
    @Override
    public String pedir(String username) {

        Gson gson = new Gson();

        DBConnection conn = new DBConnection();

        String sql = "SELECT * FROM clientes WHERE username = '" + username + "'";

        try {

            Statement st = conn.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String contrasena = rs.getString("contrasena");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String departamento = rs.getString("departamento");
                String ciudad = rs.getString("ciudad");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                double saldo = rs.getDouble("saldo");

                Clientes cliente = new Clientes(username, contrasena, nombre, apellido, email, departamento, ciudad, direccion, telefono, saldo);

                return gson.toJson(cliente);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }

        return "false";
    }

    //RESTAR DINERO
    @Override
    public String restarDinero(String username, double nuevoSaldo) {

        DBConnection conn = new DBConnection();
        String sql = "UPDATE clientes SET saldo = " + nuevoSaldo + " WHERE username = '" + username + "'";

        try {

            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }

        return "false";
    }

    //MODIFICAR DATOS DEL USUARIO
    @Override
    public String modificar(String username, String nuevaContrasena, String nuevoNombre,
            String nuevosApellidos, String nuevoEmail, String nuevoDepartamento, String nuevoCiudad,
            String nuevoDireccion, String nuevoTelefono, double nuevoSaldo) {

        DBConnection conn = new DBConnection();

        String sql = "UPDATE clientes SET "
                + "contrasena = '" + nuevaContrasena + "', "
                + "nombre = '" + nuevoNombre + "', "
                + "apellido = '" + nuevosApellidos + "', "
                + "email = '" + nuevoEmail + "', "
                + "departamento = '" + nuevoDepartamento + "', "
                + "ciudad = '" + nuevoCiudad + "', "
                + "direccion = '" + nuevoDireccion + "', "
                + "telefono = '" + nuevoTelefono + "', "
                + "saldo = " + nuevoSaldo;

        sql += " WHERE username = '" + username + "'";

        try {

            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }

        return "false";

    }

    //VER COPIAS
    @Override
    public String verCopias(String username) {

        DBConnection con = new DBConnection();
        String sql = "SELECT id_producto, COUNT(*) AS num_copias FROM compras WHERE username = '" 
                + username + "' GROUP BY id_producto;";

        Map<Integer, Integer> cantidad_producto = new HashMap<Integer, Integer>();

        try {

            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id_producto = rs.getInt("id_producto");
                int num_copias = rs.getInt("num_copias");

                cantidad_producto.put(id_producto, num_copias);
            }

            devolverProductos(username, cantidad_producto);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";

    }
        
    //DEVOLVER PRODUCTOS
    @Override
    public String devolverProductos(String username, Map<Integer, Integer> cantidad_producto) {

        DBConnection conn = new DBConnection();

        try {
            for (Map.Entry<Integer, Integer> producto : cantidad_producto.entrySet()) {
                int id_producto = producto.getKey();
                int num_copias = producto.getValue();

                String sql = "UPDATE productos SET cantidad_producto = (SELECT cantidad_producto + " + num_copias +
                        " FROM productos WHERE id_producto = " + id_producto + ") where id_producto = " + id_producto;

                Statement st = conn.getConnection().createStatement();
                st.executeUpdate(sql);

            }

            this.eliminar(username);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }
        return "false";
    }

    public String eliminar(String username) {

        DBConnection conn = new DBConnection();

        String sql1 = "DELETE FROM compras WHERE username = '" + username + "'";
        String sql2 = "DELETE FROM clientes WHERE username = '" + username + "'";

        try {
            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql1);
            st.executeUpdate(sql2);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }

        return "false";
    }

}

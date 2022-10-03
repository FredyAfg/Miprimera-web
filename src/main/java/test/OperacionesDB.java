package test;

import beans.Productos;
import connection.DBConnection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OperacionesDB {

    public static void main(String[] args) {
        
        //Mostrar la lista de productos
        listarProductos();
    
    }
        
    //LISTA DE PRODUCTOS
    public static void listarProductos(){

        //Crear un objeto de la clase DBConnection para conectarnos con la base de datos
        DBConnection conn = new DBConnection();
        
        //Sentencia SQL
        String sql = "SELECT * FROM productos";

        //Cuando se establece la conexión
        try {
            
            //
            Statement st = conn.getConnection().createStatement();

            //
            ResultSet rs = st.executeQuery(sql);

            //
            while(rs.next()){
                
                int id_producto = rs.getInt("id_producto");
                String nombre_producto = rs.getString("nombre_producto");
                String categoria_producto = rs.getString("categoria_producto");
                String descripcion_producto = rs.getString("descripcion_producto");
                double precio_producto = rs.getDouble("precio_producto");
                int cantidad_producto = rs.getInt("cantidad_producto");
                                
                //Crear un objeto de la clase Ofertantes para almacenar la info de cada producto
                Productos producto = new Productos(id_producto, nombre_producto, categoria_producto, descripcion_producto, precio_producto, cantidad_producto);

                //Mostrar en pantalla la info de las ofertantes
                System.out.println(producto.toString());      
                
            }           

            //
            st.executeQuery(sql);

        } 
        //Error en el proceso de conexión
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        //Finalizar el proceso de conexión
        finally{
            conn.desconectar();
        }
        
    }    
    
}

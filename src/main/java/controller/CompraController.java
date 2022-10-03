package controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import com.google.gson.Gson;

import beans.Compras;
import connection.DBConnection;

public class CompraController implements ICompraController {

    @Override
    public String listarCompras(String username) {

        Gson gson = new Gson();

        DBConnection conn = new DBConnection();

        String sql = "SELECT l.id_producto, l.nombre_producto, l.categoria_producto, a.fecha_compra FROM productos l "
                + "INNER JOIN compras a on l.id_producto = a.id_producto INNER JOIN clientes u on a.username = u.username "
                + "WHERE a.username = '" + username + "'";

        List<String> compras = new ArrayList<String>();

        try {

            Statement st = conn.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id_producto = rs.getInt("id_producto");
                String nombre_producto = rs.getString("nombre_producto");
                String categoria_producto = rs.getString("categoria_producto");
                Date fecha_compra = rs.getDate("fecha_compra");

                Compras compra = new Compras(id_producto, nombre_producto, fecha_compra, categoria_producto);

                compras.add(gson.toJson(compra));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }
        return gson.toJson(compras);
    }
    
}
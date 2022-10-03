package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    
    Connection connection; 
    
    //CREAR EL PROCESO DE CONEXIÓN
    static String db = "railway"; //Nombre de la base de datos creada en HeidiSQL
    static String port = "5941"; //Puerto que estamos utilizando en HeidiSQL
    static String login = "root"; //Usuario en HeidiSQL
    static String password = "sSyKMCnjVjB1MOt3vj8F"; //Contraseña en HeidiSQL
    static String ip= "containers-us-west-72.railway.app";
    
    //MÉTODO CONSTRUCTOR
    public DBConnection() {
        
        //Cuando se establece conexión
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + DBConnection.ip + ":" + DBConnection.port + "/" + DBConnection.db;
            connection = DriverManager.getConnection(url, this.login, this.password);
            System.out.println("Conexión establecida.");
        }
        catch (Exception ex) {
            System.out.println("Error en la conexión.");  
        }
        
    }
    
    //MÉTODO PARA CONECTAR
    public Connection getConnection(){
        return connection;
    }
    
    //MÉTODO PARA DESCONECTAR
    public void desconectar(){
        connection = null;
    }
    
}

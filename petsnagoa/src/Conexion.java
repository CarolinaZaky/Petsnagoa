//importamos la libreria a utilizar
import java.sql.*;

public class Conexion {
    //datos para conectar la bd
    //direccion de nuestra url
    static private String url = "jdbc:mysql://localhost:3306/petsnagoa";
    //nombre de usuario de la bd
    static private String user = "root";
    //contraseña para utilizar la bd
    static private String password = "root0544";
    
    //Funcion para conectar con la bd y compruebe si funciona o no con un mensaje
    public static Connection conectar(){
        //conexion
        Connection con= null;
        //probamos si funciona o no
        try{
        //conectamos con los drives
        con=DriverManager.getConnection(url,user,password);
        //mensaje para ver si conecto
            System.out.println("Conexion exitosa");
        }catch(SQLException e ){   
        //mensaje de error
        e.printStackTrace();
            System.out.println("Conexion exitosa");
        }
        return con; //retornar
    }
}

//Importamos librerias para trabajar directamente con el modelo
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;


public class Modelo {
    
//validacion si existe el usuario de cajero para login
            public boolean UsuarioExistenteCajero(String usuario_cajero) {
                String query = "SELECT COUNT(*) FROM cajero WHERE usuario_cajero = ?";

                try {
                    Connection con = Conexion.conectar();
                    
                    PreparedStatement ps = con.prepareStatement(query);
                    
                    ps.setString(1, usuario_cajero);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        return count > 0; // true si existe
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al verificar usuario");
                    ex.printStackTrace();
                }

                return false; // por defecto, asumimos que no existe
            }

//validacion si existe el usuario de cliente a traves de la cedula
            public boolean UsuarioExistenteCliente(String cod_cliente) {
                String query = "SELECT COUNT(*) FROM cliente WHERE cod_cliente = ?";

                try {
                    Connection con = Conexion.conectar();
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, cod_cliente);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        return count > 0; // true si existe
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al verificar cliente");
                    ex.printStackTrace();
                }

                return false;
            }
    
//Validaciones si existe o no el usuario para registrarlo
    public boolean ValidarCajero(String usuario, String contraseña) {
    String query = "SELECT * FROM cajero WHERE usuario_cajero = ? AND contraseña_cajero = ?";
    
    try {
        Connection con = Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, usuario);
        ps.setString(2, contraseña);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // true si encontró coincidencia
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al validar usuario");
        ex.printStackTrace();
    }
    return false;
}
    
//Para que se mantenga con el tiempo y se muestren correctamente en la factura
    //Idcajero
    public int obtenerIdCajeroDesdeUsuario(String usuario) {
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement("SELECT id_cajero FROM cajero WHERE usuario_cajero = ?")) {
        ps.setString(1, usuario);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt("id_cajero");
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1;
}
    //Idcliente
public int obtenerIdClienteDesdeCedula(String cedula) {
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement("SELECT id_cliente FROM cliente WHERE cod_cliente = ?")) {
        ps.setString(1, cedula);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt("id_cliente");
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1;
}


//insertamos los datos para cajero
    public void Insertar_Cajero(String nombre_cajero, String apellido_cajero, String usuario_cajero, String contraseña_cajero){
            
//verificar que en el nombre y apellido se agreguen letras 
            if (!nombre_cajero.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                JOptionPane.showMessageDialog(null, "El nombre solo debe contener letras.");
                return;
            }
            
            if (!apellido_cajero.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                JOptionPane.showMessageDialog(null, "El apellido solo debe contener letras.");
                return;
            }

//Verificar si el usuario existe
        if (UsuarioExistenteCajero(usuario_cajero)) {
        JOptionPane.showMessageDialog(null, "El usuario ya existe. Por favor elige otro.");
        return; // salimos del método
        }
        
//comando para insertar datos en la tabla
        String query="insert into cajero (nombre_cajero,apellido_cajero,usuario_cajero,contraseña_cajero) values (?,?,?,?)";
        
        try{
//conectamos 
            Connection con = Conexion.conectar();
            
            PreparedStatement ps = con.prepareStatement(query);
            //agregamos en la columna correspondiente de cada dato.
            ps.setString(1, nombre_cajero);
            ps.setString(2, apellido_cajero);
            ps.setString(3, usuario_cajero);
            ps.setString(4, contraseña_cajero);
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos insertados con exito");
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al agregar datos en la tabla");
            ex.printStackTrace();
        }
    }
    
//insertamos los datos para cliente
    public void Insertar_Cliete(String cod_cliente, String nombre_cliente, String apellido_cliente, String correo_cliente, String telefono_cliente, String direccion_cliente){

//Validacion de existencia
        if (UsuarioExistenteCliente(cod_cliente)) {
        JOptionPane.showMessageDialog(null, "El cliente ya existe. Por favor elige otro.");
        return; // salimos del método
        }
        
        
// Validación: nombre y apellido solo letras
        if (!nombre_cliente.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            JOptionPane.showMessageDialog(null, "El nombre solo debe contener letras.");
            return;
        }

        if (!apellido_cliente.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            JOptionPane.showMessageDialog(null, "El apellido solo debe contener letras.");
            return;
        }

// Validación: correo electrónico válido
        if (!correo_cliente.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(null, "Correo electrónico no válido.");
            return;
        }

// Validación: teléfono solo números y opcionalmente el signo +
        if (!telefono_cliente.matches("^\\+?[0-9]{7,15}$")) {
            JOptionPane.showMessageDialog(null, "Teléfono no válido. Solo se permiten números y el signo + al inicio.");
            return;
        }

// Validación: dirección con letras, números y símbolos comunes
        if (!direccion_cliente.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s,\\.\\-#]+")) {
            JOptionPane.showMessageDialog(null, "Dirección no válida.");
            return;
        }

//comando para insertar datos en la tabla
        String query="insert into cliente (cod_cliente,nombre_cliente,apellido_cliente,correo_cliente,telefono_cliente,direccion_cliente) values (?,?,?,?,?,?)";
        
        try{
//conectamos 
            Connection con = Conexion.conectar();
            
            PreparedStatement ps = con.prepareStatement(query);
            
            ps.setString(1, cod_cliente);
            ps.setString(2, nombre_cliente);
            ps.setString(3, apellido_cliente);
            ps.setString(4, correo_cliente);
            ps.setString(5, telefono_cliente);
            ps.setString(6, direccion_cliente);
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente insertado con exito");
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al agregar cliente en la tabla");
            ex.printStackTrace();
        }
    }
    
//insertamos los datos para cliente
public void Insertar_Factura(int id_producto, int id_cliente, int id_cajero, String forma_pago) {
    String query = "INSERT INTO factura (id_producto, id_cliente, id_cajero, forma_pago) VALUES (?, ?, ?, ?)";
    
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(query)) {
        
        ps.setInt(1, id_producto);
        ps.setInt(2, id_cliente);
        ps.setInt(3, id_cajero);
        ps.setString(4, forma_pago);
        
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Factura procesada con éxito");
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al procesar factura en la tabla");
        ex.printStackTrace();
    }
}

//Obtener el id del producto
public int obtenerIdProductoDesdeCodigo(String codigo) {
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement("SELECT id_producto FROM producto WHERE cod_producto = ?")) {
        ps.setString(1, codigo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt("id_producto");
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1;
}

//Obtener el nombre del cliente
public String obtenerNombreCliente(int idCliente) {
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(
             "SELECT nombre_cliente, apellido_cliente FROM cliente WHERE id_cliente = ?")) {
        ps.setInt(1, idCliente);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String nombre = rs.getString("nombre_cliente");
            String apellido = rs.getString("apellido_cliente");
            return nombre + " " + apellido;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return "Desconocido";
}


//Obtener el nombre del cajero
public String obtenerNombreCajero(int idCajero) {
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(
             "SELECT nombre_cajero, apellido_cajero FROM cajero WHERE id_cajero = ?")) {
        ps.setInt(1, idCajero);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String nombre = rs.getString("nombre_cajero");
            String apellido = rs.getString("apellido_cajero");
            return nombre + " " + apellido;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return "Desconocido";
}


//fechayhora actualizadas en la base de datos
 
public String obtenerFechaFacturaReciente() {
    String fechaHora = "No disponible";

    String query = "SELECT fechayhora_factura FROM factura ORDER BY id_factura DESC LIMIT 1";

    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            Timestamp timestamp = rs.getTimestamp("fechayhora_factura");
            fechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return fechaHora;
}

}


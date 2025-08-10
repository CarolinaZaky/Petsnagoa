//Librerias a utilizar
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Historial extends JFrame{
    //Los objetos que se van a utilizar
    JFrame ventana;
    JLabel titulo,cliente,x;
    JTextField filtro;
    JButton regresar, salir, filtrar;
    DefaultTableModel modelo;
    JTable tabla;
    List<ProductoCompra> carrito;



    public Historial(List<ProductoCompra> carrito) {
        this.carrito = carrito;
   }
    
    //Inicio de la ventana
    public void iniciar(){
        //Llamamos a cada objeto
        ventana = new JFrame("Petsnagoa");
        titulo = new JLabel("Pestnagoa Historial");
        cliente = new JLabel("Cédula del cliente:");
        regresar = new JButton("Regresar");
        salir = new JButton("Salir");
        filtrar = new JButton("Filtrar");
        filtro = new JTextField();
        
        //Esto solo sirve para que nada se descuadre
        x= new JLabel();
        
        
        // Tabla
        modelo = new DefaultTableModel(new String[]{"Código","Cliente", "Producto", "Cajero", "FormaPago", "Fecha y hora"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Solo cantidad editable
            }
        };
        
        //Creacion de la tabla y ubicacion de la misma
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(15, 80, 400, 180);
        
        //tamaño en x, y, ancho y largo
        ventana.setSize(450, 350);
        titulo.setBounds(130, 2, 300, 45);
        cliente.setBounds(10, 20, 400, 90);
        filtro.setBounds(145, 55, 100, 20);
        regresar.setBounds(15, 275, 100, 30);
        salir.setBounds(315, 275, 100, 30);
        filtrar.setBounds(165, 275, 100, 30);
        
        //modificar el tamaño, estilo y el tipo de letra en los botones
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 25));
        regresar.setFont(new Font("Times New Roman", Font.BOLD, 15));
        salir.setFont(new Font("Times New Roman", Font.BOLD, 15));
        filtrar.setFont(new Font("Times New Roman", Font.BOLD, 15));
        cliente.setFont(new Font("Times New Roman", Font.BOLD, 17));
        
        //Agregar objeto en la ventana
        ventana.add(titulo);
        ventana.add(regresar);
        ventana.add(salir);
        ventana.add(cliente);
        ventana.add(scroll);
        ventana.add(filtro);
        ventana.add(filtrar);
        ventana.add(x);
        
        //Pregunta para estar con el mismo, otro cliente o salir.
        regresar.addActionListener((ActionEvent e) -> {
            int answer = -1;
            do {
                String input = JOptionPane.showInputDialog(
                        null,
                        "¿Qué desea hacer?\n1.Regresar al inicio.\n2. Seguir en el historial.\n3. Salir del programa.",
                        "Pestnagoa, te pregunta",
                        JOptionPane.QUESTION_MESSAGE
                );
                
                if (input == null) {
                    // Si el usuario cancela el diálogo
                    JOptionPane.showMessageDialog(null, "Operación cancelada.");
                    return;
                }
                
                try {
                    answer = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
            
                switch (answer) {
                    case 1 -> {
                        ventana.dispose();
                        LoginCajero login = new LoginCajero(carrito);
                        login.iniciar();
                    }
                    case 2 -> JOptionPane.showMessageDialog(null, "Ya estas aquí, siga averiguando");
                    case 3 -> {
                        JOptionPane.showMessageDialog(null, "Gracias hasta llegar al final! Hasta luego!");
                        System.exit(0);
                    }
                    default -> JOptionPane.showMessageDialog(null, "La opción es inexistente, intente nuevamente.", "Pestnagoa,te recuerda", JOptionPane.WARNING_MESSAGE);
                }
                
            } while (answer < 1 || answer > 3);
        });

        //Salir del programa
        salir.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "Gracias por probar el sistema de Petsnagoa, hasta luego!!");
            System.exit(0);
        });
        
        
        
        filtrar.addActionListener(e -> {
    String cedula = filtro.getText().trim();
    if (cedula.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Ingrese una cédula para filtrar.");
        return;
    }

    modelo.setRowCount(0); // Limpiar tabla
    try (Connection con = Conexion.conectar()) {
        String sql = """
            SELECT f.id_factura, 
                   cl.nombre_cliente, 
                   p.nombre_producto, 
                   CONCAT(ca.nombre_cajero, ' ', ca.apellido_cajero) AS cajero,
                   f.forma_pago, 
                   f.fechayhora_factura
            FROM factura f
            JOIN producto p ON f.id_producto = p.id_producto
            JOIN cajero ca ON f.id_cajero = ca.id_cajero
            JOIN cliente cl ON f.id_cliente = cl.id_cliente
            WHERE cl.cod_cliente = ?
            ORDER BY f.fechayhora_factura DESC
        """;
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, cedula);
        ResultSet rs = ps.executeQuery();
        boolean encontrado = false;
        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getInt("id_factura"),
                rs.getString("nombre_cliente"),
                rs.getString("nombre_producto"),
                rs.getString("cajero"),
                rs.getString("forma_pago"),
                rs.getTimestamp("fechayhora_factura")
            });
            encontrado = true;
        }
        if (!encontrado) {
            JOptionPane.showMessageDialog(null, "No se encontraron compras para esa cédula.");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al filtrar: " + ex.getMessage());
    }
});


        
        cargarHistorial();
        //De como se va a verse la tabla
        modelo.addTableModelListener(e ->{
                if(e.getType() == TableModelEvent.UPDATE && e.getColumn() ==2){
                 int fila = e.getFirstRow();
                try {
                int nuevaCantidad = Integer.parseInt(modelo.getValueAt(fila, 2).toString());
                carrito.get(fila).setCantidad(nuevaCantidad);
               
                }catch (NumberFormatException ex) {
                   JOptionPane.showMessageDialog(null, "Cantidad invalida.");
                   modelo.setValueAt(carrito.get(fila). getCantidad(), fila,2);
                  }
                 }
        });
        
        
        
        //cositas a tomar en cuenta para la ventana
        ventana.setVisible(true);
        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
    
}
     
//Se cargan todo el historial de mi base de datos
public void cargarHistorial() {
    modelo.setRowCount(0); // Limpiar tabla
    try (Connection con = Conexion.conectar()) {
        String sql = """
            SELECT f.id_factura, 
                   cl.nombre_cliente, 
                   p.nombre_producto, 
                   CONCAT(ca.nombre_cajero, ' ', ca.apellido_cajero) AS cajero,
                   f.forma_pago, 
                   f.fechayhora_factura
            FROM factura f
            JOIN producto p ON f.id_producto = p.id_producto
            JOIN cajero ca ON f.id_cajero = ca.id_cajero
            JOIN cliente cl ON f.id_cliente = cl.id_cliente
            ORDER BY f.fechayhora_factura DESC
        """;
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getInt("id_factura"),
                rs.getString("nombre_cliente"),
                rs.getString("nombre_producto"),
                rs.getString("cajero"),
                rs.getString("forma_pago"),
                rs.getTimestamp("fechayhora_factura")
            });
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al cargar historial: " + e.getMessage());
    }
}


}
    


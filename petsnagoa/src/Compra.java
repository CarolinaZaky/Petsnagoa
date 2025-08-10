//Librerias a utilizar
import java.util.ArrayList;
import java.util.List;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTextField;
import java.sql.*;
import javax.swing.JOptionPane;

public class Compra extends JFrame{
    //Objetos que se van a utilizar
    JFrame ventana;
    JLabel titulo,mensaje,seleccion,cantidad,x;
    JButton agregar, regresar;
    JComboBox <ProductoCompra> ComboProductos;
    JTextField cantidadtxt;
    List<ProductoCompra> carrito;
    
    //Una instancia que interactua con el codigo ProductoCompra
    //esta guarda en forma de lista y el codigo actua que guarde y "viaje"
    public Compra(List<ProductoCompra> carrito) {
        this.carrito = carrito;
    }
    
    //Inicio de la ventana
    public void iniciar(){
        //LLamamos a cada objeto a utilizar
        ventana = new JFrame("Petsnagoa");
        titulo = new JLabel("Pestnagoa Compra");
        mensaje = new JLabel("Seleccione los productos que necesita el cliente");
        seleccion = new JLabel("Seleccion de productos:");
        ComboProductos = new JComboBox<>();
        agregar = new JButton("Agregar");
        regresar = new JButton("Regresar");
        cantidadtxt = new JTextField();
        cantidad = new JLabel("Cantidad:");
        //Recuerda solo esto funciona que no se descuadre todo
        x= new JLabel();
        
        //tamaño y ubicacion de cada objeto en x, y, ancho y largo 
        ventana.setSize(450, 350);
        titulo.setBounds(130, 2, 300, 45);
        mensaje.setBounds(25, 50, 400, 15);
        seleccion.setBounds(10, 50, 200, 90);
        ComboProductos.setBounds(180, 87, 250, 20);
        cantidad.setBounds(10, 90, 150, 90);
        agregar.setBounds(35, 275, 100, 30);
        regresar.setBounds(300, 275, 100, 30);
        cantidadtxt.setBounds(85, 125, 100, 20);

        //modificar el tamaño, estilo y el tipo de letra en los botones
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 25));
        mensaje.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        seleccion.setFont(new Font("Times New Roman", Font.BOLD, 17));
        cantidad.setFont(new Font("Times New Roman", Font.BOLD, 17));
        agregar.setFont(new Font("Times New Roman", Font.BOLD, 15));
        regresar.setFont(new Font("Times New Roman", Font.BOLD, 15));
        
        //Agregar objeto en la ventana 
        ventana.add(titulo);
        ventana.add(mensaje);
        ventana.add(ComboProductos);
        ventana.add(agregar);
        ventana.add(regresar);
        ventana.add(seleccion);
        ventana.add(cantidadtxt);
        ventana.add(cantidad);
        ventana.add(x);
        
        //accion del combo box que va a comunicarse con el codigo
        for(ProductoCompra p: CargarProductos()){
            ComboProductos.addItem(p);
        }
        
        //De acuerdo lo que se haga elegido en la lista
        //y en la cantidad esta misma se agrega en nuestro carrito
        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoCompra seleccionado = (ProductoCompra) ComboProductos.getSelectedItem();
                try {
                    int cantidad = Integer.parseInt(cantidadtxt.getText());
                    if(cantidad <= 0) throw  new NumberFormatException();
                    
                    carrito.add(new ProductoCompra(seleccionado.getCodigo(),
                            seleccionado.getNombre(),
                            cantidad,
                            seleccionado.getPrecio()
                    ));
                    
                    JOptionPane.showMessageDialog(null, "Producto agregado al carrito");
                    cantidadtxt.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Cantidad inválida");
                }
            }
        });
        
        //Dado caso que no quiera seguir agregando productos se puede ir a los detalles
        regresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.dispose();
                DetalleCompra detalle = new DetalleCompra(carrito);
                detalle.iniciar();
            }
        });
        
        //cositas a tomar en cuenta para la ventana
        ventana.setVisible(true);
        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
    
    }
    
    //Funcion para que tome en cuenta el codigo, nombre y precio del producto
    //esta misma se va a mostrar en un combobox 
    private List<ProductoCompra> CargarProductos() {
        List<ProductoCompra> lista = new ArrayList<>();
        try (Connection conn = Conexion.conectar()) {
            String sql = "SELECT cod_producto, nombre_producto, precio_producto FROM producto";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new ProductoCompra(
                    rs.getString("cod_producto"),
                    rs.getString("nombre_producto"),
                    1,
                    rs.getDouble("precio_producto")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
}
    


//Importar las cosas que se van a utilizar
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;




public class DetalleCompra extends JFrame{
    
    //Los objetos que se van a utilizar 
    JFrame ventana;
    JLabel titulo,mensaje,metodo,lsub,liva,ltotal,x;
    JButton eliminar, agregar, aceptar;
    JComboBox <String> metodoOp;
    DefaultTableModel modelo;
    JTable tabla;
    //La lista que viene de comunicacion con la clase Compra y ProductoCompra
    List<ProductoCompra> carrito;

    //Que tome en cuenta el carrito que viene de la otra ventana y la lista que viene 
    //en productoCompra que guarda cada lista
    public DetalleCompra(List<ProductoCompra> carrito) {
        this.carrito = carrito;
   }
    
    //Inicio de la ventana
    public void iniciar(){
        
        //la instancia de cada objeto
        ventana = new JFrame("Petsnagoa");
        titulo = new JLabel("Pestnagoa Detalles");
        mensaje = new JLabel("verifique los datos antes de proceder la factura");
        metodo = new JLabel("Metodo de pago:");
        metodoOp = new JComboBox<>(new String[]{"Seleccione:","Efectivo bs","Tarjeta", "Transferencia"});
        lsub = new JLabel("Subtotal: Bs. 0.00");
        liva = new JLabel("IVA (16%): Bs. 0.00");
        ltotal = new JLabel("Total: Bs. 0.00");
        eliminar = new JButton("Eliminar");
        agregar = new JButton("Agregar");
        aceptar = new JButton("Aceptar");
        
        x= new JLabel();
        
        // Funcion para que no se pueda modificar nada dentro de la tabla
        modelo = new DefaultTableModel(new String[]{"Código", "Nombre", "Cantidad", "Precio Unitario", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Solo cantidad editable
            }
        };
        
        //la creacion y donde va a  estar ubicado la tabla
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(15, 115, 400, 115);
        
        //tamaño y ubicacion de cada objeto
        ventana.setSize(450, 350);
        titulo.setBounds(130, 2, 300, 45);
        mensaje.setBounds(25, 50, 400, 20);
        metodo.setBounds(10, 50, 150, 90);
        metodoOp.setBounds(135, 87, 110, 20);
        //contra.setBounds(35, 30, 150, 90);
        eliminar.setBounds(35, 275, 100, 30);
        agregar.setBounds(170, 275, 100, 30);
        aceptar.setBounds(300, 275, 100, 30);
        lsub.setBounds(20, 235, 200, 25);
        liva.setBounds(180, 235, 200, 25);
        ltotal.setBounds(300, 235, 200, 25);

        //modificar el tamaño, estilo y el tipo de letra en los botones
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 25));
        mensaje.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        metodo.setFont(new Font("Times New Roman", Font.BOLD, 17));
        eliminar.setFont(new Font("Times New Roman", Font.BOLD, 15));
        agregar.setFont(new Font("Times New Roman", Font.BOLD, 15));
        aceptar.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lsub.setFont(new Font("Times New Roman", Font.BOLD, 16));
        liva.setFont(new Font("Times New Roman", Font.BOLD, 16));
        ltotal.setFont(new Font("Times New Roman", Font.BOLD, 16));
        
        //Se agregan las cosas en la ventana
        ventana.add(titulo);
        ventana.add(mensaje);
        ventana.add(metodo);
        ventana.add(metodoOp);
        ventana.add(eliminar);
        ventana.add(agregar);
        ventana.add(aceptar);
        ventana.add(lsub);
        ventana.add(liva);
        ventana.add(ltotal);
        ventana.add(scroll);
        
        //solo sirve para que no se descuadre todo jaja
        ventana.add(x);

        //Se va a la funcion compra en caso de que quiera agregar algo mas
        agregar.addActionListener((ActionEvent e) -> {
            ventana.dispose();
            Compra com = new Compra(carrito);
            com.iniciar();
        });
        
        //Se seleciona y se pregunta cuanta cantidad quiere eliminar del carrito
        eliminar.addActionListener(e -> {
                int fila = tabla.getSelectedRow();

                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Selecciona un producto para eliminar.");
                    return;
                }

                String codigo = modelo.getValueAt(fila, 0).toString(); // Código del producto
                int cantidadActual = Integer.parseInt(modelo.getValueAt(fila, 2).toString()); // Cantidad actual

                String input = JOptionPane.showInputDialog("¿Cuántas unidades deseas eliminar?");
                if (input == null || input.isEmpty()) return;

                try {
                    int cantidadEliminar = Integer.parseInt(input);

                    if (cantidadEliminar <= 0 || cantidadEliminar > cantidadActual) {
                        JOptionPane.showMessageDialog(null, "Cantidad inválida.");
                        return;
                    }

                    if (cantidadEliminar == cantidadActual) {
                        carrito.remove(fila); // Elimina el producto del carrito
                    } else {
                        carrito.get(fila).setCantidad(cantidadActual - cantidadEliminar); // Resta cantidad
                    }

                    actualizarTabla();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida.");
                }
        });

        //Una vez listo y verificado, toma en cuenta el metodo
        //la tabla para dar la factura definitiva
        aceptar.addActionListener((ActionEvent e) -> {
            String metodoPago = metodoOp.getSelectedItem().toString();
            Factura factu = new Factura(carrito);
            Modelo mo = new Modelo();
            int idCliente = Seccion.idCliente;
            int idCajero = Seccion.idCajero;
            if (metodoPago.equals("Seleccione:")) {
                JOptionPane.showMessageDialog(null, "Selecciona un método de pago");
                return;
            }
            
            for (ProductoCompra producto : carrito) {
                int idProducto = mo.obtenerIdProductoDesdeCodigo(producto.getCodigo());
                mo.Insertar_Factura(idProducto, idCliente, idCajero, metodoPago);
            }
            
            //toma las variables desde aqui y las envia en la otra clase en este
            //caso a factura
            factu.setMetodoPago(metodoPago);
            factu.setFechaYHora(mo.obtenerFechaFacturaReciente());
            factu.setClienteNombre(mo.obtenerNombreCliente(idCliente));
            factu.setCajeroNombre(mo.obtenerNombreCajero(idCajero));
            factu.iniciar();
            ventana.dispose();
        });
        
        //llamamos la funcion que actualice y recalcule
        actualizarTabla();
        
        //accion de crear la tabla de acuerdo lo que venga de la clase compra a traves del carrito
        
        modelo.addTableModelListener(e ->{
                if(e.getType() == TableModelEvent.UPDATE && e.getColumn() ==2){
                 int fila = e.getFirstRow();
                try {
                int nuevaCantidad = Integer.parseInt(modelo.getValueAt(fila, 2).toString());
                carrito.get(fila).setCantidad(nuevaCantidad);
                actualizarTabla();
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
    
    //Funcion para actualizar la tabla y recalcular en cada label
   public void actualizarTabla(){
   modelo.setRowCount(0);
   double subtotal = 0;
   
    for(ProductoCompra p: carrito){
        double totalproducto = p.getCantidad() * p.getPrecio();
        subtotal += totalproducto;
        
        modelo.addRow(new Object[]{
        p.getCodigo(),
        p.getNombre(),
        p.getCantidad(),
        String.format("Bs. %.2f", p.getPrecio()),
       String.format("Bs. %.2f", totalproducto)
    });
    }
    
    double iva = subtotal * 0.16;
    double total = subtotal + iva;
    
    lsub.setText(String.format("Subtotal: Bs. %.2f", subtotal));
    liva.setText(String.format("IVA: Bs. %.2f", iva));
    ltotal.setText(String.format("Total: Bs. %.2f", total));
   } 
  

}
    


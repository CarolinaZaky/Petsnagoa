//Librerias a utilizar
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class Factura extends JFrame{
    //Los objetos que se van a utilizar
    JFrame ventana;
    JLabel titulo,condicion,metodo,cliente,lsub,liva,ltotal,fechayhora,cajero,x;
    JButton regresar, salir,historial;
    DefaultTableModel modelo;
    JTable tabla;
    List<ProductoCompra> carrito;

    //Las variables que viajan de la clase anterior y mostrarlo aqui
    private String metodoPago;
    private String clienteNombre;
    private String cajeroNombre;
    private String fechaYHora;

public void setClienteNombre(String nombre) {
    this.clienteNombre = nombre;
}

public void setCajeroNombre(String nombre) {
    this.cajeroNombre = nombre;
}

public void setFechaYHora(String fechaYHora) {
    this.fechaYHora = fechaYHora;
}

public void setMetodoPago(String metodoPago) {
    this.metodoPago = metodoPago;
}

    public Factura(List<ProductoCompra> carrito) {
        this.carrito = carrito;
   }
    
    //Inicio de la ventana
    public void iniciar(){
        //Llamamos a cada objeto
        ventana = new JFrame("Petsnagoa");
        titulo = new JLabel("Pestnagoa Factura");
        condicion = new JLabel("Metodo de pago:");
        metodo = new JLabel("xx");
        cliente = new JLabel("Cliente:");
        lsub = new JLabel("Subtotal: Bs. 0.00");
        liva = new JLabel("IVA (16%): Bs. 0.00");
        ltotal = new JLabel("Total: Bs. 0.00");
        fechayhora = new JLabel("Fecha y hora:");
        cajero = new JLabel("Cajero:");
        regresar = new JButton("Regresar");
        salir = new JButton("Salir");
        historial = new JButton("Historial");
        //Esto solo sirve para que nada se descuadre
        x= new JLabel();
        
        //Las variables que tomamos de la ventana anterior se reflejen en las etiquetas
        metodo.setText(metodoPago);
        cliente.setText("Cliente: " + clienteNombre);
        cajero.setText("Cajero: " + cajeroNombre);
        fechayhora.setText("Fecha y hora: " + fechaYHora);

        
        // Tabla
        modelo = new DefaultTableModel(new String[]{"Código", "Nombre", "Cantidad", "Precio Unitario", "Total"}, 0) {
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
        scroll.setBounds(15, 105, 400, 125);
        
        //tamaño en x, y, ancho y largo
        ventana.setSize(450, 350);
        titulo.setBounds(130, 2, 300, 45);
        condicion.setBounds(10, 55, 400, 20);
        fechayhora.setBounds(10, 45, 400, 90);
        cajero.setBounds(270, 45, 150, 90);
        metodo.setBounds(135, 20, 150, 90);
        cliente.setBounds(250, 20, 400, 90);
        historial.setBounds(168, 275, 100, 30);
        regresar.setBounds(35, 275, 100, 30);
        salir.setBounds(300, 275, 100, 30);
        lsub.setBounds(20, 235, 200, 25);
        liva.setBounds(180, 235, 200, 25);
        ltotal.setBounds(300, 235, 200, 25);

        //modificar el tamaño, estilo y el tipo de letra en los botones
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 25));
        condicion.setFont(new Font("Times New Roman", Font.BOLD, 17));
        metodo.setFont(new Font("Times New Roman", Font.BOLD, 17));
        fechayhora.setFont(new Font("Times New Roman", Font.BOLD, 17));
        cajero.setFont(new Font("Times New Roman", Font.BOLD, 17));
        regresar.setFont(new Font("Times New Roman", Font.BOLD, 15));
        salir.setFont(new Font("Times New Roman", Font.BOLD, 15));
        historial.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lsub.setFont(new Font("Times New Roman", Font.BOLD, 16));
        liva.setFont(new Font("Times New Roman", Font.BOLD, 16));
        cliente.setFont(new Font("Times New Roman", Font.BOLD, 17));
        ltotal.setFont(new Font("Times New Roman", Font.BOLD, 16));
        
        //Agregar objeto en la ventana
        ventana.add(titulo);
        ventana.add(condicion);
        ventana.add(metodo);
        ventana.add(regresar);
        ventana.add(salir);
        ventana.add(historial);
        ventana.add(cliente);
        ventana.add(lsub);
        ventana.add(liva);
        ventana.add(ltotal);
        ventana.add(scroll);
        ventana.add(fechayhora);
        ventana.add(cajero);
        ventana.add(x);
        
        //Pregunta para estar con el mismo, otro cliente o salir.
        regresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           int answer = -1;
                do {
                    String input = JOptionPane.showInputDialog(
                        null,
                        "¿Qué desea hacer?\n1. Seguir Comprando con el mismo cliente.\n2. Regresar desde el principio.\n3. Salir del programa.",
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
                        case 1:
                            ventana.dispose();
                            DetalleCompra deta = new DetalleCompra(carrito);
                            deta.iniciar();
                            break;
                        case 2:
                            ventana.dispose();
                            RegistroCliente regis = new RegistroCliente(carrito);
                            regis.iniciar();
                            break;
                        case 3:
                            JOptionPane.showMessageDialog(null, "Gracias hasta llegar al final! Hasta luego!");
                            System.exit(0);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "La opción es inexistente, intente nuevamente.", "Pestnagoa,te recuerda", JOptionPane.WARNING_MESSAGE);
                            break;
                    }
                    
                } while (answer < 1 || answer > 3);
            }
            
        });

        //Salir del programa
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Gracias por probar el sistema de Petsnagoa!");
                JOptionPane.showMessageDialog(null, "Hasta luego!!");
                System.exit(0);
            }
        });
        
        //Historial lo que exista en mi base de datos
        historial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int answer = -1;
                do {
                    String input = JOptionPane.showInputDialog(
                        null,
                        "¿Qué desea hacer?\n1. Seguir viendo solo su factura.\n2. Ver el historial de todos que han comprado.",
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
                        case 1:
                            JOptionPane.showMessageDialog(null, "Ya estas aqui, cuál es el chiste?");
                            break;
                        case 2:
                            ventana.dispose();
                            Historial histo = new Historial(carrito);
                            histo.iniciar();
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "La opción es inexistente, intente nuevamente.", "Pestnagoa,te recuerda", JOptionPane.WARNING_MESSAGE);
                            break;
                    }
                    
                } while (answer < 1 || answer > 2);
            }
           
        });
        
        //funcion de actualizar la tabla
        actualizarTabla();
        
        //De como se va a verse la tabla
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
    
    //Funcion de actualizar la tabla y recalcular en las etiquetas
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
    


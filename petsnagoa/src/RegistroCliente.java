//Importamos las librerias a utilizar
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.List;

public class RegistroCliente {
    //Los objetos que se van a utilizar
    JFrame ventana;
    JLabel titulo,mensaje,cd,correo,nombre,apellido,telefono,direccion,x;
    JTextField nombretxt,apellidotxt,cdtxt,correotxt,telefonotxt,direcciontxt;
    JButton limpiar, registrar, siguiente;
    List<ProductoCompra> carrito;
    
    //no afecta al codigo en si solo viaja
    public RegistroCliente(List<ProductoCompra> carrito) {
        this.carrito = carrito;
    }
    //Inicio de la ventana
    public void iniciar(){
        //LLamamos a cada objeto que se van a utilizar a la ventana
        ventana = new JFrame("Petsnagoa");
        titulo = new JLabel("Pestnagoa Registro");
        mensaje = new JLabel("Ingrese los datos correspondientes del cliente");
        cd = new JLabel("Cédula:");
        correo = new JLabel("Correo:");
        nombre = new JLabel("Nombre:");
        apellido = new JLabel("Apellido:");
        telefono = new JLabel("Telefono:");
        direccion = new JLabel("Dirección:");
        cdtxt = new JTextField();
        correotxt = new JTextField();
        nombretxt = new JTextField();
        apellidotxt = new JTextField();
        telefonotxt = new JTextField();
        direcciontxt = new JTextField();
        limpiar = new JButton("Limpiar");
        registrar = new JButton("Registrar");
        siguiente = new JButton("Siguiente");
        //Recuerda solo sirve que no se descuadre todo
        x= new JLabel();
        
        //tamaño en x, y, ancho y largo
        ventana.setSize(450, 350);
        titulo.setBounds(90, 2, 300, 50);
        mensaje.setBounds(35, 50, 400, 35);
        cd.setBounds(20, 75, 150, 90);
        correo.setBounds(215, 75, 150, 90);
        nombre.setBounds(20, 110, 150, 90);
        apellido.setBounds(20, 140, 100, 90);
        telefono.setBounds(200, 110, 150, 90);
        direccion.setBounds(200, 140, 100, 90);
        cdtxt.setBounds(85, 110, 100, 20);
        correotxt.setBounds(275, 110, 150, 20);
        telefonotxt.setBounds(275, 145, 150, 20);
        nombretxt.setBounds(85, 145, 100, 20);
        apellidotxt.setBounds(85, 175, 100, 20);
        direcciontxt.setBounds(275, 175, 150, 20);
        limpiar.setBounds(35, 250, 100, 30);
        registrar.setBounds(170, 250, 100, 30);
        siguiente.setBounds(300, 250, 100, 30);

        //modificar el tamaño, estilo y el tipo de letra en los botones
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 30));
        mensaje.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        cd.setFont(new Font("Times New Roman", Font.BOLD, 17));
        correo.setFont(new Font("Times New Roman", Font.BOLD, 17));
        nombre.setFont(new Font("Times New Roman", Font.BOLD, 17));
        apellido.setFont(new Font("Times New Roman", Font.BOLD, 17));
        telefono.setFont(new Font("Times New Roman", Font.BOLD, 17));
        limpiar.setFont(new Font("Times New Roman", Font.BOLD, 17));
        direccion.setFont(new Font("Times New Roman", Font.BOLD, 17));
        registrar.setFont(new Font("Times New Roman", Font.BOLD, 17));
        siguiente.setFont(new Font("Times New Roman", Font.BOLD, 17));

        //Agregar los objetos en la ventana
        ventana.add(titulo);
        ventana.add(mensaje);
        ventana.add(cd);
        ventana.add(correotxt);
        ventana.add(cdtxt);
        ventana.add(correo);
        ventana.add(nombre);
        ventana.add(apellido);
        ventana.add(nombretxt);
        ventana.add(apellidotxt);
        ventana.add(telefono);
        ventana.add(telefonotxt);
        ventana.add(direccion);
        ventana.add(direcciontxt);
        ventana.add(limpiar);
        ventana.add(registrar);
        ventana.add(siguiente);
        ventana.add(x);
        
        //Acciones de cada boton
        
        //Limpia cada campo y redirige
        limpiar.addActionListener((ActionEvent e) -> {
            cdtxt.requestFocus();
            nombretxt.setText(null);
            apellidotxt.setText(null);
            cdtxt.setText(null);
            correotxt.setText(null);
            direcciontxt.setText(null);
            telefonotxt.setText(null);
        });
        
        //Pregunta para continuar, registrar o salir del programa
        siguiente.addActionListener((ActionEvent e) -> {
            int answer = -1;
            do {
                String input = JOptionPane.showInputDialog(
                        null,
                        "¿Qué desea hacer?\n1. Seguir registrando.\n2. Continuar.\n3. Salir del programa.",
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
                    case 1 -> JOptionPane.showMessageDialog(null, "Proceda a colocar los otros datos.");
                    case 2 -> {
                        
                        String cedulatexto = cdtxt.getText().trim();
                        Modelo mo = new Modelo();
                        
                        if (cedulatexto.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, coloque una cédula del cliente.");
                        return;
                        }
                        
                        if(mo.UsuarioExistenteCliente(cedulatexto)){
                        Seccion.cedulaCliente = cedulatexto;
                        Seccion.idCliente = mo.obtenerIdClienteDesdeCedula(cedulatexto);
                        ventana.dispose();
                        Compra com = new Compra(carrito);
                        com.iniciar();
                        }else{
                        JOptionPane.showMessageDialog(null, "Cédula del cliente es  incorrecto y/o inexsistente.");
                           }
                        
                    }
                    case 3 -> System.exit(0);
                    default -> JOptionPane.showMessageDialog(null, "La opción es inexistente, intente nuevamente.", "Pestnagoa,te recuerda", JOptionPane.WARNING_MESSAGE);
                }
                
            } while (answer < 1 || answer > 3);
        });

        //Registra el cliente en nuestra base de datos
        registrar.addActionListener((ActionEvent e) -> {
            String cedulatexto = cdtxt.getText().trim();
            String nombretexto = nombretxt.getText().trim();
            String apellidotexto = apellidotxt.getText().trim();
            String correotexto = correotxt.getText().trim();
            String telefonotexto = telefonotxt.getText().trim();
            String direcciontexto = direcciontxt.getText().trim();
            
            if (cedulatexto.isEmpty() || nombretexto.isEmpty() || apellidotexto.isEmpty() || correotexto.isEmpty()|| telefonotexto.isEmpty() || direcciontexto.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.");
                return;
            }
            
            Modelo mo = new Modelo();
            mo.Insertar_Cliete(cedulatexto, nombretexto, apellidotexto, correotexto, telefonotexto, direcciontexto);
        });
        
        //cositas a tomar en cuenta para la ventana
        ventana.setVisible(true);
        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
    
    }
}
    


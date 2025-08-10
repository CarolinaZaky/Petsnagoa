//Importamos lo que se va a utilizar
import java.util.List;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RegistroCajero {
    
    //Objetos a utilizar
    JFrame ventana;
    JLabel titulo,mensaje,nombre,apellido,usu,contra,x;
    JTextField nombretxt,apellidotxt,usutxt,contratxt;
    JButton limpiar, aceptar, salir;
    List<ProductoCompra> carrito;
    
    //No afecta directamente al registro compra
    public RegistroCajero(List<ProductoCompra> carrito) {
        this.carrito = carrito;
    }
    
    //Inicia la ventana
    public void iniciar(){
        //LLamamos a cada objeto
        ventana = new JFrame("Petsnagoa");
        titulo = new JLabel("Pestnago Registro");
        mensaje = new JLabel("Ingrese los datos correspondientes");
        usu = new JLabel("Usuario:");
        contra = new JLabel("Contraseña:");
        nombre = new JLabel("Nombre:");
        apellido = new JLabel("Apellido:");
        usutxt = new JTextField();
        contratxt = new JTextField();
        nombretxt = new JTextField();
        apellidotxt = new JTextField();
        limpiar = new JButton("Limpiar");
        aceptar = new JButton("Aceptar");
        salir = new JButton("Regresar");
        //Recuerda solo existe para que ayude a no descuadrar todo
        x= new JLabel();
        
        //tamaño en x, y, ancho y largo
        ventana.setSize(450, 350);
        titulo.setBounds(80, 2, 300, 80);
        mensaje.setBounds(75, 50, 400, 90);
        usu.setBounds(20, 140, 150, 90);
        contra.setBounds(200, 140, 150, 90);
        nombre.setBounds(20, 110, 150, 90);
        apellido.setBounds(225, 110, 100, 90);
        usutxt.setBounds(85, 175, 100, 20);
        contratxt.setBounds(295, 175, 100, 20);
        nombretxt.setBounds(85, 145, 100, 20);
        apellidotxt.setBounds(295, 145, 100, 20);
        limpiar.setBounds(35, 250, 100, 30);
        aceptar.setBounds(170, 250, 100, 30);
        salir.setBounds(300, 250, 100, 30);

        //modificar el tamaño, estilo y el tipo de letra en los botones
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 35));
        mensaje.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        usu.setFont(new Font("Times New Roman", Font.BOLD, 17));
        contra.setFont(new Font("Times New Roman", Font.BOLD, 17));
        nombre.setFont(new Font("Times New Roman", Font.BOLD, 17));
        apellido.setFont(new Font("Times New Roman", Font.BOLD, 17));
        limpiar.setFont(new Font("Times New Roman", Font.BOLD, 17));
        aceptar.setFont(new Font("Times New Roman", Font.BOLD, 17));
        salir.setFont(new Font("Times New Roman", Font.BOLD, 17));

        //Agregar objetos en la ventana
        ventana.add(titulo);
        ventana.add(mensaje);
        ventana.add(usu);
        ventana.add(contratxt);
        ventana.add(usutxt);
        ventana.add(contra);
        ventana.add(nombre);
        ventana.add(apellido);
        ventana.add(nombretxt);
        ventana.add(apellidotxt);
        ventana.add(limpiar);
        ventana.add(aceptar);
        ventana.add(salir);
        ventana.add(x);
        
        //Acciones de cada boton
        
        //Limpia cada campo y redirige
        limpiar.addActionListener((ActionEvent e) -> {
            nombretxt.requestFocus();
            nombretxt.setText(null);
            apellidotxt.setText(null);
            usutxt.setText(null);
            contratxt.setText(null);
        });
        
        //Sale de acuerdo a lo que se quiere hacer
        salir.addActionListener((ActionEvent e) -> {
            int answer = -1;
            do {
                String input = JOptionPane.showInputDialog(
                        null,
                        "¿Qué desea hacer?\n1. Seguir registrando.\n2. Regresar a la ventana anterior.\n3. Salir del programa.",
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
                        JOptionPane.showMessageDialog(null, "Proceda a colocar los otros datos.");
                        break;
                    case 2:
                        ventana.dispose();
                        LoginCajero login = new LoginCajero(carrito);
                        login.iniciar();
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "La opción es inexistente, intente nuevamente.", "Pestnagoa,te recuerda", JOptionPane.WARNING_MESSAGE);
                        break;
                }
                
            } while (answer < 1 || answer > 3);
        });

        //Agregamos el nuevo cajero a la base de datos
        aceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           
                String usutexto = usutxt.getText().trim();
                String contratexto = contratxt.getText().trim();
                String nombretexto = nombretxt.getText().trim();
                String apellidotexto = apellidotxt.getText().trim();
                
                if (usutexto.isEmpty() || contratexto.isEmpty() || nombretexto.isEmpty() || apellidotexto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.");
                    return;
                }
                
                Modelo mo = new Modelo();
                mo.Insertar_Cajero(nombretexto, apellidotexto, usutexto, contratexto);
            
            }
        });
        
        //cositas a tomar en cuenta para la ventana
        ventana.setVisible(true);
        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
    
    }
}
    


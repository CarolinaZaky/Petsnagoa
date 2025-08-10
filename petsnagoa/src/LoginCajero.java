//Importar librerias a utilizar
import java.util.List;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;


public class LoginCajero {
    
    //Objetos a utilizar
    JFrame ventana;
    JLabel titulo,mensaje,usu,contra,x;
    JTextField usutxt,contratxt;
    JButton limpiar, registrar, aceptar;
    List<ProductoCompra> carrito;
    
    //El carrito no afecta en nada solo que el programa funcione
    public LoginCajero(List<ProductoCompra> carrito) {
        this.carrito = carrito;
    }
    
    //Inicio de la ventana
    public void iniciar(){
        
        //Llamamos a los objetos
        ventana = new JFrame("Petsnagoa");
        titulo = new JLabel("Pestnagoa Inicio");
        mensaje = new JLabel("Bienvenido,ingrese los datos para continuar");
        usu = new JLabel("Usuario:");
        contra = new JLabel("Contraseña:");
        usutxt = new JTextField();
        contratxt = new JTextField();
        limpiar = new JButton("Limpiar");
        registrar = new JButton("Registrar");
        aceptar = new JButton("Aceptar");
        //la x para que no se descuadre todo
        x= new JLabel();
        
        //tamaño en x, y, ancho y largo
        ventana.setSize(450, 350);
        titulo.setBounds(100, 2, 300, 80);
        mensaje.setBounds(35, 50, 400, 90);
        usu.setBounds(35, 110, 150, 90);
        contra.setBounds(35, 140, 150, 90);
        usutxt.setBounds(100, 145, 100, 20);
        contratxt.setBounds(130, 175, 100, 20);
        limpiar.setBounds(35, 250, 100, 30);
        registrar.setBounds(170, 250, 100, 30);
        aceptar.setBounds(300, 250, 100, 30);

        //modificar el tamaño, estilo y el tipo de letra en los botones
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 35));
        mensaje.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        usu.setFont(new Font("Times New Roman", Font.BOLD, 17));
        contra.setFont(new Font("Times New Roman", Font.BOLD, 17));
        limpiar.setFont(new Font("Times New Roman", Font.BOLD, 17));
        registrar.setFont(new Font("Times New Roman", Font.BOLD, 17));
        aceptar.setFont(new Font("Times New Roman", Font.BOLD, 17));

        //Agregar los objetos en la ventana
        ventana.add(titulo);
        ventana.add(mensaje);
        ventana.add(usu);
        ventana.add(contratxt);
        ventana.add(usutxt);
        ventana.add(contra);
        ventana.add(limpiar);
        ventana.add(registrar);
        ventana.add(aceptar);
        ventana.add(x);
        
            //Botones con sus respectivas acciones
            
            //Limpia los campos y lo redirije
            limpiar.addActionListener((ActionEvent e) -> {
                usutxt.setText(null);
                contratxt.setText(null);
                usutxt.requestFocus();
        });
            
            //En caso de no existir se va a Registrar el cajero
             registrar.addActionListener((ActionEvent e) -> {
                 ventana.dispose();
                 RegistroCajero regis = new RegistroCajero(carrito);
                 regis.iniciar();
        });
        
             //Si existe ambos campos puede continuar a la ventana del cliente
            aceptar.addActionListener((ActionEvent e) -> {
                String usutexto = usutxt.getText().trim();
                String contratexto = contratxt.getText().trim();
                
                Modelo mo = new Modelo();
                RegistroCliente cliente = new RegistroCliente(carrito);
                
                if (usutexto.isEmpty() || contratexto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.");
                    return;
                }
                if (mo.ValidarCajero(usutexto, contratexto)) {
                    Seccion.idCajero = mo.obtenerIdCajeroDesdeUsuario(usutexto);
                    Seccion.nombreCajero = usutexto;
                    ventana.dispose();
                    JOptionPane.showMessageDialog(null, "¡Bienvenido, " + usutexto + "!");
                    cliente.iniciar();
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos/inexsistente.");
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
    


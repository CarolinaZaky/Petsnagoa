//Librerias
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.List;

public class Bienvenida {
    //Objetos a utilizar
    JFrame ventana;
    JLabel titulo, mensaje, mensaje2,creador,x;
    JButton siguiente, salir;
    List<ProductoCompra> carrito;
    
    //El carrito viaja mas no afecta en nada
    public Bienvenida(List<ProductoCompra> carrito) {
        this.carrito = carrito;
    }
    
    //Inicio de la ventana
    public void iniciar(){
        
        //Llamamos a los objetos a utilizar
        ventana = new JFrame("Petsnagoa");
        siguiente = new JButton("Siguiente");
        salir = new JButton("Salir");
        titulo = new JLabel("Pestnagoa");
        mensaje = new JLabel("Bienvenido a nuestra tienda de mascotas");
        mensaje2 = new JLabel("de su sistema de facturación");
        creador = new JLabel("Credito: Castillo Marian y MiMi (Laptop)");
        x = new JLabel();
        
        //tamaño en x, y, ancho y largo
        ventana.setSize(450, 350);
        siguiente.setBounds(300, 250, 100, 30);
        titulo.setBounds(140, 2, 200, 80);
        mensaje.setBounds(50, 50, 800, 90);
        mensaje2.setBounds(115, 110, 350, 30);
        creador.setBounds(50, 180, 500, 30);
        salir.setBounds(35, 250, 100, 30);
        
        //modificar el tamaño, estilo y el tipo de letra en los botones
        mensaje.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        mensaje2.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        creador.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 20));
        siguiente.setFont(new Font("Times new Roman", Font.BOLD,17));
        salir.setFont(new Font("Times new Roman", Font.BOLD,17));
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 35));
        
        //invocarlo en la ventana
        ventana.add(siguiente);
        ventana.add(salir);
        ventana.add(titulo);
        ventana.add(mensaje);
        ventana.add(mensaje2);
        ventana.add(creador);
        ventana.add(x);
        
        //Accion de continuar
        siguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    ventana.dispose();
                    LoginCajero login = new LoginCajero(carrito); //el siguiente objeto a llamar
                    login.iniciar();
        
            }
        });
        
        //Accion de salir de la ventana
        salir.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "Gracias por ver! Adios!");
            System.exit(0); //otra manera de salir 
        });
        
        //cositas a tomar en cuenta para la ventana
        ventana.setVisible(true);
        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);

    }
    
}

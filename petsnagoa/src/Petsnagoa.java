//Se importan libreria para que tome en cuenta el carrito que no va afectar en nada de cada funcion
import java.util.ArrayList;
import java.util.List;


public class Petsnagoa {


    public static void main(String[] args) {
        List<ProductoCompra> carrito = new ArrayList<>();


        Bienvenida bien = new Bienvenida(carrito);
        
        bien.iniciar();

        //Factura factu = new Factura(carrito);
        //factu.iniciar();
        
        //Historial histo = new Historial(carrito);
        //histo.iniciar();
        
    }
    
}

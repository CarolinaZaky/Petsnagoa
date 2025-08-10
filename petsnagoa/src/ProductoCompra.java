//La logica para guardar la lista de cada producto
public class ProductoCompra {
    private String codigo;
    private String nombre;
    private int cantidad;
    private double precio;

    public ProductoCompra(String codigo, String nombre, int cantidad, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return nombre + " (Bs. " + String.format("%.2f", precio) + ")";
    }
}

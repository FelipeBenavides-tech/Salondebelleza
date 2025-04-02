
package model;

public class Producto {
    private int productoId;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private int stockMinimo;
    private byte[] imagen;
    private int categoriaId;
    private boolean activo;
    
    // Constructor vacío
    public Producto() {
    }
    
    // Constructor completo
    public Producto(int productoId, String nombre, String descripcion, double precio, 
                   int stock, int stockMinimo, byte[] imagen, int categoriaId, boolean activo) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.imagen = imagen;
        this.categoriaId = categoriaId;
        this.activo = activo;
    }
    
    // Getters y Setters
    public int getProductoId() {
        return productoId;
    }
    
    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public int getStockMinimo() {
        return stockMinimo;
    }
    
    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
    
    public byte[] getImagen() {
        return imagen;
    }
    
    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
    
    public int getCategoriaId() {
        return categoriaId;
    }
    
    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    // Método para verificar si el stock está bajo
    public boolean stockBajo() {
        return stock <= stockMinimo;
    }
}

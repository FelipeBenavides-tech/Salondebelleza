
package model;

public class Servicio {
    private int servicioId;
    private String nombre;
    private String descripcion;
    private double precio;
    private int tiempoEstimado;
    private byte[] imagen;
    private int categoriaId;
    private boolean activo;
    
    // Constructor vacío
    public Servicio() {
    }
    
    // Constructor completo
    public Servicio(int servicioId, String nombre, String descripcion, double precio, 
                   int tiempoEstimado, byte[] imagen, int categoriaId, boolean activo) {
        this.servicioId = servicioId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tiempoEstimado = tiempoEstimado;
        this.imagen = imagen;
        this.categoriaId = categoriaId;
        this.activo = activo;
    }
    
    // Getters y Setters
    public int getServicioId() {
        return servicioId;
    }
    
    public void setServicioId(int servicioId) {
        this.servicioId = servicioId;
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
    
    public int getTiempoEstimado() {
        return tiempoEstimado;
    }
    
    public void setTiempoEstimado(int tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
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
}

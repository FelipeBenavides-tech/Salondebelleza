

package controller;


public class ProductoController {
    private ProductoDao productoDao;
    
    public ProductoController() {
        productoDao = new ProductoDao();
    }
    
    /**
     * Método para registrar un nuevo producto
     * @param nombre Nombre del producto
     * @param descripcion Descripción del producto
     * @param precio Precio del producto
     * @param stock Stock inicial
     * @param stockMinimo Stock mínimo
     * @param imagen Imagen del producto (puede ser null)
     * @param categoriaId ID de la categoría
     * @return Resultado de la operación (1 = éxito, 0 = error)
     */
    public int registrarProducto(String nombre, String descripcion, double precio, 
                               int stock, int stockMinimo, byte[] imagen, int categoriaId) {
        // Validar campos
        if (nombre == null || nombre.trim().isEmpty() ||
            precio <= 0 || stock < 0 || stockMinimo < 0 || categoriaId <= 0) {
            return 0;
        }
        
        // Crear el producto
        Producto producto = new Producto(0, nombre, descripcion, precio, 
                                        stock, stockMinimo, imagen, categoriaId, true);
        
        // Guardar en la base de datos
        return productoDao.setAgregar(producto);
    }
    
    /**
     * Método para actualizar un producto existente
     * @param id ID del producto
     * @param nombre Nombre actualizado
     * @param descripcion Descripción actualizada
     * @param precio Precio actualizado
     * @param stockMinimo Stock mínimo actualizado
     * @param imagen Imagen actualizada (puede ser null)
     * @param categoriaId ID de categoría actualizado
     * @return Resultado de la operación (1 = éxito, 0 = error)
     */
    public int actualizarProducto(int id, String nombre, String descripcion, double precio, 
                                int stockMinimo, byte[] imagen, int categoriaId) {
        // Obtener el producto actual
        Producto producto = productoDao.obtenerPorId(id);
        
        if (producto == null) {
            return 0; // Producto no encontrado
        }
        
        // Actualizar los campos
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStockMinimo(stockMinimo);
        
        // Solo actualizar la imagen si se proporciona una nueva
        if (imagen != null) {
            producto.setImagen(imagen);
        }
        
        producto.setCategoriaId(categoriaId);
        
        // Guardar cambios
        return productoDao.setActualizar(producto);
    }
    
    /**
     * Método para actualizar el stock de un producto
     * @param id ID del producto
     * @param cantidad Cantidad a agregar (positiva) o restar (negativa)
     * @return Resultado de la operación (true = éxito, false = error)
     */
    public boolean actualizarStock(int id, int cantidad) {
        return productoDao.actualizarStock(id, cantidad);
    }
    
    /**
     * Método para eliminar un producto (desactivarlo)
     * @param id ID del producto a eliminar
     * @return Resultado de la operación (1 = éxito, 0 = error)
     */
    public int eliminarProducto(int id) {
        return productoDao.setEliminar(id);
    }
    
    /**
     * Método para obtener todos los productos activos
     * @return Lista de productos activos
     */
    public List<Producto> obtenerTodos() {
        return productoDao.listar();
    }
    
    /**
     * Método para obtener productos por categoría
     * @param categoriaId ID de la categoría
     * @return Lista de productos de la categoría especificada
     */
    public List<Producto> obtenerPorCategoria(int categoriaId) {
        return productoDao.listarPorCategoria(categoriaId);
    }
    
    /**
     * Método para obtener productos con bajo stock
     * @return Lista de productos con stock bajo
     */
    public List<Producto> obtenerProductosBajoStock() {
        return productoDao.listarProductosBajoStock();
    }
    
    /**
     * Método para buscar un producto por ID
     * @param id ID del producto
     * @return Producto encontrado o null si no existe
     */
    public Producto buscarPorId(int id) {
        return productoDao.obtenerPorId(id);
    }
}
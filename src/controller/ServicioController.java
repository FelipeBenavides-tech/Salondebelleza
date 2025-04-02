
package controller;




public class ServicioController {
    private ServicioDao servicioDao;
    
    public ServicioController() {
        servicioDao = new ServicioDao();
    }
    
    /**
     * Método para registrar un nuevo servicio
     * @param nombre Nombre del servicio
     * @param descripcion Descripción del servicio
     * @param precio Precio del servicio
     * @param tiempoEstimado Tiempo estimado en minutos
     * @param imagen Imagen del servicio (puede ser null)
     * @param categoriaId ID de la categoría
     * @return Resultado de la operación (1 = éxito, 0 = error)
     */
    public int registrarServicio(String nombre, String descripcion, double precio, 
                               int tiempoEstimado, byte[] imagen, int categoriaId) {
        // Validar campos
        if (nombre == null || nombre.trim().isEmpty() ||
            precio <= 0 || tiempoEstimado <= 0 || categoriaId <= 0) {
            return 0;
        }
        
        // Crear el servicio
        Servicio servicio = new Servicio(0, nombre, descripcion, precio, 
                                        tiempoEstimado, imagen, categoriaId, true);
        
        // Guardar en la base de datos
        return servicioDao.setAgregar(servicio);
    }
    
    /**
     * Método para actualizar un servicio existente
     * @param id ID del servicio
     * @param nombre Nombre actualizado
     * @param descripcion Descripción actualizada
     * @param precio Precio actualizado
     * @param tiempoEstimado Tiempo estimado actualizado
     * @param imagen Imagen actualizada (puede ser null)
     * @param categoriaId ID de categoría actualizado
     * @return Resultado de la operación (1 = éxito, 0 = error)
     */
    public int actualizarServicio(int id, String nombre, String descripcion, double precio, 
                                int tiempoEstimado, byte[] imagen, int categoriaId) {
        // Obtener el servicio actual
        Servicio servicio = servicioDao.obtenerPorId(id);
        
        if (servicio == null) {
            return 0; // Servicio no encontrado
        }
        
        // Actualizar los campos
        servicio.setNombre(nombre);
        servicio.setDescripcion(descripcion);
        servicio.setPrecio(precio);
        servicio.setTiempoEstimado(tiempoEstimado);
        
        // Solo actualizar la imagen si se proporciona una nueva
        if (imagen != null) {
            servicio.setImagen(imagen);
        }
        
        servicio.setCategoriaId(categoriaId);
        
        // Guardar cambios
        return servicioDao.setActualizar(servicio);
    }
    
    /**
     * Método para eliminar un servicio (desactivarlo)
     * @param id ID del servicio a eliminar
     * @return Resultado de la operación (1 = éxito, 0 = error)
     */
    public int eliminarServicio(int id) {
        return servicioDao.setEliminar(id);
    }
    
    /**
     * Método para obtener todos los servicios activos
     * @return Lista de servicios activos
     */
    public List<Servicio> obtenerTodos() {
        return servicioDao.listar();
    }
    
    /**
     * Método para obtener servicios por categoría
     * @param categoriaId ID de la categoría
     * @return Lista de servicios de la categoría especificada
     */
    public List<Servicio> obtenerPorCategoria(int categoriaId) {
        return servicioDao.listarPorCategoria(categoriaId);
    }
    
    /**
     * Método para buscar un servicio por ID
     * @param id ID del servicio
     * @return Servicio encontrado o null si no existe
     */
    public Servicio buscarPorId(int id) {
        return servicioDao.obtenerPorId(id);
    }
}


package controller;


public class CitaController {
    private CitaDao citaDao;
    
    public CitaController() {
        citaDao = new CitaDao();
    }
    
    /**
     * Método para programar una nueva cita
     * @param fecha Fecha de la cita
     * @param horaInicio Hora de inicio
     * @param horaFin Hora de finalización
     * @param estado Estado inicial de la cita
     * @param notas Notas adicionales
     * @param clienteId ID del cliente
     * @param empleadoId ID del empleado
     * @return Resultado de la operación (1 = éxito, 0 = error)
     */
    public int programarCita(Date fecha, Time horaInicio, Time horaFin, 
                           String estado, String notas, int clienteId, int empleadoId) {
        // Validar campos
        if (fecha == null || horaInicio == null || horaFin == null || 
            clienteId <= 0 || empleadoId <= 0) {
            return 0;
        }
        
        // Verificar disponibilidad
        if (!citaDao.verificarDisponibilidad(empleadoId, fecha, horaInicio, horaFin)) {
            return 0; // Horario no disponible
        }
        
        // Crear la cita
        Cita cita = new Cita(0, fecha, horaInicio, horaFin, 
                           estado != null ? estado : "Programada", 
                           notas, clienteId, empleadoId, null);
        
        // Guardar en la base de datos
        return citaDao.setAgregar(cita);
    }
    
    /**
     * Método para actualizar una cita existente
     * @param id ID de la cita
     * @param fecha Fecha actualizada
     * @param horaInicio Hora de inicio actualizada
     * @param horaFin Hora de finalización actualizada
     * @param estado Estado actualizado
     * @param notas Notas actualizadas
     * @param clienteId ID del cliente actualizado
     * @param empleadoId ID del empleado actualizado
     * @return Resultado de la operación (1 = éxito, 0 = error)
     */
    public int actualizarCita(int id, Date fecha, Time horaInicio, Time horaFin, 
                            String estado, String notas, int clienteId, int empleadoId) {
        // Obtener la cita actual
        List<Cita> citas = citaDao.listar();
        Cita citaActual = null;
        
        for (Cita c : citas) {
            if (c.getCitaId() == id) {
                citaActual = c;
                break;
            }
        }
        
        if (citaActual == null) {
            return 0; // Cita no encontrada
        }
        
        // Si cambia la fecha, hora o empleado, verificar disponibilidad
        if ((fecha != null && !fecha.equals(citaActual.getFecha())) ||
            (horaInicio != null && !horaInicio.equals(citaActual.getHoraInicio())) ||
            (horaFin != null && !horaFin.equals(citaActual.getHoraFin())) ||
            (empleadoId > 0 && empleadoId != citaActual.getEmpleadoId())) {
            
            Date nuevaFecha = fecha != null ? fecha : citaActual.getFecha();
            Time nuevaHoraInicio = horaInicio != null ? horaInicio : citaActual.getHoraInicio();
            Time nuevaHoraFin = horaFin != null ? horaFin : citaActual.getHoraFin();
            int nuevoEmpleadoId = empleadoId > 0 ? empleadoId : citaActual.getEmpleadoId();
            
            if (!citaDao.verificarDisponibilidad(nuevoEmpleadoId, nuevaFecha, nuevaHoraInicio, nuevaHoraFin)) {
                return 0; // Horario no disponible
            }
        }
        
        // Actualizar los campos
        if (fecha != null) citaActual.setFecha(fecha);
        if (horaInicio != null) citaActual.setHoraInicio(horaInicio);
        if (horaFin != null) citaActual.setHoraFin(horaFin);
        if (estado != null) citaActual.setEstado(estado);
        if (notas != null) citaActual.setNotas(notas);
        if (clienteId > 0) citaActual.setClienteId(clienteId);
        if (empleadoId > 0) citaActual.setEmpleadoId(empleadoId);
        
        // Guardar cambios
        return citaDao.setActualizar(citaActual);
    }
    
    /**
     * Método para cancelar una cita
     * @param id ID de la cita a cancelar
     * @return Resultado de la operación (1 = éxito, 0 = error)
     */
    public int cancelarCita(int id) {
        return citaDao.setEliminar(id); // Esto cambia el estado a 'Cancelada'
    }
    
    /**
     * Método para cambiar el estado de una cita
     * @param id ID de la cita
     * @param nuevoEstado Nuevo estado (Programada, Confirmada, En proceso, Completada, Cancelada)
     * @return Resultado de la operación (1 = éxito, 0 = error)
     */
    public int cambiarEstadoCita(int id, String nuevoEstado) {
        // Obtener la cita actual
        List<Cita> citas = citaDao.listar();
        Cita citaActual = null;
        
        for (Cita c : citas) {
            if (c.getCitaId() == id) {
                citaActual = c;
                break;
            }
        }
        
        if (citaActual == null) {
            return 0; // Cita no encontrada
        }
        
        // Validar que el estado sea válido
        String[] estadosValidos = {"Programada", "Confirmada", "En proceso", "Completada", "Cancelada"};
        boolean estadoValido = false;
        
        for (String estado : estadosValidos) {
            if (estado.equals(nuevoEstado)) {
                estadoValido = true;
                break;
            }
        }
        
        if (!estadoValido) {
            return 0; // Estado no válido
        }
        
        // Actualizar el estado
        citaActual.setEstado(nuevoEstado);
        
        // Guardar cambios
        return citaDao.setActualizar(citaActual);
    }
    
    /**
     * Método para obtener todas las citas
     * @return Lista de citas
     */
    public List<Cita> obtenerTodas() {
        return citaDao.listar();
    }
    
    /**
     * Método para obtener citas de un cliente
     * @param clienteId ID del cliente
     * @return Lista de citas del cliente
     */
    public List<Cita> obtenerPorCliente(int clienteId) {
        return citaDao.listarPorCliente(clienteId);
    }
    
    /**
     * Método para obtener citas de un empleado
     * @param empleadoId ID del empleado
     * @return Lista de citas del empleado
     */
    public List<Cita> obtenerPorEmpleado(int empleadoId) {
        return citaDao.listarPorEmpleado(empleadoId);
    }
    
    /**
     * Método para obtener citas de una fecha específica
     * @param fecha Fecha a consultar
     * @return Lista de citas de la fecha
     */
    public List<Cita> obtenerPorFecha(Date fecha) {
        return citaDao.listarPorFecha(fecha);
    }
}
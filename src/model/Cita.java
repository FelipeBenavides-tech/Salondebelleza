
package model;

import java.sql.Date;
import java.sql.Time;

public class Cita {
    private int citaId;
    private Date fecha;
    private Time horaInicio;
    private Time horaFin;
    private String estado;
    private String notas;
    private int clienteId;
    private int empleadoId;
    private java.sql.Timestamp fechaRegistro;
    
    // Constructor vacío
    public Cita() {
    }
    
    // Constructor completo
    public Cita(int citaId, Date fecha, Time horaInicio, Time horaFin, String estado, 
                String notas, int clienteId, int empleadoId, java.sql.Timestamp fechaRegistro) {
        this.citaId = citaId;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
        this.notas = notas;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.fechaRegistro = fechaRegistro;
    }
    
    // Getters y Setters
    public int getCitaId() {
        return citaId;
    }
    
    public void setCitaId(int citaId) {
        this.citaId = citaId;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public Time getHoraInicio() {
        return horaInicio;
    }
    
    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }
    
    public Time getHoraFin() {
        return horaFin;
    }
    
    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getNotas() {
        return notas;
    }
    
    public void setNotas(String notas) {
        this.notas = notas;
    }
    
    public int getClienteId() {
        return clienteId;
    }
    
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    
    public int getEmpleadoId() {
        return empleadoId;
    }
    
    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }
    
    public java.sql.Timestamp getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(java.sql.Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
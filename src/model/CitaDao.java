
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitaDao implements Crud<Cita> {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    @Override
    public List<Cita> listar() {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbl_cita ORDER BY cit_fecha DESC, cit_hora_inicio ASC";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Cita c = new Cita();
                c.setCitaId(rs.getInt("cit_id"));
                c.setFecha(rs.getDate("cit_fecha"));
                c.setHoraInicio(rs.getTime("cit_hora_inicio"));
                c.setHoraFin(rs.getTime("cit_hora_fin"));
                c.setEstado(rs.getString("cit_estado"));
                c.setNotas(rs.getString("cit_notas"));
                c.setClienteId(rs.getInt("usu_id_cliente"));
                c.setEmpleadoId(rs.getInt("usu_id_empleado"));
                c.setFechaRegistro(rs.getTimestamp("cit_fecha_registro"));
                
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        
        return lista;
    }
    
    @Override
    public int setAgregar(Cita c) {
        String sql = "INSERT INTO tbl_cita (cit_fecha, cit_hora_inicio, cit_hora_fin, cit_estado, cit_notas, usu_id_cliente, usu_id_empleado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            
            ps.setDate(1, c.getFecha());
            ps.setTime(2, c.getHoraInicio());
            ps.setTime(3, c.getHoraFin());
            ps.setString(4, c.getEstado());
            ps.setString(5, c.getNotas());
            ps.setInt(6, c.getClienteId());
            ps.setInt(7, c.getEmpleadoId());
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar cita: " + e.getMessage());
            return 0;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    @Override
    public int setActualizar(Cita c) {
        String sql = "UPDATE tbl_cita SET cit_fecha=?, cit_hora_inicio=?, cit_hora_fin=?, cit_estado=?, cit_notas=?, usu_id_cliente=?, usu_id_empleado=? WHERE cit_id=?";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            
            ps.setDate(1, c.getFecha());
            ps.setTime(2, c.getHoraInicio());
            ps.setTime(3, c.getHoraFin());
            ps.setString(4, c.getEstado());
            ps.setString(5, c.getNotas());
            ps.setInt(6, c.getClienteId());
            ps.setInt(7, c.getEmpleadoId());
            ps.setInt(8, c.getCitaId());
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar cita: " + e.getMessage());
            return 0;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    @Override
    public int setEliminar(int id) {
        // Para citas, en lugar de eliminar, cambiamos su estado a 'Cancelada'
        String sql = "UPDATE tbl_cita SET cit_estado='Cancelada' WHERE cit_id=?";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al cancelar cita: " + e.getMessage());
            return 0;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    // Métodos adicionales específicos
    
    public List<Cita> listarPorCliente(int clienteId) {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbl_cita WHERE usu_id_cliente=? ORDER BY cit_fecha DESC, cit_hora_inicio ASC";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, clienteId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Cita c = new Cita();
                c.setCitaId(rs.getInt("cit_id"));
                c.setFecha(rs.getDate("cit_fecha"));
                c.setHoraInicio(rs.getTime("cit_hora_inicio"));
                c.setHoraFin(rs.getTime("cit_hora_fin"));
                c.setEstado(rs.getString("cit_estado"));
                c.setNotas(rs.getString("cit_notas"));
                c.setClienteId(rs.getInt("usu_id_cliente"));
                c.setEmpleadoId(rs.getInt("usu_id_empleado"));
                c.setFechaRegistro(rs.getTimestamp("cit_fecha_registro"));
                
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas por cliente: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        
        return lista;
    }
    
    public List<Cita> listarPorEmpleado(int empleadoId) {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbl_cita WHERE usu_id_empleado=? ORDER BY cit_fecha, cit_hora_inicio";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, empleadoId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Cita c = new Cita();
                c.setCitaId(rs.getInt("cit_id"));
                c.setFecha(rs.getDate("cit_fecha"));
                c.setHoraInicio(rs.getTime("cit_hora_inicio"));
                c.setHoraFin(rs.getTime("cit_hora_fin"));
                c.setEstado(rs.getString("cit_estado"));
                c.setNotas(rs.getString("cit_notas"));
                c.setClienteId(rs.getInt("usu_id_cliente"));
                c.setEmpleadoId(rs.getInt("usu_id_empleado"));
                c.setFechaRegistro(rs.getTimestamp("cit_fecha_registro"));
                
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas por empleado: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        
        return lista;
    }
    
    public List<Cita> listarPorFecha(java.sql.Date fecha) {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbl_cita WHERE cit_fecha=? ORDER BY cit_hora_inicio";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setDate(1, fecha);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Cita c = new Cita();
                c.setCitaId(rs.getInt("cit_id"));
                c.setFecha(rs.getDate("cit_fecha"));
                c.setHoraInicio(rs.getTime("cit_hora_inicio"));
                c.setHoraFin(rs.getTime("cit_hora_fin"));
                c.setEstado(rs.getString("cit_estado"));
                c.setNotas(rs.getString("cit_notas"));
                c.setClienteId(rs.getInt("usu_id_cliente"));
                c.setEmpleadoId(rs.getInt("usu_id_empleado"));
                c.setFechaRegistro(rs.getTimestamp("cit_fecha_registro"));
                
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas por fecha: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        
        return lista;
    }
    
    public boolean verificarDisponibilidad(int empleadoId, java.sql.Date fecha, java.sql.Time horaInicio, java.sql.Time horaFin) {
        String sql = "SELECT COUNT(*) FROM tbl_cita WHERE usu_id_empleado=? AND cit_fecha=? AND " +
                     "((cit_hora_inicio <= ? AND cit_hora_fin > ?) OR " +
                     "(cit_hora_inicio < ? AND cit_hora_fin >= ?) OR " +
                     "(cit_hora_inicio >= ? AND cit_hora_fin <= ?)) AND " +
                     "cit_estado != 'Cancelada'";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, empleadoId);
            ps.setDate(2, fecha);
            ps.setTime(3, horaInicio);
            ps.setTime(4, horaInicio);
            ps.setTime(5, horaFin);
            ps.setTime(6, horaFin);
            ps.setTime(7, horaInicio);
            ps.setTime(8, horaFin);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) == 0; // Si no hay citas que se solapen, está disponible
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar disponibilidad: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        
        return false; // Por defecto, si hay error, asumimos que no está disponible
    }
}


package model;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioDao implements Crud<Servicio> {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    @Override
    public List<Servicio> listar() {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbl_servicio WHERE ser_activo = true";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Servicio s = new Servicio();
                s.setServicioId(rs.getInt("ser_id"));
                s.setNombre(rs.getString("ser_nombre"));
                s.setDescripcion(rs.getString("ser_descripcion"));
                s.setPrecio(rs.getDouble("ser_precio"));
                s.setTiempoEstimado(rs.getInt("ser_tiempo_estimado"));
                
                // Convertir Blob a byte[]
                Blob blob = rs.getBlob("ser_imagen");
                if (blob != null) {
                    int blobLength = (int) blob.length();
                    s.setImagen(blob.getBytes(1, blobLength));
                }
                
                s.setCategoriaId(rs.getInt("cats_id"));
                s.setActivo(rs.getBoolean("ser_activo"));
                
                lista.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar servicios: " + e.getMessage());
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
    public int setAgregar(Servicio s) {
        String sql = "INSERT INTO tbl_servicio (ser_nombre, ser_descripcion, ser_precio, ser_tiempo_estimado, ser_imagen, cats_id, ser_activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getDescripcion());
            ps.setDouble(3, s.getPrecio());
            ps.setInt(4, s.getTiempoEstimado());
            
            if (s.getImagen() != null) {
                ps.setBytes(5, s.getImagen());
            } else {
                ps.setNull(5, java.sql.Types.BLOB);
            }
            
            ps.setInt(6, s.getCategoriaId());
            ps.setBoolean(7, s.isActivo());
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar servicio: " + e.getMessage());
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
    public int setActualizar(Servicio s) {
        String sql = "UPDATE tbl_servicio SET ser_nombre=?, ser_descripcion=?, ser_precio=?, ser_tiempo_estimado=?, ser_imagen=?, cats_id=?, ser_activo=? WHERE ser_id=?";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getDescripcion());
            ps.setDouble(3, s.getPrecio());
            ps.setInt(4, s.getTiempoEstimado());
            
            if (s.getImagen() != null) {
                ps.setBytes(5, s.getImagen());
            } else {
                ps.setNull(5, java.sql.Types.BLOB);
            }
            
            ps.setInt(6, s.getCategoriaId());
            ps.setBoolean(7, s.isActivo());
            ps.setInt(8, s.getServicioId());
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar servicio: " + e.getMessage());
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
        // En lugar de eliminar físicamente, hacemos un borrado lógico
        String sql = "UPDATE tbl_servicio SET ser_activo=false WHERE ser_id=?";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar servicio: " + e.getMessage());
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
    
    public List<Servicio> listarPorCategoria(int categoriaId) {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbl_servicio WHERE cats_id=? AND ser_activo=true";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, categoriaId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Servicio s = new Servicio();
                s.setServicioId(rs.getInt("ser_id"));
                s.setNombre(rs.getString("ser_nombre"));
                s.setDescripcion(rs.getString("ser_descripcion"));
                s.setPrecio(rs.getDouble("ser_precio"));
                s.setTiempoEstimado(rs.getInt("ser_tiempo_estimado"));
                
                // Convertir Blob a byte[]
                Blob blob = rs.getBlob("ser_imagen");
                if (blob != null) {
                    int blobLength = (int) blob.length();
                    s.setImagen(blob.getBytes(1, blobLength));
                }
                
                s.setCategoriaId(rs.getInt("cats_id"));
                s.setActivo(rs.getBoolean("ser_activo"));
                
                lista.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar servicios por categoría: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        
        return lista;
    }
    
    public Servicio obtenerPorId(int id) {
        String sql = "SELECT * FROM tbl_servicio WHERE ser_id=?";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Servicio s = new Servicio();
                s.setServicioId(rs.getInt("ser_id"));
                s.setNombre(rs.getString("ser_nombre"));
                s.setDescripcion(rs.getString("ser_descripcion"));
                s.setPrecio(rs.getDouble("ser_precio"));
                s.setTiempoEstimado(rs.getInt("ser_tiempo_estimado"));
                
                // Convertir Blob a byte[]
                Blob blob = rs.getBlob("ser_imagen");
                if (blob != null) {
                    int blobLength = (int) blob.length();
                    s.setImagen(blob.getBytes(1, blobLength));
                }
                
                s.setCategoriaId(rs.getInt("cats_id"));
                s.setActivo(rs.getBoolean("ser_activo"));
                
                return s;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener servicio por ID: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        
        return null;
    }
}

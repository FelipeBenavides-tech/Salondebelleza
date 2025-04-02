
package model;


import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao implements Crud<Producto> {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    @Override
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbl_producto WHERE pro_activo = true";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Producto p = new Producto();
                p.setProductoId(rs.getInt("pro_id"));
                p.setNombre(rs.getString("pro_nombre"));
                p.setDescripcion(rs.getString("pro_descripcion"));
                p.setPrecio(rs.getDouble("pro_precio"));
                p.setStock(rs.getInt("pro_stock"));
                p.setStockMinimo(rs.getInt("pro_stock_minimo"));
                
                // Convertir Blob a byte[]
                Blob blob = rs.getBlob("pro_imagen");
                if (blob != null) {
                    int blobLength = (int) blob.length();
                    p.setImagen(blob.getBytes(1, blobLength));
                }
                
                p.setCategoriaId(rs.getInt("catp_id"));
                p.setActivo(rs.getBoolean("pro_activo"));
                
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
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
    public int setAgregar(Producto p) {
        String sql = "INSERT INTO tbl_producto (pro_nombre, pro_descripcion, pro_precio, pro_stock, pro_stock_minimo, pro_imagen, catp_id, pro_activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getStockMinimo());
            
            if (p.getImagen() != null) {
                ps.setBytes(6, p.getImagen());
            } else {
                ps.setNull(6, java.sql.Types.BLOB);
            }
            
            ps.setInt(7, p.getCategoriaId());
            ps.setBoolean(8, p.isActivo());
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar producto: " + e.getMessage());
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
    public int setActualizar(Producto p) {
        String sql = "UPDATE tbl_producto SET pro_nombre=?, pro_descripcion=?, pro_precio=?, pro_stock=?, pro_stock_minimo=?, pro_imagen=?, catp_id=?, pro_activo=? WHERE pro_id=?";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getStockMinimo());
            
            if (p.getImagen() != null) {
                ps.setBytes(6, p.getImagen());
            } else {
                ps.setNull(6, java.sql.Types.BLOB);
            }
            
            ps.setInt(7, p.getCategoriaId());
            ps.setBoolean(8, p.isActivo());
            ps.setInt(9, p.getProductoId());
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
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
        String sql = "UPDATE tbl_producto SET pro_activo=false WHERE pro_id=?";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
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
    
    public List<Producto> listarPorCategoria(int categoriaId) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbl_producto WHERE catp_id=? AND pro_activo=true";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, categoriaId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Producto p = new Producto();
                p.setProductoId(rs.getInt("pro_id"));
                p.setNombre(rs.getString("pro_nombre"));
                p.setDescripcion(rs.getString("pro_descripcion"));
                p.setPrecio(rs.getDouble("pro_precio"));
                p.setStock(rs.getInt("pro_stock"));
                p.setStockMinimo(rs.getInt("pro_stock_minimo"));
                
                // Convertir Blob a byte[]
                Blob blob = rs.getBlob("pro_imagen");
                if (blob != null) {
                    int blobLength = (int) blob.length();
                    p.setImagen(blob.getBytes(1, blobLength));
                }
                
                p.setCategoriaId(rs.getInt("catp_id"));
                p.setActivo(rs.getBoolean("pro_activo"));
                
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos por categoría: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        
        return lista;
    }
    
    public List<Producto> listarProductosBajoStock() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbl_producto WHERE pro_stock <= pro_stock_minimo AND pro_activo=true";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Producto p = new Producto();
                p.setProductoId(rs.getInt("pro_id"));
                p.setNombre(rs.getString("pro_nombre"));
                p.setDescripcion(rs.getString("pro_descripcion"));
                p.setPrecio(rs.getDouble("pro_precio"));
                p.setStock(rs.getInt("pro_stock"));
                p.setStockMinimo(rs.getInt("pro_stock_minimo"));
                
                // Convertir Blob a byte[]
                Blob blob = rs.getBlob("pro_imagen");
                if (blob != null) {
                    int blobLength = (int) blob.length();
                    p.setImagen(blob.getBytes(1, blobLength));
                }
                
                p.setCategoriaId(rs.getInt("catp_id"));
                p.setActivo(rs.getBoolean("pro_activo"));
                
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos con bajo stock: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        
        return lista;
    }
    
    public boolean actualizarStock(int productoId, int cantidad) {
        String sql = "UPDATE tbl_producto SET pro_stock = pro_stock + ? WHERE pro_id = ?";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cantidad);
            ps.setInt(2, productoId);
            
            int resultado = ps.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM tbl_producto WHERE pro_id=?";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Producto p = new Producto();
                p.setProductoId(rs.getInt("pro_id"));
                p.setNombre(rs.getString("pro_nombre"));
                p.setDescripcion(rs.getString("pro_descripcion"));
                p.setPrecio(rs.getDouble("pro_precio"));
                p.setStock(rs.getInt("pro_stock"));
                p.setStockMinimo(rs.getInt("pro_stock_minimo"));
                
                // Convertir Blob a byte[]
                Blob blob = rs.getBlob("pro_imagen");
                if (blob != null) {
                    int blobLength = (int) blob.length();
                    p.setImagen(blob.getBytes(1, blobLength));
                }
                
                p.setCategoriaId(rs.getInt("catp_id"));
                p.setActivo(rs.getBoolean("pro_activo"));
                
                return p;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener producto por ID: " + e.getMessage());
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
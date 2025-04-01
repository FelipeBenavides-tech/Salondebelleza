package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class UsuarioDao implements Crud<Usuario> {
    
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public int setAgregar(Usuario u) {
        String sql = "INSERT INTO tbl_usuario (usu_nombre, usu_apellido, usu_correo, usu_contrasena, usu_tipo) VALUES(?,?,?,?,?)";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getContrasena());
            ps.setString(5, u.getTipoUsuario());
            
            ps.executeUpdate();
            return 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return 0;
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqle) {
                JOptionPane.showMessageDialog(null, sqle.toString());
            }
        }
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbl_usuario";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setUsuarioid(rs.getInt("usu_id"));
                u.setNombre(rs.getString("usu_nombre"));
                u.setApellido(rs.getString("usu_apellido"));
                u.setCorreo(rs.getString("usu_correo"));
                u.setContrasena(rs.getString("usu_contrasena"));
                u.setTipoUsuario(rs.getString("usu_tipo"));
                
                lista.add(u);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqle) {
                JOptionPane.showMessageDialog(null, sqle.toString());
            }
        }
        
        return lista;
    }

    @Override
    public int setActualizar(Usuario u) {
        String sql = "UPDATE tbl_usuario SET usu_nombre=?, usu_apellido=?, usu_correo=?, usu_contrasena=?, usu_tipo=? WHERE usu_id=?";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getContrasena());
            ps.setString(5, u.getTipoUsuario());
            ps.setInt(6, u.getUsuarioid());
            
            return ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return 0;
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqle) {
                JOptionPane.showMessageDialog(null, sqle.toString());
            }
        }
    }

    @Override
    public int setEliminar(int id) {
        String sql = "DELETE FROM tbl_usuario WHERE usu_id=?";
        
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            return ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return 0;
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqle) {
                JOptionPane.showMessageDialog(null, sqle.toString());
            }
        }
    }
}

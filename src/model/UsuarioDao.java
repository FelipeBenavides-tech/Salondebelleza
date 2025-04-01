
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
    ResultSet rs;
    
 
 
 @Override
    public int setAgregar(Usuario u) {
       String sql = "INSERT INTO tbl_usuario VALUES(?,?,?,?,?,?)";
       
       try{
           con = conectar.conectar();
           ps = con.prepareStatement(sql);
           
          ps.setInt(1, 0);
           ps.setString(2, u.getNombre());
           ps.setString(3, u.getApellido());
           ps.setString(4, u.getCorreo());
           ps.setString(5, u.getContrasena());
           ps.setString(6, u.getTipoUsuario());
           
           ps.executeUpdate();
           return 1;
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, e.toString());
           return 0;
       }finally{
            try{
                if(con!=null){
                    con.close();
                }
            }catch(SQLException sqle){
                JOptionPane.showMessageDialog(null, sqle.toString());
            }
        }
    
    
    
    



package MVCSB;

import model.Usuario;
import model.UsuarioDao;


public class IndexMain {

    
    public static void main(String[] args) {
         // Crear una instancia del DAO
        UsuarioDao usuarioDao = new UsuarioDao();
        
        // Crear un usuario quemado (datos fijos)
        Usuario usuario = new Usuario(0, "Alejandro", "Pérez", "alejo@example.com", "123456", "admin");
        
        // Insertar el usuario en la base de datos
        int resultado = usuarioDao.setAgregar(usuario);
        
        // Verificar si se insertó correctamente
        if (resultado == 1) {
            System.out.println("Usuario insertado correctamente.");
        } else {
            System.out.println("Error al insertar el usuario.");
        }
    }
        
}
    


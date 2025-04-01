/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package MVCSB;

import model.Usuario;
import model.UsuarioDao;

/**
 *
 * @author alejo
 */
public class IndexMain {

    /**
     * @param args the command line arguments
     */
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
    


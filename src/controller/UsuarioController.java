package controller;

import model.*;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Time;


 // Controlador para la gestión de usuarios
 
public class UsuarioController {
    private UsuarioDao usuarioDao;
    
    public UsuarioController() {
        usuarioDao = new UsuarioDao();
    }
    
    /**
     * Método para autenticar a un usuario
     * @param correo Correo electrónico del usuario
     * @param contrasena Contraseña del usuario
     * @return Usuario autenticado o null si las credenciales son inválidas
     */
    public Usuario autenticar(String correo, String contrasena) {
        List<Usuario> usuarios = usuarioDao.listar();
        
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo) && 
                verificarContrasena(contrasena, u.getContrasena())) {
                return u;
            }
        }
        
        return null;
    }
    
    /**
     * Método para registrar un nuevo usuario
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @param correo Correo electrónico del usuario
     * @param contrasena Contraseña del usuario (se encriptará)
     * @param tipoUsuario Tipo de usuario (Administrador, Peluquero, Cliente)
     * @return Resultado de la operación (1 = éxito, 0 = error)
     */
    public int registrarUsuario(String nombre, String apellido, String correo, 
                              String contrasena, String tipoUsuario) {
        // Validar campos
        if (nombre == null || nombre.trim().isEmpty() ||
            apellido == null || apellido.trim().isEmpty() ||
            correo == null || correo.trim().isEmpty() ||
            contrasena == null || contrasena.trim().isEmpty() ||
            tipoUsuario == null || tipoUsuario.trim().isEmpty()) {
            return 0;
        }
        
        // Verificar si el correo ya existe
        List<Usuario> usuarios = usuarioDao.listar();
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                return 0; // El correo ya está registrado
            }
        }
        
        // Encriptar la contraseña
        String contrasenaEncriptada = encriptarContrasena(contrasena);
        
        // Crear el usuario
        Usuario usuario = new Usuario(0, nombre, apellido, correo, contrasenaEncriptada, tipoUsuario);
        
        // Guardar en la base de datos
        return usuarioDao.setAgregar(usuario);
    }
    
    /**
     * Método para encriptar la contraseña usando SHA-256
     * @param contrasena Contraseña en texto plano
     * @return Contraseña encriptada en formato hexadecimal
     */
    private String encriptarContrasena(String contrasena) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(contrasena.getBytes(StandardCharsets.UTF_8));
            
            // Convertir bytes a formato hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Método para verificar si una contraseña en texto plano coincide con la encriptada
     * @param contrasenaPlana Contraseña en texto plano
     * @param contrasenaEncriptada Contraseña encriptada
     * @return True si las contraseñas coinciden, false en caso contrario
     */
    private boolean verificarContrasena(String contrasenaPlana, String contrasenaEncriptada) {
        String encriptada = encriptarContrasena(contrasenaPlana);
        return encriptada != null && encriptada.equals(contrasenaEncriptada);
    }
    
    /**
     * Método para actualizar un usuario existente
     * @param id ID del usuario
     * @param nombre Nombre actualizado
     * @param apellido Apellido actualizado
     * @param correo Correo actualizado
     * @param tipoUsuario Tipo de usuario actualizado
     * @return Resultado de la operación (1 = éxito, 0 = error)
     */
    public int actualizarUsuario(int id, String nombre, String apellido, String correo, String tipoUsuario) {
        // Obtener el usuario actual
        List<Usuario> usuarios = usuarioDao.listar();
        Usuario usuarioActual = null;
        
        for (Usuario u : usuarios) {
            if (u.getUsuarioid() == id) {
                usuarioActual = u;
                break;
            }
        }
        
        if (usuarioActual == null) {
            return 0; // Usuario no encontrado
        }
        
        // Actualizar los campos
        usuarioActual.setNombre(nombre);
        usuarioActual.setApellido(apellido);
        usuarioActual.setCorreo(correo);
        usuarioActual.setTipoUsuario(tipoUsuario);
        
        // Guardar cambios
        return usuarioDao.setActualizar(usuarioActual);
    }
    
    /**
     * Método para cambiar la contraseña de un usuario
     * @param id ID del usuario
     * @param contrasenaActual Contraseña actual
     * @param nuevaContrasena Nueva contraseña
     * @return Resultado de la operación (1 = éxito, 0 = error)
     */
    public int cambiarContrasena(int id, String contrasenaActual, String nuevaContrasena) {
        // Obtener el usuario
        List<Usuario> usuarios = usuarioDao.listar();
        Usuario usuario = null;
        
        for (Usuario u : usuarios) {
            if (u.getUsuarioid() == id) {
                usuario = u;
                break;
            }
        }
        
        if (usuario == null) {
            return 0; // Usuario no encontrado
        }
        
        // Verificar la contraseña actual
        if (!verificarContrasena(contrasenaActual, usuario.getContrasena())) {
            return 0; // Contraseña actual incorrecta
        }
        
        // Encriptar la nueva contraseña
        String nuevaContrasenaEncriptada = encriptarContrasena(nuevaContrasena);
        usuario.setContrasena(nuevaContrasenaEncriptada);
        
        // Guardar cambios
        return usuarioDao.setActualizar(usuario);
    }
    
    /**
     * Método para eliminar un usuario
     * @param id ID del usuario a eliminar
     * @return Resultado de la operación (1 = éxito, 0 = error)
     */
    public int eliminarUsuario(int id) {
        return usuarioDao.setEliminar(id);
    }
    
    /**
     * Método para obtener todos los usuarios
     * @return Lista de usuarios
     */
    public List<Usuario> obtenerTodos() {
        return usuarioDao.listar();
    }
    
    /**
     * Método para filtrar usuarios por tipo
     * @param tipo Tipo de usuario (Administrador, Peluquero, Cliente)
     * @return Lista de usuarios del tipo especificado
     */
    public List<Usuario> filtrarPorTipo(String tipo) {
        List<Usuario> todos = usuarioDao.listar();
        List<Usuario> filtrados = new java.util.ArrayList<>();
        
        for (Usuario u : todos) {
            if (u.getTipoUsuario().equalsIgnoreCase(tipo)) {
                filtrados.add(u);
            }
        }
        
        return filtrados;
    }
}

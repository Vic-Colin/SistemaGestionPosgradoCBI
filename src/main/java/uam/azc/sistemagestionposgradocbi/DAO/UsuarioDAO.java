package uam.azc.sistemagestionposgradocbi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uam.azc.sistemagestionposgradocbi.modelo.Usuario;
import uam.azc.sistemagestionposgradocbi.util.Conexion;

/**
 * DAO responsable de la autenticación de usuarios
 * del sistema.
 *
 * Valida credenciales utilizando hash SHA-256
 * almacenado en la base de datos.
 *
 * Seguridad:
 * - No almacena contraseñas en texto plano.
 *
 * @author Vania Alejandra Contreras Torres
 */
public class UsuarioDAO {

    /**
    * Valida las credenciales de acceso del usuario.
    *
    * La contraseña se compara utilizando SHA-256
    * directamente en la base de datos.
    *
    * @param nombreUsuario nombre de usuario
    * @param password contraseña en texto plano
    * @return Usuario autenticado o null si falla
    */
    public Usuario validarLogin(String nombreUsuario, String password) {
        Usuario usuario = null;
        
        // Comparamos el nombre de usuario y verificamos la contraseña aplicándole SHA-256 
        // para que coincida con el hash de la base de datos.
        String sql = "SELECT id_usuario, nombre_usuario, rol FROM usuario "
                   + "WHERE nombre_usuario = ? AND contrasena_hash = SHA2(?, 256)";
        
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombreUsuario);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                    usuario.setRol(rs.getString("rol"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuario; // Si no lo encuentra o la contraseña es incorrecta, retornará null
    }
}

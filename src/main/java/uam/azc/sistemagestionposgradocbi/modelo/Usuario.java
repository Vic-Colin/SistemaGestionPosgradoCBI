package uam.azc.sistemagestionposgradocbi.modelo;

/**
 * Representa un usuario del sistema encargado de acceder
 * al Sistema de Gestión de Posgrado CBI.
 * 
 * Contiene la información básica necesaria para identificar
 * al usuario y determinar sus permisos mediante el rol
 * asignado dentro del sistema.
 * 
 * Esta clase es utilizada para el control de acceso y
 * autenticación de usuarios.
 * 
 * @author Vania Alejandra Contreras Torres
 */
public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String rol;

    /**
     * Constructor vacío de la clase Usuario.
     * 
     * Permite la creación de objetos sin inicialización
     * inmediata de atributos.
     */
    public Usuario() {}

    /**
     * Obtiene el identificador del usuario.
     * 
     * @return id único del usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el identificador del usuario.
     * 
     * @param idUsuario identificador único
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el nombre de usuario.
     * 
     * @return nombre utilizado para iniciar sesión
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Define el nombre de usuario.
     * 
     * @param nombreUsuario nombre de acceso al sistema
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene el rol asignado al usuario.
     * 
     * El rol determina los permisos y funcionalidades
     * disponibles dentro del sistema.
     * 
     * @return rol del usuario
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     * 
     * @param rol tipo de usuario dentro del sistema
     *            (por ejemplo: administrador, coordinador, consulta)
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
}

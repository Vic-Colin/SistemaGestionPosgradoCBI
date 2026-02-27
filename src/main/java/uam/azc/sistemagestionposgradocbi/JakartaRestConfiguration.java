package uam.azc.sistemagestionposgradocbi;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures Jakarta RESTful Web Services for the application.
 * Clase de configuración para habilitar los servicios REST
 * dentro de la aplicación Jakarta EE.
 *
 * Define el punto base desde el cual se expondrán
 * todos los recursos REST del sistema.
 *
 * Ruta base:
 * http://localhost:8080/SistemaGestionPosgradoCBI/resources/
 *
 * Forma parte de la configuración de servicios web.
 * @author Juneau
 */
@ApplicationPath("resources")
public class JakartaRestConfiguration extends Application {
    
}

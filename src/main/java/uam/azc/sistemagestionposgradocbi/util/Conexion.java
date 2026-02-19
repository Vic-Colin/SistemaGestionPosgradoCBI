package uam.azc.sistemagestionposgradocbi.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Conexion {

    public static Connection getConnection() throws SQLException {
        try {
            // Buscamos el contexto inicial de Tomcat
            Context initContext = new InitialContext();
            Context envContext  = (Context) initContext.lookup("java:/comp/env");
            
            // Buscamos nuestro recurso por el nombre que le dimos en el context.xml
            DataSource ds = (DataSource) envContext.lookup("jdbc/mcc_db");
            
            // Tomamos una conexión prestada del pool
            return ds.getConnection();
            
        } catch (NamingException e) {
            System.err.println("Error al buscar el DataSource: " + e.getMessage());
            throw new SQLException("Error de configuración JNDI en el Pool de Conexiones", e);
        }
    }
}
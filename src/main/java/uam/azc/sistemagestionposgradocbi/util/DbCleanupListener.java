package uam.azc.sistemagestionposgradocbi.util;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Listener encargado de liberar recursos JDBC
 * cuando la aplicación es detenida.
 *
 * Previene fugas de memoria del driver MySQL.
 *
 * Ejecuta:
 * - Shutdown del cleanup thread
 * - Desregistro de drivers JDBC
 */
@WebListener
public class DbCleanupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // No se requiere acción al iniciar
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 1. Detener el hilo de limpieza de MySQL
        AbandonedConnectionCleanupThread.checkedShutdown();

        // 2. Desregistrar drivers de JDBC para evitar fugas de memoria
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

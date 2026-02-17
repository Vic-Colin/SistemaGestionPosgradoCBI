/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uam.azc.sistemagestionposgradocbi.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = 
        "jdbc:mysql://localhost:3306/sistema_posgrado_cbi?useSSL=false&serverTimezone=UTC";
    
    private static final String USER = "root";
    private static final String PASSWORD = "1234"; // Cambia por tu contraseña

    static {
        try {
            // Cargar driver MySQL 8+
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }
}


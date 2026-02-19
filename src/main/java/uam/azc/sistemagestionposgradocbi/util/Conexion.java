/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uam.azc.sistemagestionposgradocbi.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static final String URL =
        "jdbc:mysql://localhost:3306/mcc_uam_azcapotzalco?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private static final String USER = "root";        // cambia si usas otro
    private static final String PASSWORD = "root";    // cambia si usas otro

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error cargando Driver MySQL", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

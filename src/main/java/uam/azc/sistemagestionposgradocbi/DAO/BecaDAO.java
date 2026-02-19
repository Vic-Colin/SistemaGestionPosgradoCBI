/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uam.azc.sistemagestionposgradocbi.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import uam.azc.sistemagestionposgradocbi.modelo.Beca;
import uam.azc.sistemagestionposgradocbi.util.Conexion;

/**
 *
 * @author CASH
 */
public class BecaDAO {
    public List<Beca> listar(String matricula) {
    List<Beca> lista = new ArrayList<>();
    // Usamos LIKE matricula% para que respete el orden inicial
    String sql = "SELECT * FROM beca WHERE matricula LIKE ?"; 
    
    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        // AQUÍ ESTÁ EL CAMBIO: solo un % al final
        ps.setString(1, matricula + "%"); 
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Beca b = new Beca();
                b.setMatricula(rs.getString("matricula"));
                // ... setear los demás campos (estatus, fecha, etc.)
                lista.add(b);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
    }
}

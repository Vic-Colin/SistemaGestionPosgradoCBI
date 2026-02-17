/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uam.azc.sistemagestionposgradocbi.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import uam.azc.sistemagestionposgradocbi.modelo.Profesor;
import uam.azc.sistemagestionposgradocbi.util.Conexion;

public class ProfesorDAO {

    // 🔹 Buscar profesores con filtros dinámicos
    public List<Profesor> buscarProfesores(String noEconomico, String cvu) {
    List<Profesor> lista = new ArrayList<>();

    String sql = "SELECT * FROM profesor WHERE 1=1";

    if (noEconomico != null && !noEconomico.isEmpty()) {
        sql += " AND no_economico LIKE ?";
    }

    if (cvu != null && !cvu.isEmpty()) {
        sql += " AND cvu LIKE ?";
    }

    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        int index = 1;

        if (noEconomico != null && !noEconomico.isEmpty()) {
            ps.setString(index++, "%" + noEconomico + "%");
        }

        if (cvu != null && !cvu.isEmpty()) {
            ps.setString(index++, "%" + cvu + "%");
        }

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Profesor p = new Profesor();
            p.setNoEconomico(rs.getString("no_economico"));
            p.setNombreCompleto(rs.getString("nombre_completo"));
            p.setCvu(rs.getString("cvu"));
            p.setPrograma(rs.getString("programa"));
            p.setCorreoInstitucional(rs.getString("correo_institucional"));
            p.setCategoria(rs.getString("categoria"));
            lista.add(p);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}


}

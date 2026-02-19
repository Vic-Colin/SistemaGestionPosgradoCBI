package uam.azc.sistemagestionposgradocbi.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import uam.azc.sistemagestionposgradocbi.modelo.Profesor;
import uam.azc.sistemagestionposgradocbi.util.Conexion;

public class ProfesorDAO {

    public List<Profesor> buscarProfesores(String numeroEconomico, String cvu) {

        List<Profesor> lista = new ArrayList<>();

        String sql =
            "SELECT " +
            "p.numero_economico, " +
            "p.cvu, " +
            "p.nombre_completo, " +
            "ns.nombre AS nivel_sni, " +
            "td.nombre AS tipo_dedicacion, " +
            "d.nombre AS departamento, " +
            "p.fecha_ingreso_nucleo, " +   // 🔥 NUEVO
            "p.correo_institucional " +
            "FROM profesor p " +
            "LEFT JOIN nivel_sni ns ON p.id_nivel_sni = ns.id_nivel_sni " +
            "INNER JOIN tipo_dedicacion td ON p.id_dedicacion = td.id_dedicacion " +
            "INNER JOIN departamento d ON p.id_departamento = d.id_departamento " +
            "WHERE 1=1 ";

        if (numeroEconomico != null && !numeroEconomico.isEmpty()) {
            sql += " AND p.numero_economico LIKE ?";
        }

        if (cvu != null && !cvu.isEmpty()) {
            sql += " AND p.cvu LIKE ?";
        }

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int index = 1;

            if (numeroEconomico != null && !numeroEconomico.isEmpty()) {
                ps.setString(index++, "%" + numeroEconomico + "%");
            }

            if (cvu != null && !cvu.isEmpty()) {
                ps.setString(index++, "%" + cvu + "%");
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Profesor p = new Profesor();

                p.setNumeroEconomico(rs.getString("numero_economico"));
                p.setCvu(rs.getString("cvu"));
                p.setNombreCompleto(rs.getString("nombre_completo"));
                p.setNivelSni(rs.getString("nivel_sni"));
                p.setTipoDedicacion(rs.getString("tipo_dedicacion"));
                p.setDepartamento(rs.getString("departamento"));
                p.setFechaIngresoNucleo(rs.getDate("fecha_ingreso_nucleo"));
                p.setCorreoInstitucional(rs.getString("correo_institucional"));

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}

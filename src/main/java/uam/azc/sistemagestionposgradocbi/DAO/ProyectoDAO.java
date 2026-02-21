package uam.azc.sistemagestionposgradocbi.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import uam.azc.sistemagestionposgradocbi.modelo.Proyecto;
import uam.azc.sistemagestionposgradocbi.util.Conexion;

public class ProyectoDAO implements CrudDAO<Proyecto, String> {

    @Override
    public List<Proyecto> listar() {
        return buscarPorFiltros("", "", "");
    }

    // =========================
    // FILTROS DINÁMICOS
    // =========================
    public List<Proyecto> buscarPorFiltros(String matricula, String titulo, String asesor) {
        List<Proyecto> lista = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT t.matricula, a.nombre_completo AS alumno, t.titulo, " +
            "pd.nombre_completo AS director, pc.nombre_completo AS codirector, " +
            "ac.nombre AS area " +
            "FROM tesis t " +
            "INNER JOIN alumno a ON t.matricula = a.matricula " +
            "INNER JOIN area_concentracion ac ON t.id_area_concentracion = ac.id_area " +
            "LEFT JOIN profesor pd ON t.numero_economico_profesor_director = pd.numero_economico " +
            "LEFT JOIN profesor pc ON t.numero_economico_profesor_codirector = pc.numero_economico " +
            "WHERE 1=1"
        );

        if (matricula != null && !matricula.isEmpty()) {
            sql.append(" AND t.matricula LIKE ?");
        }
        if (titulo != null && !titulo.isEmpty()) {
            sql.append(" AND t.titulo LIKE ?");
        }
        if (asesor != null && !asesor.isEmpty()) {
            // Busca coincidencia ya sea en el director o en el codirector
            sql.append(" AND (pd.nombre_completo LIKE ? OR pc.nombre_completo LIKE ?)");
        }

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int index = 1;
            if (matricula != null && !matricula.isEmpty()) {
                ps.setString(index++, matricula + "%"); // Empieza con
            }
            if (titulo != null && !titulo.isEmpty()) {
                ps.setString(index++, "%" + titulo + "%"); // Contiene
            }
            if (asesor != null && !asesor.isEmpty()) {
                ps.setString(index++, "%" + asesor + "%"); // Contiene (Para Director)
                ps.setString(index++, "%" + asesor + "%"); // Contiene (Para Codirector)
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Proyecto p = new Proyecto();
                    p.setMatricula(rs.getString("matricula"));
                    p.setNombreAlumno(rs.getString("alumno"));
                    p.setTituloTesis(rs.getString("titulo"));
                    p.setDirector(rs.getString("director"));
                    p.setCodirector(rs.getString("codirector")); // Puede ser null
                    p.setAreaConcentracion(rs.getString("area"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Métodos CrudDAO restantes por implementar
    @Override public Proyecto buscarPorId(String id) { return null; }
    @Override public boolean insertar(Proyecto objeto) { return false; }
    @Override public boolean actualizar(Proyecto objeto) { return false; }
    @Override public boolean eliminar(String id) { return false; }
}

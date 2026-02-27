package uam.azc.sistemagestionposgradocbi.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import uam.azc.sistemagestionposgradocbi.modelo.Proyecto;
import uam.azc.sistemagestionposgradocbi.util.Conexion;

/**
 * DAO encargado de recuperar información de proyectos
 * de tesis asociados a alumnos del posgrado.
 *
 * Realiza consultas mediante múltiples JOIN
 * entre alumno, profesor y área de concentración.
 *
 * Principalmente utilizado para consultas
 * y visualización.
 *
 * @author Vania Alejandra Contreras Torres
 */
public class ProyectoDAO implements CrudDAO<Proyecto, String> {

    /**
    * Recupera todos los proyectos registrados.
    *
    * Internamente reutiliza el método de búsqueda
    * utilizando filtros vacíos.
    *
    * @return lista completa de proyectos
    */
    @Override
    public List<Proyecto> listar() {
        return buscarPorFiltros("", "", "");
    }

    /**
    * Consulta proyectos de tesis aplicando filtros dinámicos.
    *
    * Permite buscar por:
    * - matrícula
    * - título de tesis
    * - asesor o codirector
    *
    * @param matricula matrícula del alumno
    * @param titulo título parcial de tesis
    * @param asesor nombre del asesor
    * @return lista de proyectos encontrados
    */
    public List<Proyecto> buscarPorFiltros(String matricula, String titulo, String asesor) {
        List<Proyecto> lista = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT t.matricula, a.nombre_completo AS alumno,"+
            "e.nombre AS estatus_uam, " +
            "t.titulo, " +
            "pd.nombre_completo AS director, pc.nombre_completo AS codirector, " +
            "ac.nombre AS area " +
            "FROM tesis t " +
            "INNER JOIN alumno a ON t.matricula = a.matricula " +
            "INNER JOIN estatus_alumno e ON a.id_estatus = e.id_estatus " +
            "LEFT JOIN area_concentracion ac ON t.id_area_concentracion = ac.id_area " +
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
                    p.setEstatusUam(rs.getString("estatus_uam"));
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

        /**
    * Método no implementado.
    *
    * @throws UnsupportedOperationException
    */
    @Override public Proyecto buscarPorId(String id) { return null; }
    
    /**
    * Método no implementado.
    *
    * @throws UnsupportedOperationException
    */
    @Override public boolean insertar(Proyecto objeto) { return false; }
    
    /**
    * Método no implementado.
    *
    * @throws UnsupportedOperationException
    */
    @Override public boolean actualizar(Proyecto objeto) { return false; }
    
    /**
    * Método no implementado.
    *
    * @throws UnsupportedOperationException
    */
    @Override public boolean eliminar(String id) { return false; }
}

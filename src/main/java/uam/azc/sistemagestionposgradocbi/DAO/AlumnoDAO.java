package uam.azc.sistemagestionposgradocbi.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import uam.azc.sistemagestionposgradocbi.modelo.Alumno;
import uam.azc.sistemagestionposgradocbi.util.Conexion;

public class AlumnoDAO implements CrudDAO<Alumno, String> {

    // ==========================================
    // 1. LECTURA Y FILTROS MÚLTIPLES
    // ==========================================
    public List<Alumno> buscarPorFiltros(String matricula, String trimIngreso, String trimPierdeCalidad, String anioTitulacion, String estatusUam) {
        List<Alumno> lista = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT a.matricula, a.nombre_completo, a.correo_institucional, a.correo_alternativo, a.telefono, " +
            "a.trimestre_ingreso, a.fecha_inicio, a.trimestre_pierde_calidad, a.numero_acta, a.fecha_titulacion, " +
            "ea.nombre AS estatus_uam, b.estatus_beca, t.titulo AS titulo_tesis, ac.nombre AS area_concentracion, " +
            "pd.nombre_completo AS director, pc.nombre_completo AS codirector " +
            "FROM alumno a " +
            "INNER JOIN estatus_alumno ea ON a.id_estatus = ea.id_estatus " +
            "LEFT JOIN beca b ON a.matricula = b.matricula " +
            "LEFT JOIN tesis t ON a.matricula = t.matricula " +
            "LEFT JOIN area_concentracion ac ON t.id_area_concentracion = ac.id_area " +
            "LEFT JOIN profesor pd ON t.numero_economico_profesor_director = pd.numero_economico " +
            "LEFT JOIN profesor pc ON t.numero_economico_profesor_codirector = pc.numero_economico " +
            "WHERE 1=1"
        );

        if (matricula != null && !matricula.isEmpty()) sql.append(" AND a.matricula LIKE ?");
        if (trimIngreso != null && !trimIngreso.isEmpty()) sql.append(" AND a.trimestre_ingreso = ?");
        if (trimPierdeCalidad != null && !trimPierdeCalidad.isEmpty()) sql.append(" AND a.trimestre_pierde_calidad = ?");
        if (anioTitulacion != null && !anioTitulacion.isEmpty()) sql.append(" AND YEAR(a.fecha_titulacion) = ?");
        if (estatusUam != null && !estatusUam.isEmpty()) sql.append(" AND ea.nombre = ?");

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int index = 1;
            if (matricula != null && !matricula.isEmpty()) ps.setString(index++, matricula + "%");
            if (trimIngreso != null && !trimIngreso.isEmpty()) ps.setString(index++, trimIngreso);
            if (trimPierdeCalidad != null && !trimPierdeCalidad.isEmpty()) ps.setString(index++, trimPierdeCalidad);
            if (anioTitulacion != null && !anioTitulacion.isEmpty()) ps.setString(index++, anioTitulacion);
            if (estatusUam != null && !estatusUam.isEmpty()) ps.setString(index++, estatusUam);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Alumno a = new Alumno();
                    a.setMatricula(rs.getString("matricula"));
                    a.setNombreCompleto(rs.getString("nombre_completo"));
                    a.setCorreoInstitucional(rs.getString("correo_institucional"));
                    a.setCorreoAlternativo(rs.getString("correo_alternativo"));
                    a.setTelefono(rs.getString("telefono"));
                    a.setTrimestreIngreso(rs.getString("trimestre_ingreso"));
                    a.setFechaInicio(rs.getDate("fecha_inicio"));
                    a.setTrimestrePierdeCalidad(rs.getString("trimestre_pierde_calidad"));
                    a.setNumeroActa(rs.getString("numero_acta"));
                    a.setFechaTitulacion(rs.getDate("fecha_titulacion"));
                    a.setEstatusUam(rs.getString("estatus_uam"));
                    a.setEstatusBeca(rs.getString("estatus_beca"));
                    a.setTituloTesis(rs.getString("titulo_tesis"));
                    a.setAreaConcentracion(rs.getString("area_concentracion"));
                    a.setDirector(rs.getString("director"));
                    a.setCodirector(rs.getString("codirector"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    @Override
    public List<Alumno> listar() {
        return buscarPorFiltros("", "", "", "", "");
    }

    // ==========================================
    // 2. MÉTODOS CRUD PARA AGREGAR, EDITAR Y ELIMINAR
    // ==========================================
    @Override
    public boolean insertar(Alumno a) {
        String sql = "INSERT INTO alumno (matricula, nombre_completo, correo_institucional, correo_alternativo, telefono, trimestre_ingreso, trimestre_pierde_calidad) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getMatricula());
            ps.setString(2, a.getNombreCompleto());
            ps.setString(3, a.getCorreoInstitucional());
            ps.setString(4, a.getCorreoAlternativo());
            ps.setString(5, a.getTelefono());
            ps.setString(6, a.getTrimestreIngreso());
            ps.setString(7, a.getTrimestrePierdeCalidad());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public boolean actualizar(Alumno a) {
        String sql = "UPDATE alumno SET nombre_completo=?, correo_institucional=?, correo_alternativo=?, telefono=?, trimestre_ingreso=?, trimestre_pierde_calidad=? WHERE matricula=?";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNombreCompleto());
            ps.setString(2, a.getCorreoInstitucional());
            ps.setString(3, a.getCorreoAlternativo());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getTrimestreIngreso());
            ps.setString(6, a.getTrimestrePierdeCalidad());
            ps.setString(7, a.getMatricula());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public boolean eliminar(String matricula) {
        String sql = "DELETE FROM alumno WHERE matricula = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, matricula);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public Alumno buscarPorId(String id) { return null; /* Implementar si necesitas cargar datos al modal de edición */ }
}

package uam.azc.sistemagestionposgradocbi.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import uam.azc.sistemagestionposgradocbi.modelo.Beca;
import uam.azc.sistemagestionposgradocbi.util.Conexion;

// Implementamos CrudDAO usando la matrícula como clave primaria
public class BecaDAO implements CrudDAO<Beca, String> {

    // =========================
    // LISTAR TODOS
    // =========================
    @Override
    public List<Beca> listar() {
        // Reutilizamos el método de búsqueda con filtros vacíos
        return buscarPorFiltros("", "", "");
    }

    // =========================
    // FILTROS DINÁMICOS
    // =========================
    public List<Beca> buscarPorFiltros(String cvu, String generacion, String estatus) {
        List<Beca> lista = new ArrayList<>();
        // SQL con JOIN a la tabla alumno para obtener nombres y datos académicos
        StringBuilder sql = new StringBuilder(
                "SELECT b.matricula, a.nombre_completo, a.cvu, a.trimestre_ingreso, " +
                "b.fecha_fin_vigencia AS fin_beca, b.fecha_max_conahcyt, " +
                "b.estatus_beca, a.fecha_titulacion " +
                "FROM beca b " +
                "INNER JOIN alumno a ON b.matricula = a.matricula " +
                "WHERE 1=1"
        );

        // Construcción dinámica de la consulta
        if (cvu != null && !cvu.isEmpty()) {
            sql.append(" AND a.cvu LIKE ?");
        }
        if (generacion != null && !generacion.isEmpty()) {
            sql.append(" AND a.trimestre_ingreso = ?");
        }
        if (estatus != null && !estatus.isEmpty()) {
            sql.append(" AND b.estatus_beca = ?");
        }

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int index = 1;
            if (cvu != null && !cvu.isEmpty()) {
                ps.setString(index++, cvu + "%"); // "Empieza con" para respetar orden
            }
            if (generacion != null && !generacion.isEmpty()) {
                ps.setString(index++, generacion); // Búsqueda exacta
            }
            if (estatus != null && !estatus.isEmpty()) {
                ps.setString(index++, estatus); // Búsqueda exacta
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Beca b = new Beca();
                    b.setMatricula(rs.getString("matricula"));
                    b.setNombreAlumno(rs.getString("nombre_completo"));
                    b.setCvu(rs.getString("cvu"));
                    b.setTrimestreIngreso(rs.getString("trimestre_ingreso"));
                    b.setFechaFinBeca(rs.getDate("fin_beca"));
                    b.setFechaMaxConahcyt(rs.getDate("fecha_max_conahcyt"));
                    b.setEstatusBeca(rs.getString("estatus_beca"));
                    b.setFechaTitulacion(rs.getDate("fecha_titulacion"));
                    lista.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // =========================
    // MÉTODOS CRUDDAO (Por implementar si los necesitas luego)
    // =========================
    @Override
    public Beca buscarPorId(String id) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override
    public boolean insertar(Beca objeto) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override
    public boolean actualizar(Beca objeto) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override
    public boolean eliminar(String id) { throw new UnsupportedOperationException("Not supported yet."); }
}
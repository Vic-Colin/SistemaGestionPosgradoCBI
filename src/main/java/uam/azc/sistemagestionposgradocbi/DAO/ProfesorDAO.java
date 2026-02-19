package uam.azc.sistemagestionposgradocbi.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import uam.azc.sistemagestionposgradocbi.modelo.Profesor;
import uam.azc.sistemagestionposgradocbi.util.Conexion;

public class ProfesorDAO implements CrudDAO<Profesor, String> {

    // =========================
    // LISTAR TODOS
    // =========================
    @Override
    public List<Profesor> listar() {
        return buscarPorFiltros("", "");
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    @Override
    public Profesor buscarPorId(String numeroEconomico) {

        String sql =
            "SELECT * FROM profesor WHERE numero_economico = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, numeroEconomico);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Profesor p = new Profesor();
                p.setNumeroEconomico(rs.getString("numero_economico"));
                p.setCvu(rs.getString("cvu"));
                p.setNombreCompleto(rs.getString("nombre_completo"));
                p.setFechaIngresoNucleo(rs.getDate("fecha_ingreso_nucleo"));
                p.setCorreoInstitucional(rs.getString("correo_institucional"));
                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // =========================
    // FILTROS DINÁMICOS
    // =========================
    public List<Profesor> buscarPorFiltros(String numeroEconomico, String cvu) {

        List<Profesor> lista = new ArrayList<>();

        String sql =
            "SELECT " +
            "p.numero_economico, " +
            "p.cvu, " +
            "p.nombre_completo, " +
            "ns.nombre AS nivel_sni, " +
            "td.nombre AS tipo_dedicacion, " +
            "d.nombre AS departamento, " +
            "p.fecha_ingreso_nucleo, " +
            "p.correo_institucional " +
            "FROM profesor p " +
            "LEFT JOIN nivel_sni ns ON p.id_nivel_sni = ns.id_nivel_sni " +
            "INNER JOIN tipo_dedicacion td ON p.id_dedicacion = td.id_dedicacion " +
            "INNER JOIN departamento d ON p.id_departamento = d.id_departamento " +
            "WHERE 1=1 ";

        if (numeroEconomico != null && !numeroEconomico.isEmpty()) {
            sql += " AND p.numero_economico LIKE ? ";
        }

        if (cvu != null && !cvu.isEmpty()) {
            sql += " AND p.cvu LIKE ? ";
        }

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int index = 1;

            if (numeroEconomico != null && !numeroEconomico.isEmpty()) {
                ps.setString(index++,numeroEconomico + "%");
            }

            if (cvu != null && !cvu.isEmpty()) {
                ps.setString(index++,cvu + "%");
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

    // =========================
    // INSERTAR
    // =========================
    @Override
    public boolean insertar(Profesor p) {

        String sql =
            "INSERT INTO profesor " +
            "(numero_economico, cvu, nombre_completo, id_nivel_sni, id_dedicacion, id_departamento, fecha_ingreso_nucleo, correo_institucional) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNumeroEconomico());
            ps.setString(2, p.getCvu());
            ps.setString(3, p.getNombreCompleto());
            ps.setInt(4, 1);  // Ajustar según formulario
            ps.setInt(5, 1);
            ps.setInt(6, 1);
            ps.setDate(7, (Date) p.getFechaIngresoNucleo());
            ps.setString(8, p.getCorreoInstitucional());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // ACTUALIZAR
    // =========================
    @Override
    public boolean actualizar(Profesor p) {

        String sql =
            "UPDATE profesor SET cvu=?, nombre_completo=?, fecha_ingreso_nucleo=?, correo_institucional=? " +
            "WHERE numero_economico=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getCvu());
            ps.setString(2, p.getNombreCompleto());
            ps.setDate(3, (Date) p.getFechaIngresoNucleo());
            ps.setString(4, p.getCorreoInstitucional());
            ps.setString(5, p.getNumeroEconomico());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // ELIMINAR
    // =========================
    @Override
    public boolean eliminar(String numeroEconomico) {

        String sql = "DELETE FROM profesor WHERE numero_economico=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, numeroEconomico);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}

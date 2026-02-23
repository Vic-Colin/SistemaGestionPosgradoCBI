package uam.azc.sistemagestionposgradocbi.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import uam.azc.sistemagestionposgradocbi.modelo.Alumno;
import uam.azc.sistemagestionposgradocbi.modelo.AreaConcentracion;
import uam.azc.sistemagestionposgradocbi.modelo.Profesor;
import uam.azc.sistemagestionposgradocbi.util.Conexion;

public class AlumnoDAO implements CrudDAO<Alumno, String> {

    // ==========================================
    // 1. LECTURA Y FILTROS MÚLTIPLES
    // ==========================================
    public List<Alumno> buscarPorFiltros(String matricula, String trimIngreso, String trimPierdeCalidad, String anioTitulacion, String estatusUam) {
        List<Alumno> lista = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT a.matricula, a.curp, a.nombre_completo, a.correo_institucional, a.correo_alternativo, a.telefono, " +
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
                    a.setCurp(rs.getString("curp"));
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
        Connection con = null;
        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false); // 🔴 INICIA TRANSACCIÓN

            // 1. Insertar en ALUMNO (Se corrigió la query agregando 'curp' y su '?')
            String sqlAlumno = "INSERT INTO alumno (matricula, curp, cvu, nombre_completo, correo_institucional, correo_alternativo, telefono, trimestre_ingreso, trimestre_pierde_calidad, id_generacion, id_estatus, fecha_inicio, numero_acta, fecha_titulacion) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 1, 1, CURDATE(), ?, ?)";
            try (PreparedStatement psA = con.prepareStatement(sqlAlumno)) {
                psA.setString(1, a.getMatricula());
                psA.setString(2, a.getCurp());
                psA.setString(3, (a.getCvu() != null && !a.getCvu().isEmpty()) ? a.getCvu() : null);
                psA.setString(4, a.getNombreCompleto());
                psA.setString(5, a.getCorreoInstitucional());
                psA.setString(6, a.getCorreoAlternativo());
                psA.setString(7, a.getTelefono());
                psA.setString(8, a.getTrimestreIngreso());
                psA.setString(9, a.getTrimestrePierdeCalidad());
                psA.setString(10, a.getNumeroActa());
                
                if(a.getFechaTitulacion() != null) psA.setDate(11, a.getFechaTitulacion()); 
                else psA.setNull(11, Types.DATE);
                
                psA.executeUpdate();
            }

            // 2. Insertar en BECA
            if (a.getEstatusBeca() != null && !a.getEstatusBeca().equals("NO TUVO BECA")) {
                String sqlBeca = "INSERT INTO beca (matricula, estatus_beca, fecha_fin_vigencia, fecha_max_conahcyt) VALUES (?, ?, ?, ?)";
                try (PreparedStatement psB = con.prepareStatement(sqlBeca)) {
                    psB.setString(1, a.getMatricula());
                    psB.setString(2, a.getEstatusBeca());
                    // Control de fechas vacías desde JS
                    psB.setString(3, (a.getRegFechaFinBeca() == null || a.getRegFechaFinBeca().isEmpty()) ? null : a.getRegFechaFinBeca());
                    psB.setString(4, (a.getRegFechaMax() == null || a.getRegFechaMax().isEmpty()) ? null : a.getRegFechaMax());
                    psB.executeUpdate();
                }
            }

            // 3. Insertar en TESIS
            if (a.getTituloTesis() != null && !a.getTituloTesis().isEmpty()) {
                String sqlTesis = "INSERT INTO tesis (matricula, titulo, id_area_concentracion, numero_economico_profesor_director, numero_economico_profesor_codirector) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement psT = con.prepareStatement(sqlTesis)) {
                    psT.setString(1, a.getMatricula());
                    psT.setString(2, a.getTituloTesis());
                    if (a.getIdAreaConcentracion() > 0) psT.setInt(3, a.getIdAreaConcentracion()); else psT.setNull(3, Types.INTEGER);
                    if (a.getNumEcoDirector() != null && !a.getNumEcoDirector().isEmpty()) psT.setString(4, a.getNumEcoDirector()); else psT.setNull(4, Types.VARCHAR);
                    if (a.getNumEcoCodirector() != null && !a.getNumEcoCodirector().isEmpty()) psT.setString(5, a.getNumEcoCodirector()); else psT.setNull(5, Types.VARCHAR);
                    psT.executeUpdate();
                }
            }

            con.commit(); // 🟢 CONFIRMA TRANSACCIÓN
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } 
            }
            return false;
        } finally {
            if (con != null) { try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
    }

    @Override
    public boolean actualizar(Alumno a) {
        Connection con = null;
        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false);

            // 1. UPDATE ALUMNO
            String sqlAlumno = "UPDATE alumno SET curp=?, cvu=?, nombre_completo=?, correo_institucional=?, correo_alternativo=?, telefono=?, trimestre_ingreso=?, trimestre_pierde_calidad=?, numero_acta=?, fecha_titulacion=? WHERE matricula=?";
            try (PreparedStatement ps = con.prepareStatement(sqlAlumno)) {
                ps.setString(1, a.getCurp());
                ps.setString(2, (a.getCvu() != null && !a.getCvu().isEmpty()) ? a.getCvu() : null);
                ps.setString(3, a.getNombreCompleto());
                ps.setString(4, a.getCorreoInstitucional());
                ps.setString(5, a.getCorreoAlternativo());
                ps.setString(6, a.getTelefono());
                ps.setString(7, a.getTrimestreIngreso());
                ps.setString(8, a.getTrimestrePierdeCalidad());
                ps.setString(9, a.getNumeroActa());
                if(a.getFechaTitulacion() != null) ps.setDate(10, a.getFechaTitulacion()); else ps.setNull(10, Types.DATE);
                ps.setString(11, a.getMatricula());
                ps.executeUpdate();
            }

            // Variables seguras para fechas (evita error si vienen vacías de JS)
            String fFin = (a.getRegFechaFinBeca() == null || a.getRegFechaFinBeca().isEmpty()) ? null : a.getRegFechaFinBeca();
            String fMax = (a.getRegFechaMax() == null || a.getRegFechaMax().isEmpty()) ? null : a.getRegFechaMax();

            // 2. UPSERT EN BECA (Actualiza o Inserta dependiendo si ya existía)
            if (a.getEstatusBeca() != null && !a.getEstatusBeca().equals("NO TUVO BECA")) {
                String updateBeca = "UPDATE beca SET estatus_beca=?, fecha_fin_vigencia=?, fecha_max_conahcyt=? WHERE matricula=?";
                try (PreparedStatement psUpdateB = con.prepareStatement(updateBeca)) {
                    psUpdateB.setString(1, a.getEstatusBeca());
                    psUpdateB.setString(2, fFin);
                    psUpdateB.setString(3, fMax);
                    psUpdateB.setString(4, a.getMatricula());
                    int filas = psUpdateB.executeUpdate();
                    
                    if (filas == 0) { // El alumno no tenía beca registrada previamente, la insertamos
                        String insertBeca = "INSERT INTO beca (matricula, estatus_beca, fecha_inicio, fecha_fin_vigencia, fecha_max_conahcyt) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement psInsB = con.prepareStatement(insertBeca)) {
                            psInsB.setString(1, a.getMatricula());
                            psInsB.setString(2, a.getEstatusBeca());
                            psInsB.setString(3, fFin);
                            psInsB.setString(4, fMax);
                            psInsB.executeUpdate();
                        }
                    }
                }
            } else {
                // Si ahora dice "Sin Beca", borramos el registro si es que lo tenía
                try (PreparedStatement psDelB = con.prepareStatement("DELETE FROM beca WHERE matricula=?")) {
                    psDelB.setString(1, a.getMatricula());
                    psDelB.executeUpdate();
                }
            }

            // 3. UPSERT EN TESIS
            if (a.getTituloTesis() != null && !a.getTituloTesis().isEmpty()) {
                String updateTesis = "UPDATE tesis SET titulo=?, id_area_concentracion=?, numero_economico_profesor_director=?, numero_economico_profesor_codirector=? WHERE matricula=?";
                try (PreparedStatement psUpdateT = con.prepareStatement(updateTesis)) {
                    psUpdateT.setString(1, a.getTituloTesis());
                    if (a.getIdAreaConcentracion() > 0) psUpdateT.setInt(2, a.getIdAreaConcentracion()); else psUpdateT.setNull(2, Types.INTEGER);
                    if (a.getNumEcoDirector() != null && !a.getNumEcoDirector().isEmpty()) psUpdateT.setString(3, a.getNumEcoDirector()); else psUpdateT.setNull(3, Types.VARCHAR);
                    if (a.getNumEcoCodirector() != null && !a.getNumEcoCodirector().isEmpty()) psUpdateT.setString(4, a.getNumEcoCodirector()); else psUpdateT.setNull(4, Types.VARCHAR);
                    psUpdateT.setString(5, a.getMatricula());
                    
                    int filasTesis = psUpdateT.executeUpdate();
                    if (filasTesis == 0) { // No tenía tesis, se inserta nueva
                        String insertTesis = "INSERT INTO tesis (matricula, titulo, id_area_concentracion, numero_economico_profesor_director, numero_economico_profesor_codirector) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement psInsT = con.prepareStatement(insertTesis)) {
                            psInsT.setString(1, a.getMatricula());
                            psInsT.setString(2, a.getTituloTesis());
                            if (a.getIdAreaConcentracion() > 0) psInsT.setInt(3, a.getIdAreaConcentracion()); else psInsT.setNull(3, Types.INTEGER);
                            if (a.getNumEcoDirector() != null && !a.getNumEcoDirector().isEmpty()) psInsT.setString(4, a.getNumEcoDirector()); else psInsT.setNull(4, Types.VARCHAR);
                            if (a.getNumEcoCodirector() != null && !a.getNumEcoCodirector().isEmpty()) psInsT.setString(5, a.getNumEcoCodirector()); else psInsT.setNull(5, Types.VARCHAR);
                            psInsT.executeUpdate();
                        }
                    }
                }
            } else {
                 try (PreparedStatement psDelT = con.prepareStatement("DELETE FROM tesis WHERE matricula=?")) {
                    psDelT.setString(1, a.getMatricula());
                    psDelT.executeUpdate();
                }
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace(); // Revisa la consola de tu servidor si algo sigue fallando
            if (con != null) { try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } }
            return false;
        } finally {
            if (con != null) { try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
    }

    @Override
    public boolean eliminar(String matricula) {
        String sql = "DELETE FROM alumno WHERE matricula = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, matricula);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ==========================================
    // 3. MÉTODOS PARA OBTENER CATÁLOGOS (Selects)
    // ==========================================
    public List<AreaConcentracion> obtenerAreas() {
        List<AreaConcentracion> lista = new ArrayList<>();
        String sql = "SELECT id_area, nombre FROM area_concentracion";
        try (Connection con = Conexion.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {
            while(rs.next()){
                AreaConcentracion a = new AreaConcentracion();
                a.setIdArea(rs.getInt("id_area"));
                a.setNombre(rs.getString("nombre"));
                lista.add(a);
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Profesor> obtenerProfesores() {
        List<Profesor> lista = new ArrayList<>();
        // Traemos solo lo necesario para el select, ordenado alfabéticamente
        String sql = "SELECT numero_economico, nombre_completo FROM profesor ORDER BY nombre_completo";
        try (Connection con = Conexion.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {
            while(rs.next()){
                Profesor p = new Profesor();
                p.setNumeroEconomico(rs.getString("numero_economico"));
                p.setNombreCompleto(rs.getString("nombre_completo"));
                lista.add(p);
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return lista;
    }
    
    @Override
    public Alumno buscarPorId(String matricula) {
        Alumno a = null;
        String sql = "SELECT a.matricula,a.curp, a.nombre_completo, a.correo_institucional, a.correo_alternativo, a.telefono, " +
                     "a.trimestre_ingreso, a.fecha_inicio, a.trimestre_pierde_calidad, a.numero_acta, a.fecha_titulacion, a.cvu, " +
                     "ea.nombre AS estatus_uam, " +
                     "b.estatus_beca, b.fecha_fin_vigencia AS beca_fin, b.fecha_max_conahcyt AS beca_max, " +
                     "t.titulo AS titulo_tesis, t.id_area_concentracion, t.numero_economico_profesor_director, t.numero_economico_profesor_codirector " +
                     "FROM alumno a " +
                     "LEFT JOIN estatus_alumno ea ON a.id_estatus = ea.id_estatus " +
                     "LEFT JOIN beca b ON a.matricula = b.matricula " +
                     "LEFT JOIN tesis t ON a.matricula = t.matricula " +
                     "WHERE a.matricula = ?";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    a = new Alumno();
                    
                    // Datos Generales y Académicos
                    a.setMatricula(rs.getString("matricula"));
                    a.setCurp(rs.getString("curp"));
                    a.setNombreCompleto(rs.getString("nombre_completo"));
                    a.setCorreoInstitucional(rs.getString("correo_institucional"));
                    a.setCorreoAlternativo(rs.getString("correo_alternativo"));
                    a.setTelefono(rs.getString("telefono"));
                    a.setTrimestreIngreso(rs.getString("trimestre_ingreso"));
                    a.setFechaInicio(rs.getDate("fecha_inicio"));
                    a.setTrimestrePierdeCalidad(rs.getString("trimestre_pierde_calidad"));
                    a.setNumeroActa(rs.getString("numero_acta"));
                    a.setFechaTitulacion(rs.getDate("fecha_titulacion"));
                    a.setCvu(rs.getString("cvu"));
                    a.setEstatusUam(rs.getString("estatus_uam"));
                    
                    // Datos de Beca (Mapeados como String para evitar conflictos de parseo en JSON)
                    a.setEstatusBeca(rs.getString("estatus_beca"));
                    a.setRegFechaFinBeca(rs.getString("beca_fin"));
                    a.setRegFechaMax(rs.getString("beca_max"));
                    
                    // Datos de Tesis (Incluye los IDs necesarios para los Selects)
                    a.setTituloTesis(rs.getString("titulo_tesis"));
                    a.setIdAreaConcentracion(rs.getInt("id_area_concentracion"));
                    a.setNumEcoDirector(rs.getString("numero_economico_profesor_director"));
                    a.setNumEcoCodirector(rs.getString("numero_economico_profesor_codirector"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }
}

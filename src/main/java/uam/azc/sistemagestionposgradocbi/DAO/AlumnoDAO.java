package uam.azc.sistemagestionposgradocbi.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import uam.azc.sistemagestionposgradocbi.modelo.Alumno;
import uam.azc.sistemagestionposgradocbi.modelo.AreaConcentracion;
import uam.azc.sistemagestionposgradocbi.modelo.Profesor;
import uam.azc.sistemagestionposgradocbi.util.Conexion;

/**
 * DAO encargado de la gestión de persistencia
 * de la entidad Alumno.
 *
 * Implementa operaciones CRUD y consultas
 * complejas mediante JOINs con:
 *  - beca
 *  - tesis
 *  - profesor
 *  - area_concentracion
 *
 * Maneja transacciones JDBC para garantizar
 * integridad de datos.
 *
 * Patrón aplicado:
 * Data Access Object (DAO)
 *
 * Dependencias:
 * Conexion
 * Alumno
 * Profesor
 * AreaConcentracion
 *
 * @author Vania Alejandra Contreras Torres
 */
public class AlumnoDAO implements CrudDAO<Alumno, String> {

    /**
    * Busca alumnos aplicando filtros dinámicos.
    *
    * @param matricula matrícula parcial o completa
    * @param trimIngreso trimestre de ingreso
    * @param trimPierdeCalidad trimestre pérdida calidad
    * @param anioTitulacion año de titulación
    * @param estatusUam estatus académico
    * @return lista de alumnos filtrados
    */
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

    /**
    * Recupera todos los alumnos registrados.
    *
    * Internamente reutiliza el método de búsqueda
    * utilizando filtros vacíos.
    *
    * @return lista completa de alumnos
    */
    @Override
    public List<Alumno> listar() {
        return buscarPorFiltros("", "", "", "", "");
    }

    /**
    * Inserta un nuevo alumno en la base de datos.
    *
    * @param a alumno a registrar
    * @return true si la inserción fue exitosa
    */
    @Override
    public boolean insertar(Alumno a) {
        Connection con = null;
        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false); // INICIA TRANSACCIÓN

            // 1. INSERTAR EN ALUMNO (Mapeando el Estatus UAM de String a ID)
            String sqlAlumno = "INSERT INTO alumno (matricula, curp, cvu, nombre_completo, correo_institucional, correo_alternativo, telefono, trimestre_ingreso, trimestre_pierde_calidad, id_generacion, id_estatus, fecha_inicio, numero_acta, fecha_titulacion) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 1, COALESCE((SELECT id_estatus FROM estatus_alumno WHERE nombre = ? LIMIT 1), 1), CURDATE(), ?, ?)";
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
                psA.setString(10, a.getEstatusUam()); // String (ej. "VIGENTE")
                psA.setString(11, a.getNumeroActa());
                if(a.getFechaTitulacion() != null) psA.setDate(12, a.getFechaTitulacion()); else psA.setNull(12, Types.DATE);
                psA.executeUpdate();
            }

            String fFin = (a.getRegFechaFinBeca() == null || a.getRegFechaFinBeca().isEmpty()) ? null : a.getRegFechaFinBeca();
            String fMax = (a.getRegFechaMax() == null || a.getRegFechaMax().isEmpty()) ? null : a.getRegFechaMax();

            // 2. INSERTAR EN BECA
            if (a.getEstatusBeca() != null && !a.getEstatusBeca().equals("NO TUVO BECA")) {
                String sqlBeca = "INSERT INTO beca (matricula, estatus_beca, fecha_fin_vigencia, fecha_max_conahcyt) VALUES (?, ?, ?, ?)";
                try (PreparedStatement psB = con.prepareStatement(sqlBeca)) {
                    psB.setString(1, a.getMatricula());
                    psB.setString(2, a.getEstatusBeca());
                    psB.setString(3, fFin);
                    psB.setString(4, fMax);
                    psB.executeUpdate();
                }
            }

            // 3. INSERTAR EN TESIS
            if (a.getTituloTesis() != null && !a.getTituloTesis().trim().isEmpty()) {
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

            con.commit(); 
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) { try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } }
            return false;
        } finally {
            if (con != null) { try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
    }

    /**
    * Actualiza la información de un alumno existente.
    *
    * @param a alumno con datos modificados
    * @return true si la actualización fue exitosa
    */
    @Override
    public boolean actualizar(Alumno a) {
        Connection con = null;
        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false);

            // 1. UPDATE ALUMNO
            String sqlAlumno = "UPDATE alumno SET curp=?, cvu=?, nombre_completo=?, correo_institucional=?, correo_alternativo=?, telefono=?, trimestre_ingreso=?, trimestre_pierde_calidad=?, id_estatus=COALESCE((SELECT id_estatus FROM estatus_alumno WHERE nombre = ? LIMIT 1), id_estatus), numero_acta=?, fecha_titulacion=? WHERE matricula=?";
            try (PreparedStatement ps = con.prepareStatement(sqlAlumno)) {
                ps.setString(1, a.getCurp());
                ps.setString(2, (a.getCvu() != null && !a.getCvu().isEmpty()) ? a.getCvu() : null);
                ps.setString(3, a.getNombreCompleto());
                ps.setString(4, a.getCorreoInstitucional());
                ps.setString(5, a.getCorreoAlternativo());
                ps.setString(6, a.getTelefono());
                ps.setString(7, a.getTrimestreIngreso());
                ps.setString(8, a.getTrimestrePierdeCalidad());
                ps.setString(9, a.getEstatusUam()); // Ej: "Titulado"
                ps.setString(10, a.getNumeroActa());
                if(a.getFechaTitulacion() != null) ps.setDate(11, a.getFechaTitulacion()); else ps.setNull(11, Types.DATE);
                ps.setString(12, a.getMatricula());
                ps.executeUpdate();
            }

            String fFin = (a.getRegFechaFinBeca() == null || a.getRegFechaFinBeca().isEmpty()) ? null : a.getRegFechaFinBeca();
            String fMax = (a.getRegFechaMax() == null || a.getRegFechaMax().isEmpty()) ? null : a.getRegFechaMax();

            // 2. UPSERT SEGURO EN BECA (Solución al error principal)
            if (a.getEstatusBeca() != null && !a.getEstatusBeca().equals("NO TUVO BECA")) {
                boolean existeBeca = false;
                try (PreparedStatement psCheck = con.prepareStatement("SELECT 1 FROM beca WHERE matricula = ?")) {
                    psCheck.setString(1, a.getMatricula());
                    try (ResultSet rsCheck = psCheck.executeQuery()) {
                        if (rsCheck.next()) existeBeca = true;
                    }
                }

                if (existeBeca) {
                    String updateBeca = "UPDATE beca SET estatus_beca=?, fecha_fin_vigencia=?, fecha_max_conahcyt=? WHERE matricula=?";
                    try (PreparedStatement psUpdB = con.prepareStatement(updateBeca)) {
                        psUpdB.setString(1, a.getEstatusBeca());
                        psUpdB.setString(2, fFin);
                        psUpdB.setString(3, fMax);
                        psUpdB.setString(4, a.getMatricula());
                        psUpdB.executeUpdate();
                    }
                } else {
                    String insertBeca = "INSERT INTO beca (matricula, estatus_beca, fecha_fin_vigencia, fecha_max_conahcyt) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement psInsB = con.prepareStatement(insertBeca)) {
                        psInsB.setString(1, a.getMatricula());
                        psInsB.setString(2, a.getEstatusBeca());
                        psInsB.setString(3, fFin);
                        psInsB.setString(4, fMax);
                        psInsB.executeUpdate();
                    }
                }
            } else {
                try (PreparedStatement psDelB = con.prepareStatement("DELETE FROM beca WHERE matricula=?")) {
                    psDelB.setString(1, a.getMatricula());
                    psDelB.executeUpdate();
                }
            }

            // 3. UPSERT SEGURO EN TESIS
            if (a.getTituloTesis() != null && !a.getTituloTesis().trim().isEmpty()) {
                boolean existeTesis = false;
                try (PreparedStatement psCheckT = con.prepareStatement("SELECT 1 FROM tesis WHERE matricula = ?")) {
                    psCheckT.setString(1, a.getMatricula());
                    try (ResultSet rsCheckT = psCheckT.executeQuery()) {
                        if (rsCheckT.next()) existeTesis = true;
                    }
                }

                if (existeTesis) {
                    String updateTesis = "UPDATE tesis SET titulo=?, id_area_concentracion=?, numero_economico_profesor_director=?, numero_economico_profesor_codirector=? WHERE matricula=?";
                    try (PreparedStatement psUpdT = con.prepareStatement(updateTesis)) {
                        psUpdT.setString(1, a.getTituloTesis());
                        if (a.getIdAreaConcentracion() > 0) psUpdT.setInt(2, a.getIdAreaConcentracion()); else psUpdT.setNull(2, Types.INTEGER);
                        if (a.getNumEcoDirector() != null && !a.getNumEcoDirector().isEmpty()) psUpdT.setString(3, a.getNumEcoDirector()); else psUpdT.setNull(3, Types.VARCHAR);
                        if (a.getNumEcoCodirector() != null && !a.getNumEcoCodirector().isEmpty()) psUpdT.setString(4, a.getNumEcoCodirector()); else psUpdT.setNull(4, Types.VARCHAR);
                        psUpdT.setString(5, a.getMatricula());
                        psUpdT.executeUpdate();
                    }
                } else {
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
            } else {
                 try (PreparedStatement psDelT = con.prepareStatement("DELETE FROM tesis WHERE matricula=?")) {
                    psDelT.setString(1, a.getMatricula());
                    psDelT.executeUpdate();
                }
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            if (con != null) { try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } }
            return false;
        } finally {
            if (con != null) { try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
    }
    
    /**
    * Elimina un alumno mediante su matricula.
    *
    * @param matricula identificador del alumno
    * @return true si fue eliminado correctamente
    */
    @Override
    public boolean eliminar(String matricula) {
        // La sintaxis {CALL ...} es el estándar de JDBC para Procedimientos Almacenados
        String sql = "{CALL eliminar_alumno_completo(?)}";
        
        try (Connection con = Conexion.getConnection(); 
             CallableStatement cs = con.prepareCall(sql)) {
            
            cs.setString(1, matricula);
            
            // Ejecutamos el procedimiento. Retorna 'true' si el primer resultado es un ResultSet.
            boolean hasResults = cs.execute();
            
            if (hasResults) {
                try (ResultSet rs = cs.getResultSet()) {
                    if (rs.next()) {
                        // Leemos el SELECT CONCAT(...) AS mensaje; que enviaste en tu SQL
                        String mensaje = rs.getString("mensaje");
                        
                        // Validamos si la respuesta es de éxito
                        if (mensaje.contains("eliminado completamente")) {
                            return true;
                        } else {
                            System.out.println("Aviso SP: " + mensaje); // "No se encontró..."
                            return false;
                        }
                    }
                }
            }
            return false;
            
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false; 
        }
    }

    /**
    * Regresa todas las áreas de concentración que existen
    * 
    * @return lista completa de las areas de concentración
    */
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

    /**
    * Regresa todos los profesores que existen
    * 
    * @return lista completa de los profesores
    */
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
    
    /**
    * Busca un alumno mediante su matricula.
    *
    * @param matricula identificador del alumno
    * @return objeto Alumno o null si no existe
    */
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
    
    /**
 * Calcula automáticamente el trimestre en que pierde calidad estudiantil
 * basado en el trimestre de ingreso.
 * 
 * Regla:
 * - Si ingreso >= 22I (2022 Invierno): +12 trimestres (12 base + 2 pandemia)
 * - Si ingreso < 22I: +18 trimestres (extensión por pandemia)
 * 
 * Formato: "YY-T" donde YY=año corto (00-99), T=O/P/I
 * 
 * @param trimestreIngreso formato "YY-T" (ej: "23-O", "22-I")
 * @return trimestre de pérdida de calidad o null si hay error
 */
    public String calcularTrimestrePierdeCalidad(String trimestreIngreso) {
    if (trimestreIngreso == null || trimestreIngreso.trim().isEmpty()) {
        return null;
    }
    
    try {
        // Separar año y trimestre (ej: "23-O" → 23, O)
        String[] partes = trimestreIngreso.trim().split("-");
        if (partes.length != 2) return null;
        
        int anioCorto = Integer.parseInt(partes[0]);
        String trim = partes[1].toUpperCase().trim();
        
        // Convertir trimestre a número (O=1, P=2, I=3)
        int numTrim;
        switch (trim) {
            case "O": numTrim = 1; break;  // Otoño
            case "P": numTrim = 2; break;  // Primavera  
            case "I": numTrim = 3; break;  // Invierno
            default: return null;
        }
         // Calcular total de trimestres desde referencia año 0
        // 22I = (22 * 3) + 3 = 69 trimestres
        int totalTrimestresIngreso = (anioCorto * 3) + numTrim;
        int trim22I = (22 * 3) + 3; // 69
        
        // Determinar cuántos trimestres agregar según regla
        int trimestresAAgregar = (totalTrimestresIngreso >= trim22I) ? 13 : 18;
        
        // Calcular el trimestre final
        int totalTrimestresFinal = totalTrimestresIngreso + trimestresAAgregar;
        
        // Convertir de nuevo a formato YY-T
        int anioCortoFinal = totalTrimestresFinal / 3;
        int numTrimFinal = totalTrimestresFinal % 3;
        
        // Ajustar si el residuo es 0 (último trimestre del año anterior)
        if (numTrimFinal == 0) {
            anioCortoFinal--;
            numTrimFinal = 3;
        }
         // Convertir número a letra
        String trimFinal;
        switch (numTrimFinal) {
            case 1: trimFinal = "O"; break;
            case 2: trimFinal = "P"; break;
            case 3: trimFinal = "I"; break;
            default: trimFinal = "O";
        }
        
        // Formatear con 2 dígitos (ej: "27-P")
        return String.format("%02d-%s", anioCortoFinal % 100, trimFinal);
        
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
}

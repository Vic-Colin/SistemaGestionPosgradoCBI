package uam.azc.sistemagestionposgradocbi.modelo;

import java.io.Serializable;
import java.sql.Date;

/**
 * Clase que representa la información académica y administrativa
 * de un alumno del nucleo Académico del Posgrado.
 * 
 * Contiene datos institucionales utilizados para la gestión de
 * alumnos, asignación de directores, proyectos y consultas
 * dentro del Sistema de Gestión de Posgrado CBI.
 * 
 * Implementa Serializable para permitir la transferencia del objeto
 * entre capas del sistema o su persistencia temporal.
 * 
 * @author Vania Alejandra Contreras Torres
 */
public class Alumno implements Serializable {
    // Datos nativos del alumno
    private String matricula;
    private String curp;
    private String nombreCompleto;
    private String correoInstitucional;
    private String correoAlternativo;
    private String telefono;
    private String trimestreIngreso;
    private Date fechaInicio;
    private String trimestrePierdeCalidad;
    private String numeroActa;
    private Date fechaTitulacion;
    
    // Datos de otras tablas (JOINs)
    private String estatusUam;
    private String estatusBeca;
    private String tituloTesis;
    private String areaConcentracion;
    private String director;
    private String codirector;
    
    private String cvu;
    private String regFechaFinBeca;
    private String regFechaMax;
    private String regEstatusBeca;

    private int idAreaConcentracion; // ID del Área
    private String numEcoDirector;   // Matrícula/No. Económico del Director
    private String numEcoCodirector; // Matrícula/No. Económico del Codirector
    

    public Alumno() {}

    /**
    * Obtiene la matrícula del alumno
    *
    * @return matrícula del alumno
    */
    public String getMatricula() {
        return matricula;
    }

    /**
    * Establece la matrícula del alumno
    *
    * @param matricula del alumno
    */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
    * Obtiene el curp del alumno
    *
    * @return curp del alumno
    */
    public String getCurp() {
        return curp;
    }

    /**
    * Establece el curp del alumno
    *
    * @param curp del alumno
    */
    public void setCurp(String curp) {
        this.curp = curp;
    }

    /**
    * Obtiene el nombre completo del alumno
    *
    * @return nombre completo del alumno
    */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
    * Establece el nombre completo del alumno
    *
    * @param nombreCompleto del alumno
    */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
    * Obtiene el correo instirucional del alumno
    *
    * @return correo instirucional del alumno
    */
    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    /**
    * Establece el correo institucional del alumno
    *
    * @param correoInstitucional del alumno
    */
    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    /**
    * Obtiene el correo alternativo del alumno
    *
    * @return correo alternativo del alumno
    */
    public String getCorreoAlternativo() {
        return correoAlternativo;
    }

    /**
    * Establece el correo alternativo del alumno
    *
    * @param correoAlternativo del alumno
    */
    public void setCorreoAlternativo(String correoAlternativo) {
        this.correoAlternativo = correoAlternativo;
    }

    /**
    * Obtiene el telefono del alumno
    *
    * @return telefono del alumno
    */
    public String getTelefono() {
        return telefono;
    }

    /**
    * Establece el telefono del alumno
    *
    * @param telefono del alumno
    */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
    * Obtiene el trimestre de ingreso del alumno
    *
    * @return trimestre de ingreso del alumno
    */
    public String getTrimestreIngreso() {
        return trimestreIngreso;
    }

    /**
    * Establece el trimestre de ingreso del alumno
    *
    * @param trimestreIngreso del alumno
    */
    public void setTrimestreIngreso(String trimestreIngreso) {
        this.trimestreIngreso = trimestreIngreso;
    }

    /**
    * Obtiene la fecha de ingreso del alumno
    *
    * @return fecha de ingreso del alumno
    */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
    * Establece fecha de inicio del alumno
    *
    * @param fechaInicio del alumno
    */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
    * Obtiene el trimestre que pierde la calidad de alumno
    *
    * @return trimestre que pierde calidad de alumno
    */
    public String getTrimestrePierdeCalidad() {
        return trimestrePierdeCalidad;
    }

    /**
    * Establece el trimestre que pierde calidad de alumno
    *
    * @param trimestrePierdeCalidad del alumno
    */
    public void setTrimestrePierdeCalidad(String trimestrePierdeCalidad) {
        this.trimestrePierdeCalidad = trimestrePierdeCalidad;
    }

    /**
    * Obtiene el numero de acta de la tesis del alumno
    *
    * @return numero de acta de la tesis del alumno
    */
    public String getNumeroActa() {
        return numeroActa;
    }

    /**
    * Establece el numero de acta del alumno
    *
    * @param numeroActa del alumno
    */
    public void setNumeroActa(String numeroActa) {
        this.numeroActa = numeroActa;
    }

    /**
    * Obtiene la fecha de titulación del alumno
    *
    * @return fecha de titulación del alumno
    */
    public Date getFechaTitulacion() {
        return fechaTitulacion;
    }

    /**
    * Establece la fecha de titulación del alumno
    *
    * @param fechaTitulacion del alumno
    */
    public void setFechaTitulacion(Date fechaTitulacion) {
        this.fechaTitulacion = fechaTitulacion;
    }

    /**
    * Obtiene el estatus del alumno
    *
    * @return estatus del alumno
    */
    public String getEstatusUam() {
        return estatusUam;
    }

    /**
    * Establece el estatus en la UAM del alumno
    *
    * @param estatusUam del alumno
    */
    public void setEstatusUam(String estatusUam) {
        this.estatusUam = estatusUam;
    }

    /**
    * Obtiene el estatus de la beca del alumno
    *
    * @return estatus de la beca del alumno
    */
    public String getEstatusBeca() {
        return estatusBeca;
    }

    /**
    * Establece el estatus de la beca del alumno
    *
    * @param estatusBeca del alumno
    */
    public void setEstatusBeca(String estatusBeca) {
        this.estatusBeca = estatusBeca;
    }

    /**
    * Obtiene el titulo de la tesis del alumno
    *
    * @return titulo de la tesis del alumno
    */
    public String getTituloTesis() {
        return tituloTesis;
    }

    /**
    * Establece el titulo de la tesis del alumno
    *
    * @param tituloTesis del alumno
    */
    public void setTituloTesis(String tituloTesis) {
        this.tituloTesis = tituloTesis;
    }

    /**
    * Obtiene el área de concentracion de la tesis del alumno
    *
    * @return área de concentracion de la tesis del alumno
    */
    public String getAreaConcentracion() {
        return areaConcentracion;
    }

    /**
    * Establece el área de concentración de la tesis del alumno
    *
    * @param areaConcentracion de la tesis del alumno
    */
    public void setAreaConcentracion(String areaConcentracion) {
        this.areaConcentracion = areaConcentracion;
    }

    /**
    * Obtiene el director de la tesis del alumno
    *
    * @return director de la tesis del alumno
    */
    public String getDirector() {
        return director;
    }

    /**
    * Establece el director de la tesis del alumno
    *
    * @param director de la tesis del alumno
    */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
    * Obtiene el codirector de la tesis del alumno
    *
    * @return codirector de la tesis del alumno
    */
    public String getCodirector() {
        return codirector;
    }

    /**
    * Establece el codirector de la tesis del alumno
    *
    * @param codirector de la tesis del alumno
    */
    public void setCodirector(String codirector) {
        this.codirector = codirector;
    }

    /**
    * Obtiene el cvu del alumno
    *
    * @return cvu del alumno
    */
    public String getCvu() {
        return cvu;
    }

    /**
    * Establece el cvu del alumno
    *
    * @param cvu del alumno
    */
    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    /**
    * Obtiene la fecha del fin de la beca del alumno
    *
    * @return fecha del fin de la beca del alumno
    */
    public String getRegFechaFinBeca() {
        return regFechaFinBeca;
    }

    /**
    * Establece la fecha del fin de la beca del alumno
    *
    * @param regFechaFinBeca de la beca del alumno
    */
    public void setRegFechaFinBeca(String regFechaFinBeca) {
        this.regFechaFinBeca = regFechaFinBeca;
    }

    /**
    * Obtiene la fecha máxima de la beca del alumno
    *
    * @return fecha maxcima de la beca del alumno
    */
    public String getRegFechaMax() {
        return regFechaMax;
    }

    /**
    * Establece la fecha maxima de la beca del alumno
    *
    * @param regFechaMax de la beca del alumno
    */
    public void setRegFechaMax(String regFechaMax) {
        this.regFechaMax = regFechaMax;
    }

    /**
    * Obtiene el estatus de la beca del alumno
    *
    * @return estatus de la beca del alumno
    */
    public String getRegEstatusBeca() {
        return regEstatusBeca;
    }

    /**
    * Establece el estatus de la beca del alumno
    *
    * @param regEstatusBeca estatus de la beca del alumno
    */
    public void setRegEstatusBeca(String regEstatusBeca) {
        this.regEstatusBeca = regEstatusBeca;
    }

    /**
    * Obtiene el id del área de concentración de la tesis del alumno
    *
    * @return id del area de concentracion de la tesis del alumno
    */
    public int getIdAreaConcentracion() {
        return idAreaConcentracion;
    }

    /**
    * Establece el ID del área de concentracion de la tesis del alumno
    *
    * @param idAreaConcentracion de la tesis del alumno
    */
    public void setIdAreaConcentracion(int idAreaConcentracion) {
        this.idAreaConcentracion = idAreaConcentracion;
    }

    /**
    * Obtiene el numero economico del director de la tesis del alumno
    *
    * @return numero economico del director de la tesis del alumno
    */
    public String getNumEcoDirector() {
        return numEcoDirector;
    }

    /**
    * Establece el número economico del Director
    *
    * @param numEcoDirector numero economico del Director
    */
    public void setNumEcoDirector(String numEcoDirector) {
        this.numEcoDirector = numEcoDirector;
    }

    /**
    * Obtiene el numero economico del codirector de la tesis del alumno
    *
    * @return numero economico del codirector de la tesis del alumno
    */
    public String getNumEcoCodirector() {
        return numEcoCodirector;
    }

    /**
    * Establece el número economico del Codirector
    *
    * @param numEcoCodirector numero economico del Codirector
    */
    public void setNumEcoCodirector(String numEcoCodirector) {
        this.numEcoCodirector = numEcoCodirector;
    }
    
    
}
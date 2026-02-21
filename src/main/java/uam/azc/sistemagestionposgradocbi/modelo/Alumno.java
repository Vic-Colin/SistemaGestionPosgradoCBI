package uam.azc.sistemagestionposgradocbi.modelo;

import java.io.Serializable;
import java.sql.Date;

public class Alumno implements Serializable {
    // Datos nativos del alumno
    private String matricula;
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

    public Alumno() {}

    // Getter y Setters

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getCorreoAlternativo() {
        return correoAlternativo;
    }

    public void setCorreoAlternativo(String correoAlternativo) {
        this.correoAlternativo = correoAlternativo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTrimestreIngreso() {
        return trimestreIngreso;
    }

    public void setTrimestreIngreso(String trimestreIngreso) {
        this.trimestreIngreso = trimestreIngreso;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getTrimestrePierdeCalidad() {
        return trimestrePierdeCalidad;
    }

    public void setTrimestrePierdeCalidad(String trimestrePierdeCalidad) {
        this.trimestrePierdeCalidad = trimestrePierdeCalidad;
    }

    public String getNumeroActa() {
        return numeroActa;
    }

    public void setNumeroActa(String numeroActa) {
        this.numeroActa = numeroActa;
    }

    public Date getFechaTitulacion() {
        return fechaTitulacion;
    }

    public void setFechaTitulacion(Date fechaTitulacion) {
        this.fechaTitulacion = fechaTitulacion;
    }

    public String getEstatusUam() {
        return estatusUam;
    }

    public void setEstatusUam(String estatusUam) {
        this.estatusUam = estatusUam;
    }

    public String getEstatusBeca() {
        return estatusBeca;
    }

    public void setEstatusBeca(String estatusBeca) {
        this.estatusBeca = estatusBeca;
    }

    public String getTituloTesis() {
        return tituloTesis;
    }

    public void setTituloTesis(String tituloTesis) {
        this.tituloTesis = tituloTesis;
    }

    public String getAreaConcentracion() {
        return areaConcentracion;
    }

    public void setAreaConcentracion(String areaConcentracion) {
        this.areaConcentracion = areaConcentracion;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCodirector() {
        return codirector;
    }

    public void setCodirector(String codirector) {
        this.codirector = codirector;
    }
    
}
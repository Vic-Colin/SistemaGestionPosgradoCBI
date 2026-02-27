package uam.azc.sistemagestionposgradocbi.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase que representa la información académica y administrativa
 * de un profesor perteneciente al Núcleo Académico del Posgrado.
 * 
 * Contiene datos institucionales utilizados para la gestión de
 * profesores, asignación de alumnos, proyectos y consultas
 * dentro del Sistema de Gestión de Posgrado CBI.
 * 
 * Implementa Serializable para permitir la transferencia del objeto
 * entre capas del sistema o su persistencia temporal.
 * 
 * @author Vania Alejandra Contreras Torres
 */
public class Profesor implements Serializable {

    private String numeroEconomico;
    private String cvu;
    private String nombreCompleto;
    private String nivelSni;
    private String tipoDedicacion;
    private String departamento;
    private Date fechaIngresoNucleo; 
    private String correoInstitucional;

    /**
     * Constructor vacío de la clase Profesor.
     * 
     * Requerido para el mapeo de datos provenientes
     * de la base de datos y funcionamiento de DAOs.
     */
    public Profesor() {}

    /**
     * Obtiene el número económico del profesor.
     * 
     * @return número económico institucional
     */
    public String getNumeroEconomico() {
        return numeroEconomico;
    }

    /**
     * Establece el número económico del profesor.
     * 
     * @param numeroEconomico identificador institucional del profesor
     */
    public void setNumeroEconomico(String numeroEconomico) {
        this.numeroEconomico = numeroEconomico;
    }

    /**
     * Obtiene el CVU del profesor.
     * 
     * @return clave CVU CONAHCYT
     */
    public String getCvu() {
        return cvu;
    }

    /**
     * Define el CVU del profesor.
     * 
     * @param cvu clave única CVU
     */
    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    /**
     * Obtiene el nombre completo del profesor.
     * 
     * @return nombre completo
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Establece el nombre completo del profesor.
     * 
     * @param nombreCompleto nombre del profesor
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Obtiene el nivel SNI del profesor.
     * 
     * @return nivel del Sistema Nacional de Investigadores
     */
    public String getNivelSni() {
        return nivelSni;
    }

    /**
     * Define el nivel SNI del profesor.
     * 
     * @param nivelSni nivel SNI asignado
     */
    public void setNivelSni(String nivelSni) {
        this.nivelSni = nivelSni;
    }

    /**
     * Obtiene el tipo de dedicación del profesor.
     * 
     * @return tipo de dedicación académica
     */
    public String getTipoDedicacion() {
        return tipoDedicacion;
    }

    /**
     * Establece el tipo de dedicación del profesor.
     * 
     * @param tipoDedicacion dedicación académica
     */
    public void setTipoDedicacion(String tipoDedicacion) {
        this.tipoDedicacion = tipoDedicacion;
    }

    /**
     * Obtiene el departamento académico del profesor.
     * 
     * @return departamento institucional
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Define el departamento académico del profesor.
     * 
     * @param departamento departamento al que pertenece
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * Obtiene la fecha de ingreso al Núcleo Académico.
     * 
     * @return fecha de ingreso al núcleo
     */
    public Date getFechaIngresoNucleo() {
        return fechaIngresoNucleo;
    }

    /**
     * Establece la fecha de ingreso al Núcleo Académico.
     * 
     * @param fechaIngresoNucleo fecha de incorporación
     */
    public void setFechaIngresoNucleo(Date fechaIngresoNucleo) {
        this.fechaIngresoNucleo = fechaIngresoNucleo;
    }

    /**
     * Obtiene el correo institucional del profesor.
     * 
     * @return correo electrónico institucional
     */
    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    /**
     * Define el correo institucional del profesor.
     * 
     * @param correoInstitucional correo oficial UAM
     */
    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }
}


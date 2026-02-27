package uam.azc.sistemagestionposgradocbi.modelo;

import java.io.Serializable;

/**
 * Representa la información académica del proyecto de tesis
 * asociado a un alumno del programa de posgrado.
 * 
 * Contiene datos relacionados con el estado del proyecto,
 * dirección académica y área de concentración dentro
 * del Sistema de Gestión de Posgrado CBI.
 * 
 * Implementa Serializable para permitir la transferencia
 * del objeto entre capas del sistema o su persistencia.
 * 
 * @author Vania Alejandra Contreras Torres
 */
public class Proyecto implements Serializable {

    private String matricula;
    private String nombreAlumno;
    private String estatusUam;
    private String tituloTesis;
    private String director;
    private String codirector;
    private String areaConcentracion;

    /**
     * Constructor vacío de la clase Proyecto.
     * 
     * Necesario para el mapeo de datos desde la base
     * de datos mediante la capa DAO.
     */
    public Proyecto() {
    }

    /**
     * Obtiene la matrícula del alumno.
     * 
     * @return matrícula del alumno
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Establece la matrícula del alumno.
     * 
     * @param matricula identificador único del alumno
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Obtiene el nombre del alumno asociado al proyecto.
     * 
     * @return nombre completo del alumno
     */
    public String getNombreAlumno() {
        return nombreAlumno;
    }

    /**
     * Define el nombre del alumno.
     * 
     * @param nombreAlumno nombre completo del alumno
     */
    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    /**
     * Obtiene el estatus académico del alumno en la UAM.
     * 
     * @return estatus académico institucional
     */
    public String getEstatusUam() {
        return estatusUam;
    }

    /**
     * Establece el estatus académico del alumno.
     * 
     * @param estatusUam estado académico dentro del posgrado
     */
    public void setEstatusUam(String estatusUam) {
        this.estatusUam = estatusUam;
    }

    /**
     * Obtiene el título del proyecto de tesis.
     * 
     * @return título de la tesis
     */
    public String getTituloTesis() {
        return tituloTesis;
    }

    /**
     * Define el título del proyecto de tesis.
     * 
     * @param tituloTesis nombre oficial de la tesis
     */
    public void setTituloTesis(String tituloTesis) {
        this.tituloTesis = tituloTesis;
    }

    /**
     * Obtiene el nombre del director de tesis.
     * 
     * @return director del proyecto
     */
    public String getDirector() {
        return director;
    }

    /**
     * Establece el director de tesis.
     * 
     * @param director nombre del profesor director
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Obtiene el nombre del codirector de tesis.
     * 
     * @return codirector del proyecto
     */
    public String getCodirector() {
        return codirector;
    }

    /**
     * Define el codirector de tesis.
     * 
     * @param codirector nombre del profesor codirector
     */
    public void setCodirector(String codirector) {
        this.codirector = codirector;
    }

    /**
     * Obtiene el área de concentración del proyecto.
     * 
     * @return área académica de concentración
     */
    public String getAreaConcentracion() {
        return areaConcentracion;
    }

    /**
     * Establece el área de concentración del proyecto.
     * 
     * @param areaConcentracion área académica asignada
     */
    public void setAreaConcentracion(String areaConcentracion) {
        this.areaConcentracion = areaConcentracion;
    }
}

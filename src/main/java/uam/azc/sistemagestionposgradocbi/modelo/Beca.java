package uam.azc.sistemagestionposgradocbi.modelo;

import java.io.Serializable;
import java.sql.Date;

/**
 * Clase que representa la información de la beca asociada a un alumno
 * dentro del Sistema de Gestión de Posgrado CBI.
 * 
 * Contiene datos relacionados con el apoyo CONAHCYT del alumno,
 * incluyendo fechas importantes y estatus de la beca.
 * 
 * Algunos atributos son obtenidos mediante consultas JOIN con
 * otras tablas del sistema como Alumno.
 * 
 * Implementa Serializable para permitir el transporte del objeto
 * entre capas del sistema o su almacenamiento temporal.
 * 
 * @author Vania Alejandra Contreras Torres
 */
public class Beca implements Serializable {

    private String matricula;
    private String nombreAlumno; // Se obtiene con JOIN
    private String cvu;
    private String trimestreIngreso; // Se obtiene con JOIN
    private Date fechaFinBeca;
    private Date fechaMaxConahcyt;
    private String estatusBeca;
    private Date fechaTitulacion; // Se obtiene con JOIN

    /**
     * Constructor vacío de la clase Beca.
     * 
     * Necesario para frameworks, DAOs y mapeo de resultados
     * provenientes de la base de datos.
     */
    public Beca() {
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
     * @param matricula matrícula única del alumno
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Obtiene el nombre completo del alumno.
     * 
     * @return nombre del alumno
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
     * Obtiene el CVU del alumno registrado en CONAHCYT.
     * 
     * @return CVU del alumno
     */
    public String getCvu() {
        return cvu;
    }

    /**
     * Establece el CVU del alumno.
     * 
     * @param cvu clave CVU del alumno
     */
    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    /**
     * Obtiene el trimestre de ingreso del alumno.
     * 
     * @return trimestre de ingreso
     */
    public String getTrimestreIngreso() {
        return trimestreIngreso;
    }

    /**
     * Define el trimestre de ingreso del alumno.
     * 
     * @param trimestreIngreso trimestre de ingreso al posgrado
     */
    public void setTrimestreIngreso(String trimestreIngreso) {
        this.trimestreIngreso = trimestreIngreso;
    }

    /**
     * Obtiene la fecha de finalización de la beca.
     * 
     * @return fecha fin de beca
     */
    public Date getFechaFinBeca() {
        return fechaFinBeca;
    }

    /**
     * Establece la fecha de finalización de la beca.
     * 
     * @param fechaFinBeca fecha de término de la beca
     */
    public void setFechaFinBeca(Date fechaFinBeca) {
        this.fechaFinBeca = fechaFinBeca;
    }

    /**
     * Obtiene la fecha máxima autorizada por CONAHCYT.
     * 
     * @return fecha máxima CONAHCYT
     */
    public Date getFechaMaxConahcyt() {
        return fechaMaxConahcyt;
    }

    /**
     * Establece la fecha máxima permitida por CONAHCYT.
     * 
     * @param fechaMaxConahcyt fecha límite autorizada
     */
    public void setFechaMaxConahcyt(Date fechaMaxConahcyt) {
        this.fechaMaxConahcyt = fechaMaxConahcyt;
    }

    /**
     * Obtiene el estatus actual de la beca.
     * 
     * @return estatus de la beca
     */
    public String getEstatusBeca() {
        return estatusBeca;
    }

    /**
     * Define el estatus actual de la beca.
     * 
     * @param estatusBeca estado de la beca
     */
    public void setEstatusBeca(String estatusBeca) {
        this.estatusBeca = estatusBeca;
    }

    /**
     * Obtiene la fecha de titulación del alumno.
     * 
     * @return fecha de titulación
     */
    public Date getFechaTitulacion() {
        return fechaTitulacion;
    }

    /**
     * Establece la fecha de titulación del alumno.
     * 
     * @param fechaTitulacion fecha en que el alumno obtuvo el grado
     */
    public void setFechaTitulacion(Date fechaTitulacion) {
        this.fechaTitulacion = fechaTitulacion;
    }
}
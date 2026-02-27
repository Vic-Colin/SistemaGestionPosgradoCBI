package uam.azc.sistemagestionposgradocbi.modelo;

import java.io.Serializable;

/**
 * Representa un área de concentración académica
 * dentro del programa de posgrado.
 *
 * Contiene la información básica utilizada para
 * clasificar proyectos y líneas de investigación.
 *
 * @author Vania Alejandra Contreras Torres
 */
public class AreaConcentracion {

    private int idArea;
    private String nombre;

    /**
     * Constructor por defecto.
     */
    public AreaConcentracion() {}

    /**
     * Obtiene el identificador del área.
     * @return id del área
     */
    public int getIdArea() { return idArea; }

    /**
     * Establece el identificador del área.
     * @param idArea identificador único
     */
    public void setIdArea(int idArea) { this.idArea = idArea; }

    /**
     * Obtiene el nombre del área.
     * @return nombre del área
     */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre del área.
     * @param nombre nombre del área académica
     */
    public void setNombre(String nombre) { this.nombre = nombre; }
}
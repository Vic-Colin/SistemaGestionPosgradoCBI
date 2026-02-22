package uam.azc.sistemagestionposgradocbi.modelo;

import java.io.Serializable;

public class AreaConcentracion implements Serializable {
    private int idArea;
    private String nombre;

    public AreaConcentracion() {}

    public int getIdArea() { return idArea; }
    public void setIdArea(int idArea) { this.idArea = idArea; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}

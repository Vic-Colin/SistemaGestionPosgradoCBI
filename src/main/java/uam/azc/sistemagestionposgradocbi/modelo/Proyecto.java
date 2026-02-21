package uam.azc.sistemagestionposgradocbi.modelo;

import java.io.Serializable;

public class Proyecto implements Serializable {

    private String matricula;
    private String nombreAlumno;
    private String tituloTesis;
    private String director;
    private String codirector;
    private String areaConcentracion;

    public Proyecto() {
    }

    // =========================
    // Getters y Setters
    // =========================
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNombreAlumno() { return nombreAlumno; }
    public void setNombreAlumno(String nombreAlumno) { this.nombreAlumno = nombreAlumno; }

    public String getTituloTesis() { return tituloTesis; }
    public void setTituloTesis(String tituloTesis) { this.tituloTesis = tituloTesis; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getCodirector() { return codirector; }
    public void setCodirector(String codirector) { this.codirector = codirector; }

    public String getAreaConcentracion() { return areaConcentracion; }
    public void setAreaConcentracion(String areaConcentracion) { this.areaConcentracion = areaConcentracion; }
}

package uam.azc.sistemagestionposgradocbi.modelo;

import java.io.Serializable;
import java.util.Date;

public class Profesor implements Serializable {

    private String numeroEconomico;
    private String cvu;
    private String nombreCompleto;
    private String nivelSni;
    private String tipoDedicacion;
    private String departamento;
    private Date fechaIngresoNucleo; 
    private String correoInstitucional;

    public Profesor() {}

    public String getNumeroEconomico() {
        return numeroEconomico;
    }

    public void setNumeroEconomico(String numeroEconomico) {
        this.numeroEconomico = numeroEconomico;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNivelSni() {
        return nivelSni;
    }

    public void setNivelSni(String nivelSni) {
        this.nivelSni = nivelSni;
    }

    public String getTipoDedicacion() {
        return tipoDedicacion;
    }

    public void setTipoDedicacion(String tipoDedicacion) {
        this.tipoDedicacion = tipoDedicacion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    public Date getFechaIngresoNucleo() {
        return fechaIngresoNucleo;
    }

    public void setFechaIngresoNucleo(Date fechaIngresoNucleo) {
        this.fechaIngresoNucleo = fechaIngresoNucleo;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }
}


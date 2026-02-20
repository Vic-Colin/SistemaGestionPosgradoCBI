package uam.azc.sistemagestionposgradocbi.modelo;

import java.io.Serializable;
import java.sql.Date;

public class Beca implements Serializable {

    private String matricula;
    private String nombreAlumno; // Se obtiene con JOIN
    private String cvu;
    private String trimestreIngreso; // Se obtiene con JOIN
    private Date fechaFinBeca;
    private Date fechaMaxConahcyt;
    private String estatusBeca;
    private Date fechaTitulacion; // Se obtiene con JOIN

    public Beca() {
    }

    // =========================
    // Getters y Setters
    // =========================
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getTrimestreIngreso() {
        return trimestreIngreso;
    }

    public void setTrimestreIngreso(String trimestreIngreso) {
        this.trimestreIngreso = trimestreIngreso;
    }

    public Date getFechaFinBeca() {
        return fechaFinBeca;
    }

    public void setFechaFinBeca(Date fechaFinBeca) {
        this.fechaFinBeca = fechaFinBeca;
    }

    public Date getFechaMaxConahcyt() {
        return fechaMaxConahcyt;
    }

    public void setFechaMaxConahcyt(Date fechaMaxConahcyt) {
        this.fechaMaxConahcyt = fechaMaxConahcyt;
    }

    public String getEstatusBeca() {
        return estatusBeca;
    }

    public void setEstatusBeca(String estatusBeca) {
        this.estatusBeca = estatusBeca;
    }

    public Date getFechaTitulacion() {
        return fechaTitulacion;
    }

    public void setFechaTitulacion(Date fechaTitulacion) {
        this.fechaTitulacion = fechaTitulacion;
    }
}
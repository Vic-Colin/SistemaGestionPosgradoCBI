
package uam.azc.sistemagestionposgradocbi.modelo;

public class Beca {
    private int idBeca;
    private String matricula;
    private String nombreAlumno; // Para mostrarlo en la tabla (vía JOIN)
    private String fechaInicio;
    private String fechaFinVigencia;
    private String fechaMaxConahcyt;
    private String estatusBeca;

    // Getters y Setters...

    public int getIdBeca() {
        return idBeca;
    }

    public void setIdBeca(int idBeca) {
        this.idBeca = idBeca;
    }

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

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(String fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    public String getFechaMaxConahcyt() {
        return fechaMaxConahcyt;
    }

    public void setFechaMaxConahcyt(String fechaMaxConahcyt) {
        this.fechaMaxConahcyt = fechaMaxConahcyt;
    }

    public String getEstatusBeca() {
        return estatusBeca;
    }

    public void setEstatusBeca(String estatusBeca) {
        this.estatusBeca = estatusBeca;
    }
    
}

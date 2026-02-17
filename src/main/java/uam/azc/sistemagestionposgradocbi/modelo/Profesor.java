/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uam.azc.sistemagestionposgradocbi.modelo;

import java.io.Serializable;

public class Profesor implements Serializable {

    private String noEconomico;
    private String nombreCompleto;
    private String cvu;
    private String programa;
    private String correoInstitucional;
    private String categoria;

    // 🔹 Constructor vacío (OBLIGATORIO para Gson y JDBC)
    public Profesor() {
    }

    // 🔹 Constructor completo
    public Profesor(String noEconomico, String nombreCompleto, String cvu,
                    String programa, String correoInstitucional, String categoria) {
        this.noEconomico = noEconomico;
        this.nombreCompleto = nombreCompleto;
        this.cvu = cvu;
        this.programa = programa;
        this.correoInstitucional = correoInstitucional;
        this.categoria = categoria;
    }

    // 🔹 Getters y Setters

    public String getNoEconomico() {
        return noEconomico;
    }

    public void setNoEconomico(String noEconomico) {
        this.noEconomico = noEconomico;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}

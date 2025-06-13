package modelo;

import java.sql.Date;

public class CertificadoDto {
    private String codcertif;
    private Date fechaemi;
    private Date fechainicio;
    private Date fechafin;
    private int codplan;
    private String nomplan;

    public String getCodcertif() {
        return codcertif;
    }

    public void setCodcertif(String codcertif) {
        this.codcertif = codcertif;
    }

    public Date getFechaemi() {
        return fechaemi;
    }

    public void setFechaemi(Date fechaemi) {
        this.fechaemi = fechaemi;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public int getCodplan() {
        return codplan;
    }

    public void setCodplan(int codplan) {
        this.codplan = codplan;
    }

    public String getNomplan() {
        return nomplan;
    }

    public void setNomplan(String nomplan) {
        this.nomplan = nomplan;
    }
}

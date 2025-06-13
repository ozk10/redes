package modelo;

import java.sql.Date;

public class AseguradoDto {
    private int codaseg;
    private String nomaseg;
    private String apeaseg;
    private String dniaseg;

    private String codcertif;
    private Date fechaemi;
    private Date fechainicio;
    private Date fechafin;
    private String nomplan;
    private String status; // carente, vigente o vencido
    
    public AseguradoDto() {
    }

    public AseguradoDto(int codaseg, String nomaseg, String apeaseg, String dniaseg) {
        this.codaseg = codaseg;
        this.nomaseg = nomaseg;
        this.apeaseg = apeaseg;
        this.dniaseg = dniaseg;
    }

    public int getCodaseg() {
        return codaseg;
    }

    public void setCodaseg(int codaseg) {
        this.codaseg = codaseg;
    }

    public String getNomaseg() {
        return nomaseg;
    }

    public void setNomaseg(String nomaseg) {
        this.nomaseg = nomaseg;
    }

    public String getApeaseg() {
        return apeaseg;
    }

    public void setApeaseg(String apeaseg) {
        this.apeaseg = apeaseg;
    }

    public String getDniaseg() {
        return dniaseg;
    }

    public void setDniaseg(String dniaseg) {
        this.dniaseg = dniaseg;
    }
    
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

    public String getNomplan() {
        return nomplan;
    }

    public void setNomplan(String nomplan) {
        this.nomplan = nomplan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}

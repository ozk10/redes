package modelo;

import java.sql.Date;

public class PacienteDto {
    private int codpac;
    private Date fechaalta;
    private int codaseg;
    private String telefpac;
    private String correopac;
    private Date fechanac;
    
    private String nomaseg;
    private String apeaseg;

    // Constructor completo
    public PacienteDto(int codpac, Date fechaalta, int codaseg, String telefpac, String correopac, Date fechanac) {
        this.codpac = codpac;
        this.fechaalta = fechaalta;
        this.codaseg = codaseg;
        this.telefpac = telefpac;
        this.correopac = correopac;
        this.fechanac=fechanac;
    }

    // Constructor para actualización
    public PacienteDto(int codpac, String telefpac, String correopac, Date fechanac) {
        this.codpac = codpac;
        this.telefpac = telefpac;
        this.correopac = correopac;
        this.fechanac=fechanac;
    }
    
    // Constructor vacío
    public PacienteDto() {
    }

    // Getters y Setters
    public int getCodpac() { return codpac; }
    public void setCodpac(int codpac) { this.codpac = codpac; }

    public Date getFechaalta() { return fechaalta; }
    public void setFechaalta(Date fechaalta) { this.fechaalta = fechaalta; }

    public int getCodaseg() { return codaseg; }
    public void setCodaseg(int codaseg) { this.codaseg = codaseg; }

    public String getTelefpac() { return telefpac; }
    public void setTelefpac(String telefpac) { this.telefpac = telefpac; }

    public String getCorreopac() { return correopac; }
    public void setCorreopac(String correopac) { this.correopac = correopac; }
    
    public Date getFechanac() { return fechanac; }

    public void setFechanac(Date fechanac) { this.fechanac = fechanac; }

    public String getNomaseg() { return nomaseg; }
    public void setNomaseg(String nomaseg) { this.nomaseg = nomaseg; }

    
    public String getApeaseg() { return apeaseg; }
    public void setApeaseg(String apeaseg) { this.apeaseg = apeaseg; }
    
}


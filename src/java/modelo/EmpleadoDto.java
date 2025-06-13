package modelo;

public class EmpleadoDto {
    private int codemp;
    private String nomemp;
    private String apeemp;
    private String dniemp;
    private String nomcargo;
    private String fotoemp; // NUEVO atributo

    // Constructor con todos los campos
    public EmpleadoDto(int codemp, String nomemp, String apeemp, String dniemp, String nomcargo, String fotoemp) {
        this.codemp = codemp;
        this.nomemp = nomemp;
        this.apeemp = apeemp;
        this.dniemp = dniemp;
        this.nomcargo = nomcargo;
        this.fotoemp = fotoemp;
    }

    // Getters y Setters
    public int getCodemp() {
        return codemp;
    }

    public void setCodemp(int codemp) {
        this.codemp = codemp;
    }

    public String getNomemp() {
        return nomemp;
    }

    public void setNomemp(String nomemp) {
        this.nomemp = nomemp;
    }

    public String getApeemp() {
        return apeemp;
    }

    public void setApeemp(String apeemp) {
        this.apeemp = apeemp;
    }

    public String getDniemp() {
        return dniemp;
    }

    public void setDniemp(String dniemp) {
        this.dniemp = dniemp;
    }

    public String getNomcargo() {
        return nomcargo;
    }

    public void setNomcargo(String nomcargo) {
        this.nomcargo = nomcargo;
    }

    public String getFotoemp() {
        return fotoemp;
    }

    public void setFotoemp(String fotoemp) {
        this.fotoemp = fotoemp;
    }
}

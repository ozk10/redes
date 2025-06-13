package modelo;

public class MedicoDto {
    private String cmp;
    private String rne;

    public MedicoDto() {
    }

    public MedicoDto(String cmp, String rne) {
        this.cmp = cmp;
        this.rne = rne;
    }

    public String getCmp() {
        return cmp;
    }

    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

    public String getRne() {
        return rne;
    }

    public void setRne(String rne) {
        this.rne = rne;
    }
}

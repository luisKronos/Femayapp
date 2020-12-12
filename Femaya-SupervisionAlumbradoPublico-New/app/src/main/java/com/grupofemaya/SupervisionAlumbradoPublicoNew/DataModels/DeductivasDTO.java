package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels;

public class DeductivasDTO {

    private String idPenalty;
    private String punto;
    private String infraccion;
    private String monto;
    private String uma;

    public DeductivasDTO(String idDeductiva, String deductiva) {
        this.idPenalty = idDeductiva;
        this.punto = deductiva;
    }

    public String getIdPenalty() {
        return idPenalty;
    }

    public void setIdPenalty(String idPenalty) {
        this.idPenalty = idPenalty;
    }

    public String getPunto() {
        return punto;
    }

    public void setPunto(String punto) {
        this.punto = punto;
    }

    public String getInfraccion() {
        return infraccion;
    }

    public void setInfraccion(String infraccion) {
        this.infraccion = infraccion;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getUma() {
        return uma;
    }

    public void setUma(String uma) {
        this.uma = uma;
    }
}

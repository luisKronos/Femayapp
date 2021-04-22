package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests;

public class RQImageMaterialUsed {
    private int idReportAlumbrado;
    private String fotoMaterial = "";
    private String obsMaterial;

    public int getIdReportAlumbrado() {
        return idReportAlumbrado;
    }

    public void setIdReportAlumbrado(int idReportAlumbrado) {
        this.idReportAlumbrado = idReportAlumbrado;
    }

    public String getFotoMaterial() {
        return fotoMaterial;
    }

    public void setFotoMaterial(String fotoMaterial) {
        this.fotoMaterial = fotoMaterial;
    }

    public String getObsMaterial() {
        return obsMaterial;
    }

    public void setObsMaterial(String obsMaterial) {
        this.obsMaterial = obsMaterial;
    }
}

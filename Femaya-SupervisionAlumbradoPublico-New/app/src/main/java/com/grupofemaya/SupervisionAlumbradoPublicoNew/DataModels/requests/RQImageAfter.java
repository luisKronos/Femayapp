package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests;

public class RQImageAfter {
    private int idReportAlumbrado;
    private String fotoDespues = "";

    public int getIdReportAlumbrado() {
        return idReportAlumbrado;
    }

    public void setIdReportAlumbrado(int idReportAlumbrado) {
        this.idReportAlumbrado = idReportAlumbrado;
    }

    public String getFotoDespues() {
        return fotoDespues;
    }

    public void setFotoDespues(String fotoDespues) {
        this.fotoDespues = fotoDespues;
    }
}

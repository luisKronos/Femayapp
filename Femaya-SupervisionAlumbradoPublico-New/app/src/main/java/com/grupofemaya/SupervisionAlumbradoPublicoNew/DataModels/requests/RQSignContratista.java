package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests;

public class RQSignContratista {
    private int idReportAlumbrado;
    private String firmaContratista = "";

    public int getIdReportAlumbrado() {
        return idReportAlumbrado;
    }

    public void setIdReportAlumbrado(int idReportAlumbrado) {
        this.idReportAlumbrado = idReportAlumbrado;
    }

    public String getFirmaContratista() {
        return firmaContratista;
    }

    public void setFirmaContratista(String firmaContratista) {
        this.firmaContratista = firmaContratista;
    }
}

package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests;

public class RQSignSupervisor {
    private int idReportAlumbrado;
    private String firmaSupervision = "";

    public int getIdReportAlumbrado() {
        return idReportAlumbrado;
    }

    public void setIdReportAlumbrado(int idReportAlumbrado) {
        this.idReportAlumbrado = idReportAlumbrado;
    }

    public String getFirmaSupervision() {
        return firmaSupervision;
    }

    public void setFirmaSupervision(String firmaSupervision) {
        this.firmaSupervision = firmaSupervision;
    }
}

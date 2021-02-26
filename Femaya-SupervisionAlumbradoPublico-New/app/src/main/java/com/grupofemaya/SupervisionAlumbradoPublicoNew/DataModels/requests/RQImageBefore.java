package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests;

public class RQImageBefore {
    private int idReportAlumbrado;
    private String fotoAntes = "";

    public int getIdReportAlumbrado() {
        return idReportAlumbrado;
    }

    public void setIdReportAlumbrado(int idReportAlumbrado) {
        this.idReportAlumbrado = idReportAlumbrado;
    }

    public String getFotoAntes() {
        return fotoAntes;
    }

    public void setFotoAntes(String fotoAntes) {
        this.fotoAntes = fotoAntes;
    }
}

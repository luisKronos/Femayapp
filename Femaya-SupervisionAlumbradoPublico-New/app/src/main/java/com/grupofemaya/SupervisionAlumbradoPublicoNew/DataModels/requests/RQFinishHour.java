package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSReportInitOne;

import java.util.List;

public class RQFinishHour {
    private int idReportAlumbrado;
    private String hr_salida;
    private List<RSReportInitOne> listReportAlumbrado;

    public int getIdReportAlumbrado() {
        return idReportAlumbrado;
    }

    public void setIdReportAlumbrado(int idReportAlumbrado) {
        this.idReportAlumbrado = idReportAlumbrado;
    }

    public String getHr_salida() {
        return hr_salida;
    }

    public void setHr_salida(String hr_salida) {
        this.hr_salida = hr_salida;
    }

    public List<RSReportInitOne> getListReportAlumbrado() {
        return listReportAlumbrado;
    }

    public void setListReportAlumbrado(List<RSReportInitOne> listReportAlumbrado) {
        this.listReportAlumbrado = listReportAlumbrado;
    }
}

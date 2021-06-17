package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSIdCuadrillas;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSReportInitOne;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSSubirCuadrilla;

import java.util.List;

public class RQFinishHour {
    private int idReportAlumbrado;
    private String hr_salida;
    private int idUser;
    private List<RSReportInitOne> listReportAlumbrado;
    private List<RSSubirCuadrilla> listIdCuadrillas;

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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public List<RSSubirCuadrilla> getListCuadrillasIds() {
        return listIdCuadrillas;
    }

    public void setListCuadrillasIds(List<RSSubirCuadrilla> listCuadrillasIds) {
        this.listIdCuadrillas = listCuadrillasIds;
    }
}

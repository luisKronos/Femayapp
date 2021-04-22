package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DamageDTO;

import java.util.List;

public class RQReportInitTwo {
    private int idReportAlumbrado;
    private String folio;
    private String lon;
    private String lat;
    private List<DamageDTO> listDamages;

    public int getIdReportAlumbrado() {
        return idReportAlumbrado;
    }

    public void setIdReportAlumbrado(int idReportAlumbrado) {
        this.idReportAlumbrado = idReportAlumbrado;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public List<DamageDTO> getListDamages() {
        return listDamages;
    }

    public void setListDamages(List<DamageDTO> listDamages) {
        this.listDamages = listDamages;
    }
}

package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels;

import java.util.ArrayList;
import java.util.List;

public class ReportAlumbDirDTO {

    private String idReportAlumbrado;
    private String idVialidad;
    private String idDamage;
    private String idUser;

    private String lat;
    private String lon;
    private String foto;
    private String notas;


    public String getIdReportAlumbrado() {
        return idReportAlumbrado;
    }

    public void setIdReportAlumbrado(String idReportAlumbrado) {
        this.idReportAlumbrado = idReportAlumbrado;
    }

    public String getIdVialidad() {
        return idVialidad;
    }

    public void setIdVialidad(String idVialidad) {
        this.idVialidad = idVialidad;
    }

    public String getIdDamage() {
        return idDamage;
    }

    public void setIdDamage(String idDamage) {
        this.idDamage = idDamage;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}

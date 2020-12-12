package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests;

public class RQInitCheck {

    private String idUser;
    private String idRoad;
    private String turno;
    private String latitude;
    private String longitude;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdRoad() {
        return idRoad;
    }

    public void setIdRoad(String idRoad) {
        this.idRoad = idRoad;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}

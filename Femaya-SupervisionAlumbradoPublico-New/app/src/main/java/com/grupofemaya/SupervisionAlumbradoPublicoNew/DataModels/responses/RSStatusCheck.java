package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses;

public class RSStatusCheck {

    private String idCheck;
    private String idRoad;
    private String idUser;
    private String turno;
    private String latitude;
    private String longitude;
    private String latitudeFinish;
    private String longitudeFinish;
    private String status;
    private int isPersonal;
    private int isCars;
    private int isTools;
    private int isPickups;
    private int isDeductives;
    private int isAdvance;
    private int isActivities;
    private String createdTime;
    private String finishTime;
    private String road;

    public String getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(String idCheck) {
        this.idCheck = idCheck;
    }

    public String getIdRoad() {
        return idRoad;
    }

    public void setIdRoad(String idRoad) {
        this.idRoad = idRoad;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public String getLatitudeFinish() {
        return latitudeFinish;
    }

    public void setLatitudeFinish(String latitudeFinish) {
        this.latitudeFinish = latitudeFinish;
    }

    public String getLongitudeFinish() {
        return longitudeFinish;
    }

    public void setLongitudeFinish(String longitudeFinish) {
        this.longitudeFinish = longitudeFinish;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIsPersonal() {
        return isPersonal;
    }

    public void setIsPersonal(int isPersonal) {
        this.isPersonal = isPersonal;
    }

    public int getIsCars() {
        return isCars;
    }

    public void setIsCars(int isCars) {
        this.isCars = isCars;
    }

    public int getIsTools() {
        return isTools;
    }

    public void setIsTools(int isTools) {
        this.isTools = isTools;
    }

    public int getIsPickups() {
        return isPickups;
    }

    public void setIsPickups(int isPickups) {
        this.isPickups = isPickups;
    }

    public int getIsDeductives() {
        return isDeductives;
    }

    public void setIsDeductives(int isDeductives) {
        this.isDeductives = isDeductives;
    }

    public int getIsAdvance() {
        return isAdvance;
    }

    public void setIsAdvance(int isAdvance) {
        this.isAdvance = isAdvance;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public int getIsActivities() {
        return isActivities;
    }

    public void setIsActivities(int isActivities) {
        this.isActivities = isActivities;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }
}

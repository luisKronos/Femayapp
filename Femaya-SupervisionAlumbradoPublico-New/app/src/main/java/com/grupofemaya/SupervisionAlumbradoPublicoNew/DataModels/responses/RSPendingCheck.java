package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses;

public class RSPendingCheck {

    private int checkStatus;
    private String idCheck;
    private boolean hasPendindChecks;


    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(String idCheck) {
        this.idCheck = idCheck;
    }

    public boolean isHasPendindChecks() {
        return hasPendindChecks;
    }

    public void setHasPendindChecks(boolean hasPendindChecks) {
        this.hasPendindChecks = hasPendindChecks;
    }
}

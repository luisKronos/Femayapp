package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses;

import java.util.List;

public class RSPendingsChecks {

    private List<RSStatusCheck> checks;
    private boolean hasPendindChecks;


    public List<RSStatusCheck> getChecks() {
        return checks;
    }

    public void setChecks(List<RSStatusCheck> checks) {
        this.checks = checks;
    }

    public boolean isHasPendindChecks() {
        return hasPendindChecks;
    }

    public void setHasPendindChecks(boolean hasPendindChecks) {
        this.hasPendindChecks = hasPendindChecks;
    }
}

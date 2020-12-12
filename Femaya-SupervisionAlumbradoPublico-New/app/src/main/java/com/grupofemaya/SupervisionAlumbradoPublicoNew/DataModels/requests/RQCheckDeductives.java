package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DeductivasDTO;

import java.util.ArrayList;
import java.util.List;

public class RQCheckDeductives {

    private String idCheck;
    private List<DeductivasDTO> deductives = new ArrayList<>();

    public String getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(String idCheck) {
        this.idCheck = idCheck;
    }

    public List<DeductivasDTO> getDeductives() {
        return deductives;
    }

    public void setDeductives(List<DeductivasDTO> deductives) {
        this.deductives = deductives;
    }
}

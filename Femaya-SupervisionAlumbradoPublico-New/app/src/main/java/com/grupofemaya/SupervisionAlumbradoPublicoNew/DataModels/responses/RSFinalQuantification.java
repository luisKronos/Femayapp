package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DeductivesDTO;

import java.util.ArrayList;
import java.util.List;

public class RSFinalQuantification {

    private String percentAdvance;
    private String cars;
    private String personalPresent;
    private String uniformes;
    private String totalTools;
    private String totalDeductives;
    private List<DeductivesDTO> deductives = new ArrayList<>();


    public String getPercentAdvance() {
        return percentAdvance;
    }

    public void setPercentAdvance(String percentAdvance) {
        this.percentAdvance = percentAdvance;
    }

    public String getCars() {
        return cars;
    }

    public void setCars(String cars) {
        this.cars = cars;
    }

    public String getPersonalPresent() {
        return personalPresent;
    }

    public void setPersonalPresent(String personalPresent) {
        this.personalPresent = personalPresent;
    }

    public String getUniformes() {
        return uniformes;
    }

    public void setUniformes(String uniformes) {
        this.uniformes = uniformes;
    }

    public String getTotalTools() {
        return totalTools;
    }

    public void setTotalTools(String totalTools) {
        this.totalTools = totalTools;
    }

    public String getTotalDeductives() {
        return totalDeductives;
    }

    public void setTotalDeductives(String totalDeductives) {
        this.totalDeductives = totalDeductives;
    }

    public List<DeductivesDTO> getDeductives() {
        return deductives;
    }

    public void setDeductives(List<DeductivesDTO> deductives) {
        this.deductives = deductives;
    }
}

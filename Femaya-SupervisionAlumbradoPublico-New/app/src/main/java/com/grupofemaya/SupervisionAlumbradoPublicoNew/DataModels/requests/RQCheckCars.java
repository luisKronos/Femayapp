package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.CarDTO;

import java.util.ArrayList;
import java.util.List;

public class RQCheckCars {

    private String idCheck;
    private List<CarDTO> cars = new ArrayList<>();

    public String getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(String idCheck) {
        this.idCheck = idCheck;
    }

    public List<CarDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarDTO> cars) {
        this.cars = cars;
    }
}

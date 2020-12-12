package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels;

public class CuadranteDTO {

    private String idCuadrante;
    private String cuadrante;
    private boolean value=false;

    public String getIdCuadrante() {
        return idCuadrante;
    }

    public void setIdCuadrante(String idCuadrante) {
        this.idCuadrante = idCuadrante;
    }

    public String getCuadrante() {
        return cuadrante;
    }

    public void setCuadrante(String cuadrante) {
        this.cuadrante = cuadrante;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}

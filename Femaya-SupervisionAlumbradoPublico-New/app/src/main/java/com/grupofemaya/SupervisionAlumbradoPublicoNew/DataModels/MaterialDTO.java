package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels;

public class MaterialDTO {

    private String idMaterial;
    private String material;
    private boolean value=false;
    private String valueForOtro;

    public String getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(String idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getValueForOtro() {
        return valueForOtro;
    }

    public void setValueForOtro(String valueForOtro) {
        this.valueForOtro = valueForOtro;
    }
}

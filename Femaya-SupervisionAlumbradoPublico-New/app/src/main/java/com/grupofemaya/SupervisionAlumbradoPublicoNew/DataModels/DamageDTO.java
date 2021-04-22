package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels;

public class DamageDTO {

    private String idDamage;
    private String damage;
    private int value = 0;
//    private String valueForOtro;

    public String getIdDamage() {
        return idDamage;
    }

    public void setIdDamage(String idDamage) {
        this.idDamage = idDamage;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

//    public String getValueForOtro() { return valueForOtro; }
//
//    public void setValueForOtro(String valueForOtro) { this.valueForOtro = valueForOtro; }
}

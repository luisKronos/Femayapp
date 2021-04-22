package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests;

public class RQCuadrilla {
    private int idUser;
    private int noCuadrilla;
    private String personal;
    private String equipo;
    private String tipo;
    private String fotoCuadrilla;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getNoCuadrilla() {
        return noCuadrilla;
    }

    public void setNoCuadrilla(int noCuadrilla) {
        this.noCuadrilla = noCuadrilla;
    }

    public String getFotoCuadrilla() {
        return fotoCuadrilla;
    }

    public void setFotoCuadrilla(String fotoCuadrilla) {
        this.fotoCuadrilla = fotoCuadrilla;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

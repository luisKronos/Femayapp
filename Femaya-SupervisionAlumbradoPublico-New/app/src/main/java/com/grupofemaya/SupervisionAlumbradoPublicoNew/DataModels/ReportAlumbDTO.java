package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels;

import java.util.ArrayList;
import java.util.List;

public class ReportAlumbDTO {

    private String idReportAlumbrado;
    private String idCuadrante;
    private String idVialidad;
    private String idUser;

    private String lat;
    private String lon;
    private String turno;

    private String placas;
    private String alcaldia;
    private String tramo;

    private String colonia;

    private int cuadrilla;
    private int persElectricistaBaja;
    private int persElectricista;
    private int persElectricistaAyudante;
    private int persCabo;
    private int persOperadorGrua;
    private int persOperadorAyudante;
    private int equipGrua;
    private int equipCamioneta;
    private int equipSeguridad;
    private int equipSenalamiento;
    private String fotoCuadrilla;
    private String obsMaterial;
    private String fotoEquipo;
    private String folio;
    private String fotoAntes;
    private String fotoDurante;
    private String fotoDespues;
    private String fotoMaterial;
    private String notas;
    private String firmaContratista;
    private String firmaSupervision;

    private CuadrillaDTO listCuadrilla = new CuadrillaDTO();
    private EquipoDTO listEquipo = new EquipoDTO();
    private List<DamageDTO> listDamages = new ArrayList<>();
    private List<MaterialDTO> listaMaterial = new ArrayList<>();
    private MinutaDTO minuta = new MinutaDTO();


    public String getIdReportAlumbrado() {
        return idReportAlumbrado;
    }

    public void setIdReportAlumbrado(String idReportAlumbrado) {
        this.idReportAlumbrado = idReportAlumbrado;
    }

    public String getIdCuadrante() {
        return idCuadrante;
    }

    public void setIdCuadrante(String idCuadrante) {
        this.idCuadrante = idCuadrante;
    }

    public String getIdVialidad() {
        return idVialidad;
    }

    public void setIdVialidad(String idVialidad) {
        this.idVialidad = idVialidad;
    }

    public int getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(int cuadrilla) {
        this.cuadrilla = cuadrilla;
    }

    public int getPersElectricistaBaja() {
        return persElectricistaBaja;
    }

    public void setPersElectricistaBaja(int persElectricistaBaja) {
        this.persElectricistaBaja = persElectricistaBaja;
    }

    public int getPersElectricista() {
        return persElectricista;
    }

    public void setPersElectricista(int persElectricista) {
        this.persElectricista = persElectricista;
    }

    public int getPersElectricistaAyudante() {
        return persElectricistaAyudante;
    }

    public void setPersElectricistaAyudante(int persElectricistaAyudante) {
        this.persElectricistaAyudante = persElectricistaAyudante;
    }

    public int getPersCabo() {
        return persCabo;
    }

    public void setPersCabo(int persCabo) {
        this.persCabo = persCabo;
    }

    public int getPersOperadorGrua() {
        return persOperadorGrua;
    }

    public void setPersOperadorGrua(int persOperadorGrua) {
        this.persOperadorGrua = persOperadorGrua;
    }

    public int getPersOperadorAyudante() {
        return persOperadorAyudante;
    }

    public void setPersOperadorAyudante(int persOperadorAyudante) {
        this.persOperadorAyudante = persOperadorAyudante;
    }

    public int getEquipGrua() {
        return equipGrua;
    }

    public void setEquipGrua(int equipGrua) {
        this.equipGrua = equipGrua;
    }

    public int getEquipCamioneta() {
        return equipCamioneta;
    }

    public void setEquipCamioneta(int equipCamioneta) {
        this.equipCamioneta = equipCamioneta;
    }

    public int getEquipSeguridad() {
        return equipSeguridad;
    }

    public void setEquipSeguridad(int equipSeguridad) {
        this.equipSeguridad = equipSeguridad;
    }

    public int getEquipSenalamiento() {
        return equipSenalamiento;
    }

    public void setEquipSenalamiento(int equipSenalamiento) {
        this.equipSenalamiento = equipSenalamiento;
    }

    public String getFotoCuadrilla() {
        return fotoCuadrilla;
    }

    public void setFotoCuadrilla(String fotoCuadrilla) {
        this.fotoCuadrilla = fotoCuadrilla;
    }

    public String getObsMaterial() {
        return obsMaterial;
    }

    public void setObsMaterial(String obsMaterial) {
        this.obsMaterial = obsMaterial;
    }

    public String getFotoEquipo() {
        return fotoEquipo;
    }

    public void setFotoEquipo(String fotoEquipo) {
        this.fotoEquipo = fotoEquipo;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFotoAntes() {
        return fotoAntes;
    }

    public void setFotoAntes(String fotoAntes) {
        this.fotoAntes = fotoAntes;
    }

    public String getFotoDurante() {
        return fotoDurante;
    }

    public void setFotoDurante(String fotoDurante) {
        this.fotoDurante = fotoDurante;
    }

    public String getFotoDespues() {
        return fotoDespues;
    }

    public void setFotoDespues(String fotoDespues) {
        this.fotoDespues = fotoDespues;
    }

    public String getFotoMaterial() {
        return fotoMaterial;
    }

    public void setFotoMaterial(String fotoMaterial) {
        this.fotoMaterial = fotoMaterial;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getFirmaContratista() {
        return firmaContratista;
    }

    public void setFirmaContratista(String firmaContratista) {
        this.firmaContratista = firmaContratista;
    }

    public String getFirmaSupervision() {
        return firmaSupervision;
    }

    public void setFirmaSupervision(String firmaSupervision) {
        this.firmaSupervision = firmaSupervision;
    }

    public List<DamageDTO> getListDamages() {
        return listDamages;
    }

    public void setListDamages(List<DamageDTO> listDamages) {
        this.listDamages = listDamages;
    }

    public List<MaterialDTO> getListaMaterial() {
        return listaMaterial;
    }

    public void setListaMaterial(List<MaterialDTO> listaMaterial) {
        this.listaMaterial = listaMaterial;
    }

    public CuadrillaDTO getListCuadrilla() {
        return listCuadrilla;
    }

    public void setListCuadrilla(CuadrillaDTO listCuadrilla) {
        this.listCuadrilla = listCuadrilla;
    }

    public EquipoDTO getListEquipo() {
        return listEquipo;
    }

    public void setListEquipo(EquipoDTO listEquipo) {
        this.listEquipo = listEquipo;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public MinutaDTO getMinuta() {
        return minuta;
    }

    public void setMinuta(MinutaDTO minuta) {
        this.minuta = minuta;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPlacas() {
        return placas;
    }

    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public String getAlcaldia() {
        return alcaldia;
    }

    public void setAlcaldia(String alcaldia) {
        this.alcaldia = alcaldia;
    }

    public String getTramo() {
        return tramo;
    }

    public void setTramo(String tramo) {
        this.tramo = tramo;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) { this.colonia = colonia; }
}

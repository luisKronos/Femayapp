package com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils;


import android.graphics.Bitmap;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.CarDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DamageDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MaterialDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.ReportAlumbDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.ReportAlumbDirDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.VialidadDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSFinalQuantification;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSStatusCheck;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by luiscarlin on 7/12/16.
 */
public class LiveData {

    private String idCheck="";
    private RSStatusCheck currentCheck = new RSStatusCheck();
    private ArrayList<CarDTO> vehiculos = new ArrayList<>();
    private RSFinalQuantification finalQuantification = new RSFinalQuantification();
    private Bitmap signTemporaly;
    private List<RSStatusCheck> pendingChecks = new ArrayList<>();

    //NEW
    private ReportAlumbDTO liveReport = new ReportAlumbDTO();
    private ReportAlumbDirDTO liveReportDirector = new ReportAlumbDirDTO();
    private List<VialidadDTO> vialidades = new ArrayList<>();
    private List<DamageDTO>  listDamges = new ArrayList<>();
    private List<MaterialDTO>  listMaterials = new ArrayList<>();
    private Bitmap signContratista;
    private Bitmap signSupervision;


    private static LiveData ourInstance = new LiveData();


    public static LiveData getInstance() {
        return ourInstance;
    }


    public String getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(String idCheck) {
        this.idCheck = idCheck;
    }

    private LiveData() {
    }


    public RSStatusCheck getCurrentCheck() {
        return currentCheck;
    }

    public void setCurrentCheck(RSStatusCheck currentCheck) {
        this.currentCheck = currentCheck;
    }

    public ArrayList<CarDTO> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(ArrayList<CarDTO> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public RSFinalQuantification getFinalQuantification() {
        return finalQuantification;
    }

    public void setFinalQuantification(RSFinalQuantification finalQuantification) {
        this.finalQuantification = finalQuantification;
    }

    public Bitmap getSignTemporaly() {
        return signTemporaly;
    }

    public void setSignTemporaly(Bitmap signTemporaly) {
        this.signTemporaly = signTemporaly;
    }

    public List<RSStatusCheck> getPendingChecks() {
        return pendingChecks;
    }

    public void setPendingChecks(List<RSStatusCheck> pendingChecks) {
        this.pendingChecks = pendingChecks;
    }

    public ReportAlumbDTO getLiveReport() {
        return liveReport;
    }

    public void setLiveReport(ReportAlumbDTO liveReport) {
        this.liveReport = liveReport;
    }

    public List<VialidadDTO> getVialidades() {
        return vialidades;
    }

    public void setVialidades(List<VialidadDTO> vialidades) {
        this.vialidades = vialidades;
    }

    public List<DamageDTO> getListDamges() {
        return listDamges;
    }

    public void setListDamges(List<DamageDTO> listDamges) {
        this.listDamges = listDamges;
    }

    public List<MaterialDTO> getListMaterials() {
        return listMaterials;
    }

    public void setListMaterials(List<MaterialDTO> listMaterials) {
        this.listMaterials = listMaterials;
    }

    public Bitmap getSignContratista() {
        return signContratista;
    }

    public void setSignContratista(Bitmap signContratista) {
        this.signContratista = signContratista;
    }

    public Bitmap getSignSupervision() {
        return signSupervision;
    }

    public void setSignSupervision(Bitmap signSupervision) {
        this.signSupervision = signSupervision;
    }

    public ReportAlumbDirDTO getLiveReportDirector() {
        return liveReportDirector;
    }

    public void setLiveReportDirector(ReportAlumbDirDTO liveReportDirector) {
        this.liveReportDirector = liveReportDirector;
    }
}

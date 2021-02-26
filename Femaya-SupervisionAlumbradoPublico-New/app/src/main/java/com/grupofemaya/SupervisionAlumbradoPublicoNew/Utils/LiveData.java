package com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils;


import android.graphics.Bitmap;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.Actividad;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.CarDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.Causa;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DamageDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.Diagnostico;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MaterialDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MaterialNew;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MinutaDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.ReportAlumbDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.ReportAlumbDirDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.VialidadDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCuadrilla;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQFinishHour;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQImageAfter;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQImageBefore;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQImageDuring;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQImageMaterialUsed;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQMaterialUsed;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQNotas;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInit;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInitThree;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInitTwo;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQSignContratista;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQSignSupervisor;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSFinalQuantification;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSGetListPendings;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSIdCuadrillas;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSInitReport;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSReportInitOne;
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

    private List<RSGetListPendings> listPendings = new ArrayList<>();


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

    public ReportAlumbDirDTO getLiveReportDirector() {
        return liveReportDirector;
    }

    public void setLiveReportDirector(ReportAlumbDirDTO liveReportDirector) {
        this.liveReportDirector = liveReportDirector;
    }

    public List<RSGetListPendings> getListPendings() {
        return listPendings;
    }

    public void setListPendings(List<RSGetListPendings> listPendings) {
        this.listPendings = listPendings;
    }

    public static LiveData getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(LiveData ourInstance) {
        LiveData.ourInstance = ourInstance;
    }


    //NEW LINE RQ
    private RQCuadrilla cuadrillaReport = new RQCuadrilla();
    private RQReportInit reportInit = new RQReportInit();
    private RQReportInitTwo reportInitTwo = new RQReportInitTwo();
    private RQImageBefore reportImageBefore = new RQImageBefore();
    private RQImageDuring reportImageDuring = new RQImageDuring();
    private RQImageAfter reportImageAfter = new RQImageAfter();
    private RQReportInitThree reportInitThree = new RQReportInitThree();
    private MinutaDTO infoMinuta = new MinutaDTO();
    private RQMaterialUsed reportMaterialUsed = new RQMaterialUsed();
    private RQImageMaterialUsed reportImageMaterialUsed = new RQImageMaterialUsed();
    private RQNotas reportNotas = new RQNotas();
    private RQSignContratista reportSignContratista = new RQSignContratista();
    private RQSignSupervisor reportSignSupervisor = new RQSignSupervisor();
    private Bitmap signContratista = null;
    private Bitmap signSupervision = null;
    private RQFinishHour reportFinishHour = new RQFinishHour();
    private List<MaterialNew> listM = new ArrayList<>();
    private List<DamageDTO> listD = new ArrayList<>();
    private List<RSReportInitOne> listIdReportAlumbrados = new ArrayList<>();

    public RQCuadrilla getCuadrillaReport() {
        return cuadrillaReport;
    }

    public void setCuadrillaReport(RQCuadrilla cuadrillaReport) {
        this.cuadrillaReport = cuadrillaReport;
    }

    public RQReportInit getReportInit() {
        return reportInit;
    }

    public void setReportInit(RQReportInit reportInit) {
        this.reportInit = reportInit;
    }

    public RQReportInitTwo getReportInitTwo() {
        return reportInitTwo;
    }

    public void setReportInitTwo(RQReportInitTwo reportInitTwo) {
        this.reportInitTwo = reportInitTwo;
    }

    public RQImageBefore getReportImageBefore() {
        return reportImageBefore;
    }

    public void setReportImageBefore(RQImageBefore reportImageBefore) {
        this.reportImageBefore = reportImageBefore;
    }

    public RQImageDuring getReportImageDuring() {
        return reportImageDuring;
    }

    public void setReportImageDuring(RQImageDuring reportImageDuring) {
        this.reportImageDuring = reportImageDuring;
    }

    public RQImageAfter getReportImageAfter() {
        return reportImageAfter;
    }

    public void setReportImageAfter(RQImageAfter reportImageAfter) {
        this.reportImageAfter = reportImageAfter;
    }

    public RQReportInitThree getReportInitThree() {
        return reportInitThree;
    }

    public void setReportInitThree(RQReportInitThree reportInitThree) {
        this.reportInitThree = reportInitThree;
    }

    public MinutaDTO getInfoMinuta() {
        return infoMinuta;
    }

    public void setInfoMinuta(MinutaDTO infoMinuta) {
        this.infoMinuta = infoMinuta;
    }

    public RQMaterialUsed getReportMaterialUsed() {
        return reportMaterialUsed;
    }

    public void setReportMaterialUsed(RQMaterialUsed reportMaterialUsed) {
        this.reportMaterialUsed = reportMaterialUsed;
    }

    public RQImageMaterialUsed getReportImageMaterialUsed() {
        return reportImageMaterialUsed;
    }

    public void setReportImageMaterialUsed(RQImageMaterialUsed reportImageMaterialUsed) {
        this.reportImageMaterialUsed = reportImageMaterialUsed;
    }

    public RQNotas getReportNotas() {
        return reportNotas;
    }

    public void setReportNotas(RQNotas reportNotas) {
        this.reportNotas = reportNotas;
    }

    public RQSignContratista getReportSignContratista() {
        return reportSignContratista;
    }

    public void setReportSignContratista(RQSignContratista reportSignContratista) {
        this.reportSignContratista = reportSignContratista;
    }

    public RQSignSupervisor getReportSignSupervisor() {
        return reportSignSupervisor;
    }

    public void setReportSignSupervisor(RQSignSupervisor reportSignSupervisor) {
        this.reportSignSupervisor = reportSignSupervisor;
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

    public RQFinishHour getReportFinishHour() {
        return reportFinishHour;
    }

    public void setReportFinishHour(RQFinishHour reportFinishHour) {
        this.reportFinishHour = reportFinishHour;
    }

    public List<MaterialNew> getListM() {
        return listM;
    }

    public void setListM(List<MaterialNew> listM) {
        this.listM = listM;
    }

    public List<DamageDTO> getListD() {
        return listD;
    }

    public void setListD(List<DamageDTO> listD) {
        this.listD = listD;
    }

    public List<RSReportInitOne> getListIdReportAlumbrados() {
        return listIdReportAlumbrados;
    }

    public void setListIdReportAlumbrados(List<RSReportInitOne> listIdReportAlumbrados) {
        this.listIdReportAlumbrados = listIdReportAlumbrados;
    }

    //RS
    private List<RSIdCuadrillas> idCuadrillas = new ArrayList<>();
    private RSReportInitOne responseReportInit = new RSReportInitOne();
    private List<Causa> listCausa = new ArrayList<>();
    private List<Diagnostico> listDiagnostico = new ArrayList<>();
    private List<Actividad> listActividad = new ArrayList<>();

    public List<RSIdCuadrillas> getIdCuadrillas() {
        return idCuadrillas;
    }

    public void setIdCuadrillas(List<RSIdCuadrillas> idCuadrillas) {
        this.idCuadrillas = idCuadrillas;
    }

    public RSReportInitOne getResponseReportInit() {
        return responseReportInit;
    }

    public void setResponseReportInit(RSReportInitOne responseReportInit) {
        this.responseReportInit = responseReportInit;
    }

    public List<Causa> getListCausa() {
        return listCausa;
    }

    public void setListCausa(List<Causa> listCausa) {
        this.listCausa = listCausa;
    }

    public List<Diagnostico> getListDiagnostico() {
        return listDiagnostico;
    }

    public void setListDiagnostico(List<Diagnostico> listDiagnostico) {
        this.listDiagnostico = listDiagnostico;
    }

    public List<Actividad> getListActividad() {
        return listActividad;
    }

    public void setListActividad(List<Actividad> listActividad) {
        this.listActividad = listActividad;
    }
}

package com.grupofemaya.SupervisionAlumbradoPublicoNew.Connection;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.Actividad;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.Causa;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DamageDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.Diagnostico;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MaterialDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.ReportAlumbDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.ReportAlumbDirDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.User;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.VialidadDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckPersonal;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCuadrilla;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQFinishHour;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQGetPending;
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
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQStatusCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DeductivasDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckActividades;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckAdvance;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckCars;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckDeductives;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckPickups;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckTools;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQInitCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQLogin;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQPendingCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQRevised;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQRoadsBySector;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSFinalQuantification;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSGetListPendings;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSIdCuadrillas;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSInitCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSInitReport;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSPendingCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSPendingsChecks;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSReportInitOne;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSRoad;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSSector;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSStatusCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSSubirCuadrilla;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.rsLogin;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.rsGeneral;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.rsGeneralList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("ws/loginForm.php")
    Call<rsGeneral<rsLogin>> requestLogin(@Body RQLogin request);

    @GET("ws/getSectors.php")
    Call<rsGeneralList<RSSector>> requesSectors();

    @POST("ws/getRoadsBySector.php")
    Call<rsGeneralList<RSRoad>> requestRoadsBySector(@Body RQRoadsBySector request);

    @POST("ws/initCheck.php")
    Call<rsGeneral<RSInitCheck>> requestInitCheck(@Body RQInitCheck request);

    @POST("ws/getStatusCheck.php")
    Call<rsGeneralList<RSStatusCheck>> requestStatusCheck(@Body RQStatusCheck request);

    @POST("ws/getStatusCheckByUser.php")
    Call<rsGeneral<RSPendingCheck>> requestPendingCheck(@Body RQPendingCheck request);

    @POST("ws/getPendingChecks.php")
    Call<rsGeneral<RSPendingsChecks>> requestPendingsChecks(@Body RQPendingCheck request);

    @POST("ws/newCheckPersonal.php")
    Call<rsGeneral<RSInitCheck>> requestCheckPersonal(@Body RQCheckPersonal request);

    @POST("ws/newCheckCars.php")
    Call<rsGeneral<String>> requestNewCheckCars(@Body RQCheckCars request);

    @POST("ws/newCheckTools.php")
    Call<rsGeneral<String>> requestNewCheckTools(@Body RQCheckTools request);

    @POST("ws/newCheckPickups.php")
    Call<rsGeneral<String>> requestnewCheckPickups(@Body RQCheckPickups request);

    @POST("ws/newCheckActivities.php")
    Call<rsGeneral<String>> requestnewCheckActividades(@Body RQCheckActividades request);

    @POST("ws/newCheckDeductives.php")
    Call<rsGeneral<String>> requestNewCheckDeductives(@Body RQCheckDeductives request);

    @POST("ws/newCheckAdvance.php")
    Call<rsGeneral<String>> requestNewCheckAdvance(@Body RQCheckAdvance request);

    @POST("ws/getFinalQuantification.php")
    Call<rsGeneral<RSFinalQuantification>> requestFinalQuantification(@Body RQStatusCheck request);

    @POST("ws/checkRevised.php")
    Call<rsGeneral<String>> requestCheckRevised(@Body RQRevised request);

    @GET("ws/getPenalties.php")
    Call<rsGeneralList<DeductivasDTO>> requestGetPenalties();

    //NEW

    @GET("ws/getMaterials.php")
    Call<rsGeneralList<MaterialDTO>> requestGetMaterials();

    @GET("ws/getTipoDamages.php")
    Call<rsGeneralList<DamageDTO>> requestGetMDamages();

    @GET("ws/getAllVialidades.php")
    Call<rsGeneralList<VialidadDTO>> requestAllVialidades();

    @GET("ws/getVialidadesPorCuadrante.php")
    Call<rsGeneralList<VialidadDTO>> requestGetVialidades(@Query("idCuadrante") String emailID);

    @POST("ws/reportInit.php")
    Call<rsGeneral<RSInitReport>> requestReportInit(@Body ReportAlumbDTO request);

    @POST("ws/reportStep2.php")
    Call<rsGeneral<RSInitReport>> requestReport2(@Body ReportAlumbDTO request);

    @POST("ws/reportStep3.php")
    Call<rsGeneral<RSInitReport>> requestReport3(@Body ReportAlumbDTO request);

    @POST("ws/loginForm.php")
    Call<rsGeneral<String>> requestLoginNew(@Body RQLogin request);

    @POST("ws/reportDirector.php")
    Call<rsGeneral<RSInitReport>> requestReportDirector(@Body ReportAlumbDirDTO request);

    @POST("ws/getPendingList.php")
    Call<rsGeneralList<RSGetListPendings>> requestListPendings(@Body RQGetPending request);

    //NEW
    @POST("ws/subirCuadrillas.php")
    Call<rsGeneral<RSSubirCuadrilla>> requestCuadrillas(@Body RQCuadrilla request);

    @POST("ws/getCuadrillasIds.php")
    Call<rsGeneralList<RSIdCuadrillas>> requestIdCuadrillas(@Body User idUser);

    @POST("ws/reportInit.php")
    Call<rsGeneral<RSReportInitOne>> requestReportInitOne(@Body RQReportInit request);

    @POST("ws/reportStep2.php")
    Call<rsGeneral<String>> requestReportInitTwo(@Body RQReportInitTwo request);

    @POST("ws/sendImageBefore.php")
    Call<rsGeneral<RSReportInitOne>> requestImageBefore(@Body RQImageBefore request);

    @POST("ws/sendImageDuring.php")
    Call<rsGeneral<RSReportInitOne>> requestImageDuring(@Body RQImageDuring request);

    @POST("ws/sendImageAfter.php")
    Call<rsGeneral<RSReportInitOne>> requestImageAfter(@Body RQImageAfter request);

    @POST("ws/reportStep3.php")
    Call<rsGeneral<RSReportInitOne>> requestReportInitThree(@Body RQReportInitThree request);

    @GET("ws/getCausaNoCompletado.php")
    Call<rsGeneralList<Causa>> requestGetCausas();

    @GET("ws/getDiagnosticoFalla.php")
    Call<rsGeneralList<Diagnostico>> requestGetDiagnostico();

    @GET("ws/getActividadRealizada.php")
    Call<rsGeneralList<Actividad>> requestGetActividades();

    @POST("ws/subirMaterialesUsed.php")
    Call<rsGeneral<RSReportInitOne>> requestMaterialesUsed(@Body RQMaterialUsed request);

    @POST("ws/sendImageMaterial.php")
    Call<rsGeneral<RSReportInitOne>> requestImageMaterialUsed(@Body RQImageMaterialUsed request);

    @POST("ws/reportNotes.php")
    Call<rsGeneral<RSReportInitOne>> requestReportNotas(@Body RQNotas request);

    @POST("ws/sendSignEnterprise.php")
    Call<rsGeneral<RSReportInitOne>> requestSignContratista(@Body RQSignContratista request);

    @POST("ws/sendSignSupervisor.php")
    Call<rsGeneral<RSReportInitOne>> requestSignSupervisor(@Body RQSignSupervisor request);

    @POST("ws/finishHour.php")
    Call<rsGeneral<RSReportInitOne>> requestFinishHour(@Body RQFinishHour request);
}

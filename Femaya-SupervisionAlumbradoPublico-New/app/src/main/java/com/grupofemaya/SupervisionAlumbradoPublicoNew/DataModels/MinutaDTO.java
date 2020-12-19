package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels;

public class MinutaDTO {

    private String idReportMinuta;
    private String tipoLuminario;
    private String fallaDetectada;
    private String diagFalla;
    private String accionRealizada;
    private String estatusReparacion;
    private String observaciones;
    private String causaNoCompletado = "";

    public String getIdReportMinuta() {
        return idReportMinuta;
    }

    public void setIdReportMinuta(String idReportMinuta) {
        this.idReportMinuta = idReportMinuta;
    }

    public String getTipoLuminario() {
        return tipoLuminario;
    }

    public void setTipoLuminario(String tipoLuminario) {
        this.tipoLuminario = tipoLuminario;
    }

    public String getFallaDetectada() {
        return fallaDetectada;
    }

    public void setFallaDetectada(String fallaDetectada) {
        this.fallaDetectada = fallaDetectada;
    }

    public String getDiagFalla() {
        return diagFalla;
    }

    public void setDiagFalla(String diagFalla) {
        this.diagFalla = diagFalla;
    }

    public String getAccionRealizada() {
        return accionRealizada;
    }

    public void setAccionRealizada(String accionRealizada) {
        this.accionRealizada = accionRealizada;
    }

    public String getEstatusReparacion() {
        return estatusReparacion;
    }

    public void setEstatusReparacion(String estatusReparacion) {
        this.estatusReparacion = estatusReparacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCausaNoCompletado() {
        return causaNoCompletado;
    }

    public void setCausaNoCompletado(String causaNoCompletado) {
        this.causaNoCompletado = causaNoCompletado;
    }
}

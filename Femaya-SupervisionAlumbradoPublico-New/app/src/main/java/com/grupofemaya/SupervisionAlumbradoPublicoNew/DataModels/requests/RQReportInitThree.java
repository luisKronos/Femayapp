package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MinutaDTO;

public class RQReportInitThree {
    private int idReportAlumbrado;
    private MinutaDTO minuta;

    public int getIdReportAlumbrado() {
        return idReportAlumbrado;
    }

    public void setIdReportAlumbrado(int idReportAlumbrado) {
        this.idReportAlumbrado = idReportAlumbrado;
    }

    public MinutaDTO getMinuta() {
        return minuta;
    }

    public void setMinuta(MinutaDTO minuta) {
        this.minuta = minuta;
    }
}

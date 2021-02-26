package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MaterialDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MaterialNew;

import java.util.List;

public class RQMaterialUsed {
    private int idReportAlumbrado;
    private List<MaterialNew> listaMaterial;

    public int getIdReportAlumbrado() {
        return idReportAlumbrado;
    }

    public void setIdReportAlumbrado(int idReportAlumbrado) {
        this.idReportAlumbrado = idReportAlumbrado;
    }

    public List<MaterialNew> getListaMaterial() {
        return listaMaterial;
    }

    public void setListaMaterial(List<MaterialNew> listaMaterial) {
        this.listaMaterial = listaMaterial;
    }
}

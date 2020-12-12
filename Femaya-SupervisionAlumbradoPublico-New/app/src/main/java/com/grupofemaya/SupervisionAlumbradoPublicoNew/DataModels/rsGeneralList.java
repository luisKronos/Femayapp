package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels;


import java.util.List;

public class rsGeneralList<T> {

    private RSHeader header;
    private List<T> data;

    public RSHeader getHeader() {
        return header;
    }

    public void setHeader(RSHeader header) {
        this.header = header;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

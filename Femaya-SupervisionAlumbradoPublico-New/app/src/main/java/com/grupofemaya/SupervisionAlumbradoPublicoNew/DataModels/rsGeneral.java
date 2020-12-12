package com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels;


public class rsGeneral<T> {

    private RSHeader header;
    private T data;

    public RSHeader getHeader() {
        return header;
    }

    public void setHeader(RSHeader header) {
        this.header = header;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

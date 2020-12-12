package com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository;


public interface RepositoryImp<T>  {
    void succedResponse(T response);
    void requestFail(String message);
}

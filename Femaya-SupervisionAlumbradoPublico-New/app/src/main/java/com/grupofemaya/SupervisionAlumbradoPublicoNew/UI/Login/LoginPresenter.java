package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Login;

import android.content.Intent;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.rsLogin;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.SharedPreferencesManager;


public class LoginPresenter implements RepositoryImp {

    private LoginActivity mActivity;

    public LoginPresenter(LoginActivity activity) {
        mActivity = activity;
    }

    public void getLogin(String email, String password) {
        mActivity.showProgress();
        Repository.getInstance().setRepositoryImp(this);
        Repository.getInstance().requesLogin(email,password);
    }


    @Override
    public void succedResponse(Object response) {
        String idUser = ((rsLogin)response).getIdUser();
        String name = ((rsLogin)response).getName();
        String turno = ((rsLogin)response).getTurno();

        SharedPreferencesManager.getInstance().setIdUser(idUser);
        SharedPreferencesManager.getInstance().setUserName(name);
        SharedPreferencesManager.getInstance().setTurno(turno);

        mActivity.hideProgress();
        goHome();
    }


    @Override
    public void requestFail(String message) {
        mActivity.hideProgress();
        mActivity.showDialog(message);
    }

    public void goHome() {
        Intent intent = new Intent(mActivity, MainActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }


}

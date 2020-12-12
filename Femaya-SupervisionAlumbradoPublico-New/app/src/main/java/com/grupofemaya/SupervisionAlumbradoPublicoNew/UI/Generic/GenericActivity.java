package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Constantes;



public class GenericActivity extends AppCompatActivity implements MainView {

    public GenericActivity that;
    SharedPreferences sharedpreferences;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        that=this;

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        sharedpreferences = getSharedPreferences(Constantes.PREFERENCES, Context.MODE_PRIVATE);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Espera...");
        pDialog.setCancelable(false);
    }

    public void showDialog(Context context, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void hideKeyboard(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void showDialog(String message) {
            showDialog(this,message);
    }

    public void showProgress(){
        if(pDialog!=null && !pDialog.isShowing()) {
            pDialog.show();
        }
    }

    public void hideProgress(){
        if(pDialog!=null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}

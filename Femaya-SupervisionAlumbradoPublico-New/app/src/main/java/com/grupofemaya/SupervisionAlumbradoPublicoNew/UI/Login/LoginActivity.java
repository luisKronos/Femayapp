package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericActivity;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.GPSTracker;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.SharedPreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends GenericActivity {

    LoginPresenter loginPresenter;
    @BindView(R.id.txtEmail) EditText txtEmail;
    @BindView(R.id.txtPassword) EditText txtPass;

    GPSTracker gpsTracker;
    final int REQUEST_ID_MULTIPLE_PERMISSIONS=666;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        loginPresenter = new LoginPresenter( this);
        SharedPreferencesManager.initializeInstance(this);
        ButterKnife.bind(this);

        gpsTracker = new GPSTracker(this);


        checkAndRequestPermissions();
    }

    @OnClick(R.id.btnLogin)
    public void clickLogin(View view) {
        loginPresenter.getLogin(txtEmail.getText().toString(),txtPass.getText().toString());
    }


    private  void checkAndRequestPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(LoginActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ID_MULTIPLE_PERMISSIONS);
            }else{
                checkInit();
            }
        }else{
            checkInit();
        }
    }


    private void checkInit(){
        if(gpsTracker.canGetLocation()) {
            if(!SharedPreferencesManager.getInstance().getIdUser().equals("0")) {
                loginPresenter.goHome();
            }
        }else{
            Toast.makeText(LoginActivity.this.getApplicationContext(), "No se puede verificar la ubicación.", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkAndRequestPermissions();
                } else {
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "Debes aceptar los permisos de uso para entrar a la aplicación.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}

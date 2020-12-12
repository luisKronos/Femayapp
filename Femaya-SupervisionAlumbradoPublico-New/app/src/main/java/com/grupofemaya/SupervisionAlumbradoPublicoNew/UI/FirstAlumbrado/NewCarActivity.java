package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.FirstAlumbrado;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.CarDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewCarActivity extends GenericActivity implements View.OnClickListener {

    GenericActivity that;

    @BindView(R.id.txtPlacas)
    EditText txtPlacas;

    @BindView(R.id.spinnerTorretas)
    Spinner spinnerTorretas;

    @BindView(R.id.spinnerLuces)
    Spinner spinnerLuces;

    @BindView(R.id.spinnerLaminas)
    Spinner spinnerLaminas;

    @BindView(R.id.spinnerLonas)
    Spinner spinnerLonas;

    @BindView(R.id.imageButton)
    ImageButton imageButton;

    @BindView(R.id.btnFinish)
    Button btnFinish;

    final static int CODE_PHOTO=100;
    final static int CODE_REQ_PERMISSION=600;
    String photoReport="";
    Funcs mFuncs = new Funcs();

    String txtTorretas="";
    String txtLuces="";
    String txtLaminas="";
    String txtLona="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_new_car );
        ButterKnife.bind(this);
        btnFinish.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        that=this;
        fillSpinners();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v==btnFinish){
            goFinish();
        }else if(v==imageButton){
            btnPhoto();
        }
    }


    private void goFinish(){
        if(txtPlacas.getText().toString().equals("")){
            showDialog("Debes ingresar las placas.");
        }else if(txtTorretas.equals("")){
            showDialog("Debes ingresar las torretas.");
        }else if(txtLuces.equals("")){
            showDialog("Debes ingresar las luces.");
        }else if(txtLaminas.equals("")){
            showDialog("Debes ingresar las laminas.");
        }else if(txtLona.equals("")){
            showDialog("Debes ingresar la lona.");
        }else if(photoReport.equals("")){
            showDialog("Debes ingresar una imágen.");
        }else{
            requestNewCar();
        }
    }


    private void requestNewCar(){
        CarDTO newCar = new CarDTO();
        newCar.setPlacas(txtPlacas.getText().toString());
        newCar.setLaminas(txtLaminas);
        newCar.setLona(txtLona);
        newCar.setLuces(txtLuces);
        newCar.setTorretas(txtTorretas);
        newCar.setImagen(photoReport);
        LiveData.getInstance().getVehiculos().add(newCar);
        finish();
    }


    private void btnPhoto(){
        if (ContextCompat.checkSelfPermission(that,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(that, Manifest.permission.CAMERA)) {
                tomarFoto();
            } else {
                ActivityCompat.requestPermissions(that,new String[]{Manifest.permission.CAMERA}, CODE_REQ_PERMISSION);
            }
        }else{
            tomarFoto();
        }
    }


    private void tomarFoto( ) {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(that.getPackageManager()) != null){
            File photoFile = null;
            try {
                photoFile = mFuncs.createImageFile(that);
                photoReport = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                that.showDialog(ex.getMessage());
            }
            Uri photoURI = FileProvider.getUriForFile(that,that.getApplicationContext().getPackageName()+".provider",photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(pictureIntent, CODE_PHOTO);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CODE_PHOTO && resultCode==-1){
            try {
                mFuncs.setImageOnImageButton(photoReport,imageButton);
            } catch (Exception e) {
                Toast.makeText(that.getApplicationContext(), "Error al cargar la foto", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CODE_REQ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tomarFoto();
                } else {
                    Toast.makeText(that.getApplicationContext(), "No se puede acceder a la camara", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }


    private void fillSpinners(){


        final List<String> opcionesArray = new ArrayList<>();
        opcionesArray.add("Selecciona una opción");
        opcionesArray.add("CUMPLE");
        opcionesArray.add("NO CUMPLE");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(that, android.R.layout.simple_spinner_item, opcionesArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerTorretas.setAdapter(dataAdapter);
        spinnerTorretas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    if(position==1){
                        txtTorretas="1";
                    }else{
                        txtTorretas="0";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerLonas.setAdapter(dataAdapter);
        spinnerLonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    if(position==1){
                        txtLona="1";
                    }else{
                        txtLona="0";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerLaminas.setAdapter(dataAdapter);
        spinnerLaminas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    if(position==1){
                        txtLaminas="1";
                    }else{
                        txtLaminas="0";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerLuces.setAdapter(dataAdapter);
        spinnerLuces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    if(position==1){
                        txtLuces="1";
                    }else{
                        txtLuces="0";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

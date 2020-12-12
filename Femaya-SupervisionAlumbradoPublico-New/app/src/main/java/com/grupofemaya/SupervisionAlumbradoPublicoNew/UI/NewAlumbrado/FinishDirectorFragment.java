package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentTransaction;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.ReportAlumbDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.GPSTracker;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.SharedPreferencesManager;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FinishDirectorFragment extends GenericFragment {

    MainActivity that;
    View view;

    GPSTracker gpsTracker;



    @BindView(R.id.btnFoto)
    Button btnFoto;

    @BindView(R.id.imgView)
    ImageView imgView;


    static int CODE_PHOTO=100;
    static final int CODE_REQ_PERMISSION=600;
    String photoReport="";
    Funcs mFuncs = new Funcs();


    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initRequest();
        }
    };

    private final Runnable mMessageSender = new Runnable() {
        public void run() {
            Message msg = mHandler.obtainMessage();
            if(LiveData.getInstance().getLiveReportDirector().getFoto()!=null) {
                String path = LiveData.getInstance().getLiveReportDirector().getFoto();
                LiveData.getInstance().getLiveReportDirector().setFoto(mFuncs.convierteBase64(path));
            }
            mHandler.sendMessage(msg);
        }
    };




    public FinishDirectorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_foto_director, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        gpsTracker = new GPSTracker(that);

        return view;
    }


    @OnClick(R.id.btnFoto)
    public void clickFoto(){
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



    @OnClick(R.id.btn)
    public void clickContinuar(){
        if(gpsTracker.canGetLocation() && gpsTracker.getLatitude() != 0.0){
            LiveData.getInstance().getLiveReportDirector().setLat(String.valueOf(gpsTracker.getLatitude()));
            LiveData.getInstance().getLiveReportDirector().setLon(String.valueOf(gpsTracker.getLongitude()));
            LiveData.getInstance().getLiveReportDirector().setIdUser(SharedPreferencesManager.getInstance().getIdUser());
            ask();
        }else{
            gpsTracker.showSettingsAlert();
            that.showDialog("No se puede obtener tu ubicación");
        }
    }

    private void ask(){
        AlertDialog.Builder builder = new AlertDialog.Builder(that);
        builder.setMessage("¿Deseas continuar?")
                .setCancelable(true)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        prepareReq();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }



    private void prepareReq(){
        that.showProgress();
        new Thread(mMessageSender).start();
    }


    private void initRequest(){
        Repository.getInstance().requestReportDirector(new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                that.showDialog("Reporte guardado");
                goNext();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                that.showDialog(message);
            }
        });
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
                LiveData.getInstance().getLiveReportDirector().setFoto(photoReport);
                mFuncs.setImageOnImageView(photoReport,imgView);
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



    private void goNext(){

        NewHomeFragment newFragment = new NewHomeFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.FirstAlumbrado;

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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckAdvance;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSInitCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.rsGeneral;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AvanceFragment extends GenericFragment implements View.OnClickListener {

    MainActivity that;
    View view;


    @BindView(R.id.btnFinish)
    Button btnFinish;

    @BindView(R.id.imageButton)
    ImageButton imageButton;


    @BindView(R.id.txtPercent)
    EditText txtPercent;

    @BindView(R.id.txtObs)
    EditText txtObs;

    RQCheckAdvance request;

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            requestFinish();
        }
    };

    private final Runnable mMessageSender = new Runnable() {
        public void run() {
            Message msg = mHandler.obtainMessage();

            request = new RQCheckAdvance();
            request.setIdCheck(LiveData.getInstance().getIdCheck());
            request.setPercent(txtPercent.getText().toString());
            request.setPhoto(mFuncs.convierteBase64(photoReport));
            request.setLat(String.valueOf(that.gpsTracker.getLatitude()));
            request.setLon(String.valueOf(that.gpsTracker.getLongitude()));
            request.setObs(txtObs.getText().toString());

            mHandler.sendMessage(msg);
        }
    };


    public AvanceFragment() {
        // Required empty public constructor
    }


    static int CODE_PHOTO=100;
    static final int CODE_REQ_PERMISSION=600;
    String photoReport="";
    Funcs mFuncs = new Funcs();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_avance, container, false);
        that = (MainActivity) getActivity();
        ButterKnife.bind(this, view);


        btnFinish.setOnClickListener(this);
        imageButton.setOnClickListener(this);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v == btnFinish) {
            finishAvance();
        } else if (v == imageButton) {
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


    private void finishAvance(){
        if(photoReport.equals("")){
            that.showDialog("Debes tomar una foto.");
        }else{
            prepareRequest();
        }
    }


    private void prepareRequest(){
        that.showProgress();
        new Thread(mMessageSender).start();
    }

    private void requestFinish(){
        Repository.getInstance().requestCheckAdvance(request,new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                rsGeneral<RSInitCheck> res = (rsGeneral<RSInitCheck>) response;
                AlertDialog.Builder builder = new AlertDialog.Builder(that);
                builder.setMessage(res.getHeader().getMessage())
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                goHome();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                showDialog(message);
            }
        });
    }


    public void goHome() {
        HomeFragment newFragment = new HomeFragment();
        FragmentTransaction transaction = that.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

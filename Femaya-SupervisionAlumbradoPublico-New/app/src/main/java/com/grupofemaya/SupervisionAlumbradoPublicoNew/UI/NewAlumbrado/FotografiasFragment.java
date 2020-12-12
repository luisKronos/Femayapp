package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FotografiasFragment extends GenericFragment {

    MainActivity that;
    View view;


    @BindView(R.id.imgView1)
    ImageView imgView1;

    @BindView(R.id.imgView2)
    ImageView imgView2;

    @BindView(R.id.imgView3)
    ImageView imgView3;


    static int CODE_PHOTO_ANTES=100;
    static int CODE_PHOTO_DURANTE=200;
    static int CODE_PHOTO_DESPUES=300;

    static final int CODE_REQ_PERMISSION=600;
    String photoReportAntes="";
    String photoReportDurante="";
    String photoReportDespues="";

    Funcs mFuncs = new Funcs();

    public FotografiasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fotos, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();


        return view;
    }

    @OnClick(R.id.btnAntes)
    public void clickFotoantes(){
        if (ContextCompat.checkSelfPermission(that,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(that, Manifest.permission.CAMERA)) {
                tomarFotoAntes();
            } else {
                ActivityCompat.requestPermissions(that,new String[]{Manifest.permission.CAMERA}, CODE_REQ_PERMISSION);
            }
        }else{
            tomarFotoAntes();
        }
    }

    @OnClick(R.id.btnDurante)
    public void clickFotoDurante(){
        if (ContextCompat.checkSelfPermission(that,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(that, Manifest.permission.CAMERA)) {
                tomarFotoDurante();
            } else {
                ActivityCompat.requestPermissions(that,new String[]{Manifest.permission.CAMERA}, CODE_REQ_PERMISSION);
            }
        }else{
            tomarFotoDurante();
        }
    }

    @OnClick(R.id.btnDespues)
    public void clickFotoDespues(){
        if (ContextCompat.checkSelfPermission(that,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(that, Manifest.permission.CAMERA)) {
                tomarFotoDespues();
            } else {
                ActivityCompat.requestPermissions(that,new String[]{Manifest.permission.CAMERA}, CODE_REQ_PERMISSION);
            }
        }else{
            tomarFotoDespues();
        }
    }

    private void tomarFotoAntes() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(that.getPackageManager()) != null){
            File photoFile = null;
            try {
                photoFile = mFuncs.createImageFile(that);
                photoReportAntes = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                that.showDialog(ex.getMessage());
            }
            Uri photoURI = FileProvider.getUriForFile(that,that.getApplicationContext().getPackageName()+".provider",photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(pictureIntent, CODE_PHOTO_ANTES);
        }
    }

    private void tomarFotoDurante() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(that.getPackageManager()) != null){
            File photoFile = null;
            try {
                photoFile = mFuncs.createImageFile(that);
                photoReportDurante = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                that.showDialog(ex.getMessage());
            }
            Uri photoURI = FileProvider.getUriForFile(that,that.getApplicationContext().getPackageName()+".provider",photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(pictureIntent, CODE_PHOTO_DURANTE);
        }
    }

    private void tomarFotoDespues() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(that.getPackageManager()) != null){
            File photoFile = null;
            try {
                photoFile = mFuncs.createImageFile(that);
                photoReportDespues = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                that.showDialog(ex.getMessage());
            }
            Uri photoURI = FileProvider.getUriForFile(that,that.getApplicationContext().getPackageName()+".provider",photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(pictureIntent, CODE_PHOTO_DESPUES);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CODE_PHOTO_ANTES && resultCode==-1){
            try {
                LiveData.getInstance().getLiveReport().setFotoAntes(photoReportAntes);
                mFuncs.setImageOnImageView(photoReportAntes,imgView1);
            } catch (Exception e) {
                Toast.makeText(that.getApplicationContext(), "Error al cargar la foto", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if(requestCode==CODE_PHOTO_DURANTE && resultCode==-1){
            try {
                LiveData.getInstance().getLiveReport().setFotoDurante(photoReportDurante);
                mFuncs.setImageOnImageView(photoReportDurante,imgView2);
            } catch (Exception e) {
                Toast.makeText(that.getApplicationContext(), "Error al cargar la foto", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if(requestCode==CODE_PHOTO_DESPUES && resultCode==-1){
            try {
                LiveData.getInstance().getLiveReport().setFotoDespues(photoReportDespues);
                mFuncs.setImageOnImageView(photoReportDespues,imgView3);
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
                    //tomarFoto();
                } else {
                    Toast.makeText(that.getApplicationContext(), "No se puede acceder a la camara", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }


    @OnClick(R.id.btn)
    public void clickContinuar(){
        if(photoReportAntes.equals("")){
            that.showDialog("Debes tomar una foto de antes.");
        }else if(photoReportDurante.equals("")){
            that.showDialog("Debes tomar una foto de durante.");
        }else if(photoReportDespues.equals("")){
            that.showDialog("Debes tomar una foto de después.");
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(that);
            builder.setMessage("¿Deseas continuar?")
                    .setCancelable(true)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            goNext();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void goNext(){
        MinutaFragment newFragment = new MinutaFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

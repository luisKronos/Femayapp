package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FotoCuadrillaFragment extends GenericFragment {

    MainActivity that;
    View view;

    @BindView(R.id.btnFoto)
    Button btnFoto;

    @BindView(R.id.imgView)
    ImageView imgView;


    static int CODE_PHOTO=100;
    static int CODE_PHOTO_GALERY=200;
    static final int CODE_REQ_PERMISSION=600;
    String photoReport="";
    Funcs mFuncs = new Funcs();
    Bitmap bitmap;

    public FotoCuadrillaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_foto_cuadrilla, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();


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

    private void tomarFoto( ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Abrir fotografía");
        builder.setCancelable(true);
        builder.setPositiveButton("Tomar foto", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (pictureIntent.resolveActivity(that.getPackageManager()) != null) {
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
        });
        builder.setNegativeButton("Galería", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent galeryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galeryIntent, CODE_PHOTO_GALERY);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CODE_PHOTO && resultCode==-1){
            try {
                LiveData.getInstance().getLiveReport().setFotoCuadrilla(photoReport);
                mFuncs.setImageOnImageView(photoReport,imgView);
            } catch (Exception e) {
                Toast.makeText(that.getApplicationContext(), "Error al cargar la foto", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if(requestCode==CODE_PHOTO_GALERY && resultCode==-1) {
            try {
                Uri photoURI = data.getData();

                bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), photoURI);
                imgView.setImageBitmap(bitmap);

                ByteArrayOutputStream array = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, array);
                byte[] imagenByte = array.toByteArray();
                photoReport = Base64.encodeToString(imagenByte, Base64.DEFAULT);
                LiveData.getInstance().getLiveReport().setFotoCuadrilla(photoReport);
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


    @OnClick(R.id.btn)
    public void clickContinuar(){
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

    private void goNext(){
        SeCuentaFragment newFragment = new SeCuentaFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

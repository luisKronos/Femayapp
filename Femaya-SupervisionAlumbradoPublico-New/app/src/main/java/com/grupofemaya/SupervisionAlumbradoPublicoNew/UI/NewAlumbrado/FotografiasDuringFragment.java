package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DamageDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQImageBefore;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQImageDuring;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FotografiasDuringFragment extends Fragment {

    MainActivity that;
    View view;
    Bitmap bitmap;

    RQImageDuring rqImage;

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

            LiveData.getInstance().getReportImageDuring().setIdReportAlumbrado(LiveData.getInstance().getResponseReportInit().getIdReportAlumbrado());

            rqImage = LiveData.getInstance().getReportImageDuring();
            if(LiveData.getInstance().getReportImageDuring().getFotoDurante() != null) {
                if (LiveData.getInstance().getReportImageDuring().getFotoDurante().length() < 500) {
                    rqImage.setFotoDurante(mFuncs.convierteBase64(LiveData.getInstance().getReportImageDuring().getFotoDurante()));
                } else {
                    rqImage.setFotoDurante(LiveData.getInstance().getReportImageDuring().getFotoDurante());
                }
            }
            mHandler.sendMessage(msg);
        }
    };

    @BindView(R.id.imgView2)
    ImageView imgView2;

    static int CODE_PHOTO_DURANTE=200;
    static int CODE_GALERY_PHOTO_ANTES=250;
    static final int CODE_REQ_PERMISSION=600;
    String photoReportDurante="";

    Funcs mFuncs = new Funcs();

    List<DamageDTO> listDamages;

    public FotografiasDuringFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fotografias_during, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        Bundle recuperar = getArguments();
        if (recuperar != null) {
            listDamages = (List<DamageDTO>) recuperar.getSerializable("LIST_DAMAGES");
        }

        return view;
    }

    @OnClick(R.id.btnDurante)
    public void clickFotoDurante(){
        if (ContextCompat.checkSelfPermission(that, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(that, Manifest.permission.CAMERA)) {
                tomarFotoDurante();
            } else {
                ActivityCompat.requestPermissions(that,new String[]{Manifest.permission.CAMERA}, CODE_REQ_PERMISSION);
            }
        }else{
            tomarFotoDurante();
        }
    }

    private void tomarFotoDurante() {
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
                        photoReportDurante = photoFile.getAbsolutePath();
                    } catch (IOException ex) {
                        that.showDialog(ex.getMessage());
                    }
                    Uri photoURI = FileProvider.getUriForFile(that,that.getApplicationContext().getPackageName()+".provider",photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(pictureIntent, CODE_PHOTO_DURANTE);
                }
            }
        });
        builder.setNegativeButton("Galería", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent galeryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galeryIntent, CODE_GALERY_PHOTO_ANTES);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODE_PHOTO_DURANTE && resultCode==-1){
            try {
                LiveData.getInstance().getReportImageDuring().setFotoDurante(photoReportDurante);
                mFuncs.setImageOnImageView(photoReportDurante,imgView2);
            } catch (Exception e) {
                Toast.makeText(that.getApplicationContext(), "Error al cargar la foto", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if(requestCode==CODE_GALERY_PHOTO_ANTES && resultCode==-1) {
            try {
                Uri photoURI = data.getData();

                bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), photoURI);
                imgView2.setImageBitmap(bitmap);

                ByteArrayOutputStream array = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, array);
                byte[] imagenByte = array.toByteArray();
                photoReportDurante = Base64.encodeToString(imagenByte, Base64.DEFAULT);
                LiveData.getInstance().getReportImageDuring().setFotoDurante(photoReportDurante);
            } catch (Exception e) {
                Toast.makeText(that.getApplicationContext(), "Error al cargar la foto", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CODE_REQ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tomarFotoDurante();
                } else {
                    Toast.makeText(that.getApplicationContext(), "No se puede acceder a la camara", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }


    @OnClick(R.id.btn)
    public void clickContinuar(){
        if(photoReportDurante.equals("")){
            that.showDialog("Debes agregar una foto de durante.");
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
                            prepareReq();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void prepareReq(){
        that.showProgress();
        new Thread(mMessageSender).start();
    }

    private void initRequest(){
        Repository.getInstance().requestImageDuring(rqImage, new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                that.showDialog(response.toString());
                goNext();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                that.showDialog(message);
            }
        });
    }

    private void goNext(){
        FotografiasAfterFragment newFragment = new FotografiasAfterFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle b = new Bundle();
        b.putSerializable("LIST_DAMAGES", (Serializable) listDamages);
        newFragment.setArguments(b);

        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
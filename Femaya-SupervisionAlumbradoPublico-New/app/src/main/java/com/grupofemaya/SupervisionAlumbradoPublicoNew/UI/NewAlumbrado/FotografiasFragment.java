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
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DamageDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQImageBefore;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
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

public class FotografiasFragment extends GenericFragment {

    MainActivity that;
    View view;

    RQImageBefore rqImageBefore;

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

            LiveData.getInstance().getReportImageBefore().setIdReportAlumbrado(LiveData.getInstance().getResponseReportInit().getIdReportAlumbrado());

            rqImageBefore = LiveData.getInstance().getReportImageBefore();
            if(LiveData.getInstance().getReportImageBefore().getFotoAntes() != null) {
                if (LiveData.getInstance().getReportImageBefore().getFotoAntes().length() < 500) {
                    rqImageBefore.setFotoAntes(mFuncs.convierteBase64(LiveData.getInstance().getReportImageBefore().getFotoAntes()));
                } else {
                    rqImageBefore.setFotoAntes(LiveData.getInstance().getReportImageBefore().getFotoAntes());
                }
            }
            mHandler.sendMessage(msg);
        }
    };

    static int CODE_PHOTO_ANTES=100;
    static int CODE_GALERY_PHOTO_ANTES=150;
    static final int CODE_REQ_PERMISSION=600;
    String photoReportAntes="";
    Bitmap bitmap;

    @BindView(R.id.imgView1)
    ImageView imgView1;
    List<DamageDTO> listDamages;

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

        Bundle recuperar = getArguments();
        if (recuperar != null) {
            listDamages = (List<DamageDTO>) recuperar.getSerializable("LIST_DAMAGES");
            Log.i("TAG v", listDamages.toString());
        }

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

    private void tomarFotoAntes() {
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
                        photoReportAntes = photoFile.getAbsolutePath();
                    } catch (IOException ex) {
                        that.showDialog(ex.getMessage());
                    }
                    Uri photoURI = FileProvider.getUriForFile(that, that.getApplicationContext().getPackageName() + ".provider", photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(pictureIntent, CODE_PHOTO_ANTES);
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
        if(requestCode==CODE_PHOTO_ANTES && resultCode==-1){
            try {
                LiveData.getInstance().getReportImageBefore().setFotoAntes(photoReportAntes);
                mFuncs.setImageOnImageView(photoReportAntes,imgView1);
            } catch (Exception e) {
                Toast.makeText(that.getApplicationContext(), "Error al cargar la foto", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if(requestCode==CODE_GALERY_PHOTO_ANTES && resultCode==-1) {
            try {
                Uri photoURI = data.getData();

                bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), photoURI);
                imgView1.setImageBitmap(bitmap);

                ByteArrayOutputStream array = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, array);
                byte[] imagenByte = array.toByteArray();
                photoReportAntes = Base64.encodeToString(imagenByte, Base64.DEFAULT);
                LiveData.getInstance().getReportImageBefore().setFotoAntes(photoReportAntes);
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
                    tomarFotoAntes();
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
            that.showDialog("Debes agregar una foto de antes.");
        } else {
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
        Repository.getInstance().requestImageBefore(rqImageBefore, new RepositoryImp() {
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
        FotografiasDuringFragment newFragment = new FotografiasDuringFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle b = new Bundle();
        b.putSerializable("LIST_DAMAGES", (Serializable) listDamages);
        newFragment.setArguments(b);

        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

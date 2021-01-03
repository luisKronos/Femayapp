package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.ReportAlumbDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.SignActivityContratista;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.SignActivitySupervision;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FirmasFragment extends GenericFragment {

    MainActivity that;
    View view;


    @BindView(R.id.imgViewContratista)
    ImageView imgViewContratista;

    @BindView(R.id.imgViewFirmaSupervision)
    ImageView imgViewFirmaSupervision;

    ReportAlumbDTO rqReport;

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
            rqReport = LiveData.getInstance().getLiveReport();

            if(LiveData.getInstance().getSignContratista()!=null) {
                rqReport.setFirmaContratista(mFuncs.BitmapBase64PNG(LiveData.getInstance().getSignContratista()));
            }

            if(LiveData.getInstance().getSignSupervision()!=null) {
                rqReport.setFirmaSupervision(mFuncs.BitmapBase64PNG(LiveData.getInstance().getSignSupervision()));
            }

            if(LiveData.getInstance().getLiveReport().getFotoMaterial()!=null) {
                rqReport.setFotoMaterial(mFuncs.convierteBase64(LiveData.getInstance().getLiveReport().getFotoMaterial()));
            }

            if(LiveData.getInstance().getLiveReport().getFotoAntes()!=null) {
                if(LiveData.getInstance().getLiveReport().getFotoAntes().length() < 500) {
                    rqReport.setFotoAntes(mFuncs.convierteBase64(LiveData.getInstance().getLiveReport().getFotoAntes()));
                } else {
                    rqReport.setFotoAntes(LiveData.getInstance().getLiveReport().getFotoAntes());
                }
            }

            if(LiveData.getInstance().getLiveReport().getFotoDurante()!=null) {
                if(LiveData.getInstance().getLiveReport().getFotoDurante().length() < 500) {
                    rqReport.setFotoDurante(mFuncs.convierteBase64(LiveData.getInstance().getLiveReport().getFotoDurante()));
                } else {
                    rqReport.setFotoDurante(LiveData.getInstance().getLiveReport().getFotoDurante());
                }
            }

            if(LiveData.getInstance().getLiveReport().getFotoDespues()!=null) {
                if(LiveData.getInstance().getLiveReport().getFotoDespues().length() < 500) {
                    rqReport.setFotoDespues(mFuncs.convierteBase64(LiveData.getInstance().getLiveReport().getFotoDespues()));
                } else {
                    rqReport.setFotoDespues(LiveData.getInstance().getLiveReport().getFotoDespues());
                }
            }

            mHandler.sendMessage(msg);
        }
    };



    public FirmasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_firmas, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();


        return view;
    }

    @OnClick(R.id.btnFirmaContratista)
    public void clickbtnFirmaContratista(){
        Intent intent = new Intent(that, SignActivityContratista.class);
        that.startActivity(intent);
    }

    @OnClick(R.id.btnFirmaSupervision)
    public void clickbtnFirmaSupervision(){
        Intent intent = new Intent(that, SignActivitySupervision.class);
        that.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(LiveData.getInstance().getSignSupervision()!=null){
            imgViewFirmaSupervision.setImageBitmap(LiveData.getInstance().getSignSupervision());
        }

        if(LiveData.getInstance().getSignContratista()!=null){
            imgViewContratista.setImageBitmap(LiveData.getInstance().getSignContratista());
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
        Repository.getInstance().requesReportThird(rqReport,new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                that.showDialog("Reporte actualizado");
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
        FinFolioFragment newFragment = new FinFolioFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        LiveData.getInstance().setSignSupervision(null);
        LiveData.getInstance().setSignContratista(null);

        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

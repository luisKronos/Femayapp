package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.ReportAlumbDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQSignSupervisor;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.SignActivitySupervision;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.SharedPreferencesManager;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirmaSupervisorFragment extends Fragment {

    MainActivity that;
    View view;

    @BindView(R.id.imgViewFirmaSupervision)
    ImageView imgViewFirmaSupervision;

    RQSignSupervisor rqSignSupervisor;

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
            rqSignSupervisor = LiveData.getInstance().getReportSignSupervisor();

            LiveData.getInstance().getReportSignSupervisor().setIdReportAlumbrado(LiveData.getInstance().getResponseReportInit().getIdReportAlumbrado());

            if(LiveData.getInstance().getSignSupervision() != null) {
                rqSignSupervisor.setFirmaSupervision(mFuncs.BitmapBase64PNG(LiveData.getInstance().getSignSupervision()));
            }

            mHandler.sendMessage(msg);
        }
    };


    public FirmaSupervisorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_firma_supervisor, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();
        return view;
    }

    @OnClick(R.id.btnFirmaSupervision)
    public void clickbtnFirmaSupervision(){
        Intent intent = new Intent(that, SignActivitySupervision.class);
        that.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(LiveData.getInstance().getSignSupervision() != null){
            imgViewFirmaSupervision.setImageBitmap(LiveData.getInstance().getSignSupervision());
        }
    }

    @OnClick(R.id.btn)
    public void clickContinuar(){
        if (LiveData.getInstance().getSignSupervision() == null) {
            that.showDialog("Agregue la firma del Supervisor");
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
        Repository.getInstance().requestSignSupervisor(rqSignSupervisor,new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                that.showDialog("Firma enviada satisfactoriamente");
                goNext();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                that.showDialog(message);
            }
        });
    }

    private void goNext() {
        LiveData.getInstance().setSignSupervision(null);
        if (SharedPreferencesManager.getInstance().getVisibleReport()) {
            ReporteFinalFragment newFragment = new ReporteFinalFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.content_main, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            FinFolioFragment newFragment = new FinFolioFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.content_main, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.ReportAlumbDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.GPSTracker;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FolioPDLFragment extends GenericFragment {

    MainActivity that;
    View view;

    @BindView(R.id.txtFolio)
    EditText txtFolio;

    GPSTracker gpsTracker;


    Funcs mFuncs = new Funcs();

    ReportAlumbDTO rqReport;



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
            if(LiveData.getInstance().getLiveReport().getFotoEquipo()!=null) {
                rqReport.setFotoEquipo(mFuncs.convierteBase64(LiveData.getInstance().getLiveReport().getFotoEquipo()));
            }
            mHandler.sendMessage(msg);
        }
    };




    public FolioPDLFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_folio_pdl, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        gpsTracker = new GPSTracker(that);

        return view;
    }




    @OnClick(R.id.btn)
    public void clickContinuar(){
        if(that.gpsTracker.canGetLocation() && that.gpsTracker.getLatitude() != 0.0){
            LiveData.getInstance().getLiveReport().setLat(String.valueOf(that.gpsTracker.getLatitude()));
            LiveData.getInstance().getLiveReport().setLon(String.valueOf(that.gpsTracker.getLongitude()));
            LiveData.getInstance().getLiveReport().setFolio(txtFolio.getText().toString());
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
        Repository.getInstance().requesReportSecond(rqReport,new RepositoryImp() {
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
        ListDamagesFragment newFragment = new ListDamagesFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

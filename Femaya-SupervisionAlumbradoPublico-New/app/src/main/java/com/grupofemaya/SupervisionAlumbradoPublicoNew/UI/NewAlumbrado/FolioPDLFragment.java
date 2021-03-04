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
            LiveData.getInstance().getReportInitTwo().setIdReportAlumbrado(LiveData.getInstance().getResponseReportInit().getIdReportAlumbrado());
            LiveData.getInstance().getReportInitTwo().setLon(String.valueOf(that.gpsTracker.getLongitude()));
            LiveData.getInstance().getReportInitTwo().setLat(String.valueOf(that.gpsTracker.getLatitude()));
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
                        goNext();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void goNext(){
        LiveData.getInstance().getReportInitTwo().setFolio(txtFolio.getText().toString());
        ListDamagesFragment newFragment = new ListDamagesFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

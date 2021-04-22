package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassifierEvent;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.ReportAlumbDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInit;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.FirstAlumbrado.HomeFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.SharedPreferencesManager;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SeCuentaFragment extends Fragment {

    MainActivity that;
    View view;

    RQReportInit rqInitReport;


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

            LiveData.getInstance().getReportInit().setIdUser(Integer.parseInt(SharedPreferencesManager.getInstance().getIdUser()));
            LiveData.getInstance().getReportInit().setTurno(SharedPreferencesManager.getInstance().getTurno());
            LiveData.getInstance().getReportInit().setTramo(txtTramo.getText().toString());
            LiveData.getInstance().getReportInit().setAlcaldia(txtAlcaldia.getText().toString());
            LiveData.getInstance().getReportInit().setPlacas(txtPLacas.getText().toString());
            LiveData.getInstance().getReportInit().setColonia(txtColonia.getText().toString());
            LiveData.getInstance().getReportInit().setReferencia(txtReferencia.getText().toString());
            LiveData.getInstance().getReportInit().setHr_entrada(SharedPreferencesManager.getInstance().getCheckIn());

            rqInitReport = LiveData.getInstance().getReportInit();
            mHandler.sendMessage(msg);
        }
    };

    @BindView(R.id.txtPLacas)
    EditText txtPLacas;
    @BindView(R.id.txtAlcaldia)
    EditText txtAlcaldia;
    @BindView(R.id.txtColonia)
    EditText txtColonia;
    @BindView(R.id.txtTramo)
    EditText txtTramo;
    @BindView(R.id.txtReferencia)
    EditText txtReferencia;

    public SeCuentaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_secuenta_material, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();


        return view;
    }


    @OnClick(R.id.btn)
    public void clickContinue(View view) {
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

    private void prepareReq() {
        that.showProgress();
        new Thread(mMessageSender).start();
    }


    private void initRequest() {
        Repository.getInstance().requestReportInitOne(rqInitReport, new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
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
        FolioPDLFragment newFragment = new FolioPDLFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

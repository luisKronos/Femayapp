package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.ReportAlumbDTO;
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
    Funcs mFuncs = new Funcs();

    ReportAlumbDTO rqInitReport;



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

            LiveData.getInstance().getLiveReport().setIdUser(SharedPreferencesManager.getInstance().getIdUser());
            LiveData.getInstance().getLiveReport().setTurno(SharedPreferencesManager.getInstance().getTurno());

            LiveData.getInstance().getLiveReport().setPlacas(txtPLacas.getText().toString().toUpperCase());
            LiveData.getInstance().getLiveReport().setAlcaldia(txtAlcaldia.getText().toString().toUpperCase());
            LiveData.getInstance().getLiveReport().setTramo(txtTramo.getText().toString().toUpperCase());
            LiveData.getInstance().getLiveReport().setColonia(txtColonia.getText().toString().toUpperCase());
            LiveData.getInstance().getLiveReport().setReferencia(txtReferencia.getText().toString().toUpperCase());

            rqInitReport = LiveData.getInstance().getLiveReport();
            if(LiveData.getInstance().getLiveReport().getFotoCuadrilla() != null) {
                rqInitReport.setFotoCuadrilla(mFuncs.convierteBase64(LiveData.getInstance().getLiveReport().getFotoCuadrilla()));
            }
            mHandler.sendMessage(msg);
        }
    };

    @BindView(R.id.switch1)
    Switch switch1;

    @BindView(R.id.txtAlcaldia)
    EditText txtAlcaldia;
    @BindView(R.id.txtTramo)
    EditText txtTramo;
    @BindView(R.id.txtPLacas)
    EditText txtPLacas;
    @BindView(R.id.txtColonia)
    EditText txtColonia;
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

    private void prepareReq(){
        that.showProgress();
        new Thread(mMessageSender).start();
    }


    private void initRequest(){
        Repository.getInstance().requestInitReport(rqInitReport,new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                Toast.makeText(getContext(), "Reporte guardado", Toast.LENGTH_SHORT).show();
                addOther();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                that.showDialog(message);
            }
        });
    }

    private void addOther() {
        AlertDialog.Builder builder = new AlertDialog.Builder(that);
        builder.setMessage("¿Desea agregar otra cuadrilla?")
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { goNext(); }
                })
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goAddOther();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void goAddOther() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Numero de Cuadrilla");
        View viewInflated = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_number_cuadrilla, (ViewGroup) getView(), false);
        EditText txtNumber = viewInflated.findViewById(R.id.txtNumberCuadrilla);
        builder.setView(viewInflated)
                .setCancelable(false)
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO: Evitar que se cierre el dialog cuando este vcio
                        if(!txtNumber.getText().toString().isEmpty()) {
                            LiveData.getInstance().getLiveReport().setCuadrilla(Integer.parseInt(txtNumber.getText().toString()));
                            LiveData.getInstance().getLiveReport().setIdReportAlumbradoAux(Integer.parseInt(LiveData.getInstance().getLiveReport().getIdReportAlumbrado()));
                            PersonalCuadrillasFragment newFragment = new PersonalCuadrillasFragment();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.content_main, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void goNext(){
        //if(switch1.isChecked()){
            CuadrillaObraCivilFragment newFragment = new CuadrillaObraCivilFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        //}
    }
}

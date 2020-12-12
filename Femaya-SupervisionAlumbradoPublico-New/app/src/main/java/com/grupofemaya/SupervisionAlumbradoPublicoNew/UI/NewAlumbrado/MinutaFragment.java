package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import androidx.fragment.app.FragmentTransaction;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterMaterial;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MinutaFragment extends GenericFragment {

    MainActivity that;
    View view;

    @BindView(R.id.txtTipoLuminaria)
    EditText txtTipoLuminaria;

    @BindView(R.id.txtFallaDetectada)
    EditText txtFallaDetectada;

    @BindView(R.id.diagFalla)
    EditText txtDiagFalla;

    @BindView(R.id.txtAccionRealizada)
    EditText txtAccionRealizada;

    @BindView(R.id.txtObservaciones)
    EditText txtObservaciones;

    @BindView(R.id.switchStatus)
    Switch switchStatus;




    public MinutaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_minuta, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        return view;
    }




    @OnClick(R.id.btn)
    public void clickContinuar(){
        askContinue();
    }

    private void askContinue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(that);
        builder.setMessage("¿Deseas continuar?")
                .setCancelable(true)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LiveData.getInstance().getLiveReport().getMinuta().setTipoLuminario(txtTipoLuminaria.getText().toString().toUpperCase());
                        LiveData.getInstance().getLiveReport().getMinuta().setFallaDetectada(txtFallaDetectada.getText().toString().toUpperCase());
                        LiveData.getInstance().getLiveReport().getMinuta().setDiagFalla(txtDiagFalla.getText().toString().toUpperCase());
                        LiveData.getInstance().getLiveReport().getMinuta().setAccionRealizada(txtAccionRealizada.getText().toString().toUpperCase());
                        LiveData.getInstance().getLiveReport().getMinuta().setObservaciones(txtObservaciones.getText().toString().toUpperCase());
                        LiveData.getInstance().getLiveReport().getMinuta().setEstatusReparacion(getStatus(switchStatus.isChecked()));
                        goNext();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private String getStatus(boolean status){
        if(status){
            return "COMPLETO";
        } else {
            return "INCOMPLETO";
        }
    }


    private void goNext(){
        ListMaterialFragment newFragment = new ListMaterialFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

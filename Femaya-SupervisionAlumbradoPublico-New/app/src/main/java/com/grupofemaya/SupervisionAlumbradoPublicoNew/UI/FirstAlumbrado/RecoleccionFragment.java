package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.FirstAlumbrado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSInitCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.rsGeneral;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckPickups;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecoleccionFragment extends GenericFragment implements View.OnClickListener {

    MainActivity that;
    View view;

    @BindView(R.id.txtM3Organico)
    EditText txtM3Organico;

    @BindView(R.id.txtM3Inorganico)
    EditText txtM3Inorganico;

    @BindView(R.id.txtM3Tierra)
    EditText txtM3Tierra;

    @BindView(R.id.txtPropaganda)
    EditText txtPropaganda;

    @BindView(R.id.txtObjMayores)
    EditText txtObjMayores;

    @BindView(R.id.txtOrganismos)
    EditText txtOrganismos;


    @BindView(R.id.btnFinish)
    Button btnFinish;


    Funcs mFuncs = new Funcs();


    public RecoleccionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recoleccion, container, false);
        that = (MainActivity) getActivity();
        ButterKnife.bind(this, view);

        btnFinish.setOnClickListener(this);
        return view;
    }




    @Override
    public void onClick(View v) {
        if(v==btnFinish){
            finishPersonal();
        }
    }


    private void finishPersonal(){
        if(txtM3Organico.getText().toString().equals("Faltan los m3 de Material Orgánico")){
            that.showDialog("");
        }else if(txtM3Inorganico.getText().toString().equals("Faltan los m3 de Material Inorgánico")){
            that.showDialog("");
        }else if(txtM3Tierra.getText().toString().equals("Faltan los m3 de Tierra")){
            that.showDialog("");
        }else if(txtPropaganda.getText().toString().equals("Faltan los m3 de propaganda")){
            that.showDialog("");
        }else if(txtObjMayores.getText().toString().equals("Faltan los m3 de objetos mayores")){
            that.showDialog("");
        }else if(txtOrganismos.getText().toString().equals("Faltan los m3 de organismos")){
            that.showDialog("");
        }else{
            requestFinish();
        }
    }


    private void requestFinish(){
        that.showProgress();

        RQCheckPickups request = new RQCheckPickups();
        request.setIdCheck(LiveData.getInstance().getIdCheck());
        request.setM3Inorganico(txtM3Inorganico.getText().toString());
        request.setM3Organico(txtM3Organico.getText().toString());
        request.setM3Tierra(txtM3Tierra.getText().toString());
        request.setObjMayores(txtObjMayores.getText().toString());
        request.setPropaganda(txtPropaganda.getText().toString());
        request.setOrganismos(txtOrganismos.getText().toString());

        Repository.getInstance().requestCheckPickups(request,new RepositoryImp() {
                @Override
                public void succedResponse(Object response) {
                    that.hideProgress();
                    rsGeneral<RSInitCheck> res = (rsGeneral<RSInitCheck>) response;
                    AlertDialog.Builder builder = new AlertDialog.Builder(that);
                    builder.setMessage(res.getHeader().getMessage())
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    backHome();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                @Override
                public void requestFail(String message) {
                    that.hideProgress();
                    showDialog(message);
                }
            });
    }


}

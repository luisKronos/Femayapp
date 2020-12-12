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
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckTools;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ToolsFragment extends GenericFragment implements View.OnClickListener {

    MainActivity that;
    View view;

    @BindView(R.id.txtEscobasMijo)
    EditText txtEscobasMijo;

    @BindView(R.id.txtEscobasVara)
    EditText txtEscobasVara;

    @BindView(R.id.txtCarritos)
    EditText txtCarritos;

    @BindView(R.id.txtPalasCarboneras)
    EditText txtPalasCarboneras;

    @BindView(R.id.txtPalasPlanas)
    EditText txtPalasPlanas;

    @BindView(R.id.txtExtensiones)
    EditText txtExtensiones;


    @BindView(R.id.btnFinish)
    Button btnFinish;


    Funcs mFuncs = new Funcs();


    public ToolsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tools, container, false);
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
        if(txtEscobasMijo.getText().toString().equals("")){
            that.showDialog("");
        }else if(txtEscobasVara.getText().toString().equals("")){
            that.showDialog("");
        }else if(txtCarritos.getText().toString().equals("")){
            that.showDialog("");
        }else if(txtPalasCarboneras.getText().toString().equals("")){
            that.showDialog("");
        }else if(txtPalasPlanas.getText().toString().equals("")){
            that.showDialog("");
        }else if(txtExtensiones.getText().toString().equals("")){
            that.showDialog("");
        }else{
            requestFinish();
        }
    }


    private void requestFinish(){
        that.showProgress();
        RQCheckTools rqCheckTools = new RQCheckTools();
        rqCheckTools.setCarritos(txtCarritos.getText().toString());
        rqCheckTools.setEscobasMijo(txtEscobasMijo.getText().toString());
        rqCheckTools.setEscobasVara(txtEscobasVara.getText().toString());
        rqCheckTools.setExtensiones(txtExtensiones.getText().toString());
        rqCheckTools.setPalasCarboneras(txtPalasCarboneras.getText().toString());
        rqCheckTools.setPalasPlanas(txtPalasPlanas.getText().toString());
        rqCheckTools.setIdCheck(LiveData.getInstance().getIdCheck());

        Repository.getInstance().requestCheckTools(rqCheckTools,new RepositoryImp() {
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

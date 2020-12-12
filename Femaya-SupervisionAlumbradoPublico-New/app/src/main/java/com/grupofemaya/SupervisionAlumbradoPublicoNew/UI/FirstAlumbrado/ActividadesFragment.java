package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.FirstAlumbrado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckActividades;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSInitCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.rsGeneral;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActividadesFragment extends GenericFragment implements View.OnClickListener {

    MainActivity that;
    View view;

    @BindView(R.id.spinner1)
    Spinner spinner1;

    @BindView(R.id.spinner2)
    Spinner spinner2;

    @BindView(R.id.spinner3)
    Spinner spinner3;

    @BindView(R.id.spinner4)
    Spinner spinner4;

    @BindView(R.id.spinner5)
    Spinner spinner5;

    @BindView(R.id.spinner6)
    Spinner spinner6;

    @BindView(R.id.spinner7)
    Spinner spinner7;

    @BindView(R.id.spinner8)
    Spinner spinner8;


    @BindView(R.id.btnFinish)
    Button btnFinish;


    Funcs mFuncs = new Funcs();

    String barridoRustico="";
    String barridoFino="";
    String papeleo="";
    String propaganda="";
    String recTiraderos="";
    String recTierra="";
    String retiroMayores="";
    String retiroBrote="";


    public ActividadesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_actividades, container, false);
        that = (MainActivity) getActivity();
        ButterKnife.bind(this, view);

        btnFinish.setOnClickListener(this);
        fillSpinners();
        return view;
    }


    private void fillSpinners(){


        final List<String> opcionesArray = new ArrayList<>();
        opcionesArray.add("Selecciona una opción");
        opcionesArray.add("REALIZADA");
        opcionesArray.add("NO REALIZADA");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(that, android.R.layout.simple_spinner_item, opcionesArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner1.setAdapter(dataAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    if(position==1){
                        barridoRustico="1";
                    }else{
                        barridoRustico="0";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setAdapter(dataAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    if(position==1){
                        barridoFino="1";
                    }else{
                        barridoFino="0";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner3.setAdapter(dataAdapter);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    if(position==1){
                        papeleo="1";
                    }else{
                        papeleo="0";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner4.setAdapter(dataAdapter);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    if(position==1){
                        propaganda="1";
                    }else{
                        propaganda="0";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner5.setAdapter(dataAdapter);
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    if(position==1){
                        recTiraderos="1";
                    }else{
                        recTiraderos="0";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner6.setAdapter(dataAdapter);
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    if(position==1){
                        recTierra="1";
                    }else{
                        recTierra="0";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner7.setAdapter(dataAdapter);
        spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    if(position==1){
                        retiroMayores="1";
                    }else{
                        retiroMayores="0";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner8.setAdapter(dataAdapter);
        spinner8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    if(position==1){
                        retiroBrote="1";
                    }else{
                        retiroBrote="0";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==btnFinish){
            goSave();
        }
    }


    private void goSave(){
        if(barridoFino.equals("")){
            that.showDialog("Debes llenar todos los campos");
        }if(barridoRustico.equals("")){
            that.showDialog("Debes llenar todos los campos");
        }if(papeleo.equals("")){
            that.showDialog("Debes llenar todos los campos");
        }if(propaganda.equals("")){
            that.showDialog("Debes llenar todos los campos");
        }if(recTiraderos.equals("")){
            that.showDialog("Debes llenar todos los campos");
        }if(recTierra.equals("")){
            that.showDialog("Debes llenar todos los campos");
        }if(retiroMayores.equals("")){
            that.showDialog("Debes llenar todos los campos");
        }if(retiroBrote.equals("")){
            that.showDialog("Debes llenar todos los campos");
        }else{
            requestSave();
        }
    }


    private void requestSave(){
        that.showProgress();

        RQCheckActividades request = new RQCheckActividades();
        request.setIdCheck(LiveData.getInstance().getIdCheck());
        request.setBarridoRustico(barridoRustico);
        request.setBarridoFino(barridoFino);
        request.setPapeleo(papeleo);
        request.setPropaganda(propaganda);
        request.setRecTiraderos(recTiraderos);
        request.setRecTierra(recTierra);
        request.setRetiroMayores(retiroMayores);
        request.setRetiroBrote(retiroBrote);


        Repository.getInstance().requestCheckActividades(request,new RepositoryImp() {
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

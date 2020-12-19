package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CuadrillaObraCivilCheksFragment extends Fragment {

    MainActivity that;
    View view;

    @BindView(R.id.check0)
    CheckBox check0;

    @BindView(R.id.check1)
    CheckBox check1;

    @BindView(R.id.check2)
    CheckBox check2;

    @BindView(R.id.check3)
    CheckBox check3;

    @BindView(R.id.check4)
    CheckBox check4;

    @BindView(R.id.check5)
    CheckBox check5;

    @BindView(R.id.check6)
    CheckBox check6;

    @BindView(R.id.check7)
    CheckBox check7;

    @BindView(R.id.check8)
    CheckBox check8;

    @BindView(R.id.check9)
    CheckBox check9;

    @BindView(R.id.check10)
    CheckBox check10;

    @BindView(R.id.check11)
    CheckBox check11;





    public CuadrillaObraCivilCheksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cuadrilla_civil_list, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        check0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setCaboCuadrilla(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setCaboCuadrilla(0);
                                                  }
                                              }
                                          }
        );

        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    if(isChecked){
                        LiveData.getInstance().getLiveReport().getListCuadrilla().setAlbanil(1);
                    }else {
                        LiveData.getInstance().getLiveReport().getListCuadrilla().setAlbanil(0);
                    }
                }
            }
        );

        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    if(isChecked){
                        LiveData.getInstance().getLiveReport().getListCuadrilla().setAyudanteDeOficial(1);
                    }else {
                        LiveData.getInstance().getLiveReport().getListCuadrilla().setAyudanteDeOficial(0);
                    }
                }
            }
        );

        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setHerreroDeTaller(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setHerreroDeTaller(0);
                                                  }
                                              }
                                          }
        );


        check4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setAyudanteDeHerrero(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setAyudanteDeHerrero(0);
                                                  }
                                              }
                                          }
        );

        check5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setCabodeAlbanileria(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setCabodeAlbanileria(0);
                                                  }
                                              }
                                          }
        );

        check6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setCaboDeHerreria(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setCaboDeHerreria(0);
                                                  }
                                              }
                                          }
        );


        check7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setOperadorDeVehiculo(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setOperadorDeVehiculo(0);
                                                  }
                                              }
                                          }
        );

        check8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setOperadorDeCopresor(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setOperadorDeCopresor(0);
                                                  }
                                              }
                                          }
        );


        check9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setOperadorDeCortadora(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setOperadorDeCortadora(0);
                                                  }
                                              }
                                          }
        );

        check10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setOperadorDeRevolvedora(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setOperadorDeRevolvedora(0);
                                                  }
                                              }
                                          }
        );


        check11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setOperadorDeOxiacetileno(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListCuadrilla().setOperadorDeOxiacetileno(0);
                                                  }
                                              }
                                          }
        );



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
                        goNext();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void goNext(){
        CuadrillaHerramientaEquipoCheksFragment newFragment = new CuadrillaHerramientaEquipoCheksFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

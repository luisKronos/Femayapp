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


public class CuadrillaHerramientaEquipoCheksFragment extends Fragment {

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




    public CuadrillaHerramientaEquipoCheksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_equipo_herramientas_list, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        check0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setUniforme(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setUniforme(0);
                                                  }
                                              }
                                          }
        );

        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setCamion(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setCamion(0);
                                                  }
                                              }
                                          }
        );


        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setCamioneta(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setCamioneta(0);
                                                  }
                                              }
                                          }
        );


        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setCompresor(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setCompresor(0);
                                                  }
                                              }
                                          }
        );


        check4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setSoldadora(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setSoldadora(0);
                                                  }
                                              }
                                          }
        );



        check5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setEquipoCorte(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setEquipoCorte(0);
                                                  }
                                              }
                                          }
        );



        check6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setMezcladora(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setMezcladora(0);
                                                  }
                                              }
                                          }
        );



        check7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setEquipoSeguridad(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setEquipoSeguridad(0);
                                                  }
                                              }
                                          }
        );


        check8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                  if(isChecked){
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setSenalamiento(1);
                                                  }else {
                                                      LiveData.getInstance().getLiveReport().getListEquipo().setSenalamiento(0);
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
        FotoHerramientaEquipoFragment newFragment = new FotoHerramientaEquipoFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

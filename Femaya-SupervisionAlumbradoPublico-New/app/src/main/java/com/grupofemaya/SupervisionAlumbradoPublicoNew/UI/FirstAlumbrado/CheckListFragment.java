package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.FirstAlumbrado;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQStatusCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSStatusCheck;
import org.grupofemaya.SupervisionAlumbradoPublico.R;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CheckListFragment extends GenericFragment implements View.OnClickListener {

    MainActivity that;
    View view;


    @BindView(R.id.btnPersonal)
    Button btnPersonal;
    @BindView(R.id.btnVehiculos)
    Button btnVehiculos;
    @BindView(R.id.btnHerramienta)
    Button btnHerramienta;
    @BindView(R.id.btnRecoleccion)
    Button btnRecoleccion;
    @BindView(R.id.btnDeductivas)
    Button btnDeductivas;
    @BindView(R.id.btnPorcentaje)
    Button btnPorcentaje;
    @BindView(R.id.btnActivities)
    Button btnActivities;



    public CheckListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_check_list, container, false);
        that = (MainActivity) getActivity();
        ButterKnife.bind(this, view);
        getStatusCheck();


        btnPersonal.setOnClickListener(this);
        btnVehiculos.setOnClickListener(this);
        btnHerramienta.setOnClickListener(this);
        btnRecoleccion.setOnClickListener(this);
        btnDeductivas.setOnClickListener(this);
        btnPorcentaje.setOnClickListener(this);
        btnActivities.setOnClickListener(this);

        return view;
    }


    private void getStatusCheck(){
        that.showProgress();
        RQStatusCheck request = new RQStatusCheck();
        request.setIdCheck(LiveData.getInstance().getIdCheck());
        Repository.getInstance().requestStatusCheck(request,new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                List<RSStatusCheck> listResponse = (List<RSStatusCheck>)response;
                LiveData.getInstance().setCurrentCheck(listResponse.get(0));
                fillButtons();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                showDialog(message);
            }
        });
    }

    private void fillButtons(){
        btnPersonal.setEnabled(isChecked(LiveData.getInstance().getCurrentCheck().getIsPersonal()));
        btnVehiculos.setEnabled(isChecked(LiveData.getInstance().getCurrentCheck().getIsCars()));
        btnHerramienta.setEnabled(isChecked(LiveData.getInstance().getCurrentCheck().getIsTools()));
        btnRecoleccion.setEnabled(isChecked(LiveData.getInstance().getCurrentCheck().getIsPickups()));
        btnDeductivas.setEnabled(isChecked(LiveData.getInstance().getCurrentCheck().getIsDeductives()));
        btnPorcentaje.setEnabled(isChecked(LiveData.getInstance().getCurrentCheck().getIsAdvance()));
        btnActivities.setEnabled(isChecked(LiveData.getInstance().getCurrentCheck().getIsActivities()));
    }


    private boolean isChecked(int value){
        if(value==1){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        if(v==btnPersonal){
            PersonalFragment newFragment = new PersonalFragment();
            FragmentTransaction transaction = that.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(v==btnVehiculos){
            VehiculosFragment newFragment = new VehiculosFragment();
            FragmentTransaction transaction = that.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(v==btnHerramienta){
            ToolsFragment newFragment = new ToolsFragment();
            FragmentTransaction transaction = that.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(v==btnRecoleccion){
            RecoleccionFragment newFragment = new RecoleccionFragment();
            FragmentTransaction transaction = that.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(v==btnDeductivas){
            DeductivasFragment newFragment = new DeductivasFragment();
            FragmentTransaction transaction = that.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(v==btnPorcentaje){
            AvanceFragment newFragment = new AvanceFragment();
            FragmentTransaction transaction = that.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(v==btnActivities){
            ActividadesFragment newFragment = new ActividadesFragment();
            FragmentTransaction transaction = that.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}

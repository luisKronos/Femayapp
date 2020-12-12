package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.FirstAlumbrado;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQInitCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSInitCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSRoad;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSSector;
import org.grupofemaya.SupervisionAlumbradoPublico.R;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class InitCheckListFragment extends GenericFragment {

    MainActivity that;

    List<RSSector> listaSectores = new ArrayList<>();
    List<RSRoad> listaRoads = new ArrayList<>();
    View view;

    @BindView(R.id.spinnerSector)
    Spinner spinnerSector;

    @BindView(R.id.spinnerVialidad)
    Spinner spinnerVialidad;

    @BindView(R.id.spinnerTurno)
    Spinner spinnerTurno;

    @BindView(R.id.txtEmpresa)
    TextView txtEmpresa;

    @BindView(R.id.btnContinue)
    Button btnContinue;

    String idRoad="";
    RSRoad roadSelected = null;
    String turno="";


    public InitCheckListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inist_check_list, container, false);
        that = (MainActivity) getActivity();
        ButterKnife.bind(this, view);
        getSectors();
        btnContinue.setEnabled(false);
        LiveData.getInstance().getVehiculos().clear();
        return view;
    }


    private void getSectors(){
        that.showProgress();
        Repository.getInstance().requestSectors(new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                listaSectores = (List<RSSector>) response;
                fillSectores();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                showDialog(message);
            }
        });
    }


    private void getRoadsBySector(String idSector){
        that.showProgress();

        Repository.getInstance().requestRoadsBySector(idSector,new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                listaRoads = (List<RSRoad>) response;
                fillRoads();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                showDialog(message);
            }
        });
    }

    private void fillSectores(){
        List<String> sectoresArray = new ArrayList<>();
        sectoresArray.add("Selecciona una opción");
        for(RSSector item:listaSectores){
            sectoresArray.add(item.getSector());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, sectoresArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSector.setAdapter(dataAdapter);
        spinnerSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    int realPos = position-1;
                    getRoadsBySector(listaSectores.get(realPos).getIdSector());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fillRoads(){
        List<String> roadsArray = new ArrayList<>();
        roadsArray.add("Selecciona una opción");
        for(RSRoad item:listaRoads){
            roadsArray.add(item.getRoad());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, roadsArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVialidad.setAdapter(dataAdapter);
        spinnerVialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    int realPos = position-1;
                    txtEmpresa.setText(listaRoads.get(realPos).getEnterprise());
                    idRoad = listaRoads.get(realPos).getIdRoad();
                    roadSelected = listaRoads.get(realPos);
                    fillTurnos();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void fillTurnos(){
        final List<String> turnosArray = new ArrayList<>();
        turnosArray.add("Selecciona una opción");

        if(roadSelected.getpMat()>0) {
            turnosArray.add("MATUTINO");
        }

        if(roadSelected.getpVesp()>0) {
            turnosArray.add("VESPERTINO");
        }

        if(roadSelected.getpNoct()>0) {
            turnosArray.add("NOCTURNO");
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, turnosArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTurno.setAdapter(dataAdapter);
        spinnerTurno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    btnContinue.setEnabled(true);
                    turno = turnosArray.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @OnClick(R.id.btnContinue)
    public void clickInit(View view) {
        initRQCheck();
    }

    private void initRQCheck(){
        RQInitCheck rqInitCheck = new RQInitCheck();
        rqInitCheck.setIdUser(SharedPreferencesManager.getInstance().getIdUser());
        rqInitCheck.setIdRoad(idRoad);
        rqInitCheck.setLatitude(String.valueOf(that.gpsTracker.getLatitude()));
        rqInitCheck.setLongitude(String.valueOf(that.gpsTracker.getLongitude()));
        rqInitCheck.setTurno(turno);
        Repository.getInstance().requestInitCheck(rqInitCheck,new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                RSInitCheck restCheck = (RSInitCheck)response;
                LiveData.getInstance().setIdCheck(restCheck.getIdCheck());
                gotCheckList();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                showDialog(message);
            }
        });

    }

    private void gotCheckList(){
        CheckListFragment fragment = new CheckListFragment();
        FragmentManager manager = that.getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, fragment,
                fragment.getTag()).commit();
    }

}

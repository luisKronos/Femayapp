package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.Actividad;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.Causa;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.Diagnostico;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MinutaDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.ReportAlumbDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInitThree;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInitTwo;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSReportInitOne;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterMaterial;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.SharedPreferencesManager;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MinutaFragment extends GenericFragment {

    MainActivity that;
    View view;
    RQReportInitThree rqReportThree;
    MinutaDTO minuta;

    ArrayList<String> arrayCausa = new ArrayList<>();
    ArrayList<String> arrayDiagnostico = new ArrayList<>();
    ArrayList<String> arrayActividad = new ArrayList<>();

    boolean flagDiagnostico = false;
    boolean flagActividad = false;

    String aux = "";
    String textDiagnostico = "";
    String textActividad = "";
    String folio = "";

    @BindView(R.id.txtTipoLuminaria)
    EditText txtTipoLuminaria;

    @BindView(R.id.diagFalla)
    Spinner spinnerDiagFalla;

    @BindView(R.id.txtAccionRealizada)
    Spinner spinnerActividadRealizada;

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

        arrayCausa.clear();
        arrayActividad.clear();
        arrayDiagnostico.clear();
        flagDiagnostico = false;
        flagActividad = false;

        getDiagnostico();
        getActividades();
        getCausas();

        checkSpinnerDiagnostico();
        checkSpinnerActividad();

        return view;
    }


    private void getDiagnostico(){
        that.showProgress();
        Repository.getInstance().requestGetDiagnostico(new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                fillDataDiagnostico();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                that.showDialog(message);
            }
        });
    }

    private void fillDataDiagnostico(){
        for (Diagnostico item : LiveData.getInstance().getListDiagnostico()) {
            arrayDiagnostico.add(item.getDiagnostico());
        }

        ArrayAdapter<String> adapterDiagnostico= new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, arrayDiagnostico);
        adapterDiagnostico.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiagFalla.setAdapter(adapterDiagnostico);
    }

    private void getActividades(){
        that.showProgress();
        Repository.getInstance().requestGetActividades(new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                fillDataActividades();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                that.showDialog(message);
            }
        });
    }

    private void fillDataActividades(){
        for (Actividad item : LiveData.getInstance().getListActividad()) {
            arrayActividad.add(item.getActividad());
        }

        ArrayAdapter<String> adapterActividad= new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, arrayActividad);
        adapterActividad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActividadRealizada.setAdapter(adapterActividad);
    }

    @OnClick(R.id.btn)
    public void clickContinuar() {
        if (!flagDiagnostico) {
            that.showDialog("Seleccione diagnostico de la falla");
        } else if (!flagActividad) {
            that.showDialog("Seleccione una actividad realizada");
        } else if (flagActividad && flagDiagnostico) {
            askContinue();
        }
    }

    private void askContinue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(that);
        builder.setMessage("¿Deseas continuar?")
                .setCancelable(true)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        checkSwitch();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void checkSpinnerDiagnostico() {
        spinnerDiagFalla.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textDiagnostico = parent.getItemAtPosition(position).toString();
                //TODO: Cambiar valor por defecto
                if(textDiagnostico.equals("TUS PRUEBAS")) {
                    flagDiagnostico = false;
                } else {
                    LiveData.getInstance().getInfoMinuta().setDiagFalla(textDiagnostico);
                    flagDiagnostico = true;
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) { }
        });
    }

    private void checkSpinnerActividad() {
        spinnerActividadRealizada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textActividad = parent.getItemAtPosition(position).toString();
                //TODO: Cambiar valor por defecto
                if(textActividad.equals("Actividad5")) {
                    flagActividad = false;
                } else {
                    LiveData.getInstance().getInfoMinuta().setAccionRealizada(textActividad);
                    flagActividad = true;
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) { }
        });
    }

    private void getCausas(){
        that.showProgress();
        Repository.getInstance().requestGetCausas(new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                fillDataCausa();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                that.showDialog(message);
            }
        });
    }

    private void fillDataCausa() {
        for (Causa item : LiveData.getInstance().getListCausa()) {
            arrayCausa.add(item.getCausa());
        }
    }

    private void checkSwitch() {
        if (!switchStatus.isChecked()) {
            SharedPreferencesManager.getInstance().setVisibleReport(false);

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Seleccionar causa:");

            View viewInflated = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_caso_incompleto, (ViewGroup) getView(), false);
            Spinner spinnerCausas = viewInflated.findViewById(R.id.spinnerCausas);
            ArrayAdapter<String> adapter= new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, arrayCausa);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCausas.setAdapter(adapter);

            builder.setView(viewInflated)
                    .setCancelable(false)
                    .setPositiveButton("Agregar", (dialog, id) -> {
                        that.showProgress();

                        LiveData.getInstance().getInfoMinuta().setTipoLuminario(txtTipoLuminaria.getText().toString());
                        LiveData.getInstance().getInfoMinuta().setEstatusReparacion(getStatus(switchStatus.isChecked()));
                        LiveData.getInstance().getInfoMinuta().setObservaciones(txtObservaciones.getText().toString());
                        LiveData.getInstance().getInfoMinuta().setCausaNoCompletado(aux);

                        minuta = LiveData.getInstance().getInfoMinuta();

                        LiveData.getInstance().getReportInitThree().setIdReportAlumbrado(LiveData.getInstance().getResponseReportInit().getIdReportAlumbrado());
                        LiveData.getInstance().getReportInitThree().setMinuta(minuta);

                        rqReportThree = LiveData.getInstance().getReportInitThree();

                        Repository.getInstance().requestReportInitThree(rqReportThree, new RepositoryImp() {
                            @Override
                            public void succedResponse(Object response) {
                                that.hideProgress();

                                switch (LiveData.getInstance().getReportInit().getIdCuadrante()) {
                                    case 1:
                                        folio = "NP";
                                        break;
                                    case 2:
                                        folio = "NO";
                                        break;
                                    case 3:
                                        folio = "SP";
                                        break;
                                    case 4:
                                        folio = "SO";
                                        break;
                                    case 5:
                                        folio = "CT";
                                        break;
                                }

                                folio = folio + "-" + LiveData.getInstance().getReportInitTwo().getIdReportAlumbrado();

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(that);
                                builder2.setTitle("Folio para consulta");
                                builder2.setMessage("Anota el siguiente folio para proximas consultas: " + folio)
                                        .setCancelable(false)
                                        .setPositiveButton("OK", (dialog1, id1) -> goHome());
                                AlertDialog alert2 = builder2.create();
                                alert2.show();
                            }

                            @Override
                            public void requestFail(String message) {
                                that.hideProgress();
                                that.showDialog(message);
                            }
                        });
                    });

            AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

            spinnerCausas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    aux = parent.getItemAtPosition(position).toString();
                    if(aux.equals("Test")) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    } else {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView <?> parent) { }
            });
        } else {
            SharedPreferencesManager.getInstance().setVisibleReport(true);

            that.showProgress();

            LiveData.getInstance().getInfoMinuta().setTipoLuminario(txtTipoLuminaria.getText().toString());
            LiveData.getInstance().getInfoMinuta().setEstatusReparacion(getStatus(switchStatus.isChecked()));
            LiveData.getInstance().getInfoMinuta().setObservaciones(txtObservaciones.getText().toString());
            LiveData.getInstance().getInfoMinuta().setCausaNoCompletado(aux);

            minuta = LiveData.getInstance().getInfoMinuta();

            LiveData.getInstance().getReportInitThree().setIdReportAlumbrado(LiveData.getInstance().getResponseReportInit().getIdReportAlumbrado());
            LiveData.getInstance().getReportInitThree().setMinuta(minuta);

            rqReportThree = LiveData.getInstance().getReportInitThree();

            Repository.getInstance().requestReportInitThree(rqReportThree, new RepositoryImp() {
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
    }

    private void goHome() {
        arrayCausa.clear();
        arrayActividad.clear();
        arrayDiagnostico.clear();
        flagDiagnostico = false;
        flagActividad = false;

        NewHomeFragment newHomeFragment = new NewHomeFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newHomeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private String getStatus(boolean status){
        if(status){
            return "Completo";
        } else {
            return "Incompleto";
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

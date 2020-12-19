package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MinutaFragment extends GenericFragment {

    MainActivity that;
    View view;
    ArrayList<String> arrayList = new ArrayList<>();
    String aux = "";

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

        arrayList.add("Selecciona una causa");
        arrayList.add("Falta de material");
        arrayList.add("Tránsito vial");
        arrayList.add("Cambio de turno");
        arrayList.add("Lo impiden comercios informales");
        arrayList.add("Obstrucción de tránsito");
        arrayList.add("Bajo o nulo nivel de seguridad");
        arrayList.add("Por indicación de SSC");
        arrayList.add("Obras externas impiden labores");

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
                        checkSwitch();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void checkSwitch() {
        if (!switchStatus.isChecked()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Seleccionar causa:");

            View viewInflated = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_caso_incompleto, (ViewGroup) getView(), false);
            Spinner spinnerCausas = viewInflated.findViewById(R.id.spinnerCausas);
            ArrayAdapter<String> adapter= new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, arrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCausas.setAdapter(adapter);

            builder.setView(viewInflated)
                    .setCancelable(false)
                    .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            LiveData.getInstance().getLiveReport().getMinuta().setTipoLuminario(txtTipoLuminaria.getText().toString().toUpperCase());
                            LiveData.getInstance().getLiveReport().getMinuta().setFallaDetectada(txtFallaDetectada.getText().toString().toUpperCase());
                            LiveData.getInstance().getLiveReport().getMinuta().setDiagFalla(txtDiagFalla.getText().toString().toUpperCase());
                            LiveData.getInstance().getLiveReport().getMinuta().setAccionRealizada(txtAccionRealizada.getText().toString().toUpperCase());
                            LiveData.getInstance().getLiveReport().getMinuta().setObservaciones(txtObservaciones.getText().toString().toUpperCase());
                            LiveData.getInstance().getLiveReport().getMinuta().setEstatusReparacion(getStatus(switchStatus.isChecked()));
                            LiveData.getInstance().getLiveReport().getMinuta().setCausaNoCompletado(aux);
                            // TODO: Mnadar a llamar el ultimo request
                            goHome();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

            spinnerCausas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // TODO: Evitar que se selecione la primera opcion
                    aux = parent.getItemAtPosition(position).toString();
                    if(aux.equals("Selecciona una causa")) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    } else {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView <?> parent) { }
            });
        } else {
            LiveData.getInstance().getLiveReport().getMinuta().setTipoLuminario(txtTipoLuminaria.getText().toString().toUpperCase());
            LiveData.getInstance().getLiveReport().getMinuta().setFallaDetectada(txtFallaDetectada.getText().toString().toUpperCase());
            LiveData.getInstance().getLiveReport().getMinuta().setDiagFalla(txtDiagFalla.getText().toString().toUpperCase());
            LiveData.getInstance().getLiveReport().getMinuta().setAccionRealizada(txtAccionRealizada.getText().toString().toUpperCase());
            LiveData.getInstance().getLiveReport().getMinuta().setObservaciones(txtObservaciones.getText().toString().toUpperCase());
            LiveData.getInstance().getLiveReport().getMinuta().setEstatusReparacion(getStatus(switchStatus.isChecked()));
            goNext();
        }
    }

    private void goHome() {
        NewHomeFragment newHomeFragment = new NewHomeFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newHomeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

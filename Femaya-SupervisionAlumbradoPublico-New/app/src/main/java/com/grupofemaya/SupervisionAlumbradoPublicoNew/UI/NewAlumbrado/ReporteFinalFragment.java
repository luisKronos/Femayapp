package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DamageDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MaterialDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MaterialNew;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MinutaDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.VialidadDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQImageMaterialUsed;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQMaterialUsed;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQNotas;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInit;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInitThree;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInitTwo;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSIdCuadrillas;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReporteFinalFragment extends Fragment {

    MainActivity that;
    View view;

    StringBuilder damage = new StringBuilder("Fallas detectadas: \n");
    StringBuilder material = new StringBuilder("Lista material: \n");
    StringBuilder cuadrillas = new StringBuilder("Cuadrillas:  \n");

    RQReportInit reportInit = LiveData.getInstance().getReportInit();
    RQReportInitTwo reportInitTwo = LiveData.getInstance().getReportInitTwo();
//    List<DamageDTO> reportListDamage = LiveData.getInstance().getListD();
    List<DamageDTO> reportListDamage = LiveData.getInstance().getReportInitTwo().getListDamages();
    RQReportInitThree reportInitThree = LiveData.getInstance().getReportInitThree();
    MinutaDTO reportMinuta = reportInitThree.getMinuta();
//    List<MaterialNew> reportListMaterialUsed = LiveData.getInstance().getListM();
    List<MaterialNew> reportListMaterialUsed = LiveData.getInstance().getReportMaterialUsed().getListaMaterial();
    RQImageMaterialUsed reportImageMaterialUsed = LiveData.getInstance().getReportImageMaterialUsed();
    RQNotas reportNotas = LiveData.getInstance().getReportNotas();

    @BindView(R.id.txtIdUser)
    TextView txtIdUser;

    @BindView(R.id.txtIdCuadrante)
    TextView txtIdCuadrante;

    @BindView(R.id.txtIdCuadrillas)
    TextView txtIdCuadrillas;

    @BindView(R.id.txtTramo)
    TextView txtTramo;

    @BindView(R.id.txtAlcaldia)
    TextView txtAlcaldia;

    @BindView(R.id.txtPlacas)
    TextView txtPlacas;

    @BindView(R.id.txtTurno)
    TextView txtTurno;

    @BindView(R.id.txtColonia)
    TextView txtColonia;

    @BindView(R.id.txtReferencia)
    TextView txtReferencia;

    @BindView(R.id.txtHrEntrada)
    TextView txtHrEntrada;

    @BindView(R.id.txtValue)
    TextView txtValue;

    @BindView(R.id.txtValueForOtro)
    TextView txtValueForOtro;

    @BindView(R.id.txtIdReportAlumbrado)
    TextView txtIdReportAlumbrado;

    @BindView(R.id.txtFolio)
    TextView txtFolio;

    @BindView(R.id.txtListDamages)
    TextView txtListDamages;

    @BindView(R.id.txtTipoLuminario)
    TextView txtTipoLuminario;

    @BindView(R.id.txtDiagFalla)
    TextView txtDiagFalla;

    @BindView(R.id.txtAccionRealizada)
    TextView txtAccionRealizada;

    @BindView(R.id.txtObservaciones)
    TextView txtObservaciones;

    @BindView(R.id.txtListaMaterial)
    TextView txtListaMaterial;

    @BindView(R.id.txtObsMaterial)
    TextView txtObsMaterial;

    @BindView(R.id.txtNotas)
    TextView txtNotas;

    public ReporteFinalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reporte_final, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        txtIdUser.setText("Id Usuario: " + reportInit.getIdUser());
        switch (reportInit.getIdCuadrante()) {
            case 1:
                txtIdCuadrante.setText("Cuadrante: Norponiente");
                break;
            case 2:
                txtIdCuadrante.setText("Cuadrante: Nororiente");
                break;
            case 3:
                txtIdCuadrante.setText("Cuadrante: Surponiente");
                break;
            case 4:
                txtIdCuadrante.setText("Cuadrante: Suroriente");
                break;
            case 5:
                txtIdCuadrante.setText("Cuadrante: Centro");
                break;
        }

        if (!reportInit.getValue().isEmpty()) {
            txtValue.setVisibility(View.VISIBLE);
            txtValue.setText("Vialidad: " + reportInit.getValue());
        } else {
            txtValueForOtro.setVisibility(View.VISIBLE);
            txtValueForOtro.setText("Vialidad: " + reportInit.getValueForOtro());
        }

        //TODO: Checar por que imprime 4 y no solo 2 (25)
        //Separar por comas
        String[] arrSplit = reportInit.getIdCuadrillas().split(",");
        for (String s : arrSplit) {
            String SEPARADOR = "";
            for (RSIdCuadrillas item : LiveData.getInstance().getIdCuadrillas()) {
                if (item.getNoCuadrilla().equals(s)) {
                    cuadrillas.append(SEPARADOR);
                    cuadrillas.append(item.getTipo()).append(" - ").append(s).append("\n");
                }
            }
        }
        txtIdCuadrillas.setText(cuadrillas);

        txtTramo.setText("Tramo: " + reportInit.getTramo());
        txtAlcaldia.setText("Alcaldia: " + reportInit.getAlcaldia());
        txtPlacas.setText("Placas: " + reportInit.getPlacas());
        txtTurno.setText("Turno: " + reportInit.getTurno());
        txtColonia.setText("Colonia: " + reportInit.getColonia());
        txtReferencia.setText("Referencia ubicacion: " + reportInit.getReferencia());
        txtHrEntrada.setText("Hora de entrada: " + reportInit.getHr_entrada());

        //Report Two
        txtIdReportAlumbrado.setText("Id Reporte Alumbrado: " + reportInitTwo.getIdReportAlumbrado());
        txtFolio.setText("Folio: " + reportInitTwo.getFolio());

        for (DamageDTO item : reportListDamage) {
            damage.append(item.getDamage()).append("\n");
        }
        txtListDamages.setText(damage);

        //Report Three
        txtTipoLuminario.setText("Tipo de luminaria: " + reportMinuta.getTipoLuminario());
        txtDiagFalla.setText("Diagnostico de falla: " + reportMinuta.getDiagFalla());
        txtAccionRealizada.setText("Accion realizada: " + reportMinuta.getAccionRealizada());
        txtObservaciones.setText("Observaciones: " + reportMinuta.getObservaciones());

        //Report Material Used
        for (MaterialNew item : reportListMaterialUsed) {
            for (MaterialDTO mat : LiveData.getInstance().getListMaterials()) {
                if (item.getIdMaterial().equals(mat.getIdMaterial())) {
                    material.append("Material: ").append(mat.getMaterial()).append("\n")
                            .append("Cantidad: ").append(item.getCantidad()).append("\n");
                }
            }
        }
        txtListaMaterial.setText(material);

        //Report Image Material Used
        txtObsMaterial.setText("Material de resguardo: " + reportImageMaterialUsed.getObsMaterial());

        //Report Notas
        txtNotas.setText("Notas: " + reportNotas.getNotas());

        return view;
    }

    @OnClick(R.id.btn)
    public void clickContinuar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(that);
        builder.setMessage("¿Deseas continuar?")
                .setCancelable(true)
                .setNegativeButton("No", (dialog, id) -> {
                })
                .setPositiveButton("OK", (dialog, id) -> goNext());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void goNext() {
        damage.delete(0, damage.length());
        material.delete(0, material.length());
        cuadrillas.delete(0, cuadrillas.length());

        FinFolioFragment newFragment = new FinFolioFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
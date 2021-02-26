package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.VialidadDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterVialidades;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.FirstAlumbrado.PendingChecksFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VialidadesFragment extends Fragment implements AdapterVialidades.OnItemSelectedListener {

    MainActivity that;
    View view;
    Boolean typeRecuperado;

    AdapterVialidades adapterVialidades;


    public VialidadesFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.listView)
    ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_vialidad_list, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        Bundle recuperarType = getArguments();
        if (recuperarType != null) {
            typeRecuperado = recuperarType.getBoolean("type");
        }

        getVialidades();

        return view;
    }

    private void getVialidades(){

        that.showProgress();
        Repository.getInstance().requestGetVialidades("" + LiveData.getInstance().getReportInit().getIdCuadrante(),new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                fillData();

            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                that.showDialog(message);
            }
        });
    }

    private void fillData(){
        adapterVialidades = new AdapterVialidades(that,LiveData.getInstance().getVialidades());
        adapterVialidades.setItemSelectedListener(this);
        listView.setAdapter(adapterVialidades);
    }

    @Override
    public void onItemSelected(VialidadDTO item) {
        if(item.getVialidad().equals("Especial (otro)")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Agregar otra vialidad");
            View viewInflated = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_text_other_damage, (ViewGroup) getView(), false);
            EditText txtOther = viewInflated.findViewById(R.id.txtOther);
            builder.setView(viewInflated)
                    .setCancelable(false)
                    .setPositiveButton("Agregar", (dialog, id) -> {
                        LiveData.getInstance().getReportInit().setIdVialidad(item.getIdVialidad());
                        LiveData.getInstance().getReportInit().setValue("");
                        LiveData.getInstance().getReportInit().setValueForOtro(txtOther.getText().toString());

                        if (typeRecuperado) {
                            CuadrillasFragment newFragment = new CuadrillasFragment();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                            transaction.replace(R.id.content_main, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        } else {
                            PendingChecksFragment newFragment = new PendingChecksFragment();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                            transaction.replace(R.id.content_main, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

            txtOther.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable e) {
                    if(!e.toString().isEmpty()) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    } else {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    }
                }
            });
        } else {
            LiveData.getInstance().getReportInit().setIdVialidad(item.getIdVialidad());
            LiveData.getInstance().getReportInit().setValueForOtro("");
            LiveData.getInstance().getReportInit().setValue(item.getVialidad());

            if (typeRecuperado) {
                CuadrillasFragment newFragment = new CuadrillasFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.content_main, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            } else {
                PendingChecksFragment newFragment = new PendingChecksFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.content_main, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }
    }
}



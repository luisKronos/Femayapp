package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Cuadrilla;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado.CuadrantesFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado.NewHomeFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado.PersonalCuadrillasFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado.PersonalEquipFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.SharedPreferencesManager;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TypeCuadrillaFragment extends Fragment {
    MainActivity that;
    View view;

    public TypeCuadrillaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_type_cuadrilla, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        return view;
    }

    @OnClick(R.id.btnAlumbrado)
    public void clickAddAlumbrado(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Numero de Cuadrilla");
        View viewInflated = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_number_cuadrilla, (ViewGroup) getView(), false);
        EditText txtNumber = viewInflated.findViewById(R.id.txtNumberCuadrilla);
        builder.setView(viewInflated)
                .setCancelable(true)
                .setPositiveButton("Continuar", (dialog, id) -> {
                    LiveData.getInstance().getCuadrillaReport().setNoCuadrilla(Integer.parseInt(txtNumber.getText().toString()));
                    LiveData.getInstance().getCuadrillaReport().setTipo("alumbrado");

                    PersonalEquipFragment newFragment = new PersonalEquipFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    Bundle type = new Bundle();
                    type.putString("cuadrilla", "personalAlumbrado");
                    newFragment.setArguments(type);

                    transaction.replace(R.id.content_main, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        txtNumber.addTextChangedListener(new TextWatcher() {
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
    }

    @OnClick(R.id.btnObraCivil)
    public void clickAddObraCivil(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Numero de Cuadrilla");
        View viewInflated = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_number_cuadrilla, (ViewGroup) getView(), false);
        EditText txtNumber = viewInflated.findViewById(R.id.txtNumberCuadrilla);
        builder.setView(viewInflated)
                .setCancelable(true)
                .setPositiveButton("Continuar", (dialog, id) -> {
                    LiveData.getInstance().getCuadrillaReport().setNoCuadrilla(Integer.parseInt(txtNumber.getText().toString()));
                    LiveData.getInstance().getCuadrillaReport().setTipo("civil");

                    PersonalEquipFragment newFragment = new PersonalEquipFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    Bundle type = new Bundle();
                    type.putString("cuadrilla", "personalCivil");
                    newFragment.setArguments(type);

                    transaction.replace(R.id.content_main, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        txtNumber.addTextChangedListener(new TextWatcher() {
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
    }
}
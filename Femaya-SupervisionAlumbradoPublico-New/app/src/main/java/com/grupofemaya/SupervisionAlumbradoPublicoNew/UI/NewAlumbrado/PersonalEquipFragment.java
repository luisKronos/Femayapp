package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.CheckBoxItem;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterCheckBox;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalEquipFragment extends Fragment implements AdapterCheckBox.OnItemSelectedListener {

    MainActivity that;
    View view;
    String typeCuadrilla = "";
    AdapterCheckBox adapterPersonalEquip;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.textView)
    TextView textView;

    private final ArrayList<CheckBoxItem> mList = new ArrayList<>();
    private final ArrayList<String> mListAux = new ArrayList<>();
    StringBuilder onLine = new StringBuilder();

    public PersonalEquipFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_equip, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        mListAux.clear();
        mList.clear();
        onLine.delete(0, onLine.length());

        Bundle recuperarType = getArguments();
        if (recuperarType != null) {
            typeCuadrilla = recuperarType.getString("cuadrilla");
        }

        switch (typeCuadrilla) {
            case "personalAlumbrado":
                textView.setText("PERSONAL DE CUADRILLA");
                mList.add(new CheckBoxItem("Ayudante de Electricista"));
                mList.add(new CheckBoxItem("Ayudante de Operador"));
                mList.add(new CheckBoxItem("Cabo"));
                mList.add(new CheckBoxItem("Cabo de Cuadrilla"));
                mList.add(new CheckBoxItem("Electricista"));
                mList.add(new CheckBoxItem("Electricista Baja Tensión"));
                mList.add(new CheckBoxItem("Operador de Grua"));

                adapterPersonalEquip = new AdapterCheckBox(mList, this, false);
                recycler.setAdapter(adapterPersonalEquip);
                break;

            case "equipoAlumbrado":
                textView.setText("EQUIPO Y HERRAMIENTA");
                mList.add(new CheckBoxItem("Camioneta Pick Up"));
                mList.add(new CheckBoxItem("Equipo de Seguridad"));
                mList.add(new CheckBoxItem("Grua Hidráulica"));
                mList.add(new CheckBoxItem("Señalamiento"));
                mList.add(new CheckBoxItem("Uniforme"));

                adapterPersonalEquip = new AdapterCheckBox(mList, this);
                recycler.setAdapter(adapterPersonalEquip);
                break;

            case "personalCivil":
                textView.setText("CUADRILLA DE OBRA CIVIL");
                mList.add(new CheckBoxItem("Albañil"));
                mList.add(new CheckBoxItem("Ayudante de Herrero"));
                mList.add(new CheckBoxItem("Ayudante de Oficial"));
                mList.add(new CheckBoxItem("Cabo Cuadrilla"));
                mList.add(new CheckBoxItem("Cabo de Albañilería"));
                mList.add(new CheckBoxItem("Cabo de Herrería"));
                mList.add(new CheckBoxItem("Herrero de Taller"));
                mList.add(new CheckBoxItem("Operador de Compresor"));
                mList.add(new CheckBoxItem("Operador de Cortadora"));
                mList.add(new CheckBoxItem("Operador de Oxiacetileno"));
                mList.add(new CheckBoxItem("Operador de revolvedora"));
                mList.add(new CheckBoxItem("Operador de Vehículo Med."));

                adapterPersonalEquip = new AdapterCheckBox(mList, this, false);
                recycler.setAdapter(adapterPersonalEquip);
                break;

            case "equipoCivil":
                textView.setText("EQUIPO Y HERRAMIENTA");
                mList.add(new CheckBoxItem("Camión Internacional Mod 4300-210 (4x2) 210 HP con Caja de Volteo de 10 Tons."));
                mList.add(new CheckBoxItem("Camioneta Pickup 1.5 Tons."));
                mList.add(new CheckBoxItem("Compresor portatil Atlas C."));
                mList.add(new CheckBoxItem("Equipo de Corte Oxiacetileno"));
                mList.add(new CheckBoxItem("Equipo de Seguridad"));
                mList.add(new CheckBoxItem("Mezcladora de Concreto JoperMod. Rt00 Con Motor a gasolina Mca. Honda Mod. GX390T1QX de 9.698 Kw."));
                mList.add(new CheckBoxItem("Señalamiento"));
                mList.add(new CheckBoxItem("Soldadora tipo Generador Insignia 4 o 4T Infra Miller"));
                mList.add(new CheckBoxItem("Uniforme"));

                adapterPersonalEquip = new AdapterCheckBox(mList, this);
                recycler.setAdapter(adapterPersonalEquip);
                break;
        }
        return view;
    }

    @OnClick(R.id.btn)
    public void clickContinue(View view) {
        if (mListAux.isEmpty()) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(that);
            builder2.setMessage("Selecione al menos un elemento")
                    .setCancelable(true)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog alert2 = builder2.create();
            alert2.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(that);
            builder.setMessage("¿Deseas continuar?")
                    .setCancelable(true)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            addComa();
                            goNext();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void addComa() {
        String SEPARADOR = "";
        for (String s : mListAux) {
            onLine.append(SEPARADOR);
            onLine.append(s);
            SEPARADOR = ",";
        }
        switch (typeCuadrilla) {
            case "personalAlumbrado":
            case "personalCivil":
                LiveData.getInstance().getCuadrillaReport().setPersonal(onLine.toString());
                break;

            case "equipoAlumbrado":
            case "equipoCivil":
                LiveData.getInstance().getCuadrillaReport().setEquipo(onLine.toString());
                break;
        }
    }

    private void goNext(){
        PersonalEquipFragment repeatFragment = new PersonalEquipFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle type = new Bundle();

        switch (typeCuadrilla) {
            case "personalAlumbrado":
                type.putString("cuadrilla", "equipoAlumbrado");
                repeatFragment.setArguments(type);

                transaction.replace(R.id.content_main, repeatFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case "personalCivil":
                type.putString("cuadrilla", "equipoCivil");
                repeatFragment.setArguments(type);

                transaction.replace(R.id.content_main, repeatFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case "equipoAlumbrado":
                FotoCuadrillaFragment fotoFragment = new FotoCuadrillaFragment();

                transaction.replace(R.id.content_main, fotoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case "equipoCivil":
                FotoHerramientaEquipoFragment newFragment = new FotoHerramientaEquipoFragment();

                transaction.replace(R.id.content_main, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }

    @Override
    public void onItemSelected(String text, boolean isChecked, int position) {
        if (isChecked) {
            mListAux.add(text);
        } else {
            for (int i = 0; i < mListAux.size(); i++) {
                if (mListAux.get(i).equals(text)) {
                    mListAux.remove(i);
                }
            }
        }
    }
}
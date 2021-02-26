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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.CheckBoxItem;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.User;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.VialidadDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSIdCuadrillas;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterCheckBox;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterVialidades;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.SharedPreferencesManager;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CuadrillasFragment extends Fragment implements AdapterCheckBox.OnItemSelectedListener {

    MainActivity that;
    View view;

    AdapterCheckBox adapterPersonalEquip;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.textViewNotFound)
    TextView textViewNotFound;

    @BindView(R.id.btn)
    Button btn;

    private final ArrayList<CheckBoxItem> mList = new ArrayList<>();
    private final ArrayList<String> mListAux = new ArrayList<>();
    User us = new User();
    StringBuilder onLine = new StringBuilder();

    public CuadrillasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cuadrilla, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        getCuadrillas();

        return view;
    }

    private void getCuadrillas() {
        that.showProgress();
        us.setIdUser(Integer.parseInt(SharedPreferencesManager.getInstance().getIdUser()));
        Repository.getInstance().requestIdCuadrillas(us ,new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();

                textView.setText("CUADRILLAS REGISTRADAS INVOLUCRADAS");

                fillData();
            }

            @Override
            public void requestFail(String message) {
                btn.setVisibility(View.GONE);

                that.hideProgress();
                that.showDialog(message);

                textViewNotFound.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                recycler.setVisibility(View.GONE);
            }
        });
    }

    private void fillData(){
        for (RSIdCuadrillas item : LiveData.getInstance().getIdCuadrillas()) {
            mList.add(new CheckBoxItem(item.getTipo() + " - " + item.getNoCuadrilla()));
        }
        adapterPersonalEquip = new AdapterCheckBox(mList, this);
        recycler.setAdapter(adapterPersonalEquip);
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
        Toast.makeText(requireContext(), onLine.toString(), Toast.LENGTH_SHORT).show();

        LiveData.getInstance().getReportInit().setIdCuadrillas(onLine.toString());
    }

    private void goNext(){
        SeCuentaFragment newFragment = new SeCuentaFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        mListAux.clear();
        mList.clear();
        onLine.delete(0, onLine.length());

        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onItemSelected(String text, boolean isChecked, int position) {
        if (isChecked) {
            mListAux.add(LiveData.getInstance().getIdCuadrillas().get(position).getNoCuadrilla());
        } else {
            for (int i = 0; i < mListAux.size(); i++) {
                if (mListAux.get(i).equals(LiveData.getInstance().getIdCuadrillas().get(position).getNoCuadrilla())) {
                    mListAux.remove(i);
                }
            }
        }
    }
}

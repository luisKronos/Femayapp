package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.VialidadDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterVialidades;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VialidadesFragment extends Fragment implements AdapterVialidades.OnItemSelectedListener {

    MainActivity that;
    View view;
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

        getVialidades();

        return view;
    }

    /*
    @OnClick(R.id.btnCheck)
    public void clickRecoger(View view) {
        InitCheckListFragment newFragment = new InitCheckListFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/


    private void getVialidades(){

        that.showProgress();
        Repository.getInstance().requestGetVialidades(LiveData.getInstance().getLiveReport().getIdCuadrante(),new RepositoryImp() {
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
        LiveData.getInstance().getLiveReport().setIdVialidad(item.getIdVialidad());
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Numero de Cuadrilla");
        View viewInflated = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_number_cuadrilla, (ViewGroup) getView(), false);
        EditText txtNumber = viewInflated.findViewById(R.id.txtNumberCuadrilla);
        builder.setView(viewInflated)
                .setCancelable(true)
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(!txtNumber.getText().toString().isEmpty()) {
                            LiveData.getInstance().getLiveReport().setCuadrilla(Integer.parseInt(txtNumber.getText().toString()));
                            PersonalCuadrillasFragment newFragment = new PersonalCuadrillasFragment();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.content_main, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

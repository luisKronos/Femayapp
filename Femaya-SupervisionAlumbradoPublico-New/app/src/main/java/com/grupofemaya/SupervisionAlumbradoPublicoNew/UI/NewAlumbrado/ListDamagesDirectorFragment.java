package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.FragmentTransaction;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DamageDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterDamages;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterDirectorDamages;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ListDamagesDirectorFragment extends GenericFragment implements AdapterDirectorDamages.OnItemSelectedListener {

    MainActivity that;
    View view;

    @BindView(R.id.listView)
    ListView listView;

    AdapterDirectorDamages adapter;







    public ListDamagesDirectorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tipo_dano_director_list, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();
        getDamages();
        return view;
    }





    private void getDamages(){
        Repository.getInstance().requestGetDamages(new RepositoryImp() {
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
        adapter = new AdapterDirectorDamages(that,LiveData.getInstance().getListDamges());
        listView.setAdapter(adapter);
        adapter.setItemSelectedListener(this);
    }






    @Override
    public void onItemSelected(DamageDTO item) {

        LiveData.getInstance().getLiveReportDirector().setIdDamage(item.getIdDamage());
        FinishDirectorFragment newFragment = new FinishDirectorFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}

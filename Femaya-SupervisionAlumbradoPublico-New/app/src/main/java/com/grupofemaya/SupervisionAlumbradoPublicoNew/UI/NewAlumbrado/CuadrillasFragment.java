package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class CuadrillasFragment extends Fragment {

    MainActivity that;
    View view;


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


        return view;
    }


    @OnClick(R.id.btn1)
    public void clickRecoger(View view) {
        goPersonal("1");
    }

    @OnClick(R.id.btn2)
    public void clickRecoger2(View view) {
        goPersonal("2");
    }


    private void goPersonal(String cuadrilla){
        PersonalCuadrillasFragment newFragment = new PersonalCuadrillasFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

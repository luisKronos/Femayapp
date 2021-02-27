package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CuadrantesFragment extends Fragment {

    MainActivity that;
    View view;
    Boolean typeRecuperado;


    public CuadrantesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cuadrante, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        Bundle recuperarType = getArguments();
        if (recuperarType != null) {
            typeRecuperado = recuperarType.getBoolean("type");
        }

        return view;
    }


    @OnClick (R.id.btn1)
    public void clickBtn1(View view) {
        goVialidades("1");
    }

    @OnClick (R.id.btn2)
    public void clickBtn2(View view) {
        goVialidades("2");
    }

    @OnClick (R.id.btn3)
    public void clickBtn3(View view) {
        goVialidades("3");
    }

    @OnClick (R.id.btn4)
    public void clickBtn4(View view) {
        goVialidades("4");
    }

    @OnClick (R.id.btn5)
    public void clickBtn5(View view) {
        goVialidades("5");
    }



    private void goVialidades (String idCuadrante) {
        LiveData.getInstance().getReportInit().setIdCuadrante(Integer.parseInt(idCuadrante));

        VialidadesFragment newFragment = new VialidadesFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle type = new Bundle();
        type.putBoolean("type", typeRecuperado);
        newFragment.setArguments(type);

        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

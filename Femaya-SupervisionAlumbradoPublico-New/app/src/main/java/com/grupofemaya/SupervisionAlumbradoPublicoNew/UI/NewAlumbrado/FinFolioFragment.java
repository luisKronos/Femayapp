package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.SharedPreferencesManager;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FinFolioFragment extends Fragment {

    MainActivity that;
    View view;


    @BindView(R.id.txtFolio)
    TextView txtFolio;


    public FinFolioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fin_folio, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        String folio = "";

        if(LiveData.getInstance().getReportInit().getIdCuadrante() == 1 ){
            folio = "NP";
        }else if(LiveData.getInstance().getReportInit().getIdCuadrante() == 2){
            folio = "NO";
        }else if(LiveData.getInstance().getReportInit().getIdCuadrante() == 3){
            folio = "SP";
        }else if(LiveData.getInstance().getReportInit().getIdCuadrante() == 4){
            folio = "SO";
        }else if(LiveData.getInstance().getReportInit().getIdCuadrante() == 5){
            folio = "CT";
        }

        folio = folio + "-" + LiveData.getInstance().getResponseReportInit().getIdReportAlumbrado();


        txtFolio.setText(folio);


        return view;
    }


    @OnClick(R.id.btn)
    public void clickContinue(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(that);
        builder.setMessage("¿Deseas finalizar?")
                .setCancelable(true)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goNext();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void goNext(){
        if (SharedPreferencesManager.getInstance().getVisibleReport()) {
            SharedPreferencesManager.getInstance().setVisibleReport(false);
        }

        NewHomeFragment newFragment = new NewHomeFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

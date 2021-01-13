package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.FirstAlumbrado;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQGetPending;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSGetListPendings;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterListPendings;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado.ListMaterialFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PendingChecksFragment extends Fragment implements AdapterListPendings.OnItemSelectedListener {

    MainActivity that;
    View view;
    RQGetPending request = new RQGetPending();
    AdapterListPendings adapterListPendings;

    @BindView(R.id.listView)
    ListView listView;


    /*public PendingChecksFragment() {
        // Required empty public constructor
    }

    public static PendingChecksFragment newInstance(boolean checkPendings) {
        PendingChecksFragment myFragment = new PendingChecksFragment();

        Bundle args = new Bundle();
        args.putBoolean("checkPendings", checkPendings);
        myFragment.setArguments(args);

        return myFragment;
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pending_checks, container, false);
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        getListPendings();


        /*boolean isGonnaCheck = false;
        if (getArguments() != null && getArguments().containsKey("checkPendings")){
            isGonnaCheck=getArguments().getBoolean("checkPendings", false);
        }

        if(isGonnaCheck) {
            checkPendings();
        }else{
            fillChecks();
        }*/

        return view;
    }

    private void getListPendings() {
        request.setIdCuadrante(LiveData.getInstance().getLiveReport().getIdCuadrante());
        request.setIdVialidad(LiveData.getInstance().getLiveReport().getIdVialidad());

        that.showProgress();

        Repository.getInstance().requestListPendings(request, new RepositoryImp() {
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
        adapterListPendings = new AdapterListPendings(that,LiveData.getInstance().getListPendings());
        adapterListPendings.setItemSelectedListener(this);
        listView.setAdapter(adapterListPendings);
    }

    @Override
    public void onItemSelected(RSGetListPendings item) {
        LiveData.getInstance().getLiveReport().setIdReportAlumbrado(item.getIdReportAlumbrado());
        goNext();

    }
    private void goNext(){
        ListMaterialFragment newFragment = new ListMaterialFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    /*private void checkPendings(){
        RQPendingCheck rqInitCheck = new RQPendingCheck();
        rqInitCheck.setIdUser(SharedPreferencesManager.getInstance().getIdUser());
        that.showProgress();
        Repository.getInstance().requestPendingsChecks(rqInitCheck,new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                RSPendingsChecks rsPendingCheck = (RSPendingsChecks)response;

                if(rsPendingCheck.isHasPendindChecks()){
                    LiveData.getInstance().setPendingChecks(rsPendingCheck.getChecks());
                }

            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                that.showDialog(message);
            }
        });
    }

    private void fillChecks(){
        AdapterChecks adapterChecks = new AdapterChecks(that, LiveData.getInstance().getPendingChecks());
        listView.setAdapter(adapterChecks);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RSStatusCheck item = LiveData.getInstance().getPendingChecks().get(position);
                if(item.getStatus().equals("0")){
                    LiveData.getInstance().setIdCheck(item.getIdCheck());
                } else if(item.getStatus().equals("1")){
                    LiveData.getInstance().setIdCheck(item.getIdCheck());
                }
            }
        });
    }

    private void goCheck(){
        CheckListFragment newFragment = new CheckListFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void goCheckQuantification(){
        FinalQuantificationFragment newFragment = new FinalQuantificationFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/


}

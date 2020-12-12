package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.FirstAlumbrado;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQPendingCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSPendingsChecks;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.SharedPreferencesManager;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends Fragment {

    MainActivity that;
    View view;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(boolean checkPendings) {
        HomeFragment myFragment = new HomeFragment();

        Bundle args = new Bundle();
        args.putBoolean("checkPendings", checkPendings);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();
        SharedPreferencesManager.initializeInstance(that);

        boolean isGonnaCheck = true;
        if (getArguments() != null && getArguments().containsKey("checkPendings")){
            isGonnaCheck=getArguments().getBoolean("checkPendings", true);
        }

        if(isGonnaCheck) {
            checkPendings();
        }

        return view;
    }

    @OnClick(R.id.btnCheck)
    public void clickRecoger(View view) {
        InitCheckListFragment newFragment = new InitCheckListFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void checkPendings(){
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
                    goPendingChecks();
                }

            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                that.showDialog(message);
            }
        });
    }

    private void goPendingChecks(){
        PendingChecksFragment newFragment = new PendingChecksFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

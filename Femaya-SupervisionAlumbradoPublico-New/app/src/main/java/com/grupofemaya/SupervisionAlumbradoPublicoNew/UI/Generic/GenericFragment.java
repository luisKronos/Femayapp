package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;

import org.grupofemaya.SupervisionAlumbradoPublico.R;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.FirstAlumbrado.CheckListFragment;

import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenericFragment extends Fragment implements FragmentView {



    private GenericActivity mActivity;
    private Unbinder mUnBinder;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GenericActivity) {
            GenericActivity activity = (GenericActivity) context;
            this.mActivity = activity;
        }
    }



    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }


    public GenericActivity getBaseActivity() {
        return mActivity;
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }


    @Override
    public void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void showDialog(String message) {
        if (mActivity != null) {
            mActivity.showDialog(message);
        }
    }

    @Override
    public void showProgress() {
        if (mActivity != null) {
            mActivity.showProgress();
        }
    }

    @Override
    public void hideProgress() {
        if (mActivity != null) {
            mActivity.hideProgress();
        }
    }


    public void backHome() {
        CheckListFragment newFragment = new CheckListFragment();
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

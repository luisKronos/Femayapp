package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.CheckBoxItem;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DamageDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MaterialNew;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInit;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInitTwo;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSIdCuadrillas;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterCheckBox;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterDamages;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.SharedPreferencesManager;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;


public class ListDamagesFragment extends GenericFragment implements AdapterCheckBox.OnItemSelectedListener {

    MainActivity that;
    View view;

    AdapterCheckBox adapterPersonalEquip;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    RQReportInitTwo rqInitReportTwo;

    private final ArrayList<CheckBoxItem> mList = new ArrayList<>();
    private final ArrayList<DamageDTO> mListDamages = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initRequest();
        }
    };

    private final Runnable mMessageSender = new Runnable() {
        public void run() {
            Message msg = mHandler.obtainMessage();

            LiveData.getInstance().getReportInitTwo().setListDamages(LiveData.getInstance().getListD());
            rqInitReportTwo = LiveData.getInstance().getReportInitTwo();
            mHandler.sendMessage(msg);
        }
    };

    public ListDamagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tipo_dano_list, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        mList.clear();
        mListDamages.clear();

        getDamages();

        return view;
    }

    @OnClick(R.id.btn)
    public void clickContinuar(){
        askContinue();
    }

    private void askContinue(){
        if (mListDamages.isEmpty()) {
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
//                            LiveData.getInstance().getReportInitTwo().setListDamages(mListDamages);
                            LiveData.getInstance().setListD(mListDamages);
                            prepareReq();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void prepareReq() {
        that.showProgress();
        new Thread(mMessageSender).start();
    }

    private void initRequest() {
        Repository.getInstance().requestReportInitTwo(rqInitReportTwo, new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                goNext();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                that.showDialog(message);
            }
        });
    }

    private void getDamages(){
        that.showProgress();
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
        for (DamageDTO item : LiveData.getInstance().getListDamges()) {
            mList.add(new CheckBoxItem(item.getDamage()));
        }
        adapterPersonalEquip = new AdapterCheckBox(mList, this, false);
        recycler.setAdapter(adapterPersonalEquip);
    }

    private void goNext() {
        FotografiasFragment newFragment = new FotografiasFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onItemSelected(String text, boolean isChecked, int position) {
        if (isChecked) {
            mListDamages.add(LiveData.getInstance().getListDamges().get(position));
        } else {
            for (int i = 0; i < mListDamages.size(); i++) {
                if (mListDamages.get(i).equals(LiveData.getInstance().getListDamges().get(position))) {
                    mListDamages.remove(i);
                }
            }
        }
    }
}

package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.CheckBoxItem;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DamageDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MaterialDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MaterialNew;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQMaterialUsed;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInitTwo;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterCheckBox;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterMaterial;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListMaterialFragment extends GenericFragment implements AdapterCheckBox.OnItemSelectedListener {

    MainActivity that;
    View view;

    AdapterCheckBox adapterPersonalEquip;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    RQMaterialUsed rqMaterialUsed;

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

            LiveData.getInstance().getReportMaterialUsed().setListaMaterial(LiveData.getInstance().getListM());
            LiveData.getInstance().getReportMaterialUsed().setIdReportAlumbrado(LiveData.getInstance().getResponseReportInit().getIdReportAlumbrado());

            rqMaterialUsed = LiveData.getInstance().getReportMaterialUsed();
            mHandler.sendMessage(msg);
        }
    };

    private final ArrayList<CheckBoxItem> mList = new ArrayList<>();
    private final ArrayList<MaterialNew> mListMaterial = new ArrayList<>();

    public ListMaterialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_material_list, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();
        that.showProgress();
        getMaterial();
        return view;
    }

    @OnClick(R.id.btn)
    public void clickContinuar(){
        if (mListMaterial.isEmpty()) {
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
//                            LiveData.getInstance().getReportMaterialUsed().setListaMaterial(mListMaterial);
                            LiveData.getInstance().setListM(mListMaterial);
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
        Repository.getInstance().requestMaterialesUsed(rqMaterialUsed, new RepositoryImp() {
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

    private void getMaterial(){
        Repository.getInstance().requestGetMaterials(new RepositoryImp() {
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
        for (MaterialDTO item : LiveData.getInstance().getListMaterials()) {
            mList.add(new CheckBoxItem(item.getMaterial()));
        }
        adapterPersonalEquip = new AdapterCheckBox(mList, this);
        recycler.setAdapter(adapterPersonalEquip);
    }

    private void goNext(){
        mList.clear();
        mListMaterial.clear();

        FotografiaMaterialesFragment newFragment = new FotografiaMaterialesFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onItemSelected(String text, boolean isChecked, int position) {
        if (isChecked) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Cantidad utilizada");
            View viewInflated = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_text_other_damage, (ViewGroup) getView(), false);
            EditText txtCantidad = viewInflated.findViewById(R.id.txtOther);
            txtCantidad.setHint("Cantidad utilizada");
            builder.setView(viewInflated)
                    .setCancelable(false)
                    .setPositiveButton("Agregar", (dialog, id) -> {
                        MaterialNew rqMaterial = new MaterialNew();
                        rqMaterial.setIdMaterial(LiveData.getInstance().getListMaterials().get(position).getIdMaterial());
                        rqMaterial.setCantidad(txtCantidad.getText().toString());
                        mListMaterial.add(rqMaterial);
                        Toast.makeText(requireContext(), rqMaterial.getIdMaterial(), Toast.LENGTH_SHORT).show();
                    });
            AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

            txtCantidad.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable e) {
                    if(!e.toString().isEmpty()) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    } else {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    }
                }
            });
        } else {
            for (int i = 0; i < mListMaterial.size(); i++) {
                if (mListMaterial.get(i).getIdMaterial().equals(LiveData.getInstance().getListMaterials().get(position).getIdMaterial())) {
                    Toast.makeText(requireContext(), mListMaterial.get(i).getIdMaterial(), Toast.LENGTH_SHORT).show();
                    mListMaterial.remove(i);
                }
            }
        }
    }
}

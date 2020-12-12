package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.FirstAlumbrado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSInitCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.rsGeneral;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.rsGeneralList;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DeductivasDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckDeductives;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.SelectableAdapter;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Helpers.SelectableItem;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Helpers.SelectableViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DeductivasFragment extends GenericFragment implements View.OnClickListener, SelectableViewHolder.OnItemSelectedListener {

    MainActivity that;
    View view;


    @BindView(R.id.btnFinish)
    Button btnFinish;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    public DeductivasFragment() {
        // Required empty public constructor
    }
    SelectableAdapter adapter;
    List<DeductivasDTO> listPenalties = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_deductivas, container, false);
        that = (MainActivity) getActivity();
        ButterKnife.bind(this, view);


        btnFinish.setOnClickListener(this);


        requestPenalties();
        return view;
    }


    private void fillData(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(that);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SelectableAdapter(this,listPenalties,true);
        recyclerView.setAdapter(adapter);
    }

    private void requestPenalties(){
        that.showProgress();
        Repository.getInstance().requestGetPenalties(new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                rsGeneralList<DeductivasDTO> res = (rsGeneralList<DeductivasDTO>) response;
                listPenalties.addAll(res.getData());
                fillData();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                showDialog(message);
            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v==btnFinish){
            requestFinish();
        }
    }

    private void requestFinish(){
        that.showProgress();

        RQCheckDeductives request = new RQCheckDeductives();
        request.setIdCheck(LiveData.getInstance().getIdCheck());
        request.setDeductives(adapter.getSelectedItems());

        Repository.getInstance().requestCheckDeductives(request,new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                rsGeneral<RSInitCheck> res = (rsGeneral<RSInitCheck>) response;
                AlertDialog.Builder builder = new AlertDialog.Builder(that);
                builder.setMessage(res.getHeader().getMessage())
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                backHome();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                showDialog(message);
            }
        });
    }



    @Override
    public void onItemSelected(SelectableItem item, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(that);
        builder.setTitle("Ingresa la cantidad de UMA's:");
        builder.setCancelable(false);

        final EditText input = new EditText(that);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //listPenalties.get(position).setUma(input.getText().toString());
                //adapter.notifyItemChanged(position);
                adapter.setUMA(position,input.getText().toString());
            }
        });
        /*builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });*/
        builder.show();
    }
}

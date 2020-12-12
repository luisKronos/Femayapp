package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.FirstAlumbrado;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQStatusCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSInitCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.rsGeneral;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.SignActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DeductivesDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQRevised;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSFinalQuantification;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FinalQuantificationFragment extends GenericFragment implements View.OnClickListener {

    MainActivity that;
    View view;


    @BindView(R.id.btnSign)
    Button btnSign;

    @BindView(R.id.btnAcuerdo)
    Button btnAcuerdo;

    @BindView(R.id.btnDesacuerdo)
    Button btnDesacuerdo;


    @BindView(R.id.txtPorcentaje)
    TextView txtPorcentaje;
    @BindView(R.id.txtVehiculos)
    TextView txtVehiculos;
    @BindView(R.id.txtPersonal)
    TextView txtPersonal;
    @BindView(R.id.txtUniformes)
    TextView txtUniformes;
    @BindView(R.id.txtHerramientas)
    TextView txtHerramientas;
    @BindView(R.id.txtSanciones)
    TextView txtSanciones;
    @BindView(R.id.txtDeductivas)
    TextView txtDeductivas;

    @BindView(R.id.imageSign)
    ImageView imageSign;


    RQRevised request = new RQRevised();


    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            requestCheckRevised();
        }
    };

    private final Runnable mMessageSender = new Runnable() {
        public void run() {
            Message msg = mHandler.obtainMessage();
            request = new RQRevised();

            request.setIdCheck(LiveData.getInstance().getIdCheck());
            request.setSign(mFuncs.BitmapBase64PNG(LiveData.getInstance().getSignTemporaly()));

            mHandler.sendMessage(msg);
        }
    };

    public FinalQuantificationFragment() {
        // Required empty public constructor
    }

    Funcs mFuncs = new Funcs();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_final, container, false);
        that = (MainActivity) getActivity();
        ButterKnife.bind(this, view);


        btnSign.setOnClickListener(this);
        btnAcuerdo.setOnClickListener(this);
        btnDesacuerdo.setOnClickListener(this);

        requestData();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(LiveData.getInstance().getSignTemporaly()!=null){
            imageSign.setImageBitmap(LiveData.getInstance().getSignTemporaly());
        }
    }

    @Override
    public void onClick(View v) {
        if(v==btnSign){
            goSign();
        }else if(v==btnAcuerdo){
            if(LiveData.getInstance().getSignTemporaly()!=null) {
                request.setAgree("1");
                prepareReq();
            }else{
                mFuncs.showDialog(that,"Debes firmar");
            }
        }else if(v==btnDesacuerdo){
            if(LiveData.getInstance().getSignTemporaly()!=null) {
                request.setAgree("0");
                prepareReq();
            }else{
                mFuncs.showDialog(that,"Debes firmar");
            }
        }
    }




    private void requestData(){
        RQStatusCheck rqStatusCheck = new RQStatusCheck();
        rqStatusCheck.setIdCheck(LiveData.getInstance().getIdCheck());
        Repository.getInstance().requestFinalQuantification(rqStatusCheck,new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                rsGeneral<RSFinalQuantification> res = (rsGeneral<RSFinalQuantification>) response;
                LiveData.getInstance().setFinalQuantification(res.getData());
                fillData();
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                showDialog(message);
            }
        });
    }

    private void fillData(){
        txtPorcentaje.setText(LiveData.getInstance().getFinalQuantification().getPercentAdvance());
        txtVehiculos.setText(LiveData.getInstance().getFinalQuantification().getCars());
        txtPersonal.setText(LiveData.getInstance().getFinalQuantification().getPersonalPresent());
        txtUniformes.setText(LiveData.getInstance().getFinalQuantification().getUniformes());
        txtHerramientas.setText(LiveData.getInstance().getFinalQuantification().getTotalTools());

        if(LiveData.getInstance().getFinalQuantification().getDeductives()!=null &&
                LiveData.getInstance().getFinalQuantification().getDeductives().isEmpty()) {
            String dedString = "";
            for (DeductivesDTO item : LiveData.getInstance().getFinalQuantification().getDeductives()) {
                dedString = dedString + item.getPunto() + ", ";
            }
            txtSanciones.setText(dedString);
        }else{
            txtSanciones.setText("");
        }
        txtDeductivas.setText(LiveData.getInstance().getFinalQuantification().getTotalDeductives());
    }


    private void goSign(){
        Intent intent = new Intent(that, SignActivity.class);
        that.startActivity(intent);
    }

    private void prepareReq(){
        that.showProgress();
        new Thread(mMessageSender).start();
    }

    private void requestCheckRevised(){
        Repository.getInstance().requestCheckRevised(request,new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                rsGeneral<RSInitCheck> res = (rsGeneral<RSInitCheck>) response;
                AlertDialog.Builder builder = new AlertDialog.Builder(that);
                builder.setMessage(res.getHeader().getMessage())
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                goHome();
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


    private void goHome() {
        HomeFragment newFragment = new HomeFragment();
        FragmentTransaction transaction = that.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

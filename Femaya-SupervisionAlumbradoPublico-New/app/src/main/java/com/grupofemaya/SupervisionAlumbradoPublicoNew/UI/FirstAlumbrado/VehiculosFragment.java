package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.FirstAlumbrado;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckCars;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSInitCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.rsGeneral;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Adapters.AdapterVehiculos;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.CarDTO;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VehiculosFragment extends GenericFragment implements View.OnClickListener {

    MainActivity that;
    View view;


    @BindView(R.id.btnFinish)
    Button btnFinish;

    @BindView(R.id.btnNewCar)
    ImageButton btnNewCar;

    @BindView(R.id.listCars)
    ListView listCars;
    RQCheckCars rqCheckPersonal;

    AdapterVehiculos adapterVehiculos;

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            requestCheckNewCars();
        }
    };

    private final Runnable mMessageSender = new Runnable() {
        public void run() {
            Message msg = mHandler.obtainMessage();



            rqCheckPersonal = new RQCheckCars();
            rqCheckPersonal.setIdCheck(LiveData.getInstance().getIdCheck());

            for(CarDTO car : LiveData.getInstance().getVehiculos()){
                CarDTO newCar = new CarDTO();
                newCar.setTorretas(car.getTorretas());
                newCar.setLaminas(car.getLaminas());
                newCar.setLona(car.getLona());
                newCar.setLuces(car.getLuces());
                newCar.setPlacas(car.getPlacas());
                newCar.setImagen(mFuncs.convierteBase64(car.getImagen()));
                rqCheckPersonal.getCars().add(newCar);
            }



            mHandler.sendMessage(msg);
        }
    };

    public VehiculosFragment() {
        // Required empty public constructor
    }

    Funcs mFuncs = new Funcs();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cars, container, false);
        that = (MainActivity) getActivity();
        ButterKnife.bind(this, view);


        btnFinish.setOnClickListener(this);
        btnNewCar.setOnClickListener(this);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        fillCars();

    }

    @Override
    public void onClick(View v) {
        if(v==btnFinish){
            prepareReq();
        }else if(v==btnNewCar) {
            goNewCar();
        }
    }



    private void goNewCar(){
        Intent intent = new Intent(that, NewCarActivity.class);
        that.startActivity(intent);
    }




    private void fillCars(){
        adapterVehiculos = new AdapterVehiculos(that,LiveData.getInstance().getVehiculos());
        listCars.setAdapter(adapterVehiculos);
    }

    private void prepareReq(){
        that.showProgress();
        new Thread(mMessageSender).start();
    }


    private void requestCheckNewCars(){
        Repository.getInstance().requestCheckCars(rqCheckPersonal,new RepositoryImp() {
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
}

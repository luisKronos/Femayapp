package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQFinishHour;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSFinalQuantification;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSReportInitOne;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.Repository;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository.RepositoryImp;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Cuadrilla.TypeCuadrillaFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.MainActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.SharedPreferencesManager;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewHomeFragment extends Fragment {

    MainActivity that;
    View view;

    RQFinishHour reportFinishHour;

    @BindView(R.id.btnCheckHour) Button btnHour;

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

            LiveData.getInstance().getReportFinishHour().setIdReportAlumbrado(LiveData.getInstance().getResponseReportInit().getIdReportAlumbrado());

            ArrayList<RSReportInitOne> list = new ArrayList<>();
            ArrayList<String> array = SharedPreferencesManager.getInstance().getListIdReportValues();

            for (String item : array) {
                RSReportInitOne report = new RSReportInitOne();
                report.setIdReportAlumbrado(Integer.parseInt(item));
                list.add(report);
            }

            LiveData.getInstance().getReportFinishHour().setListReportAlumbrado(list);

            reportFinishHour = LiveData.getInstance().getReportFinishHour();

            mHandler.sendMessage(msg);
        }
    };

    public NewHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_new, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        that = (MainActivity) getActivity();

        if (SharedPreferencesManager.getInstance().getCheckIn() == null) {
            btnHour.setText("Hora de Entrada");
        } else {
            btnHour.setText("Hora de Salida");
        }

        return view;
    }

    @OnClick(R.id.btnCuadrillas)
    public void clickAddCuadrillas(View view) {
        TypeCuadrillaFragment newFragment = new TypeCuadrillaFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @OnClick(R.id.btnCheckHour)
    public void clickCheckHour(View view) {
        showDialogToAddHour();
    }

    private void showDialogToAddHour() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        View viewInflated = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_check_hour, (ViewGroup) getView(), false);

        TextView title = viewInflated.findViewById(R.id.textView);
        EditText txtHour = viewInflated.findViewById(R.id.textHour);
        EditText txtMinutes = viewInflated.findViewById(R.id.textMinutes);

        if (SharedPreferencesManager.getInstance().getCheckIn() == null) {
            title.setText("INGRESE SU HORA DE ENTRADA");
        } else {
            title.setText("INGRESE SU HORA DE SALIDA");
        }

        builder.setView(viewInflated)
                .setCancelable(true)
                .setPositiveButton("Continuar", (dialog, id) -> {
                    String min = txtMinutes.getText().toString();
                    if(min.length() == 1) {
                        min = "0" + min;
                        txtMinutes.setText(min);
                    }

                    String hour = txtHour.getText().toString();
                    if(hour.length() == 1) {
                        hour = "0" + hour;
                        txtHour.setText(hour);
                    }
                    String check = txtHour.getText().toString() + ":" + txtMinutes.getText().toString();

                    if (SharedPreferencesManager.getInstance().getCheckIn() == null) {
                        SharedPreferencesManager.getInstance().setCheckIn(check);
                        btnHour.setText("Hora de Salida");

                        SharedPreferencesManager.getInstance().setCheckOut(null);
                    } else {
                        SharedPreferencesManager.getInstance().setCheckOut(check);
                        LiveData.getInstance().getReportFinishHour().setHr_salida(SharedPreferencesManager.getInstance().getCheckOut());
                        prepareReq();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        txtHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable e) {
                if(!e.toString().isEmpty() && Integer.parseInt(e.toString()) > 0 && Integer.parseInt(e.toString()) < 25){
                    txtMinutes.setEnabled(true);
                } else {
                    txtMinutes.setEnabled(false);
                }
            }
        });

        txtMinutes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable e) {
                if(!e.toString().isEmpty() && Integer.parseInt(e.toString()) >= 0 && Integer.parseInt(e.toString()) < 60){
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                } else {
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }
        });
    }

    private void prepareReq(){
        that.showProgress();
        new Thread(mMessageSender).start();
    }

    private void initRequest(){
        that.showProgress();
        Repository.getInstance().requestFinishHour(reportFinishHour, new RepositoryImp() {
            @Override
            public void succedResponse(Object response) {
                that.hideProgress();
                btnHour.setText("Hora de Entrada");

                SharedPreferencesManager.getInstance().setIdReportValues(0, true);
                SharedPreferencesManager.getInstance().setCheckIn(null);
                SharedPreferencesManager.getInstance().setCheckOut(null);

                that.showDialog(response.toString());
            }

            @Override
            public void requestFail(String message) {
                that.hideProgress();
                that.showDialog(message);
            }
        });
    }

    @OnClick(R.id.btnNew)
    public void clickRecoger(View view) {
        if (SharedPreferencesManager.getInstance().getCheckIn() == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setMessage("Es necesario que agregue su hora de entrada")
                    .setCancelable(true)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog alert2 = builder.create();
            alert2.show();
        } else {
            CuadrantesFragment newFragment = new CuadrantesFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            Bundle type = new Bundle();
            type.putBoolean("type", true);
            newFragment.setArguments(type);

            transaction.replace(R.id.content_main, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @OnClick(R.id.btnPendientes)
    public void clickPendiente(View view) {
        CuadrantesFragment newFragment = new CuadrantesFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle type = new Bundle();
        type.putBoolean("type", false);
        newFragment.setArguments(type);

        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

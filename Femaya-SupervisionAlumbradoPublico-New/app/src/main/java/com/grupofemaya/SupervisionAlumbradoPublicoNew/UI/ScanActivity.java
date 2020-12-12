package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Funcs;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.GPSTracker;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScanActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    Handler handlerGetMeeting;
    Handler handlerConfirmeMeeting;
    Funcs mFuncs = new Funcs();
    GPSTracker gpsTracker;
    Activity that;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);
        mScannerView.setAutoFocus(true);
        mScannerView.setFlash(false);
        mScannerView.setFilterTouchesWhenObscured(false);
        setContentView(mScannerView);
        that=this;

        /*
        gpsTracker = new GPSTracker(this);

        if(gpsTracker.canGetLocation()){
            gpsTracker.getLocation();
        }else{
            gpsTracker.showSettingsAlert();
        }
        */

    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();


    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        mScannerView.stopCamera();

        Log.v("RESULTA", rawResult.getText());
        Log.v("RESULTA", rawResult.getBarcodeFormat().toString());
        String result = rawResult.getText();

        Toast toast=Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT);
        toast.setMargin(50,50);
        toast.show();


    }


}

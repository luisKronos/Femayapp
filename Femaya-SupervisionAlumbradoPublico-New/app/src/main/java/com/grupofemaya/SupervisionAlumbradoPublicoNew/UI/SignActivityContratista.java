package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.MyDrawView;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

public class SignActivityContratista extends AppCompatActivity {


    MyDrawView myDrawView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        myDrawView = findViewById(R.id.draw);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveData.getInstance().setSignContratista(myDrawView.getBitmap());
                finish();
            }
        });

        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawView.clear();
            }
        });
    }
}

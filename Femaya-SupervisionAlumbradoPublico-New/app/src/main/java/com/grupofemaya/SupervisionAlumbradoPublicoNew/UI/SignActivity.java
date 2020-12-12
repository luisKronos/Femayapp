package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.MyDrawView;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

public class SignActivity extends AppCompatActivity {


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
                LiveData.getInstance().setSignTemporaly(myDrawView.getBitmap());
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

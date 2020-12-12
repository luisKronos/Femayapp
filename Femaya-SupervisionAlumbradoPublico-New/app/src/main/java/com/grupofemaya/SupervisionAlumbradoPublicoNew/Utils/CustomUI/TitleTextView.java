package com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.CustomUI;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TitleTextView extends AppCompatTextView {

    public TitleTextView(Context context, AttributeSet attrs){
        super(context);
        init();
    }

    public TitleTextView(Context context){
        super(context);
        init();
    }

    private void init(){
        Typeface font_type = Typeface.createFromAsset(getContext().getAssets(),"fonts/RegloSWAP.ttf");
        setTypeface(font_type);
    }

}

package com.binaryme.HelloScrollView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.widget.AbsoluteLayout;

public class HelloScrollViewActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        AbsoluteLayout abs = (AbsoluteLayout) findViewById(R.id.mAbsoluteLayout);
        
        //set Background to AbsoluteLayout
        ShapeDrawable bg = new ShapeDrawable(new RectShape());
        int[] pixels = new int[] { 0xFFCCCCCC, 0xFFCCCCCC, 0xFFCCCCCC,
            0xFFCCCCCC, 0xFFCCCCCC, 0xFFCCCCCC, 0xFF999999, 0xFF999999};
        Bitmap bm = Bitmap.createBitmap(pixels, 1, 8, Bitmap.Config.ARGB_8888);
        Shader shader = new BitmapShader(bm,
            Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        bg.getPaint().setShader(shader);
        abs.setBackgroundDrawable(bg);

        
        tempRect t = new tempRect(this);
        @SuppressWarnings("deprecation")
		AbsoluteLayout.LayoutParams l = new AbsoluteLayout.LayoutParams(0, 0, 400, 400);
        t.setLayoutParams(l);
        abs.addView(t);
    }
}
package com.binaryme.graphics;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.Button;

public class graphics extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AbsoluteLayout rootLayout = new AbsoluteLayout(this);    
        rootLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        rootLayout.setBackgroundColor(Color.WHITE);
       
        GraphicsView mGraphicsView = new GraphicsView(getApplicationContext());
        //Measuring the View
        mGraphicsView.measure(	MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 
        						MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED) );
        int measuredHeight =  mGraphicsView.getMeasuredHeight();
       	int measuredWidth  =  mGraphicsView.getMeasuredWidth();
        
        mGraphicsView.setLayoutParams( new AbsoluteLayout.LayoutParams(measuredWidth,measuredHeight,30,50) );
        rootLayout.addView(mGraphicsView);

        //set the View for presenting the Activity content
        setContentView(rootLayout);
    }
}
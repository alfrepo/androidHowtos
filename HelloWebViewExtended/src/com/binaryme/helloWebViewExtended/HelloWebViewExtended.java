package com.binaryme.helloWebViewExtended;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.TextView;

public class HelloWebViewExtended extends Activity {
		
    /** Called when the activity is first created. */
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //PINCH VIEW
//        View pinch = new View(this);
//        PinchView pv = new PinchView(pinch);
        
        setContentView(R.layout.main);
        
        //find the canvas
        WebViewCanvas mCanvas = (WebViewCanvas) findViewById(R.id.mCanvas);
        mCanvas.setBackgroundColor(Color.YELLOW);

        
        
        
	//BUTTON
		TextView b = new TextView(this);
		b.setText("The second Text on the Button");
		
		int bx=530;
		int by=440;
		AbsoluteLayout.LayoutParams butPar = new AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, bx,by );  
		b.setLayoutParams(butPar);
		
		mCanvas.addView(b);
		
		
		
        
	//SIMPLE GRAPHIC VIEW
        SimpleGraphicView graView = new SimpleGraphicView(getApplicationContext(), mCanvas);
        
        //add block, on Position posX, posY
       	int posX3= 640;
       	int posY3= 450;
       	graView.setLayoutParams( new AbsoluteLayout.LayoutParams(0,0,posX3,posY3) );	//setting the Layout, like Size and Position
        
        mCanvas.addView(graView);
        mCanvas.addChild(graView);
        
        mCanvas.invalidate();

    }
        	

}
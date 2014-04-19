package com.binaryme.hellogallery;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.Gallery;
import android.widget.LinearLayout;

public class helloGallery extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Gallery g = (Gallery) findViewById(R.id.gallery1);

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams( new LayoutParams(
        	 LayoutParams.MATCH_PARENT,
			 LayoutParams.WRAP_CONTENT));
        
        g.addView(ll);        
        
//        //get the Content View for adding text
//        LinearLayout content = (LinearLayout) findViewById(R.id.llContentHorizontal); 
//        
//        for (int i=0; i<20; i++){
//        	TextView tv = new TextView(this);
//        	tv.setText("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
//        	tv.setLayoutParams(new LayoutParams(
//        			 LayoutParams.MATCH_PARENT,
//        			 LayoutParams.WRAP_CONTENT));
//
//        	content.addView(tv);
//        }
        
        
        
    }
}
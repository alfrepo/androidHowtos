package com.binaryme.helloDualScrollable;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabActivity extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the Context View
        setContentView(R.layout.mscroll);
        
        // add orizontalView to tab
//        SnappyHorizontalScrollView horizontal = new SnappyHorizontalScrollView(this);
//        horizontal.setLayoutParams( new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ) );
//        horizontal.setId(123456);

        //add LinearLayout for content 
//        LinearLayout ll = new LinearLayout(this);
//        ll.setLayoutParams( new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT) );
//        ll.setOrientation(LinearLayout.VERTICAL);
//        horizontal.addView(ll);
        LinearLayout ll = (LinearLayout) findViewById(R.id.llContentHorizontal);
        
        //add Text to Linear Layout
        for (int i=0; i<20; i++){
        	TextView tv = new TextView(this);
        	tv.setText("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
        	tv.setLayoutParams(new LayoutParams(
        			 LayoutParams.MATCH_PARENT,
        			 LayoutParams.WRAP_CONTENT));

        	ll.addView(tv);
        }
        

        
	}
}

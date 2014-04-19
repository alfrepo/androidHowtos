package com.binaryme.hellotabs;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TextView;

public class helloTabs extends TabActivity {
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
        TabHost tabHost = getTabHost();
        
        LayoutInflater.from(this).inflate(R.layout.tab, tabHost.getTabContentView(), true);

        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("tab1")
                .setContent(R.id.tabLayoutHorizontalScrollView));

        tabHost.addTab(tabHost.newTabSpec("tab2")
        		.setIndicator("tab2")
        		.setContent(R.id.tabLayoutHorizontalScrollView));
        
        //add Text
        TextView tv = (TextView) findViewById(R.id.ordersLabel);
        tv.setText("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
    
    }
}
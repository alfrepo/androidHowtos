package com.binaryme.helloExternalScrollbar;

import android.app.Activity;
import android.os.Bundle;

public class HelloExternalScrollbar extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ScrollContainerWithExternalScrollbar scrollCont = (ScrollContainerWithExternalScrollbar) findViewById(R.id.horizontalScrollViewOutsourcing);
        ExternalScrollbar es = (ExternalScrollbar) findViewById(R.id.externalScroller);
        scrollCont.setExternalScrollbar(es);
    }
}
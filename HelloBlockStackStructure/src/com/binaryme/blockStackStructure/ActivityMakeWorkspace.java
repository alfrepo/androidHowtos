package com.binaryme.blockStackStructure;

import android.app.Activity;
import android.os.Bundle;

public class ActivityMakeWorkspace extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Workspace w = new Workspace(getApplicationContext());
        setContentView(w);
    }
}
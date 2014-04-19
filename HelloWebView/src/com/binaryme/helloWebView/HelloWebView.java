package com.binaryme.helloWebView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class HelloWebView extends Activity {
	
	final Activity activity = this;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.main);
        
        // Hide the Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        
        
        // Enable Javascript, Disable Zoom
        WebView myWebView = (WebView) findViewById(R.id.mWebView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        
        //display console errors as a Toast
        //in the Webview, I will return Error messages to the console, so they can be returned here
        myWebView.setWebChromeClient(new WebChromeClient() {
        	public boolean onConsoleMessage(ConsoleMessage cm) {
        		
        		String errormsg =	"Error Message: " + cm.message() + "\n" +
        							"Source: " + cm.sourceId() + "\n" + 
        							"Line: " + cm.lineNumber();
        		
                // prepare the alert box
//                AlertDialog.Builder alertbox = new AlertDialog.Builder(getApplicationContext());
        		AlertDialog alertbox = new AlertDialog.Builder(activity).create();
                
                // set the message to display
                alertbox.setMessage(errormsg);
                     
                // display box
                alertbox.show();
                return true;
                
        		
//				Toast.makeText(getApplicationContext(), "Error occured: "+ cm.message(), Toast.LENGTH_LONG).show();
//			    return true;
        	}
        });
        
        //open url
        myWebView.loadUrl("http://binary.me/scratchpad/prototype2/index.html");
    }
	
}
	
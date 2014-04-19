package com.binaryme.hellohandler;

import com.binaryme.hellohandler.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetProvider extends AppWidgetProvider {
	
	public static String TAG = "MyActivity";
	
	public static String ACTION_WIDGET_BUTTON = "actionWidgetButton";
	
	private int count = 0;
	private int[] widgetIds;
	private AppWidgetManager appWidgetManager;
	private Context context;
	
    public Handler handler = new Handler() {
    	public void handleMessage(Message msg) {
    		switch(msg.what) {
    		case 0:  // We are being told to do a UI update
    			// If more than one UI update is queued up, we only need to do one.
    			removeMessages(0);
    			Log.d(TAG, "Handler.handleMessage: Here is just before starting updatUi()");
    			updateUi();
    			break;
    		case 1:  // We are being told to display an error message
    			removeMessages(1);
    		}
    	}
    };
	
	/**
	 * Adds Button Listeners to Widget-buttons, register Handlers for central UI Updates
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.d(TAG,"onUpdate");
		
		//remember Widget Instances' ids, context, appWidgetManager
		this.widgetIds = appWidgetIds;
		this.context = context;
		this.appWidgetManager = appWidgetManager;
		
		//add ButtonListener
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
		
		Intent active = new Intent(context, WidgetProvider.class);
		active.setAction(ACTION_WIDGET_BUTTON);
		
		PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);	
		remoteViews.setOnClickPendingIntent(R.id.WidgetButton, actionPendingIntent);
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
		
		//add my Handler
		UiHandler.registerClient(handler);
	}
	
	@Override
	public void onReceive(Context context, Intent intent){
		Toast.makeText(context, "onReceive", Toast.LENGTH_SHORT).show();
		Log.d(TAG,"onReceive");
		
		if( intent.getAction().equals(ACTION_WIDGET_BUTTON) ){
			
			//here is always true: count=1
			//does it mean, that onReceive does not belong to this Object?
			
			Log.d(TAG, "onReceive: ObjectID = "+System.identityHashCode(this));
			Toast.makeText(context, "A Widget button was pushed", Toast.LENGTH_SHORT).show();
			UiHandler.updateClients();
		}
		
		super.onReceive(context, intent);
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
//		UiHandler.unregisterClient(handler);
	}
	
	
	/**
	 * Updates
	 */
	public void updateUi(){
		
		Log.d(TAG,"updateUi: Object ID: "+System.identityHashCode(this) +"  ,  this.count: "+this.count);
		this.count = this.count + 1;
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
		//change Textview Text
		
		remoteViews.setTextViewText(R.id.WidgetTextView, Integer.toString(this.count) );
//		remoteViews.setTextViewText(R.id.WidgetTextView, Integer.toString(System.identityHashCode(this)) );
		appWidgetManager.updateAppWidget(this.widgetIds, remoteViews);
	}
	
	
}

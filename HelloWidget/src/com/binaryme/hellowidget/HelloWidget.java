package com.binaryme.hellowidget;

import com.binaryme.hellowidget.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class HelloWidget extends AppWidgetProvider{
	
	public static String APPWIDGET_BUTTON = "ActionReceiverWidget";

	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Toast.makeText(context, "onUpdate", Toast.LENGTH_SHORT).show();

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.example_appwidget);
		
		Intent active = new Intent(context, HelloWidget.class);
		active.setAction(APPWIDGET_BUTTON);
		
		//getBROADCAST(), because AppWidgetProvider which this class extends, is a better BroadcastReceiver.
		PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);	
		remoteViews.setOnClickPendingIntent(R.id.widgetButton, actionPendingIntent);
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	}
		

	
	@Override
	public void onReceive(Context context, Intent intent) {
	
		String action = intent.getAction();
//		android.os.Debug.waitForDebugger();
		
		// check, if our Action was called
		if (intent.getAction().equals(APPWIDGET_BUTTON)) {
			Toast.makeText(context, "My own fckin Button", Toast.LENGTH_SHORT).show();
							
		} else {
			// do nothing
		}
		
		/*
		 * VERY IMPORTANT!
		 * OnReceive redirects all Intents, to other Methods. 
		 * So, if we override onReceive - we should call super() in onReceive, 
		 * otherwise other Methods, like onUpdate() etc will never be called.
		 */
		super.onReceive(context, intent);
	}

}

package com.paywyw.helloactionbar;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static Activity activity;
	private Menu mMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_main);
		
		initHideBarButton();
		initShowBarButton();
	}
	

	// create the ActionbarMenu here
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.mMenu = menu;

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// callback, triggeredn on actionbar selection
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_item1) {
			Toast.makeText(getApplicationContext(), "Action Item clicked", Toast.LENGTH_LONG).show();
			return true;
		}else if (id == R.id.action_provider_sync) {
			addAnimatedItem();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	// ACTIONBAR METHODS
	private void initHideBarButton(){
		Button button = (Button) findViewById(R.id.buttonhideActionBar);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// find Actionbar
				ActionBar actionBar = getActionBar();
				actionBar.hide();
				
			}
		});
	}
	
	private void initShowBarButton(){
		Button button = (Button) findViewById(R.id.buttonshowActionBar);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// find Actionbar
				ActionBar actionBar = getActionBar();
				actionBar.show();
				
			}
		});
	}
	
	private void addAnimatedItem(){
		final MenuItem item = mMenu.findItem(R.id.action_provider_sync);
		item.setActionView(R.layout.actionbar_indeterminate_progress);
		
		Toast.makeText(getApplicationContext(), "Sync On", Toast.LENGTH_SHORT);
		
		item.getActionView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				item.setActionView(null);
				Toast.makeText(getApplicationContext(), "Sync Offffff", Toast.LENGTH_SHORT);		
			}
		});
		
		
	}

}

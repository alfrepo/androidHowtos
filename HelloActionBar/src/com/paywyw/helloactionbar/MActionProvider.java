package com.paywyw.helloactionbar;

import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

public class MActionProvider extends ActionProvider {
	
	Context mContext;

	public MActionProvider(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context){
		this.mContext = context;
	}


	@Override
	public View onCreateActionView() {
		/*
		 * Deprecated method.
		 * Return null so that the onPrepareSubMenu() gets called.
		 */
		return null; 

	}
	
	
	//submenu
	@Override
	public void onPrepareSubMenu(SubMenu subMenu) {
		// this method is called on every click
		if(subMenu.hasVisibleItems()){
			return;
		}
		
		/*
		 * - SubMenu is not supported.
		 * - onCreateActionView() has to return null, in order this method to be called.
		 */
		MenuItem itemPuenktlich = subMenu.add("Bus ist pünktlich").setIcon(R.drawable.ic_launcher);
		MenuItem itemSpaet = subMenu.add("Bus ist zu spät").setIcon(R.drawable.ic_launcher);
		
		// callback
		itemPuenktlich.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Toast.makeText(mContext, "Puenktlich", Toast.LENGTH_LONG).show();
				return true;
			}
		});
	}
	
	@Override
	public boolean hasSubMenu() {
		return true;
	}
}

package com.paywyw.helloactionbar;

import android.content.Context;
import android.view.ActionProvider;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Interactive Views may be displayed in the actionbar.
 * This one shows a sync icon.
 * @author skip
 *
 */
public class MActionProviderSync extends ActionProvider {
	
	Context mContext;
	SubMenu mSubMenu;
	OnClickListener onClickListener; // called when animation is running and clicked

	public MActionProviderSync(Context context) {
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
		mSubMenu = subMenu;
	}
	
	
	@Override
	public boolean hasSubMenu() {
		// important, or onPrepareSubMenu wont be triggered
		return true;
	}
	
	@Override
	public boolean onPerformDefaultAction() {
		startAnimation();
		return super.onPerformDefaultAction();
	}
	
	public void setOnAnimationCLickListener(OnClickListener clickListener){
		onClickListener = clickListener;
	}
	
	public void startAnimation(){
	     if(mSubMenu!=null && mSubMenu.getItem()!=null){
	    	 mSubMenu.getItem().setActionView(R.layout.actionbar_indeterminate_progress);
	    	 mSubMenu.getItem().getActionView().setOnClickListener(onClickListener);
	     }
	}
	
	public void stopAnimation(){
		if(mSubMenu!=null && mSubMenu.getItem()!=null){
			 // stop the animation
	    	 mSubMenu.getItem().setActionView(null);
	    }
	}
	
}

package com.paywyw.helloactionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

/**
 * Interactive Views may be displayed in the actionbar
 * @author skip
 *
 */
public class ActionView extends View {
	
	Context mContext;

	public ActionView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public ActionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ActionView(Context context) {
		super(context);
		init(context);
	}

	
	void init(Context context){
		mContext = context;
		setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
		
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "Clicked View", Toast.LENGTH_LONG).show();
				setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
				setBgColorBackDelayed();
			}
		});
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(50, 50);
	}
	
	/**
	 * After a second sets BG to orange
	 */
	private void setBgColorBackDelayed(){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				
				MainActivity.activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
						ActionView.this.invalidate();
					}
				});
			}
		});
		t.start();
	}
}

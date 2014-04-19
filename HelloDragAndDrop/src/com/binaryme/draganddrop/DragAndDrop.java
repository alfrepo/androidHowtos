package com.binaryme.draganddrop;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DragAndDrop extends Activity {
	
	protected TestDraggable mTestDraggable;
	protected DragShadowBuilder mShadowBuilder;
	protected MyDragShadow mDragShadow = new MyDragShadow();
	
	protected Button button1;
	protected Button button2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    	mTestDraggable = new TestDraggable(this);
    	mTestDraggable.onMeasure(0, 0);
    	mTestDraggable.requestLayout();
    	mTestDraggable.invalidate();
    	
        
        /**
         * making Buttons draggable on long click
         */
        button1 = (Button) findViewById(R.id.Button01);
        button2 = (Button) findViewById(R.id.Button02);
        
        mShadowBuilder = new DragShadowBuilder(mTestDraggable);
        
        
        
        
        OnLongClickListener lcListener = new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				
				Button b = (Button) v;
				String btext = (String) b.getText();
				
				//ClipData.Item simply contains some Information to pass on Drop, like STRING, URI, INTENT 
				ClipData.Item item = new ClipData.Item(btext);
				// ClipData is a container for ClipData.Items. It knows: 
				ClipData dragData  = new ClipData(b.getText(),  						// Optional Label for saved ClipData.Items
													new String[] {"text/plain"},		// MIME Type as an Array
													item								// the ClipData.Item with the Information
													);
								
				//starting the Drag event, letting the system fire an ACTION_DRAG_STARTED event
				v.startDrag(dragData, mDragShadow, null, 0);
				
				return true;
			}
		};
		
		//let Buttons be draggable on longclick
		button1.setOnLongClickListener(lcListener);
		button2.setOnLongClickListener(lcListener);
		
		
		
		/**
		 * Let Textview listen react dragging
		 */
		OnDragListener odl = new OnDragListener() {
			@Override
			public boolean onDrag(View v, DragEvent event) {
				boolean result = false;
				
				switch(event.getAction()){
					case DragEvent.ACTION_DRAG_STARTED :
						Log.d("drag","ACTION_DRAG_STARTED");
						if(event.getClipDescription().hasMimeType("text/plain")){
							result=true;	
						}
						break;
						
					
					case DragEvent.ACTION_DRAG_EXITED :
						v.setBackgroundColor(Color.rgb(256, 00, 256));
						v.invalidate();
						Log.d("drag","ACTION_DRAG_EXITED");
						result=false;
						break;
						
					case DragEvent.ACTION_DRAG_ENTERED :
//						mShadowBuilder.color = Color.GREEN;
//						mTestDraggable.color = Color.GREEN;
						button1.setBackgroundColor(Color.RED);
						button1.invalidate();
						button2.setBackgroundColor(Color.GREEN);
						button2.invalidate();
						
						Log.d("shadow","ACTION_DRAG_ENTERED The shadow color should change");
						
						v.setBackgroundColor(Color.YELLOW);
						v.invalidate();
						Log.d("drag","ACTION_DRAG_ENTERED");
						result=true;
						break;
						
					case DragEvent.ACTION_DRAG_LOCATION :
						Log.d("drag","ACTION_DRAG_LOCATION: "+event.getX()+" y:"+event.getY());
						result=true;
						break;
						
					case DragEvent.ACTION_DROP :
						ClipData clip = event.getClipData();
						ClipData.Item clipItem = clip.getItemAt(0);
						CharSequence clipItemText = clipItem.getText();
						
						//change Background back to white
						v.setBackgroundColor(Color.RED);
						
						//add dropped Text to the TextView
						TextView tv = (TextView) v;
						tv.setText(clipItemText);
						
						v.invalidate();
						
						Log.d("drag", "ACTION_DROP: "+ event.getAction());
						
						Toast.makeText(getApplicationContext(), clipItemText, Toast.LENGTH_SHORT).show();
						result=true;
						break;
						
					case DragEvent.ACTION_DRAG_ENDED:
						Log.d("drag", "ACTION_DRAG_ENDED: "+ event.getAction());
						break;
						
					default:
						Log.d("drag", "Action not catched: "+ event.getAction());
						break;
				}
				return result;
			}
		};
		
		TextView tv = (TextView) findViewById(R.id.TextView);
		tv.setOnDragListener(odl);
		
		TextView tv2 = (TextView) findViewById(R.id.TextView2);
		tv2.setOnDragListener(odl);
		
		/**
		 * Let Textview listen react Drops
		 */
		

    }
}
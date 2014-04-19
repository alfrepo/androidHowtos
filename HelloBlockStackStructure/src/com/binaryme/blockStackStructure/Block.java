package com.binaryme.blockStackStructure;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public abstract class Block extends RelativeLayout {
	
	protected View mVisualRepresentation;	
	
	public Block(Context context) {
		super(context);
		
		//set id needed for using RelativeLayout 
		this.setId(mUtils.generateUniqueId());

		/*
		 * EveryBlock should wrap it's content, which are:  
		 * 		it's visual representation
		 * 		other Blocks, connected to it through slots.
		 * Since the upper elements are drawn inside of Block and so they are the Content of the Block 
		 * If all Blocks wrap their content - they should be big enough to display the Block and other connected Blocks.  
		 */
		this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		
		/*
		 * Here we add the Visual Representation of the concrete Block, which will be added inside of the abstract method "initmVisualRepresentation(View v)"
		 * This is abstract method will be implemented by concrete Blocks.
		 */
		this.initmVisualRepresentation();
		this.addView(mVisualRepresentation);
	}


	
	
	public View getmVisualRepresentation() {
		return mVisualRepresentation;
	}




	public void setmVisualRepresentation(View mVisualRepresentation) {
		this.mVisualRepresentation = mVisualRepresentation;
	}




	/**
	 * This Method is a reminder.
	 * It should initiate the private Variable "View mVisualRepresentation" 
	 * which is a View responsible for drawing a visual representation of a concrete Block.
	 * Choose the appropriate VisualRepresentation... class to initiate mVisualRepresentation.
	 */
	protected abstract void initmVisualRepresentation();
	
	/**
	 * Every concrete Block will implement methods to add Blocks to special Slots e.g. addToSlotBootom(Block newBlock)
	 * In those methods the LayoutParams will be set. 
	 */
	
}

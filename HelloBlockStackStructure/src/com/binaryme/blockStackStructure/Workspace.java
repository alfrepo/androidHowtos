package com.binaryme.blockStackStructure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.AbsoluteLayout;


public class Workspace extends AbsoluteLayout {
	
	public Workspace(Context context) {
		super(context);
		
        this.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        this.setBackgroundColor(Color.WHITE);
       
        /*
         * TEST:
         * zu Testzwecken erstellen wir zusammen mit diesem Workspace auch einen Block.
         * Blocks werden in der Application durch drag-and-drop auf den Workspace erstellt werden.
         */
                
        BlockIf mBlock = new BlockIf(getContext());
        BlockForever mBlock2 = new BlockForever(getContext());

        mBlock.addToSlotBottom(mBlock2);
        this.addBlock(mBlock, 50, 50);       
        
        /*
         * TEST END.
         */

	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}
	
	
	/**
	 * This Method adds a {@link Block} to the workspace. The Layout is set, when adding the Block, 
	 * because the Layout contains the position of the new Block, and the Position is only known, when dropping the Block to the workspace, 
	 * with the intention of adding the Block to the workspace.
	 *  
	 * @param newblock
	 * @param posX
	 * @param posY
	 */
	public void addBlock(Block newblock, int posX, int posY){
        //Measuring the View. The Blocks should know themselves how big they should be.
		newblock.measure(	MeasureSpec.makeMeasureSpec(9999, MeasureSpec.UNSPECIFIED), 
        						MeasureSpec.makeMeasureSpec(9999, MeasureSpec.UNSPECIFIED) );
        int measuredHeight =  newblock.getMeasuredHeight();
       	int measuredWidth  =  newblock.getMeasuredWidth();
       	
		newblock.setLayoutParams( new AbsoluteLayout.LayoutParams(measuredWidth,measuredHeight,posX,posY) );	//setting the Layout, like Size and Position

		this.addView(newblock);				//adding the Block to the workspace
	}
			
	public void removeBlock(Block block){
		
	}
	

}

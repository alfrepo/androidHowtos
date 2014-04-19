package com.binaryme.hello2DDrawing;

import android.view.WindowManager;


/**
 * @author Alexander Friesen
 * This class contains custom Metric units. 
 *
 */
public class M {
	
	private static float CMperINCH = 2.54f;
	
	
	public static float MMinPx; //1 mm in px on current display
	public static float CMinPx; //1 cm in px on current display
	
	
	
	private static void calculatePixelsPerCM(){	
		//CALCULATIONS
		//assumed the xdpi rawly equals ydpi, so calculate one common value for density-independent xdpi/ydpi based on xdpi
		CMinPx =  149 / CMperINCH ; 
		MMinPx = CMinPx / 10;
		
	}
	
	public static int mm2px(int mm){
		calculatePixelsPerCM();
		return Math.round(MMinPx *mm);
	}
	
	public static int cm2px(int cm){
		calculatePixelsPerCM();
		return Math.round(CMinPx *cm);
	}

}

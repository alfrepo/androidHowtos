package com.binaryme.hello2DDrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.view.View;

public class mDrawView extends View {

	public mDrawView(Context context) {
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		canvas.drawColor(Color.WHITE); //make the whole canvas white
		
	//PATHS
		
		/*
		 *  translate()
		 *	When doing canvas.translate(x,y) - path is moved, together with the 0,0 coordinates.
		 *	Path can be used like a stencil, FILL/STROKE then translate , FILL/STROKE then translate
		 *
		 *	Coordinates are growing from upper, left  to  bottom, right corner.
		 * 
		 */
		
		//Prepare Colors
		Paint strokepaint = new Paint();
		strokepaint.setColor(Color.BLACK);
		strokepaint.setStyle(Style.STROKE); //switch Paint into Stroke mode
		strokepaint.setStrokeWidth(5);		//set strength of the paintbrush
		
		Paint fillpaint = new Paint();		
		fillpaint.setColor(Color.MAGENTA);
		fillpaint.setStyle(Style.FILL);		//switch Paint into Fill mode
		
		strokepaint.setAntiAlias(true);	//enable anti aliasing
		fillpaint.setAntiAlias(true);	//enable anti aliasing
		
		
		
		//CLOSED SHAPES 
		Path cpath = new Path();				//create a path for closed figures
		
		cpath.addCircle(50, 50, 50, Path.Direction.CCW);
		cpath.offset(0, -110);  		//moves whole paths we have drawn until now. Like translate for whole canvas.
		cpath.addRect(0,0,100,100, Path.Direction.CCW);
		cpath.offset(0, -110);
		cpath.addRoundRect( new RectF(0,0,100,100) , 10, 20, Path.Direction.CCW);
		cpath.offset(0, -110);
		cpath.addOval(new RectF(0,0, 100, 80), Path.Direction.CCW);
		
		cpath.offset(0, 320);			//move the path back
		
		fillStrokePath(canvas, Path.FillType.WINDING, strokepaint, fillpaint, cpath);	 //making the Path visible	
		
		
		
		
		
		
		
		
		//OPEN SHAPES
		canvas.translate(200,0); 			//move 0,0 to the right
		Path opath = new Path();			//create an open path
		
		
		opath.moveTo(0, 0);
		opath.quadTo(0, 100, 100, 100);		//quadTo - gebogene Kurve über einen zusätzlichn Punkt
		opath.close();						//a line is drawn to the path's first point.
		opath.offset(0, -150);
		
		opath.moveTo(0, 0);
		opath.addCircle(0, 0, 5, Direction.CCW);
		opath.lineTo(10, -20);
		opath.lineTo(20, 20);
		opath.lineTo(30, -20);
		opath.lineTo(40, 20);
		opath.lineTo(50, -20);
		opath.lineTo(60, 20);
		opath.lineTo(70, -20);
		opath.lineTo(70, 40);
		opath.lineTo(0, 40);
		opath.close();
		opath.offset(0, -70);
		
		opath.moveTo(0,0);
		opath.addCircle(0, 0, 5, Direction.CCW);
		opath.arcTo(new RectF(10, 50, 90, 200), 240, 90);
		opath.lineTo(80, 0);
		opath.close();
		opath.offset(0, -100);
		
		opath.moveTo(0,0);
		opath.addCircle(0, 0, 5, Direction.CCW);
		opath.addRect(new RectF(0, 0, 80, 200), Direction.CCW);
		opath.moveTo(40,100);
		opath.lineTo(80,100);
		opath.moveTo(40,100);
		opath.lineTo(0,100);		
		/*
		 * Arc is drawn as following:
		 * - Draw an Oval inside of given Rectangle
		 * - From the middle of the oval draw imaginary lines, at angles a, b measured clockwise, 0 degree is horizontal to the right
		 * - in between of 2 cut points, where lines cut the oval the ovel becomes visible 
		 */
		opath.addArc(new RectF(0, 0, 80, 200),  0, 180);
		
		opath.offset(0, 320);
		strokePath(canvas, Path.FillType.WINDING, strokepaint, fillpaint, opath);	 //making the Path visible
		
		
		
		
		
		
		
		
		
		//TEXT
		canvas.translate(200,0); 			//move 0,0 to the right

		canvas.save();		//beginning from now, i can undo all translate, scale, rotate, skew, concat operations by restore()

		//text settings
		Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(0);
//        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setTextSize(18);
        textPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        textPaint.setColor(Color.WHITE);
        
        String text = "Wenn [     ] weniger als [     ] ist";
        Rect    bounds = new Rect();
        float[] widths = new float[text.length()];

        int count = textPaint.getTextWidths(text, 0, text.length(), widths);
        float w = textPaint.measureText(text, 0, text.length());
        textPaint.getTextBounds(text, 0, text.length(), bounds); //saves the data in bounds
        
        
        
		//Fill my part of the working area
        int maxWidth = M.mm2px(45);
		canvas.clipRect(0, 0, maxWidth, 500);
		canvas.drawColor(Color.YELLOW);	
        canvas.translate(0, bounds.height()); 			//translate the 0,0 down and right
        
        //draw text
        canvas.drawRect(bounds, fillpaint);
        canvas.drawText(text, 0, 0, textPaint);        
        
        
        //DRAW MORE TEXT
        
		super.onDraw(canvas);
	}

	
	private void fillStrokePath(Canvas canvas, Path.FillType f, Paint strokepaint,  Paint fillpaint,  Path path){
		canvas.save();		//beginning from now, i can undo all translate, scale, rotate, skew, concat operations by restore()

		//Fill my part of the working area
		canvas.clipRect(0, 0, 150, 500);
		canvas.drawColor(Color.YELLOW);	
		canvas.translate(25, 25); 			//translate the 0,0 down and right	
		
		//Stroke the path
		canvas.drawPath(path,strokepaint);		//stroke the path	
		
		
		//Fill the path
		canvas.translate(-2, -2);			//move canvas up and left, so that fill fills the most of the stroke, except a little shadow
		path.setFillType(f);
		canvas.drawPath(path,fillpaint);
		
		canvas.restore();	// set the canvas transformations back
	}
	
	private void strokePath(Canvas canvas, Path.FillType f, Paint strokepaint,  Paint fillpaint,  Path path){
		canvas.save();		//beginning from now, i can undo all translate, scale, rotate, skew, concat operations by restore()

		//Fill my part of the working area
		canvas.clipRect(0, 0, 150, 650);
		canvas.drawColor(Color.YELLOW);	
		canvas.translate(25, 25); 			//translate the 0,0 down and right	
		
		//Stroke the path
		canvas.drawPath(path,strokepaint);		//stroke the path	
		
		canvas.restore();	// set the canvas transformations back
	}
}

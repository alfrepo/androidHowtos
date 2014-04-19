package com.binaryme.ScratchTab.Gui.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.binaryme.LayoutZoomable.ScaleEventListener;
import com.binaryme.LayoutZoomable.ScaleHandler;
import com.binaryme.tools.M;


public class MTextField extends View implements  ScaleEventListener, OnKeyListener {
	
	float paddingLeft 		= 5;
	float paddingRight 		= 5;
	float paddingTop 		= 5;
	float paddingBottom 	= 5;
	
	float mScaledPaddingLeft 		= paddingLeft;
	float mScaledPaddingRight 		= paddingRight;
	float mScaledPaddingTop 		= paddingTop;
	float mScaledPaddingBottom 		= paddingBottom;
	
	private String mTextBuffer="";
	private CursorHandler mCursorHandler = new CursorHandler();
	
	private int minWidth	= 20;					//TODO: load this from config
	private float textSize	= 20; 					//TODO: load this from config
	private Paint textPaint 		= new Paint();	//TODO: load this from config. Saves the default text size.
	private Paint scaledTextPaint	= new Paint();	//TODO: load this from config. Saves the current text size with respect to scale.
	private int textColor			= Color.BLACK;			//TODO: load this from config
	private int textSelectedColor	= Color.WHITE;			//TODO: load this from config
	
	private Paint textSelectedPaint	= new Paint();			
	
	private Rect mScaledTextBounds = new Rect();
	private boolean isTextSelected = false;
	
	private int mUnscaledWidth 	= 0;
	private int mUnscaledHeight = 0;
	private int mScaledWidth 	= 0;
	private int mScaledHeight 	= 0;
	
    private static final String TAG = "TextField";
    
    private GestureDetector gestureDetector = new GestureDetector(new MGestureListener());
    
//CONSTRUCTORS
    public MTextField(Context context) {
        super(context);
        setFocusableInTouchMode(true); // allows the keyboard to pop up on touch down
        
        //init the color of the selection rectangle. It should be the color of the text, which is the inverted color of selected text.
        textSelectedPaint.setColor(textColor);
        
        //add listeners
        this.setOnKeyListener(this);
        ScaleHandler.addScaleEventListener(this);
        
        setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if(isFocused()){
					//the MTextField obtained the focus. What should we do? Select the text, position the cursor after the last character?
					
				}else{
					//deselect
					isTextSelected = false;
				}
			}
		});
        
        this.textPaint.setTextSize(textSize);		//initiate the Paint with the text size
    }
	public MTextField(Context context, AttributeSet attrs,	int defStyle) {
			this(context);
	}
	public MTextField(Context context, AttributeSet attrs) {
		this(context);
	}
	
	
//EVENT HANDLERS

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        
        this.requestFocus();
        Log.d("TextField2", "TouchEvent "+M.motionEventResolve(event.getAction()));
        
        //pass the touch to the gesture detector to handle double or long touches
        gestureDetector.onTouchEvent(event);
        
        
        if (event.getAction() == MotionEvent.ACTION_UP) {
        	
            // show the keyboard so we can enter text
            InputMethodManager imm = (InputMethodManager) this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(this, InputMethodManager.SHOW_FORCED);
            
            //reposition the cursor
            mCursorHandler.repositionCursor(event.getX());
            
            Log.d(TAG, "Cursor position "+mCursorHandler.cursorPosXchars);
        }
        return true;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        Log.d(TAG, "onCreateInputConnection");

        BaseInputConnection fic = new BaseInputConnection(this, false); //false puts the view into a dummy mode, required to order raw key events to be sent to the current view.
//        BaseInputConnection fic = new BaseInputConnection(this, true);
        
        outAttrs.actionLabel = null;

        outAttrs.inputType = InputType.TYPE_NULL; //run connection in a "generate key event mode"
//        outAttrs.inputType = InputType.TYPE_CLASS_TEXT;

        
        outAttrs.imeOptions = EditorInfo.IME_ACTION_NEXT;
        return fic;
    }
    

    @Override
    public boolean onCheckIsTextEditor() {
        Log.d(TAG, "onCheckIsTextEditor");
        return true;
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
    	
    	

    	Log.d(TAG, "onKeyListener");
        if (event.getAction() == KeyEvent.ACTION_DOWN) {

        	//handle selection
        	if(this.isTextSelected){
	    		this.isTextSelected = false;	//deselect
	    		this.mTextBuffer = "";			//delete all the text
	    		this.mCursorHandler.setCursorAtPosition(0);
	        }
            
            switch(keyCode) {
				case KeyEvent.KEYCODE_DEL:
					delText();
					break;

				default:
	            	//respect the meta state (SHIFT, etc.)
	            	KeyCharacterMap map = event.getKeyCharacterMap();
	            	
					// Perform action on key press
                	int unicodeCharCode = event.getUnicodeChar(event.getMetaState());
                	
                	if(map.isPrintingKey(keyCode)){
	                	char cc = (char)unicodeCharCode;
	                    insertText(cc);	
                	}
					break;
			}	
                
            requestLayout();
            invalidate();
            return true;
        }
        
        //measure the new text
        scaledTextPaint.getTextBounds(this.mTextBuffer, 0, mTextBuffer.length(), mScaledTextBounds);
        
        return true;
    }


    
    
    
//DRAWING
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    	
    	//measure the unscaled text
    	Rect bounds = new Rect();
    	textPaint.getTextBounds(this.mTextBuffer, 0, mTextBuffer.length(), bounds);
    	
    	mUnscaledWidth  = (int) (paddingLeft + bounds.width()  + paddingRight);
    	mUnscaledHeight = (int) (paddingTop  + bounds.height() + paddingBottom);
    	
    	//respect the scaling level
    	this.onScaleEvent(ScaleHandler.getScale(), new Point(0,0));
    	
    	//respect the minimum size
    	int scaledW = (int) Math.max(mScaledWidth,  ScaleHandler.getScale() * (paddingLeft + minWidth + paddingRight) );
    	int scaledH = (int) Math.max(mScaledHeight, mScaledPaddingTop + scaledTextPaint.getTextSize() + mScaledPaddingBottom ); 
    	
    	setMeasuredDimension(scaledW, scaledH);
    }
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        
        float posX = this.mScaledPaddingLeft;
        float posY = scaledTextPaint.getTextSize();
//        float posY = this.mScaledHeight;
//        float posY = Math.abs(scaledTextPaint.ascent()) + this.mScaledPaddingTop;
        
        
        //XXX disable selection
        //handle selection
        if(isTextSelected){
        	scaledTextPaint.setColor(textSelectedColor);
        	
        	float selectionLeft		= 	this.mScaledPaddingLeft-2;
        	float selectionTop		=	this.mScaledPaddingTop-2;
        	float selectionRight	=	mScaledTextBounds.width()+this.mScaledPaddingLeft +2+2 ;
        	float selectionBottom	=	scaledTextPaint.getTextSize()+this.mScaledPaddingTop +2 ;
        	
            //draw the selection square
            canvas.drawRect(selectionLeft, selectionTop, selectionRight, selectionBottom, textSelectedPaint);
        }
        else{
        	scaledTextPaint.setColor(textColor);
        }
        
        //draw the text
        canvas.drawText(mTextBuffer, posX, posY, scaledTextPaint);
        
//        Log.d(TAG, "Textbuffer is: "+mTextBuffer);
        
    	//if this view is not focused - do not draw the cursor at all
    	if(this.isFocused()){
    		//TODO: draw the cursor
    	}
    }
    
    
    
    
    
    
//HELPER METHODS
    /** inserts a new character on cursor position, moves the cursor */
    private void insertText(char insertedText){
    	if(insertedText != 0){
	    	int cursorPos = this.mCursorHandler.getCursorPosXchars();
	    	String first = this.mTextBuffer.substring(0, cursorPos);
	    	String last = this.mTextBuffer.substring(cursorPos, mTextBuffer.length());

	    	Log.d(TAG,"Cursor pos is "+cursorPos);
	    	
	    	//merge
	    	this.mTextBuffer = first + insertedText + last;
	    	
	    	//move the cursor right
	    	mCursorHandler.moveCursorRight();
	    }
    }
    /** deletes the character left from the cursor, moves the cursor */
    private void delText(){
    	if(mCursorHandler.cursorPosXchars > 0){
	    	int cursorPos = this.mCursorHandler.getCursorPosXchars();
	    	String first = this.mTextBuffer.substring(0, cursorPos-1);
	    	String last = this.mTextBuffer.substring(cursorPos, mTextBuffer.length());
    		
	    	//merge
	    	this.mTextBuffer = first + last;
	    	
	    	//move the cursor left
	    	mCursorHandler.moveCursorLeft();
	    }
    }

    /** respect the scale */
	@Override
	public void onScaleEvent(float scale, Point pivot) {
    	this.mScaledWidth  = (int) Math.round(this.mUnscaledWidth  * scale);
    	this.mScaledHeight = (int) Math.round(this.mUnscaledHeight * scale);
    	
    	this.scaledTextPaint.setTextSize((float) (textSize*scale));
    	
        //measure the new text
        scaledTextPaint.getTextBounds(this.mTextBuffer, 0, mTextBuffer.length(), mScaledTextBounds);
    	
    	mScaledPaddingTop 		= paddingTop * scale;
    	mScaledPaddingLeft 		= paddingLeft * scale;
    	mScaledPaddingBottom 	= paddingBottom * scale;
    	mScaledPaddingRight 	= paddingRight * scale;


    	
    	this.requestLayout();
    	this.invalidate();

	}

	public String getValueAsString() {
		return mTextBuffer;
	}
	
	public void setText(String str){
		this.mTextBuffer = str;
	}


    
    
    
    
//HELPER CLASSES
	/** class will handle the touch events */
	private class MGestureListener extends GestureDetector.SimpleOnGestureListener{
		
	    @Override
	    public void onLongPress(MotionEvent e) {
	    	//select all the text
	    	isTextSelected = true;
	    }
	    
	    @Override
	    public boolean onDoubleTap(MotionEvent e) {
	    	//select all the text
	    	isTextSelected = true;
	    	return true;
	    }
	    
	    @Override
	    public boolean onSingleTapUp(MotionEvent e) {
	    	//deselect the text
        	isTextSelected = false;
	    	return true;
	    }
	    
	}
	
    /* knows handles the cursor position, selection etc. */
    private class CursorHandler{
    	int cursorPosXpixels =0; //cursor horizontal position in pixels, updated on every touchEvent and on a keyboard typo
    	int cursorPosXchars  =0; //cursor position in characters, updated on every touchEvent and on a keyboard typo. Needed to insert new text
    	
        public int getCursorPosXpixels() {
			return cursorPosXpixels;
		}
		public int getCursorPosXchars() {
			return cursorPosXchars;
		}
		

		/**
		 * used calculate the cursor's horizontal position, after this view was touched
		 * @param touchX 	- touch coordinates
		 * @param touchY 
		 * @param p			- Paint used to draw text. Needed to calculate the text parameters. 
		 * @param text		- the text itself
		 */
        protected void repositionCursor(float touchX){
        	
        	int resultCursorPosXpixels = 0;
        	int resultCursorPosXchars  = 0;
        	char[] chars = mTextBuffer.toCharArray();
        	Rect bounds = new Rect();
        	for(int i=0; i<mTextBuffer.length(); i++){
        		//measure a letter
        		scaledTextPaint.getTextBounds(chars, i, 1, bounds);
        		int charWidth = bounds.width();
        		
        		//if the next char does not reach the 
        		if( (resultCursorPosXpixels +charWidth) < touchX){
        			resultCursorPosXpixels  +=charWidth; //add the char width to the offset
        			resultCursorPosXchars   = i+1;		//change the character offset
        		}else{
        			break; //stop measuring because the touch position is crossed 
        		}
        	}
        	
        	//save the resulted positions
        	this.cursorPosXpixels = resultCursorPosXpixels;
        	this.cursorPosXchars = resultCursorPosXchars;
        }
        /** used to positions the cursor after the positionXchars */
        protected void setCursorAtPosition(int positionXchars){
        	//positionXchars is made from [ 0,text.length() ] interval
        	positionXchars = Math.min(positionXchars, mTextBuffer.length());
        	positionXchars = Math.max(0, positionXchars);
        	
        	//save the char cursor position
        	this.cursorPosXchars = positionXchars;
        	
        	//calculate the pixel position
        	Rect bounds = new Rect();
        	scaledTextPaint.getTextBounds(mTextBuffer, 0, positionXchars, bounds); //measure the text
        	this.cursorPosXpixels = bounds.width();
        }
        /** cursor movement */
        protected void moveCursorRight(){
        	if(cursorPosXchars<mTextBuffer.length()){
        		this.setCursorAtPosition(cursorPosXchars+1);
        	}
        }
        protected void moveCursorLeft(){
        	if(cursorPosXchars>0){
        		this.setCursorAtPosition(cursorPosXchars-1);
        	}
        }
    }







}

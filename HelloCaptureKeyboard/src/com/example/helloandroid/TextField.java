package com.example.helloandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

public class TextField extends View {
	
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
	
	private int minWidth	= 12;					//TODO: load this from config
	private float textSize	= 12; 					//TODO: load this from config
	private Paint textPaint 		= new Paint();	//TODO: load this from config
	private Paint scaledTextPaint	= new Paint();	//TODO: load this from config
	
	
	private int mUnscaledWidth = 0;
	private int mUnscaledHeight = 0;
	private int mScaledWidth = 0;
	private int mScaledHeight = 0;
	
	float scale = 3f; //TODO test scale
	
    private static final String TAG = "TextField";

    
//CONSTRUCTORS
    public TextField(Context context) {
        super(context);
        setFocusableInTouchMode(true); // allows the keyboard to pop up on touch down
        
        setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

            	
                Log.d(TAG, "onKeyListener");
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    
	                switch(keyCode) {
						case KeyEvent.KEYCODE_DEL:
							delText();
							break;
	
						default:
			            	//respect the metastate
			            	KeyCharacterMap map = event.getKeyCharacterMap();
			            	
							// Perform action on key press
		                	int unicodeCharCode = event.getUnicodeChar(event.getMetaState());
		                	
		                	if(map.isPrintingKey(keyCode)){
			                	char cc = (char)unicodeCharCode;
			                	Log.d(TAG, "isPrintingKey "+map.isPrintingKey(keyCode));
			                	Log.d(TAG, "ACTION_DOWN: pushed "+cc);
			                    insertText(cc);	
		                	}
							break;
					}	
	                    
                    requestLayout();
                    invalidate();
                    return true;
                }
                return false;
            }
        });
        
        setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if(isFocused()){
					//the TextField obtained the focus. What should we do? Select the text, position the cursor after the last character?
					
				}
			}
		});
        
        this.textPaint.setTextSize(textSize);		//initiate the Paint with the text size
    }
	public TextField(Context context, AttributeSet attrs,	int defStyle) {
			this(context);
	}
	public TextField(Context context, AttributeSet attrs) {
		this(context);
	}
		
		

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        Log.d(TAG, "onTOUCH");
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // show the keyboard so we can enter text
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
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

    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    	
    	//measure the unscaled text
    	Rect bounds = new Rect();
    	textPaint.getTextBounds(this.mTextBuffer, 0, mTextBuffer.length(), bounds);

    	mUnscaledWidth  = (int) (paddingLeft + bounds.width() + paddingRight);
    	mUnscaledHeight = (int) (paddingTop + bounds.height() + paddingBottom);
    	
    	//respect the scaling level
    	onScale(scale);
    	
    	//respect the minimum size
    	int scaledW = (int) Math.max(mScaledWidth,  scale * (paddingLeft + minWidth + paddingRight) );
    	int scaledH = (int) Math.max(mScaledHeight, mScaledPaddingTop + scaledTextPaint.getTextSize() + mScaledPaddingBottom ); 
    	
    	setMeasuredDimension(scaledW, scaledH);
    	Log.d(TAG,"width "+mScaledWidth + " height "+mScaledHeight);
    }
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        
        float posX = this.mScaledPaddingLeft;
        float posY = Math.abs(scaledTextPaint.ascent()) + this.mScaledPaddingTop;
        
        //draw the text
        canvas.drawText(mTextBuffer, posX, posY, scaledTextPaint);
        
        Log.d(TAG, "Textbuffer is: "+mTextBuffer);
        
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
    private void onScale(float scale){
    	this.mScaledWidth  = (int) Math.round(this.mUnscaledWidth  * scale);
    	this.mScaledHeight = (int) Math.round(this.mUnscaledHeight * scale);
    	
    	this.scaledTextPaint.setTextSize((float) (textSize*scale));
    	
    	mScaledPaddingTop 		= paddingTop * scale;
    	mScaledPaddingLeft 		= paddingLeft * scale;
    	mScaledPaddingBottom 	= paddingBottom * scale;
    	mScaledPaddingRight 	= paddingRight * scale;
    }
    
    
    
//HELPER CLASSES
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
        			resultCursorPosXchars   = i;		//change the character offset
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
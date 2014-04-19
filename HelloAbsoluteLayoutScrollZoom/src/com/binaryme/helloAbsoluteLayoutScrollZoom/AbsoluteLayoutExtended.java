package com.binaryme.helloAbsoluteLayoutScrollZoom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.AbsoluteLayout;


/**
 * 
 * @author skip 
 * Based on http://code.google.com/p/android-pinch
 *
 */


@SuppressWarnings("deprecation")
public class AbsoluteLayoutExtended extends AbsoluteLayout implements ScaleEventListener {
	


	public AbsoluteLayoutExtended(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
        init();
	}

	public AbsoluteLayoutExtended(Context context, AttributeSet attrs) {
		super(context, attrs);
        init();
	}
	public AbsoluteLayoutExtended(Context context) {
		super(context);
        init();
	}
	

	
//IMPLEMENT PINCH GESTURE ZOOM

	// actions
    public static final int GROW = 0;
    public static final int SHRINK = 1;
    
    // intervals
    public static final int DURATION = 150;
    public static float ZOOM_FACTOR = 0.04f;
    
    
    public int 
    		mAbsPixelHeight=this.getContext().getResources().getDisplayMetrics().heightPixels,
    		mAbsPixelWidth=this.getContext().getResources().getDisplayMetrics().widthPixels,
    		mDefaultWidth = -1, mDefaultHeight = -1,
            mOldWidth = -1, 
            mOldHeight = -1,
            mTouchSlop = 6;
    public float mOldScale = 1.0f, mMinScale = 0.5f, mMaxScale = 2f;
    
    protected static float x1, 
            x2, 
            y1, 
            y2, 
            x1_pre,
            y1_pre,
            dist_delta = 0,
            dist_curr = -1, 
            dist_pre = -1;
    
    private boolean mDragging = false;
    /*
     * if redrawing the whole system might become too expensive - caching of the View's canvas can be introduced.
     * the picture from cache will be scaled on the fly then, redrawing will happen once, on gesture end.
     * for not mPicture is useless.  
     */
    private Picture mPicture = null;

    
    
	@Override
	public void onScaleEvent(float newscale, Point pivot) {
		float mScale = newscale;
		
		//Animate this view to change the size, with respect to the new scale
			//this method doesn't work. View just doesn't scale
		
//		float mScale = ScaleHandler.getScale();
//		ObjectAnimator heightAnimation = ObjectAnimator.ofInt(new mLayoutParams(this.getLayoutParams()),
//				"height",
//				this.mDefaultWidth, 
//				1000 );
//		heightAnimation.setDuration(1000);
//		heightAnimation.start();
		
		
		//using .scaleX() Method. This works, but the pivot-point should NOT be in the middle,
		// but on the left-top edge, because it will be easier to recalculate the new Scale position, by multiplying it with Scale.
		// further there is no such a property like scaledHeight. Height is stable even after scaling - scaled height has to be calculated manually like Scale*Height.
		// it is hard to invalidate() the view Area when shrinking the View with pivot Point in the Middle, because we have to invalidate the Area with the old Scale
//		float mScale = ScaleHandler.getScale();
//		this.setScaleX(mScale);
//		this.setScaleY(mScale);
//		
//		
//		float mOldScaledHeight	= this.mDefaultHeight * mOldScale;
//		float mOldScaledWidth 	= this.mDefaultWidth * mOldScale;
//		
//		float mNewScaledHeight	= this.mDefaultHeight * mOldScale;
//		float mNewScaledWidth	= this.mDefaultWidth * mOldScale;
		

		
		//Animation	
		
		

//		//the pivot point should be, where user touched the screen first
//		ScaleAnimation animation = new ScaleAnimation(
//				1, 
//				mScale, 
//				1, 
//				mScale, 
//				ScaleAnimation.ABSOLUTE, 
//				pivot.x, 
//				ScaleAnimation.ABSOLUTE, 
//				pivot.y);
		//the pivot point should be, where user touched the screen first
		
		
		ScaleAnimation animation = new ScaleAnimation(
				1, 
				mScale, 
				1, 
				mScale, 
				ScaleAnimation.RELATIVE_TO_SELF, 
				0, 
				ScaleAnimation.RELATIVE_TO_SELF, 
				0);

		
		animation.setDuration(DURATION);
		animation.setFillEnabled(true);
		animation.setInterpolator(this.getContext(), android.R.anim.linear_interpolator);
		animation.setAnimationListener(new mAnimationListener(this, mScale));
        this.startAnimation(animation);
        Log.d("MyActivity","Width: "+this.getWidth()+", height "+this.getHeight());

		
		
		
		// Redraw the view manually, by chaning 
//		Log.d("MyActivity","Handle onScale. Scale: "+mScale);
//		ViewGroup.LayoutParams l = this.getLayoutParams();
//		
//		l.width =  Math.round(this.mDefaultWidth*mScale);
//		l.height = Math.round(this.mDefaultHeight*mScale);
//		
//		this.mOldHeight = l.height;
//		this.mOldWidth =  l.width;
//		
//		this.setLayoutParams(l);
//		
//		//refresh the rectangular part on the screen, where the resized view was/is 
//		this.invalidate(
//				Math.round(this.getX()),
//				Math.round(this.getY()),
//				Math.max(this.mOldWidth, l.width), 
//				Math.max(this.mOldHeight, l.height));
//		
//		Log.d("MyActivity","Width: "+this.getWidth()+", height "+this.getHeight());
        
	}	//onScaleEvent End
	
	/**
	 *	Class for changing the Height and Width after the animation has ended.
	 *	Saves the pointer to the View, which it should change. 
	 */
	private class mAnimationListener implements AnimationListener{
		private AbsoluteLayoutExtended scaleView;
		private float newScale; 
		
		mAnimationListener(AbsoluteLayoutExtended l, float newScale){
			this.newScale  = newScale;
			this.scaleView = l;
		}

		@Override
		public void onAnimationEnd(Animation animation) {
    		ViewGroup.LayoutParams l = scaleView.getLayoutParams();
    		
    		//update the data after scaling the view
    		scaleView.mOldHeight = l.height;
    		scaleView.mOldWidth =  l.width;
    		
    		l.width =  Math.round(scaleView.mDefaultWidth*newScale);
    		l.height = Math.round(scaleView.mDefaultHeight*newScale);

    		//temp
    		scaleView.setLayoutParams(l);

    		//redraw
    		scaleView.invalidate();
    		
    		
    		//TODO delete this comment at the end of the project
    		//this is not necessary, setFillEnabled(true) makes the invalidating working right.
//    		int redrawLeft 	= Math.round( scaleView.getX() );
//    		int redrawTop 	= Math.round( scaleView.getY() );
//    		int redrawRight = Math.round( Math.max(scaleView.getRight(), redrawLeft+l.width) );	//take max of the old right coordinate and the right coordinate after scaling
//    		int redrawBottom = Math.round( Math.max(scaleView.getBottom(), redrawTop+l.height) );	//take max of the old bottom coordinate and the bottom coordinate after scaling
//    		scaleView.invalidate(redrawLeft,redrawTop,redrawRight, redrawBottom);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
	}
	    
    public void init() {
        setWillNotDraw(false);
        
        //register for zoom events
        ScaleHandler.addScaleEventListener(this);
    } 
    
    
    public boolean onTouchEvent(MotionEvent event) {   
    	
        if (!mDragging) {
                super.onTouchEvent(event);
        }

        int action = event.getAction() & MotionEvent.ACTION_MASK, 
                p_count = event.getPointerCount();
        
        switch (action) {
        case MotionEvent.ACTION_MOVE:
        	
            // point 1 coords
            x1 = event.getX(0);
            y1 = event.getY(0);
            
            int rx1 = Math.round(event.getRawX());
            int ry1 = Math.round(event.getRawY());
            Point rawPoint1 = new Point(rx1, ry1);
            
            // if it's a multi-touch gesture
            if (p_count > 1) {     
            	
                        // point 2 coords
                        x2 = event.getX(1);
                        y2 = event.getY(1);
                        
                        // distance between 2 touch events
                        dist_curr = (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                        
                        
                        if(mDragging == false){
                        	//at the first double tap initiate the dist_pre
                        	dist_pre = dist_curr;
                        	mDragging = true;
                        }

                        	// the intervall of dist_delta is about -30 .. +30 
                        	dist_delta = dist_curr - dist_pre;
                        	
                        	// the scale level should depend on the range of user's movement 
                        	// if we assume the range of dist_delta as -30 .. +30 then a factor of dist_delta/30 will positively reflect intention
                        	float fingerDistanceFactor = 1+ Math.abs(dist_delta)/30;
                        	//to avoid extreme values, we set this factor's maximum as 2
                        	fingerDistanceFactor = Math.min( fingerDistanceFactor, 2f);
                        	

                    	if (Math.abs(dist_delta) > mTouchSlop) { 
                    		
                        	Log.d("scale","-----");
                        	Log.d("scale","dist_delta: "+dist_delta);
                        	Log.d("scale","fingerDistanceFactor: "+fingerDistanceFactor);


                        	float mScale = ScaleHandler.getScale();
                                
	                        ScaleAnimation animation = null;
	                        int mode = dist_delta > 0 ? GROW : (dist_curr == dist_pre ? 2 : SHRINK);
	                        switch (mode) {
	                        case GROW: // grow
                                    mOldScale = mScale;
                                    mScale += (ZOOM_FACTOR*fingerDistanceFactor);
                                    ScaleHandler.setScale(mScale, rawPoint1);
	                        break;
	                        case SHRINK: // shrink
                                    mOldScale = mScale;
                                    mScale -= (ZOOM_FACTOR*fingerDistanceFactor);
                                    ScaleHandler.setScale(mScale, rawPoint1);
	                        break;
	                        }
                    }
                    
                    x1_pre = x1;
                    y1_pre = y1;
                    dist_pre = dist_curr;
            }
            else {

            	// point 1 coords
                    x1_pre = event.getX(0);
                    y1_pre = event.getY(0);
            }
        break;
        case MotionEvent.ACTION_DOWN:
            
            // point 1 coords
            x1_pre = event.getX(0);
            y1_pre = event.getY(0);
        break;
        case MotionEvent.ACTION_UP:
            if (mDragging) {
                    mDragging = false;
            }
        break;
        }
            return true;
    }
    
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            Log.e("LAYOUT CHANGED", "Left: " + l + ", Top: " + t + ", Right: " + r + ", Bottom: " + b);
            //computeScroll();
    }
    
    protected void onSizeChanged(int w, int h, int ow, int oh) {
            super.onSizeChanged(w, h, ow, oh);
            Log.e("SIZE CHANGED", "Width: " + getWidth() + ", Height: " + getHeight());
    }
    
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    		
    		//remember the Default Size of this View for scrolling relative to the default size
    		if(this.mDefaultHeight<0){this.mDefaultHeight=this.getMeasuredHeight();}
    		if(this.mDefaultWidth<0){this.mDefaultWidth=this.getMeasuredWidth();}
    		
            Log.e("MyActivity", "ON MEASURE Width: " + MeasureSpec.getSize(widthMeasureSpec) + ", Height: " + MeasureSpec.getSize(heightMeasureSpec));
}
    
    protected void onDraw(Canvas canvas) {
//            canvas.setDrawFilter(sZoomFilter);
//            canvas.scale(mScale, mScale);
//            canvas.translate(-getScrollX(), -getScrollY());
//            
//            if (mPicture != null) {
//                    canvas.save();
//                    canvas.drawPicture(mPicture);
//                    canvas.restore();
//                    mPicture = null;
//            }
            
            super.onDraw(canvas);
            
    }
    
    

    
}


package com.assusoft.eFairEmall.venueMap;

import com.epch.efair.delhifair.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;

public class ZoomView extends View {
	 //specify the marker position on map
	private static  float  POSITION_X;
	private static float POSITION_Y;
		
	//These two constants specify the minimum and maximum zoom
	private static float MIN_ZOOM = 1f;
	private static float MAX_ZOOM = 5f;
	 
	private float scaleFactor = 1.f;
	private ScaleGestureDetector detector;
	 
	//These constants specify the mode that we're in
	private static int NONE = 0;
	private static int DRAG = 1;
	private static int ZOOM = 2;
	 
	private int mode;
	 
	//These two variables keep track of the X and Y coordinate of the finger when it first
	//touches the screen
	private float startX = 0f;
	private float startY = 0f;
	 
	//These two variables keep track of the amount we need to translate the canvas along the X
	//and the Y coordinate
	private float translateX = 0f;
	private float translateY = 0f;
	 
	//These two variables keep track of the amount we translated the X and Y coordinates, the last time we
	//panned.
	private float previousTranslateX = 0f;
	private float previousTranslateY = 0f;
	private boolean dragged=false;
	private float displayWidth;
	private float displayHeight;
	//my modification
	private int mapRows=20;//62
	private int mapCols=20;//42
	Bitmap tileimg ;
	Display display;
	Point outSize;
	
	private final int tileMap[][] = {
	       		/*{14,1,2,1,1,1,1,2},
	       		{14,14,14,14,1,2,3,14},
	       		{14,17,17,17,17,17,17,14},
	       		{14,17,17,17,17,17,17,14},
	       		{14,17,17,17,17,17,17,14},
	       		{14,17,17,17,17,17,17,14},
	       		{14,17,17,17,17,17,17,14},
	       		{14,14,14,14,14,14,14,14}*/
		
			{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
			{21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40},
			{41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60},
			{61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80},
			{81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100},
			{101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120},
			{121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140},
			{141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160},
			{161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180},
			{181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200},
			{201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220},
			{221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240},
			{241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260},
			{261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,276,277,278,279,280},
			{281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300},
			{301,302,303,304,305,306,307,308,309,310,311,312,313,314,315,316,317,318,319,320},
			{321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340},
			{341,342,343,344,345,346,347,348,349,350,351,352,353,354,355,356,357,358,359,360},
			{361,362,363,364,365,366,367,368,369,370,371,372,373,374,375,376,377,378,379,380},
			{381,382,383,384,385,386,387,388,389,390,391,392,393,394,395,396,397,398,399,400}
			/*{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
			{21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40},
			{41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60},
			{61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80},
			{81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100},
			{101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120},
			{121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140},
			{141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160},
			{161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180},
			{181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200},
			{201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220},
			{221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240},
			{241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260},
			{261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,276,277,278,279,280},
			{281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300},
			{301,302,303,304,305,306,307,308,309,310,311,312,313,314,315,316,317,318,319,320},
			{321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340},
			{341,342,343,344,345,346,347,348,349,350,351,352,353,354,355,356,357,358,359,360},
			{361,362,363,364,365,366,367,368,369,370,371,372,373,374,375,376,377,378,379,380},
			{381,382,383,384,385,386,387,388,389,390,391,392,393,394,395,396,397,398,399,400}*/
	
	
	       		};   
	 Paint  paint;
@SuppressWarnings("deprecation")
public ZoomView(Context context,float positionX,float positionY) {
	super(context);
	//initialize marker position
	POSITION_X=positionX;
	POSITION_Y=positionY;
	tileimg = BitmapFactory.decodeResource(getResources(),
	        R.drawable.appicon);
	
	detector = new ScaleGestureDetector(getContext(), new ScaleListener());
	WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	Display display = wm.getDefaultDisplay();
	
	displayWidth = tileimg.getWidth();
	outSize=getDisplaySize(display);
	
	Log.i("info","Displaysize Y "+ outSize.y);
	Log.i("info","Displaysize X "+ outSize.x);
	displayHeight = tileimg.getHeight();//display.getHeight();
	paint=new Paint();
	//onMeasure(tileimg.getWidth(),tileimg.getHeight());
	Log.i("info","ZoomView: Width "+
			displayWidth + " Height "+
			displayHeight);
}
//This constructor is very important because withouth of this 
//you can't insert this view in xml
public ZoomView(Context context, AttributeSet attrs) {
     super(context, attrs);
     detector = new ScaleGestureDetector(getContext(), new ScaleListener());
     WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
     display = wm.getDefaultDisplay();

     displayWidth = tileimg.getWidth();//display.getWidth();
     displayHeight = tileimg.getHeight();//display.getHeight();
     paint=new Paint(); 
     
     Log.i("info","ZoomView constructor: Width "+
    			displayWidth + " Height "+
    			displayHeight);
    	
 }
// this method is used to find the display size
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
private static Point getDisplaySize(final Display display) {
    final Point point = new Point();
    try {
        display.getSize(point);
    } catch (java.lang.NoSuchMethodError ignore) { // Older device
        point.x = display.getWidth();
        point.y = display.getHeight();
    }
    return point;
}

 
public boolean onTouchEvent(MotionEvent event) {
 
	switch (event.getAction() & MotionEvent.ACTION_MASK) {
	 
	case MotionEvent.ACTION_DOWN:
	mode = DRAG;
	
	//We assign the current X and Y coordinate of the finger to startX and startY minus the previously translated
	//amount for each coordinates This works even when we are translating the first time because the initial
	//values for these two variables is zero.              
	startX = event.getX() - previousTranslateX;
	startY = event.getY() - previousTranslateY;
	break;
	 
	case MotionEvent.ACTION_MOVE:              
	translateX = event.getX() - startX;
	translateY = event.getY() - startY;
	 
	//We cannot use startX and startY directly because we have adjusted their values using the previous translation values.
	//This is why we need to add those values to startX and startY so that we can get the actual coordinates of the finger.
	double distance = Math.sqrt(Math.pow(event.getX() - (startX + previousTranslateX), 2) +
	Math.pow(event.getY() - (startY + previousTranslateY), 2)
	);
	/*Log.i("info","onTouchEvent: event.getX() "+
			event.getX() + "startX "+
			startX);
	Log.i("info","onTouchEvent: event.getY() "+
			event.getY() + "startY "+
			startY);*/
	 
	if(distance > 0) {
	dragged = true;
	}              
	 
	break;
	 
	case MotionEvent.ACTION_POINTER_DOWN:
	mode = ZOOM;
	break;
	 
	case MotionEvent.ACTION_UP:
	mode = NONE;
	dragged = false;
	 
	//All fingers went up, so let's save the value of translateX and translateY into previousTranslateX and
	//previousTranslate          
	previousTranslateX = translateX;
	previousTranslateY = translateY;
	break;
	 
	case MotionEvent.ACTION_POINTER_UP:
	mode = DRAG;
	 
	//This is not strictly necessary; we save the value of translateX and translateY into previousTranslateX
	//and previousTranslateY when the second finger goes up
	previousTranslateX = translateX;
	previousTranslateY = translateY;
	break;      
	}
	 
	detector.onTouchEvent(event);
	 
	//We redraw the canvas only in the following cases:
	//
	// o The mode is ZOOM
	//        OR
	// o The mode is DRAG and the scale factor is not equal to 1 (meaning we have zoomed) and dragged is
	//   set to true (meaning the finger has actually moved)
	/*if ((mode == DRAG && scaleFactor != 1f && dragged) || mode == ZOOM) {
	invalidate();
	}*/
	if ((mode == DRAG && dragged) || mode == ZOOM) {
	    Log.i("info","drag condition and call invalidate");
		invalidate();
	}
	 
	 
	return true;
}

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    setMeasuredDimension(measureWidth(widthMeasureSpec),
            measureHeight(heightMeasureSpec));
    Log.i("info","onMeasure: Width "+
    		widthMeasureSpec + " Height "+
    		heightMeasureSpec);
}

/**
 * Determines the width of this view
 * @param measureSpec A measureSpec packed into an int
 * @return The width of the view, honoring constraints from measureSpec
 */
private int measureWidth(int measureSpec) {
    int result = 0;
    //This is because of background image in relativeLayout, which is 2048*2048px
    measureSpec = outSize.x;
    int specMode = MeasureSpec.getMode(MeasureSpec.UNSPECIFIED);
    int specSize = MeasureSpec.getSize(measureSpec);

    if (specMode == MeasureSpec.UNSPECIFIED) {
        // We were told how big to be
        result = specSize;
    }

    return result;
}

/**
 * Determines the height of this view
 * @param measureSpec A measureSpec packed into an int
 * @return The height of the view, honoring constraints from measureSpec
 */
private int measureHeight(int measureSpec) {
    int result = 0;
    //This is because of background image in relativeLayout, which is 2048*2048px 
    measureSpec = outSize.y;
    int specMode = MeasureSpec.getMode(MeasureSpec.UNSPECIFIED);
    int specSize = MeasureSpec.getSize(measureSpec);


    if (specMode == MeasureSpec.UNSPECIFIED) {
        // Here we say how Heigh to be
        result = specSize;
    } 
    return result;
}
 
@Override
public void onDraw(Canvas canvas) {
	super.onDraw(canvas);
	 
	canvas.save();
	/*Log.i("info","onDraw: translateX "+
			translateX + " translateY "+
			translateY);*/
	//We're going to scale the X and Y coordinates by the same amount
	canvas.scale(scaleFactor, scaleFactor);
	/*Log.i("info","onDraw: scaleFactor "+
			scaleFactor);*/
	 
	//If translateX times -1 is lesser than zero, let's set it to zero. This takes care of the left bound
	if((translateX * -1) < 0) {
	translateX = 0;
	}
	 
	//This is where we take care of the right bound. We compare translateX times -1 to (scaleFactor - 1) * displayWidth.
	//If translateX is greater than that value, then we know that we've gone over the bound. So we set the value of
	//translateX to (1 - scaleFactor) times the display width. Notice that the terms are interchanged; it's the same
	//as doing -1 * (scaleFactor - 1) * displayWidth
	else if((translateX * -1) > ((scaleFactor) * displayWidth) - canvas.getWidth()) {
		Log.i("info","canvas width"+canvas.getWidth()+"displayWidth"+displayWidth+"translateX"+translateX);
	    translateX = ((((scaleFactor) * displayWidth) - canvas.getWidth() )* -1);
		Log.i("info","traslationwidth"+( ((((scaleFactor) * displayWidth) - canvas.getWidth() )* -1)));
	}
	 
	if(translateY * -1 < 0) {
	translateY = 0;
	}
	 //We do the exact same thing for the bottom bound, except in this case we use the height of the display
	else if((translateY * -1) > ((scaleFactor) * displayHeight) - canvas.getHeight()) {
		Log.i("info","canvas height"+canvas.getHeight()+"displayheight"+displayHeight+"translateY"+translateY);
	    translateY = (((scaleFactor) * displayHeight) - canvas.getHeight() )* -1;
	//	Log.i("info","traslationHeight"+( ((((scaleFactor) * displayHeight) - canvas.getHeight() )* -1)));
	}
	
	/*Log.i("info","onDraw after : translateX "+
			translateX + " translateY "+
			translateY);*/
	//We need to divide by the scale factor here, otherwise we end up with excessive panning based on our zoom level
	//because the translation amount also gets scaled according to how much we've zoomed into the canvas.
	canvas.translate(translateX / scaleFactor, translateY / scaleFactor);
	 
	/* The rest of your canvas-drawing code */
	//draw the tile map 
	
	        
	        Log.i("info",tileimg.getHeight()+","+
	                tileimg.getWidth());
	        Log.i("info",canvas.getHeight()+","+
	                canvas.getWidth());
	       
	     //canvas.drawBitmap(tileimg,0,0, null);
			 /*for (int rowCtr=0;rowCtr<mapRows;rowCtr++) {
			      for (int colCtr=0;colCtr<mapCols;colCtr++){
			        try{
			         int tileId = tileMap[rowCtr][colCtr];
			         int sourceX = (int) (Math.floor(tileId % 20) *32);
			         int sourceY = (int) (Math.floor(tileId / 20) *32);
			         //Log.i("info",Integer.toString(sourceY));
			        
			         Bitmap tile=Bitmap.createBitmap(tileimg,sourceX,sourceY,32,32);
			         
			         canvas.drawImage(imageObj, sourceX,
			         sourceY,32,32,colCtr*32,rowCtr*32,32,32);
			         canvas.drawBitmap(tileimg, 
		                   		sourceX,sourceY,
		                   		null);
			       canvas.drawBitmap(tile,colCtr*32,rowCtr*32, null);
			        }catch(IllegalArgumentException e){
			        	
			        }
			        
			      }
			
			   }*/
	        canvas.drawBitmap(tileimg,0,0, null);
			 //set the paint propery
			 paint.setColor(Color.BLUE);
			 paint.setStrokeWidth(10.0f);
			 //draw the marker at position
			 canvas.drawCircle(POSITION_X*32,POSITION_Y*32,10, paint);
	canvas.restore();
	
}



//----------------------------------------
private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
@Override
	public boolean onScale(ScaleGestureDetector detector) {
		scaleFactor *= detector.getScaleFactor();
		scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));
		Log.i("info","scaleFactor: "+
				scaleFactor);
		return true;
		}
	}
}
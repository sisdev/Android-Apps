package com.epch.efair.delhifair;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

public class RotateButton extends Button{

	/*public RotateButton(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
	}
	public RotateButton(Context context, AttributeSet attri){
		super(context,attri);
	}
	public RotateButton(Context context, AttributeSet attri,int style){
		super(context,attri,style);
	}
	@Override
	protected void onDraw(Canvas canvas){
		canvas.save();
		//canvas.rotate(45, getWidth()*2, getHeight()/2);
		//super.draw(canvas);
		//canvas.restore();
		getLayout().draw(canvas);
		canvas.rotate(45);
	}*/
	final boolean topDown;

	public RotateButton(Context context, AttributeSet attrs){
		super(context, attrs);
		final int gravity = getGravity();
		if(Gravity.isVertical(gravity) && (gravity&Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM) {
			setGravity((gravity&Gravity.HORIZONTAL_GRAVITY_MASK) | Gravity.TOP);
			topDown = true;
		}else
			topDown = false;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(heightMeasureSpec, widthMeasureSpec);
		setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
	}

	@Override
	protected void onDraw(Canvas canvas){
		TextPaint textPaint = getPaint(); 
		textPaint.setColor(getCurrentTextColor());
		textPaint.drawableState = getDrawableState();

		canvas.save();

		if(topDown){
			canvas.translate(getWidth(), 0);
			canvas.rotate(90);
		}else {
			canvas.translate(0, getHeight());
			canvas.rotate(-90);
		}

		canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
		getLayout().draw(canvas);
		canvas.restore();
	}

}

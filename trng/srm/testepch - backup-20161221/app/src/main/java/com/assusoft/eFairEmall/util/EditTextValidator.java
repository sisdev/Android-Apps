package com.assusoft.eFairEmall.util;
/**
 * @author VIJAY PAL VISHWAKARMA
 **/

import java.util.regex.Pattern;

import com.epch.efair.delhifair.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

public class EditTextValidator {
	
	private static Drawable drawable;
	
	public static boolean isEmptyEditText(String... strValue){
		if(strValue.length<=0){
			return false;
		}
		for(int i=0;i<strValue.length;i++){
			if(strValue[i].trim().isEmpty()){
				return true;
			}
		}
		return false;
	}
	
	public static boolean validateEditField(EditText edText, boolean hasFocus, int fieldSize, String msg, int textColor, Context mContext, Pattern... patterns){
		
		drawable = mContext.getResources().getDrawable(R.drawable.error);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		
		Log.i("EFAIR","[length of pattern] "+patterns.length);
		if(patterns.length==1){
			if(patterns[0]!=null){
				if(!(patterns[0].matcher(edText.getText().toString().trim()).matches()) ||
						!(edText.getText().toString().trim().length()>=fieldSize)){
					edText.setError(msg,drawable);
					edText.setTextColor(textColor);
					return false;
				}else{
					edText.setError(null);
					edText.setTextColor(textColor);
				}
			}else {
				if(!(edText.getText().toString().trim().length()>=fieldSize)){
					edText.setError(msg,drawable);
					edText.setTextColor(textColor);
					return false;
				}else{
					edText.setError(null);
					edText.setTextColor(textColor);
				}
			}
		}else if(patterns.length>1){
			if(!(patterns[0].matcher(edText.getText().toString()).matches())|| !(patterns[1].matcher(edText.getText().toString()).matches())
					|| (edText.getText().toString().trim().length()<=fieldSize)){
				edText.setError(msg,drawable);
				edText.setTextColor(textColor);
				return false;
			}else{
				edText.setError(null);
				edText.setTextColor(textColor);
			}
		}
		return true;
	}

	public static boolean validateEditField(EditText edText_1, EditText edText_2, boolean hasFocus, int fieldSize, String msg, int textColor, Context mContext, Pattern... patterns){
		drawable = mContext.getResources().getDrawable(R.drawable.error);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		
		if(hasFocus){
			edText_1.setTextColor(Color.BLACK);
			return false;
		}
		Log.i("EFAIR","[length of pattern] "+patterns.length);
		if(patterns.length==1){
			
		}else if(patterns.length>1){
			if(!(patterns[0].matcher(edText_1.getText().toString()).matches())|| !(patterns[1].matcher(edText_1.getText().toString()).matches())
					|| (edText_1.getText().toString().trim().length()<=fieldSize) || !(edText_1.getText().toString().equals(edText_2.getText().toString()))){
				edText_1.setError(msg,drawable);
				edText_1.setTextColor(textColor);
				return false;
			}else{
				edText_1.setError(null);
				edText_1.setTextColor(textColor);
			}
		}
		return true;
	}
	
	public static void makeToastTop(Context context, String msg){
		Toast toast =Toast.makeText(context, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.TOP, 0, 0);
		toast.getView().setBackgroundColor(Color.GRAY);
		toast.show();
	}
}

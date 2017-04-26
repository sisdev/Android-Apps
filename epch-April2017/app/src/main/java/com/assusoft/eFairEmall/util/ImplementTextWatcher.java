package com.assusoft.eFairEmall.util;

import java.util.regex.Pattern;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class ImplementTextWatcher implements TextWatcher{

	private EditText edtText_1, edtText_2;
	private boolean hasFocus, flag;
	private int fieldSize;
	private String errorMessage;
	private Pattern[] patterns;
	private int textColor;
	private Context mContext;
	
	public ImplementTextWatcher() {
		super();
	}

	public ImplementTextWatcher(EditText edtText, boolean hasFocus,
			int fieldSize, String errorMessage, int textColor, Context mContext, Pattern... patterns) {
		super();
		this.edtText_1	 = edtText;
		this.hasFocus	 = hasFocus;
		this.fieldSize	 = fieldSize;
		this.errorMessage= errorMessage;
		this.patterns	 = patterns;
		this.textColor	 = textColor;
		this.mContext	 = mContext;
		this.edtText_1.addTextChangedListener(this);
		flag=true;
	}

	public ImplementTextWatcher(EditText edtText_1, EditText editText_2,
			boolean hasFocus, int fieldSize, String errorMessage, int textColor, Context mContext,
			Pattern... patterns) {
		super();
		this.edtText_1	 = edtText_1;
		this.edtText_2	 = editText_2;
		this.hasFocus	 = hasFocus;
		this.fieldSize	 = fieldSize;
		this.errorMessage= errorMessage;
		this.patterns	 = patterns;
		this.textColor	 = textColor;
		this.mContext	 = mContext;
		this.edtText_1.addTextChangedListener(this);
		this.edtText_2.addTextChangedListener(this);
		flag=false;
	}

	@Override
	public void afterTextChanged(Editable s) {
		if(flag){
			EditTextValidator.validateEditField(edtText_1, hasFocus, fieldSize, errorMessage, textColor, mContext, patterns);
		}else{
			EditTextValidator.validateEditField(edtText_1, edtText_2, hasFocus, fieldSize, errorMessage, textColor, mContext, patterns);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}

}

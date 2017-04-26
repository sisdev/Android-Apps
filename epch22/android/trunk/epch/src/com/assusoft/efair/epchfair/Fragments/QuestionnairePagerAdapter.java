package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.VisitorQueOption;
import com.assusoft.eFairEmall.entities.VisitorQuestionnaire;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.R;

public class QuestionnairePagerAdapter extends PagerAdapter{

	public static enum OptionType{option, radio}

	public static ArrayList<Integer> selectedOption;
	
	private Context mContext;
	private DatabaseHelper helper;

	private int height,margin,padding;
	private int count;
	private float scale =1.0f;
	
	private Drawable draw;
	private LinearLayout llQueLayout;
	private TextView tvQuestion;
	private RadioGroup rGroup;
	
	private ArrayList<VisitorQuestionnaire> questionnaires;
	private ArrayList<VisitorQueOption> queOptions;
	
	public QuestionnairePagerAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		
		helper = EFairEmallApplicationContext.getDatabaseHelper();
		helper.openDatabase(DatabaseHelper.READMODE);
		questionnaires = helper.getAllQuestionnaires();
		count=questionnaires.size();

		if(selectedOption==null){
			selectedOption = new ArrayList<Integer>();
		}
		scale 	= this.mContext.getResources().getDisplayMetrics().density;
		height	= (int) (85 * scale + 0.5f);
		margin	= (int) (5 * scale + 0.5f);
		padding	= (int) (5 * scale + 0.5f);
	}

	@Override
	public int getCount() {
		return count;
	}

	@SuppressLint({ "InlinedApi", "NewApi" }) 
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		LinearLayout rootView = new LinearLayout(mContext);
		LinearLayout.LayoutParams rootLayoutParam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		rootLayoutParam.setMargins(margin, margin, margin, margin);
		rootView.setLayoutParams(rootLayoutParam);
		rootView.setFocusableInTouchMode(true);
		rootView.setOrientation(LinearLayout.VERTICAL);
	    ((ViewPager) container).addView(rootView, 0);
	    
/**Handling of question view and setting its content**/	    
	    LinearLayout.LayoutParams tvParam=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);	    
	    tvParam.setMargins(margin, margin, margin, margin);
	    tvQuestion=new TextView(mContext);
	    tvQuestion.setPadding(padding, padding, padding, padding);
	    tvQuestion.setMinimumHeight(height);
	    tvQuestion.setTextSize(17.0f);
	    tvQuestion.setTextColor(mContext.getResources().getColor(R.color.black));
	    tvQuestion.setTypeface(Typeface.DEFAULT_BOLD);
	    tvQuestion.setLayoutParams(tvParam);
	    tvQuestion.setText(questionnaires.get(position).getQuestion());
	    tvQuestion.setBackgroundResource(R.drawable.rounder_transparent_bg);
	    tvQuestion.setGravity(Gravity.CENTER);
    	rootView.addView(tvQuestion);
    	
	 	ScrollView containerView= new ScrollView(mContext);
	 	LinearLayout.LayoutParams scrollParam=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	 	scrollParam.setMargins(margin, margin, margin, margin);
	 	containerView.setPadding(padding, padding, padding, padding);
	    containerView.setLayoutParams(scrollParam);
	    containerView.setBackgroundResource(R.drawable.rounder_transparent_bg);
	    rootView.addView(containerView);
	    
	    llQueLayout=new LinearLayout(mContext);
	    LinearLayout.LayoutParams llparam=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    llQueLayout.setOrientation(LinearLayout.VERTICAL);
	    llQueLayout.setLayoutParams(llparam);
	    containerView.addView(llQueLayout);
	    
/**Handling for option type of question**/	    
	    String queType=questionnaires.get(position).getQuestionType();
	    OptionType opType = OptionType.valueOf(queType);
	    switch (opType) {
		case option:
			queOptions = helper.getAllQueOptionQuestionIdWise(questionnaires.get(position).getQuestionId());
			Log.i("TABLE_DATA","queOptions "+queOptions.size());
			for(final VisitorQueOption op:queOptions){
				CheckBox chBox	= new CheckBox(mContext);
		    	draw = mContext.getResources().getDrawable(R.drawable.check_box_selector);
		    	chBox.setButtonDrawable(new StateListDrawable());
		    	chBox.setCompoundDrawablesWithIntrinsicBounds(null, null, draw, null);
		    	chBox.setPadding(padding, 0, padding, 0);
		    	chBox.setText(op.getOption());
		    	chBox.setTextColor(mContext.getResources().getColor(R.color.black));
		    	chBox.setBackgroundResource(R.drawable.questionnaire_bg_selector);
	    		llQueLayout.addView(chBox, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    		
	    		chBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							selectedOption.add(op.getOptionId());
						}else if(!isChecked){
							selectedOption.remove(Integer.valueOf(op.getOptionId()));
						}
					}
				});
	    		if(selectedOption.contains(Integer.valueOf(op.getOptionId()))){
	    			chBox.setChecked(true);
	    		}
			}
			
			break;

		case radio:
			queOptions = helper.getAllQueOptionQuestionIdWise(questionnaires.get(position).getQuestionId()); 
			rGroup	= new RadioGroup(mContext);
	    	llQueLayout.addView(rGroup, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    	
	    	for(final VisitorQueOption op:queOptions){
	    		draw = mContext.getResources().getDrawable(R.drawable.check_box_selector);
		    	RadioButton rButton	= new RadioButton(mContext);
		    	rButton.setButtonDrawable(new StateListDrawable());
		    	rButton.setCompoundDrawablesWithIntrinsicBounds(null, null, draw, null);
		    	rButton.setPadding(padding, 0, padding, 0);
		    	rButton.setText(op.getOption());
		    	rButton.setTextColor(mContext.getResources().getColor(R.color.black));
		    	rButton.setBackgroundResource(R.drawable.questionnaire_bg_selector);
	    		rGroup.addView(rButton, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    		
	    		rButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							selectedOption.add(op.getOptionId());
						}else if(!isChecked){
							selectedOption.remove(Integer.valueOf(op.getOptionId()));
						}
						
					}
				});
	    		if(selectedOption.contains(Integer.valueOf(op.getOptionId()))){
	    			rButton.setChecked(true);
	    		}
	    	}
	    	
			break;

		default:
			break;
		}
		return rootView;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==(View)arg1;
	}
	
}

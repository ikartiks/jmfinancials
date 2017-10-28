package com.kartikshah.reddit.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.kartikshah.reddit.R;


public class CustomEditText extends AppCompatEditText {
	
	Context context;
	public CustomEditText(Context context) {
		super(context);
		this.context=context;
	}
	
	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		
		TypedArray a = context.getTheme().obtainStyledAttributes(
		        attrs,
		        R.styleable.CustomTextView,
		        0, 0);
		
		
		
		try {
		       String resourse=a.getString(R.styleable.CustomTextView_ttfResourseName);
		       
		       //LoggerGeneral.e("res "+resourse);
		       if(resourse!=null&&!isInEditMode()){
		    	   Typeface tf=Typeface.createFromAsset(context.getAssets(), resourse);
			       setTypeface(tf);	         
		       }
		       
	   } finally {
	       a.recycle();
	   }
	}
	
	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context=context;
		
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomTextView,0, 0);
		
		try {
		       String resourse=a.getString(R.styleable.CustomTextView_ttfResourseName);
		       //LoggerGeneral.e("res "+resourse);
		       if(resourse!=null&&!isInEditMode()){
		    	   Typeface tf=Typeface.createFromAsset(context.getAssets(), resourse);
			       setTypeface(tf);	         
		       }
		       
		       
	   } finally {
	       a.recycle();
	   }
	}
	
	public void setCustomTypeFace(String path){
		
		if(path!=null){
			Typeface tf=Typeface.createFromAsset(context.getAssets(), path);
			setTypeface(tf);	         
		}
		invalidate();
		requestLayout();
	}
	
}

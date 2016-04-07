package com.mstar.tv.tvplayer.ui.hotelmenu;


import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ComboButton implements IUpdateSysdate{
	LinearLayout container;
	TextView textViewName;
	TextView textViewIndicator;
	private String[] items;
	private boolean[] itemsAbleFlag;
	private int idx;
	private Activity actContext;
	private int leftKeyCode = KeyEvent.KEYCODE_DPAD_LEFT;
	private int rightKeyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
	private boolean isSelectDifferent;
	
	public ComboButton(Activity con,String[] items,int resId,int first ,int second,
			boolean isSelectdiff){
		this.isSelectDifferent = isSelectdiff;
		this.actContext=  con;
		this.items = items;
		container = (LinearLayout)actContext.findViewById(resId);
		this.textViewName = (TextView) container.getChildAt(first);
		this.textViewIndicator = (TextView) container.getChildAt(second);
		setLRListener();
		idx = 0;
		if(items != null)
		{
			textViewIndicator.setText(items[idx]);
		}else
		{
			textViewIndicator.setText(""+idx);
		}
		initItemsFlag();
	}
	
	
	private void initItemsFlag() {
		// TODO Auto-generated method stub
		if(items != null)
		{
			int len = items.length;
			itemsAbleFlag = new boolean[len];
			for(int i=0;i<len;i++)
			{
				itemsAbleFlag[i] = true;
			}
		}
	}


	private void setLRListener() {
		// TODO Auto-generated method stub
		Log.v("nathan", "LRlistener===============================");
		container.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if((keyCode == leftKeyCode) && (event.getAction() == KeyEvent.ACTION_DOWN)
						 && (container.isSelected()||!isSelectDifferent))
				{
					decreaseIdx();
					doUpdate();
					return true;
				}else if((keyCode == rightKeyCode)&& (event.getAction()== KeyEvent.ACTION_DOWN)
						&&(container.isSelected()||!isSelectDifferent))
				{
					increaseIdx();
					doUpdate();
					return true;
				}
				
				return false;
			}
		});
		container.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()== KeyEvent.ACTION_DOWN){
					ComboButton.this.setFocused();
					increaseIdx();
					doUpdate();
					return true;
				}
				return false;
			}
		});
	}
	

	protected void increaseIdx() {
		// TODO Auto-generated method stub
		if(items != null)
		{
			while(true){
				idx++;
				if(idx>=items.length)
				idx = 0;
				if(itemsAbleFlag[idx])
				break;
			}
			textViewIndicator.setText(items[idx]);
		}else{
			idx++;
			if(idx>100)
				idx = 0;
			textViewIndicator.setText(""+idx);
		}
	}


	protected void decreaseIdx() {
		// TODO Auto-generated method stub
		if(items != null)
		{
			while(true)
			{
				idx--;
				if(idx<0)
				idx = items.length-1;
				if(itemsAbleFlag[idx])
					break;
			}
			textViewIndicator.setText(items[idx]);
		}else{
			idx--;
			if(idx<0)
				idx = 100;
			textViewIndicator.setText(""+idx);
		}
	}


	@Override
	public void doUpdate() {
		// TODO Auto-generated method stub
		
	}


	public void setOnFocusChangeListener(
			OnFocusChangeListener comboBtnFocusListeners) {
		// TODO Auto-generated method stub
		container.setOnFocusChangeListener(comboBtnFocusListeners);
	}


	public void setOnClickListener(OnClickListener comboBtnClickListener) {
		// TODO Auto-generated method stub
		container.setOnClickListener(comboBtnClickListener);
	}


	public void setFocused() {
		// TODO Auto-generated method stub
		container.setFocusable(true);
		container.setFocusableInTouchMode(true);
		container.requestFocus();
		container.setSelected(true);
	}


	public void setIdx(int i) {
		// TODO Auto-generated method stub
		this.idx = i;
		if(items != null)
		{
			textViewIndicator.setText(items[idx]);
		}else{
			textViewIndicator.setText(""+idx);
		}
	}

	public int getIdx() {
		return idx;
	}

	public void setEnabled(boolean b)
	{
		container.setEnabled(b);
	}
	
	public void setFoucesable(boolean b)
	{
		if (b) {
			textViewName.setTextColor(Color.WHITE);
			textViewIndicator.setTextColor(Color.WHITE);
		} else {
			textViewName.setTextColor(Color.GRAY);
			textViewIndicator.setTextColor(Color.GRAY);
		}
		container.setFocusable(b);
	}
	
	public void setItemEnable(int i, boolean b) {
		// TODO Auto-generated method stub
		itemsAbleFlag[i] = b;
	}

}

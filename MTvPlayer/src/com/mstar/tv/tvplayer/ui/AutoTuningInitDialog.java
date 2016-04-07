package com.mstar.tv.tvplayer.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;

public class AutoTuningInitDialog extends ProgressDialog {
	
	public AutoTuningInitDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.autotuingdialog);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return true;
	}
}

package com.mstar.tv.tvplayer.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.kguan.mtvplay.tvapi.K_TvPictureManager;
import com.kguan.mtvplay.tvapi.K_MKeyEvent;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.mstar.tvframework.MstarBaseActivity;

public class FreezeMenuActivity extends MstarBaseActivity {
	private TextView freezeValueText;
	private K_TvPictureManager k_PictureModel = null;
	private K_TvCommonManager k_TvCommonModel = null;
	private boolean isFreezed = false;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.freeze_menu);
		TVRootApp app = (TVRootApp) getApplication();
		k_PictureModel = K_TvPictureManager.getInstance();
		k_TvCommonModel = K_TvCommonManager.getInstance();
		freezeValueText = (TextView) findViewById(R.id.freezeType);
		if (k_TvCommonModel.K_isSignalStable(k_TvCommonModel
				.K_getCurrentTvInputSource())) {
			if (isFreezed) {
				freezeValueText.setText(R.string.str_freeze_value_off);
				k_PictureModel.K_unFreezeImage();
				isFreezed = !isFreezed;
			} else {
				freezeValueText.setText(R.string.str_freeze_value_on);
				k_PictureModel.K_freezeImage();
				isFreezed = !isFreezed;
			}
		} else {
			if (isFreezed) {
				freezeValueText.setText(R.string.str_freeze_value_on);
			} else {
				freezeValueText.setText(R.string.str_freeze_value_off);
			}
		}
	}

	@Override
	public void onTimeOut() {
		// TODO Auto-generated method stub
		super.onTimeOut();
		//finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case K_MKeyEvent.KEYCODE_FREEZE:
		case KeyEvent.KEYCODE_KTC_2D3D:
			// nathan.liao20131008 the freeze key invalid when no signal
			if (k_TvCommonModel.K_isSignalStable(k_TvCommonModel
					.K_getCurrentTvInputSource())) {
				if (isFreezed) {
					freezeValueText.setText(R.string.str_freeze_value_off);
					k_PictureModel.K_unFreezeImage();
					isFreezed = !isFreezed;
				} else {
					freezeValueText.setText(R.string.str_freeze_value_on);
					k_PictureModel.K_freezeImage();
					isFreezed = !isFreezed;
				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, keyEvent);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	
}

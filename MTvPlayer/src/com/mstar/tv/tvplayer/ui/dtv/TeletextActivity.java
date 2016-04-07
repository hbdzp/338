package com.mstar.tv.tvplayer.ui.dtv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_MKeyEvent;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.TvEvent;

/**
 * This class implements the activity to support teletext capability.
 * 
 * @author jacky.lin
 */
public class TeletextActivity extends MstarBaseActivity {

	private final static String TAG = "TeletextActivity";

	private K_ChannelModel kchaChannelModel;

	private Runnable killself = new Runnable() {

		@Override
		public void run() {
			kchaChannelModel.K_closeTeletext();
			// after close teletext, should direct activity to root activity
			Intent intent = new Intent(TeletextActivity.this,
					RootActivity.class);
			startActivity(intent);
		}
	};

	protected Handler myHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == TvEvent.CHANGE_TTX_STATUS) {
				Bundle b = msg.getData();
				// @todo: clearify teletext ui flow to sync here.
				// here is just a sample to show the event handle
				Intent intent = new Intent(TeletextActivity.this,
						RootActivity.class);
				startActivity(intent);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "OnCreate");
		super.onCreate(savedInstanceState);
		kchaChannelModel = K_ChannelModel.getInstance();
		if (getIntent() != null && getIntent().getExtras() != null) {
			if (getIntent().getBooleanExtra("TTX_MODE_CLOCK", false)) {
				if (kchaChannelModel
						.K_openTeletext(K_Constants.TTX_MODE_CLOCK) == false) {
					Log.e(TAG, "open teletext false");
				} else {
					myHandler.postDelayed(killself, 5000);
				}
			} else {
				if (kchaChannelModel
						.K_openTeletext(K_Constants.TTX_MODE_NORMAL) == false) {
					Log.e(TAG, "open teletext false");
				}
			}
		} else {
			if (kchaChannelModel
					.K_openTeletext(K_Constants.TTX_MODE_NORMAL) == false) {
				Log.e(TAG, "open teletext false");
			}
		}
	}

	@Override
	protected void onDestroy() {
		myHandler.removeCallbacks(killself);
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		kchaChannelModel.K_closeTeletext();
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// if (SwitchPageHelper.goToMenuPage(this, keyCode) == true) {
		// finish();
		// return true;
		// }
		// else if (SwitchPageHelper.goToEpgPage(this, keyCode) == true) {
		// finish();
		// return true;
		// }
		// else if (SwitchPageHelper.goToPvrPage(this, keyCode) == true) {
		// finish();
		// return true;
		// }
		switch (keyCode) {
		case KeyEvent.KEYCODE_0:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_DIGIT_0);
			return true;
		case KeyEvent.KEYCODE_1:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_DIGIT_1);
			return true;
		case KeyEvent.KEYCODE_2:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_DIGIT_2);
			return true;
		case KeyEvent.KEYCODE_3:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_DIGIT_3);
			return true;
		case KeyEvent.KEYCODE_4:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_DIGIT_4);
			return true;
		case KeyEvent.KEYCODE_5:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_DIGIT_5);
			return true;
		case KeyEvent.KEYCODE_6:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_DIGIT_6);
			return true;
		case KeyEvent.KEYCODE_7:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_DIGIT_7);
			return true;
		case KeyEvent.KEYCODE_8:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_DIGIT_8);
			return true;
		case KeyEvent.KEYCODE_9:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_DIGIT_9);
			return true;
		case KeyEvent.KEYCODE_PAGE_UP:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_PAGE_UP);
			return true;
		case KeyEvent.KEYCODE_PAGE_DOWN:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_PAGE_DOWN);
			return true;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_PAGE_LEFT);
			return true;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_PAGE_RIGHT);
			return true;
		case K_MKeyEvent.KEYCODE_TTX:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_NORMAL_MODE_NEXT_PHASE);
			if (!kchaChannelModel.K_isTeletextDisplayed()) {
				Intent intent = new Intent(TeletextActivity.this,
						RootActivity.class);
				startActivity(intent);
			}
			return true;
		case KeyEvent.KEYCODE_BACK:
			kchaChannelModel.K_closeTeletext();
			// after close teletext, should direct activity to root activity
			Intent intent = new Intent(TeletextActivity.this,
					RootActivity.class);
			startActivity(intent);
			return true;
		case KeyEvent.KEYCODE_PROG_BLUE:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_CYAN);
			return true;
		case KeyEvent.KEYCODE_PROG_YELLOW:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_YELLOW);
			return true;
		case KeyEvent.KEYCODE_PROG_GREEN:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_GREEN);
			return true;
		case KeyEvent.KEYCODE_PROG_RED:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_RED);
			return true;
		case K_MKeyEvent.KEYCODE_TV_SETTING:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_MIX);
			return true;
		case K_MKeyEvent.KEYCODE_MSTAR_UPDATE:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_UPDATE);
			return true;
		case K_MKeyEvent.KEYCODE_MSTAR_SIZE:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_SIZE);
			return true;
		case K_MKeyEvent.KEYCODE_MSTAR_INDEX:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_INDEX);
			return true;
		case K_MKeyEvent.KEYCODE_MSTAR_HOLD:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_HOLD);
			return true;
		case K_MKeyEvent.KEYCODE_MSTAR_REVEAL:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_REVEAL);
			return true;
		case K_MKeyEvent.KEYCODE_LIST:
			kchaChannelModel
					.K_sendTeletextCommand(K_Constants.TTX_COMMAND_LIST);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}

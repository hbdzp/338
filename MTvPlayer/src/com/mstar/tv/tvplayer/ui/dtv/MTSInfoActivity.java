package com.mstar.tv.tvplayer.ui.dtv;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_MKeyEvent;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.SwitchPageHelper;
import com.mstar.tv.tvplayer.ui.TVRootApp;
import com.mstar.tvframework.MstarBaseActivity;

public class MTSInfoActivity extends MstarBaseActivity {

	private final String TAG = "MTSInfoActivity";

	private TextView mtsInfo = null;

	private ArrayList<String> audioInfo = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.mtsinfo);
		mtsInfo = (TextView) findViewById(R.id.mtsType);
		TVRootApp app = (TVRootApp) getApplication();
		mtsInfo.setText(getSoundFormat());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		if (SwitchPageHelper.goToMenuPage(this, keyCode) == true) {
			finish();
			return true;
		}
		switch (keyCode) {
		case K_MKeyEvent.KEYCODE_MTS:
			K_TvCommonManager.getInstance().K_setToNextATVMtsMode();
			mtsInfo.setText(getSoundFormat());
			return true;
		default :
			break;
		}
		return super.onKeyDown(keyCode, keyEvent);
	}

	private String getSoundFormat() {
		String mStr = "";
		switch (K_TvCommonManager.getInstance().K_getATVMtsMode()) {
		case K_Constants.ATV_AUDIOMODE_MONO:
			mStr = getResources().getString(R.string.str_sound_format_mono);
			break;
		case K_Constants.ATV_AUDIOMODE_DUAL_A:
			mStr = getResources().getString(R.string.str_sound_format_dual_a);
			break;
		case K_Constants.ATV_AUDIOMODE_DUAL_AB:
			mStr = getResources().getString(R.string.str_sound_format_dual_ab);
			break;
		case K_Constants.ATV_AUDIOMODE_DUAL_B:
			mStr = getResources().getString(R.string.str_sound_format_dual_b);
			break;
		case K_Constants.ATV_AUDIOMODE_FORCED_MONO:
			mStr = getResources().getString(
					R.string.str_sound_format_forced_mono);
			break;
		case K_Constants.ATV_AUDIOMODE_G_STEREO:
			mStr = getResources().getString(R.string.str_sound_format_g_stereo);
			break;
		case K_Constants.ATV_AUDIOMODE_HIDEV_MONO:
			mStr = getResources().getString(
					R.string.str_sound_format_hidev_mono);
			break;
		case K_Constants.ATV_AUDIOMODE_K_STEREO:
			mStr = getResources().getString(R.string.str_sound_format_k_stereo);
			break;
		case K_Constants.ATV_AUDIOMODE_LEFT_LEFT:
			mStr = getResources()
					.getString(R.string.str_sound_format_left_left);
			break;
		case K_Constants.ATV_AUDIOMODE_LEFT_RIGHT:
			mStr = getResources().getString(
					R.string.str_sound_format_left_right);
			break;
		case K_Constants.ATV_AUDIOMODE_MONO_SAP:
			mStr = getResources().getString(R.string.str_sound_format_mono_sap);
			break;
		case K_Constants.ATV_AUDIOMODE_NICAM_DUAL_A:
			mStr = getResources().getString(
					R.string.str_sound_format_nicam_dual_a);
			break;
		case K_Constants.ATV_AUDIOMODE_NICAM_DUAL_AB:
			mStr = getResources().getString(
					R.string.str_sound_format_nicam_dual_ab);
			break;
		case K_Constants.ATV_AUDIOMODE_NICAM_DUAL_B:
			mStr = getResources().getString(
					R.string.str_sound_format_nicam_dual_b);
			break;
		case K_Constants.ATV_AUDIOMODE_NICAM_MONO:
			mStr = getResources().getString(
					R.string.str_sound_format_nicam_mono);
			break;
		case K_Constants.ATV_AUDIOMODE_NICAM_STEREO:
			mStr = getResources().getString(
					R.string.str_sound_format_nicam_stereo);
			break;
		case K_Constants.ATV_AUDIOMODE_RIGHT_RIGHT:
			mStr = getResources().getString(
					R.string.str_sound_format_right_right);
			break;
		case K_Constants.ATV_AUDIOMODE_STEREO_SAP:
			mStr = getResources().getString(
					R.string.str_sound_format_stereo_sap);
			break;
		case K_Constants.ATV_AUDIOMODE_INVALID:
		default:
			mStr = getResources().getString(R.string.str_sound_format_unknown);
			break;
		}
		return mStr;
	}

	@Override
	public void onTimeOut() {
		super.onTimeOut();
		finish();
	}
}

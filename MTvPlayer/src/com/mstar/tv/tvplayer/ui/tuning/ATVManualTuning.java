package com.mstar.tv.tvplayer.ui.tuning;

import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kguan.mtvplay.tvapi.K_AtvManager;
import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.kguan.mtvplay.tvapi.K_TvManager;
import com.kguan.mtvplay.tvapi.enumeration.K_EnumSetProgramInfo;
import com.kguan.mtvplay.tvapi.listener.K_OnAtvPlayerEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnMhlEventListener;
import com.kguan.mtvplay.tvapi.vo.atv.K_AtvEventScan;
import com.mstar.android.MIntent;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.holder.ViewHolder;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.PropHelp;
//clark.chiu 20140929 begin
//clark.chiu 20140929 end
public class ATVManualTuning extends MstarBaseActivity {
	/** Called when the activity is first created. */
	private static final String TAG = "ATVManualTuning";

	private ViewHolder viewholder_atvmanualtuning;

	private int mColorSystemIndex = 0;

	private int mSoundSystemIndex = 0;

	private String mSoundSystem[] = { "BG", "DK", "I", "L", "M" };

	private String mColorSystem[] = { "PAL", "NTSC", "SECAM", "NTSC_44",
			"PAL_M", "PAL_N", "PAL_60", "NO_STAND", "AUTO" };

	private K_ChannelModel mTvChannelManager = null;

	private K_OnAtvPlayerEventListener mAtvPlayerEventListener = null;

	private Handler mAtvUiEventHandler = null;

	private BroadcastReceiver mReceiver = null;

	private boolean manualtuningEndFlag = true;
	
	private LinearLayout linear_channalNum;
	private LinearLayout linear_channalColorSystem;
	private LinearLayout linear_channalSoundSystem;
	private LinearLayout linear_channalSearch;
	private LinearLayout linear_channalFreq;
	private TextView tvChaAtvManualtuning;
	
	private int inputDigit = 0;
	private int channelNumberInput = 0;
	private int currentchannelNumber = 0;

	private class AtvPlayerEventListener extends K_OnAtvPlayerEventListener {

		@Override
		public boolean K_onAtvAutoTuningScanInfo(int what, K_AtvEventScan extra) {
			Message msg = mAtvUiEventHandler.obtainMessage(what, extra);
			mAtvUiEventHandler.sendMessage(msg);
			return true;
		}

		@Override
		public boolean K_onAtvManualTuningScanInfo(int what, K_AtvEventScan extra) {
			Message msg = mAtvUiEventHandler.obtainMessage(what, extra);
			mAtvUiEventHandler.sendMessage(msg);
			return true;
		}

		@Override
		public boolean K_onAtvProgramInfoReady(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onSignalLock(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onSignalUnLock(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

/*		@Override
		public boolean onAtvAutoTuningScanInfo(int what, AtvEventScan extra) {
			Message msg = mAtvUiEventHandler.obtainMessage(what, extra);
			mAtvUiEventHandler.sendMessage(msg);
			return true;
		}

		@Override
		public boolean onAtvManualTuningScanInfo(int what, AtvEventScan extra) {
			Message msg = mAtvUiEventHandler.obtainMessage(what, extra);
			mAtvUiEventHandler.sendMessage(msg);
			return true;
		}

		@Override
		public boolean onSignalLock(int what) {
			return false;
		}

		@Override
		public boolean onSignalUnLock(int what) {
			return false;
		}

		@Override
		public boolean onAtvProgramInfoReady(int what) {
			return false;
		}*/
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atvmanualtuning);
		mTvChannelManager = K_ChannelModel.getInstance();

		viewholder_atvmanualtuning = new ViewHolder(ATVManualTuning.this);
		viewholder_atvmanualtuning.findViewForAtvManualTuning();
		// registerListeners();

		mReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(
						TvIntent.ACTION_CIPLUS_TUNER_UNAVAIABLE)) {
					Log.i(TAG, "Receive ACTION_CIPLUS_TUNER_UNAVAIABLE...");
					finish();
				}
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(TvIntent.ACTION_CIPLUS_TUNER_UNAVAIABLE);
		registerReceiver(mReceiver, filter);
		tvChaAtvManualtuning = (TextView) findViewById(R.id.textview_cha_atvmanualtuning);
		linear_channalNum = (LinearLayout) findViewById(
                R.id.linearlayout_cha_atvmanualtuning_channelnum);
		linear_channalColorSystem = (LinearLayout) findViewById(
                R.id.linearlayout_cha_atvmanualtuning_colorsystem);
		linear_channalSoundSystem = (LinearLayout) findViewById(
                R.id.linearlayout_cha_atvmanualtuning_soundsystem);
		linear_channalSearch = (LinearLayout) findViewById(
                R.id.linearlayout_cha_atvmanualtuning_starttuning);
		linear_channalFreq = (LinearLayout) findViewById(
                R.id.linearlayout_cha_atvmanualtuning_frequency);
		// registerListeners();
		boolean hasDtmb = PropHelp.newInstance().hasDtmb();
		if (hasDtmb) {
			tvChaAtvManualtuning.setText(R.string.str_cha_atvmanualtuning);
		} else {
			tvChaAtvManualtuning.setText(R.string.str_cha_manualtuning);
		}
		setOnFocusChangeListeners();
		if (K_TvCommonManager.getInstance().K_getCurrentTvInputSource() != K_Constants.INPUT_SOURCE_ATV) {
			K_TvCommonManager.getInstance().K_setInputSource(
					K_Constants.INPUT_SOURCE_ATV);
				mTvChannelManager.K_changeToFirstService(
						K_Constants.FIRST_SERVICE_INPUT_TYPE_ATV,
						K_Constants.FIRST_SERVICE_DEFAULT);
		}

		currentchannelNumber = mTvChannelManager.K_getCurrentChannelNumber() + 1;
		if (currentchannelNumber > 199)
			currentchannelNumber = 1;
		updateAtvManualtuningComponents();

		K_TvManager.getInstance().K_getMhlManager()
				.setOnMhlEventListener(new K_OnMhlEventListener() {

					@Override
					public boolean K_onAutoSwitch(int arg0, int arg1, int arg2) {
						Log.d("ATVManualTuning", "onAutoSwitch");
						if (mTvChannelManager.K_getTuningStatus() != K_Constants.TUNING_STATUS_NONE) {
							mTvChannelManager.K_stopAtvManualTuning();
						}
						finish();

						return false;
					}

					@Override
					public boolean K_onKeyInfo(int arg0, int arg1, int arg2) {
						Log.d("ATVManualTuning", "onKeyInfo");
						return false;
					}/*

					@Override
					public boolean onKeyInfo(int arg0, int arg1, int arg2) {
						Log.d("ATVManualTuning", "onKeyInfo");
						return false;
					}

					@Override
					public boolean onAutoSwitch(int arg0, int arg1, int arg2) {
						Log.d("ATVManualTuning", "onAutoSwitch");
						if (mTvChannelManager.K_getTuningStatus() != K_Constants.TUNING_STATUS_NONE) {
							mTvChannelManager.K_stopAtvManualTuning();
						}
						finish();

						return false;
					}
				*/});

		mAtvUiEventHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				updateAtvTuningScanInfo((K_AtvEventScan) msg.obj);
			}
		};
		manualtuningEndFlag = true; 
	}

	@Override
	public void onResume() {
		super.onResume();
		mAtvPlayerEventListener = new AtvPlayerEventListener();
		K_ChannelModel.getInstance().K_registerOnAtvPlayerEventListener(
				mAtvPlayerEventListener);
	}

	public boolean MapKeyPadToIR(int keyCode, KeyEvent event) {
		String deviceName = InputDevice.getDevice(event.getDeviceId())
				.getName();
		Log.d(TAG, "deviceName" + deviceName);
		if (!deviceName.equals("MStar Smart TV Keypad"))
			return false;
		switch (keyCode) {
		case KeyEvent.KEYCODE_CHANNEL_UP:
			keyInjection(KeyEvent.KEYCODE_DPAD_UP);
			return true;
		case KeyEvent.KEYCODE_CHANNEL_DOWN:
			keyInjection(KeyEvent.KEYCODE_DPAD_DOWN);
			return true;
		default:
			return false;
		}

	}

	private void keyInjection(final int keyCode) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN
				|| keyCode == KeyEvent.KEYCODE_DPAD_UP
				|| keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
				|| keyCode == KeyEvent.KEYCODE_DPAD_LEFT
				|| keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
				|| keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			new Thread() {
				public void run() {
					try {
						Instrumentation inst = new Instrumentation();
						inst.sendKeyDownUpSync(keyCode);
					} catch (Exception e) {
						Log.e("Exception when sendPointerSync", e.toString());
					}
				}
			}.start();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// If MapKeyPadToIR returns true,the previous keycode has been changed
		// to responding
		// android d_pad keys,just return.
		if (MapKeyPadToIR(keyCode, event))
			return true;
		Intent intent = new Intent();
		int currentid = getCurrentFocus().getId();
		int maxv = mColorSystem.length;
		int maxs = mSoundSystem.length;
		int nCurrentFrequency = mTvChannelManager.K_getAtvCurrentFrequency();
		int curChannelNumber = mTvChannelManager.K_getCurrentChannelNumber();

		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_RIGHT:
		case KeyEvent.KEYCODE_VOLUME_UP:
			switch (currentid) {
			case R.id.linearlayout_cha_atvmanualtuning_frequency:
				mTvChannelManager.K_startAtvManualTuning(3 * 1000,
						nCurrentFrequency,
						K_Constants.ATV_MANUAL_TUNE_MODE_FINE_TUNE_UP);
				updateAtvManualtuningfreq();
				mTvChannelManager.K_saveAtvProgram(curChannelNumber);
				return true;
			case R.id.linearlayout_cha_atvmanualtuning_starttuning:
				if(linear_channalSearch.isSelected()&&manualtuningEndFlag)
				{
				if (mTvChannelManager.K_getTuningStatus() == K_Constants.TUNING_STATUS_ATV_MANUAL_TUNING_LEFT) {
					mTvChannelManager.K_stopAtvManualTuning();
				}
				// Hisa 2016.03.04 add Freeze function start
				Intent intentCancel = new Intent();//取消静像菜单
				intentCancel.setAction(MIntent.ACTION_FREEZE_CANCEL_BUTTON);
				//K_TvPictureManager.getInstance().K_unFreezeImage();
				sendBroadcast(intentCancel);  
				// Hisa 2016.03.04 add Freeze function end
					manualtuningEndFlag = false;
					ImageView img = (ImageView) linear_channalSearch
							.getChildAt(1);
					img.setBackgroundResource(R.drawable.common_img_arrow_r);
				mTvChannelManager.K_startAtvManualTuning(5 * 1000,
						nCurrentFrequency,
						K_Constants.ATV_MANUAL_TUNE_MODE_SEARCH_UP);
				}
				return true;
			case R.id.linearlayout_cha_atvmanualtuning_channelnum:
				if (currentchannelNumber < 199)
					currentchannelNumber++;
				else
					currentchannelNumber = 1;
				mTvChannelManager.K_selectProgram((currentchannelNumber - 1),
						K_Constants.SERVICE_TYPE_ATV);
				updateAtvManualtuningComponents();
				return true;
			case R.id.linearlayout_cha_atvmanualtuning_colorsystem:
				mColorSystemIndex = (mColorSystemIndex + 1) % (maxv);
				if (mColorSystemIndex >= 3
                        && mColorSystemIndex <= 7) {
					 mColorSystemIndex = 8;
                }
				mTvChannelManager.K_setAtvVideoStandard(mColorSystemIndex);
				viewholder_atvmanualtuning.text_cha_atvmanualtuning_colorsystem_val
						.setText(mColorSystem[mColorSystemIndex]);
				return true;
			case R.id.linearlayout_cha_atvmanualtuning_soundsystem:
				mSoundSystemIndex = (mSoundSystemIndex + 1) % (maxs);
				if (mSoundSystemIndex == 3) {
					mSoundSystemIndex = 4;
				}
				mTvChannelManager.K_setAtvAudioStandard(mSoundSystemIndex);
				viewholder_atvmanualtuning.text_cha_atvmanualtuning_soundsystem_val
						.setText(mSoundSystem[mSoundSystemIndex]);
                        //clark.chiu 20140929 begin
                            if (K_AtvManager.K_getAtvScanManager() != null) {
                             /*   return K_AtvManager.getInstance().K_setAtvProgramInfo( 
                            	K_EnumSetProgramInfo.K_E_SET_AUDIO_STANDARD, curChannelNumber, (EnumAtvSystemStandard.values()[mSoundSystemIndex]).getValue());  
                            */
                            	   return K_AtvManager.getInstance().K_setAtvProgramInfo( 
                                       	K_EnumSetProgramInfo.K_E_SET_AUDIO_STANDARD, curChannelNumber, mSoundSystemIndex);  
                              }
      
                        //clark.chiu 20140929 end

				return true;
			default:
				break;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			switch (currentid) {
			case R.id.linearlayout_cha_atvmanualtuning_frequency:
				mTvChannelManager.K_startAtvManualTuning(3 * 1000,
						nCurrentFrequency,
						K_Constants.ATV_MANUAL_TUNE_MODE_FINE_TUNE_DOWN);
				updateAtvManualtuningfreq();
				mTvChannelManager.K_saveAtvProgram(curChannelNumber);
				return true;
			case R.id.linearlayout_cha_atvmanualtuning_starttuning:
				if(linear_channalSearch.isSelected() && manualtuningEndFlag)
				{
				if (mTvChannelManager.K_getTuningStatus() == K_Constants.TUNING_STATUS_ATV_MANUAL_TUNING_RIGHT) {
					mTvChannelManager.K_stopAtvManualTuning();
				}
				// Hisa 2016.03.04 add Freeze function start
				Intent intentCancel = new Intent();//取消静像菜单
				intentCancel.setAction(MIntent.ACTION_FREEZE_CANCEL_BUTTON);
				//K_TvPictureManager.getInstance().K_unFreezeImage();
				sendBroadcast(intentCancel);  
				// Hisa 2016.03.04 add Freeze function end
					manualtuningEndFlag = false;
					ImageView img = (ImageView) linear_channalSearch
							.getChildAt(2);
					img.setBackgroundResource(R.drawable.common_img_arrow_l);
				mTvChannelManager.K_startAtvManualTuning(5 * 1000,
						nCurrentFrequency,
						K_Constants.ATV_MANUAL_TUNE_MODE_SEARCH_DOWN);
				}
				return true;
			case R.id.linearlayout_cha_atvmanualtuning_channelnum:
				if (currentchannelNumber == 1)
					currentchannelNumber = 199;
				else
					currentchannelNumber--;
				mTvChannelManager.K_selectProgram((currentchannelNumber - 1),
						K_Constants.SERVICE_TYPE_ATV);
				updateAtvManualtuningComponents();
				return true;
			case R.id.linearlayout_cha_atvmanualtuning_colorsystem:
				mColorSystemIndex = (mColorSystemIndex + maxv - 1) % (maxv);
				 if (mColorSystemIndex >= 3
                         && mColorSystemIndex <= 7) {
					 mColorSystemIndex = 2;
                 }
				mTvChannelManager.K_setAtvVideoStandard(mColorSystemIndex);
				viewholder_atvmanualtuning.text_cha_atvmanualtuning_colorsystem_val
						.setText(mColorSystem[mColorSystemIndex]);
				return true;
			case R.id.linearlayout_cha_atvmanualtuning_soundsystem:
				mSoundSystemIndex = (mSoundSystemIndex + maxs - 1) % (maxs);
				if (mSoundSystemIndex == 3) {
					mSoundSystemIndex = 2;
				}
				mTvChannelManager.K_setAtvAudioStandard(mSoundSystemIndex);
				viewholder_atvmanualtuning.text_cha_atvmanualtuning_soundsystem_val
						.setText(mSoundSystem[mSoundSystemIndex]);
                        //clark.chiu 20140929 begin
                            if (K_AtvManager.K_getAtvScanManager() != null) {
                                return K_AtvManager.getInstance().K_setAtvProgramInfo( 
                            	K_EnumSetProgramInfo.K_E_SET_AUDIO_STANDARD, curChannelNumber, mSoundSystemIndex);  
                            }
 
                        //clark.chiu 20140929 end                        

				return true;
			default:
				break;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			if (!manualtuningEndFlag) {
				return true;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
			if (!manualtuningEndFlag) {
				return true;
			}
			break;
		case KeyEvent.KEYCODE_BACK:
		case KeyEvent.KEYCODE_MENU:
			if (mTvChannelManager.K_getTuningStatus() != K_Constants.TUNING_STATUS_NONE) {
				mTvChannelManager.K_stopAtvManualTuning();
			}
			intent.setAction(TvIntent.MAINMENU);
			intent.putExtra("currentPage", MainMenuActivity.CHANNEL_PAGE);
			startActivity(intent);
			finish();
			return true;
		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_1:
		case KeyEvent.KEYCODE_2:
		case KeyEvent.KEYCODE_3:
		case KeyEvent.KEYCODE_4:
		case KeyEvent.KEYCODE_5:
		case KeyEvent.KEYCODE_6:
		case KeyEvent.KEYCODE_7:
		case KeyEvent.KEYCODE_8:
		case KeyEvent.KEYCODE_9:
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_TV_INPUT:
			if (currentid == R.id.linearlayout_cha_atvmanualtuning_channelnum) {
				if (linear_channalNum.isSelected()) {
					if (keyCode == KeyEvent.KEYCODE_ENTER ||keyCode == KeyEvent.KEYCODE_TV_INPUT) {
						if (inputDigit > 0) {
							inputDigit = 0;
							if (channelNumberInput <= 199
									&& channelNumberInput > 0) {
								currentchannelNumber = channelNumberInput;
			
								mTvChannelManager.K_selectProgram((currentchannelNumber - 1),
										K_Constants.SERVICE_TYPE_ATV);
							}
							updateAtvManualtuningComponents();
							return true;
						}

					} else {
						inputDigit++;
						if (inputDigit > 3) {
							inputDigit = 0;
							channelNumberInput = 0;
							updateAtvManualtuningComponents();
							return true;
						}

						int inputnum = keyCode - KeyEvent.KEYCODE_0;

						if (inputDigit == 1) {
							channelNumberInput = inputnum;
						} else if (inputDigit == 2) {
							channelNumberInput = channelNumberInput * 10
									+ inputnum;
						} else if (inputDigit == 3) {
							channelNumberInput = channelNumberInput * 10
									+ inputnum;
						}
						String str_val;
						str_val = Integer.toString(channelNumberInput);
						viewholder_atvmanualtuning.text_cha_atvmanualtuning_channelnum_val
								.setText(str_val);
					}

				}
			}
			return true;
		default:
			break;
		}
		if(KeyEvent.KEYCODE_TV_INPUT == keyCode)
			if(!manualtuningEndFlag)
			return true;
			else
			finish();
		return super.onKeyDown(keyCode, event);
	}

	/*
	 * private void registerListeners() { } private OnClickListener listener =
	 * new OnClickListener() { Intent intent = new Intent();
	 * 
	 * @Override public void onClick(View view) { // TODO Auto-generated method
	 * stub switch (view.getId()) { } } };
	 */
	private void updateAtvManualtuningfreq() {
		String str_val;
		int freqKhz = mTvChannelManager.K_getAtvCurrentFrequency();
		//str_val = String.format("%.2f", freqKhz / 1000.0);
		int minteger = freqKhz / 1000;
		int mfraction = (freqKhz % 1000) / 10; 
		if (mfraction < 5) {
			mfraction = 0;
		} else if((mfraction >= 5) && (mfraction < 10)){
			mfraction = 5;
		} else if((mfraction >= 10) && (mfraction < 15)){
			mfraction = 10;
		} else if((mfraction >=15)&&(mfraction<20)){
			mfraction = 15;
		} else if((mfraction >=20)&&(mfraction<25)){
			mfraction = 20;
		}else if ((mfraction >= 25) && (mfraction <30)) {
			mfraction = 25;
		} else if ((mfraction >= 30) && (mfraction <35)) {
			mfraction = 30;
		}else if ((mfraction >= 35) && (mfraction <40)) {
			mfraction = 35;
		}else if ((mfraction >= 40) && (mfraction <45)) {
			mfraction = 40;
		}else if ((mfraction >= 45) && (mfraction < 50)) {
			mfraction = 45;
		} else if ((mfraction >= 50) && (mfraction < 55)) {
			mfraction = 50;
		}else if ((mfraction >= 55) && (mfraction < 60)) {
			mfraction = 55;
		}else if ((mfraction >= 60) && (mfraction < 65)) {
			mfraction = 60;
		}else if ((mfraction >= 65) && (mfraction < 70)) {
			mfraction = 65;
		}else if ((mfraction >= 70) && (mfraction < 75)) {
			mfraction = 70;
		}else if ((mfraction >= 75) && (mfraction < 80)) {
			mfraction = 75;
		}else if ((mfraction >= 80) && (mfraction < 85)) {
			mfraction = 80;
		}else if ((mfraction >= 85) && (mfraction < 90)) {
			mfraction = 85;
		}else if ((mfraction >= 90) && (mfraction < 95)) {
			mfraction = 90;
		}else if ((mfraction >= 95) && (mfraction < 100)) {
			mfraction = 95;
		}
		if (mfraction < 10)
			str_val = Integer.toString(minteger) + ".0"
					+ Integer.toString(mfraction);
		else
            str_val = Integer.toString(minteger) + "."
                    + Integer.toString(mfraction);
		viewholder_atvmanualtuning.text_cha_atvmanualtuning_freqency_val
				.setText(str_val);
	}

	private void updateAtvManualtuningComponents() {
		String str_val;
		/*int curChannelNum = mTvChannelManager.getCurrentChannelNumber() + 1;*/
		str_val = Integer.toString(currentchannelNumber/* curChannelNum */);

		// 0.250M
		str_val = Integer.toString(currentchannelNumber);
	// Hch 20160309 change for ATV manual color system display  // K_getAvdVideoStandard() 	
		mColorSystemIndex = mTvChannelManager.K_getAtvVideoSystem()
				% (mColorSystem.length); 
	// change end 
		mSoundSystemIndex = mTvChannelManager.K_getAtvAudioStandard()
				% mSoundSystem.length;
		viewholder_atvmanualtuning.text_cha_atvmanualtuning_channelnum_val
				.setText(str_val);
		viewholder_atvmanualtuning.text_cha_atvmanualtuning_colorsystem_val
				.setText(mColorSystem[mColorSystemIndex]);
		viewholder_atvmanualtuning.text_cha_atvmanualtuning_soundsystem_val
				.setText(mSoundSystem[mSoundSystemIndex]);
		updateAtvManualtuningfreq();
	}

	@Override
	protected void onPause() {
		K_ChannelModel.getInstance().K_registerOnAtvPlayerEventListener(
				mAtvPlayerEventListener);
		mAtvPlayerEventListener = null;

		super.onPause();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}

	private void updateAtvTuningScanInfo(K_AtvEventScan extra) {
		String str_val;
		int frequency = extra.getAtvEventScan().frequencyKHz;
		int percent = extra.getAtvEventScan().percent;
		int minteger = frequency / 1000;
		int mfraction = (frequency % 1000) / 10;
		str_val = Integer.toString(minteger) + "."
				+ Integer.toString(mfraction);
		viewholder_atvmanualtuning.text_cha_atvmanualtuning_freqency_val
				.setText(str_val);

		if (percent >= 100) {
			mTvChannelManager.K_stopAtvManualTuning();
			manualtuningEndFlag = true;
			ImageView img1 = (ImageView) linear_channalSearch
					.getChildAt(1);
			ImageView img2 = (ImageView) linear_channalSearch
					.getChildAt(2);
			img1.setBackgroundResource(R.drawable.common_img_arrow_l);
			img2.setBackgroundResource(R.drawable.common_img_arrow_r);
			int atvProgCount = mTvChannelManager
					.K_getProgramCount(K_Constants.PROGRAM_COUNT_ATV);
			int curProgNumber = mTvChannelManager.K_getCurrentChannelNumber();
			if ((atvProgCount == 0) || (curProgNumber == 0xFF)) {
				curProgNumber = 0;
			}

			Log.i("ATVManualTuning", "count:" + atvProgCount + "---current:"
					+ curProgNumber);

			mTvChannelManager.K_saveAtvProgram(curProgNumber);
			int curChannelNumber = mTvChannelManager.K_getCurrentChannelNumber();
			mTvChannelManager.K_selectProgram(curChannelNumber,
					K_Constants.SERVICE_TYPE_ATV);
			updateAtvManualtuningComponents();
		}
	}

	private void setOnFocusChangeListeners() {
		OnFocusChangeListener linearFocusChange = new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View view, boolean arg1) {
				// TODO Auto-generated method stub
				LinearLayout linear = (LinearLayout) view;
				if (manualtuningEndFlag) {
					if (!linear.isSelected()) {
						linear.getChildAt(1).setVisibility(View.VISIBLE);
						linear.getChildAt(linear.getChildCount() - 1)
								.setVisibility(View.VISIBLE);
						linear.setSelected(true);
					} else {
						linear.getChildAt(1).setVisibility(View.INVISIBLE);
						linear.getChildAt(linear.getChildCount() - 1)
								.setVisibility(View.INVISIBLE);
						linear.setSelected(false);
					}
					if (inputDigit != 0) {
						inputDigit = 0;
						updateAtvManualtuningComponents();
					}
				}
			}
		};
		linear_channalNum.setOnFocusChangeListener(linearFocusChange);
		linear_channalColorSystem.setOnFocusChangeListener(linearFocusChange);
		linear_channalSoundSystem.setOnFocusChangeListener(linearFocusChange);
		linear_channalSearch.setOnFocusChangeListener(linearFocusChange);
		linear_channalFreq.setOnFocusChangeListener(linearFocusChange);
	}
}

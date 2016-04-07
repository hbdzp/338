package com.mstar.tv.tvplayer.ui.tuning;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.mstar.tv.tvplayer.ui.AutoTuningInitDialog;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tv.tvplayer.ui.holder.ViewHolder;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.util.PropHelp;
//nathan.liao 2014.11.04 add for auto tuning ANR error start 
import android.os.AsyncTask;
//nathan.liao 2014.11.04 add for auto tuning ANR error end

public class ExitTuningInfoDialog extends Dialog {
	/** Called when the activity is first created. */
	@SuppressWarnings("unused")
	private ViewHolder viewholder_exittune;

	private static int ATV_MIN_FREQ = 45200;

	private static int ATV_MAX_FREQ = 876250;

	private static int ATV_EVENTINTERVAL = 500 * 1000;// every 500ms to show


	protected TextView textview_cha_exittune_yes;

	protected TextView textview_cha_exittune_no;
	protected TextView str_cha_exittuning_info;

	K_ChannelModel mTvChannelManager = null;

//nathan.liao 2014.11.04 add for auto tuning ANR error start 

	private Context mContext;
	
	private AutoTuningInitDialog progressDialog = null;
	
	private setDtvConfigTask task = new setDtvConfigTask();
//nathan.liao 2014.11.04 add for auto tuning ANR error end 

	@SuppressWarnings("unused")
	private ViewHolder viewholder_channeltune;

	public ExitTuningInfoDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exittuninginfo_dialog);
		str_cha_exittuning_info = (TextView) findViewById(R.id.textview_cha_exittuning_info);
		if (K_TvCommonManager.getInstance().K_getCurrentTvInputSource() == K_Constants.INPUT_SOURCE_ATV)
			if(PropHelp.newInstance().hasDtmb())
				str_cha_exittuning_info
						.setText(R.string.str_cha_exitATVtuning_info);
			else
				str_cha_exittuning_info
						.setText(R.string.str_cha_exittuning_info);
		else 
			str_cha_exittuning_info
					.setText(R.string.str_cha_exitDTVtuning_info);
		textview_cha_exittune_yes = (TextView) findViewById(R.id.textview_cha_exittune_yes);
		textview_cha_exittune_no = (TextView) findViewById(R.id.textview_cha_exittune_no);
		viewholder_exittune = new ViewHolder(ExitTuningInfoDialog.this);
		textview_cha_exittune_yes.requestFocus();
		registerListeners();
		mTvChannelManager = K_ChannelModel.getInstance();
	}

	private void registerListeners() {
		textview_cha_exittune_yes.setOnClickListener(listener);
		textview_cha_exittune_no.setOnClickListener(listener);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		int currentid = this.getCurrentFocus().getId();
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			ExitTuningActivityExit(false);
			return true;
		case KeyEvent.KEYCODE_TV_INPUT:
			switch (currentid) {
			case R.id.textview_cha_exittune_yes:
				//ExitTuningActivityExit(true);
				task.execute();//nathan.liao 2014.10.04
				break;
			case R.id.textview_cha_exittune_no:
				ExitTuningActivityExit(false);
				break;
			default:
				break;
			}
			return true;
			case KeyEvent.KEYCODE_VOLUME_DOWN:
			case KeyEvent.KEYCODE_VOLUME_UP:
				switch (currentid) {
				case R.id.textview_cha_exittune_yes:
					textview_cha_exittune_no.requestFocus();
					break;
				case R.id.textview_cha_exittune_no:
					textview_cha_exittune_yes.requestFocus();
					break;
		default:
			break;
				}
				return true;
			default:
				break;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.textview_cha_exittune_yes:
					//ExitTuningActivityExit(true);
					task.execute();//nathan.liao 2014.10.04
				break;
			case R.id.textview_cha_exittune_no:
				ExitTuningActivityExit(false);
				break;
			default:
				ExitTuningActivityExit(false);
				break;
			}
		}
	};

	private void ExitTuningActivityExit(boolean flag) {
		Intent intent = new Intent();

		if (flag == true)// stop tuning
		{
			switch (mTvChannelManager.K_getTuningStatus()) {
			case K_Constants.TUNING_STATUS_ATV_SCAN_PAUSING:
				mTvChannelManager.K_stopAtvAutoTuning();
				mTvChannelManager.K_changeToFirstService(
						K_Constants.FIRST_SERVICE_INPUT_TYPE_ATV,
						K_Constants.FIRST_SERVICE_DEFAULT);
				intent.setAction(TvIntent.MAINMENU);
				intent.putExtra("currentPage", MainMenuActivity.CHANNEL_PAGE);
				getContext().startActivity(intent);
				this.dismiss();
				break;
			case K_Constants.TUNING_STATUS_DTV_SCAN_PAUSING:
				mTvChannelManager.K_stopDtvScan();
				if (mTvChannelManager.K_getUserScanType() == K_Constants.TV_SCAN_ALL) {
					boolean res = mTvChannelManager.K_startAtvAutoTuning(
							ATV_EVENTINTERVAL, ATV_MIN_FREQ, ATV_MAX_FREQ);
					if (res == false) {
						Log.e("TuningService", "atvSetAutoTuningStart Error!!!");
					}
				} else {
					mTvChannelManager.K_changeToFirstService(
							K_Constants.FIRST_SERVICE_INPUT_TYPE_DTV,
							K_Constants.FIRST_SERVICE_DEFAULT);
					intent.setAction(TvIntent.MAINMENU);
					intent.putExtra("currentPage",
							MainMenuActivity.CHANNEL_PAGE);
					getContext().startActivity(intent);
				}
				this.dismiss();
				break;
			default:
				break;
			}
		} else
		// resume tuning
		{
			switch (mTvChannelManager.K_getTuningStatus()) {
			case K_Constants.TUNING_STATUS_ATV_SCAN_PAUSING:
				mTvChannelManager.K_resumeAtvAutoTuning();
				this.dismiss();
				break;
			case K_Constants.TUNING_STATUS_DTV_SCAN_PAUSING:
				mTvChannelManager.K_resumeDtvScan();
				this.dismiss();
				break;
			default:
				break;
			}
		}
	}
	//nathan.liao 2014.11.04 add for auto tuning ANR error start  
	class setDtvConfigTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
		    progressDialog = new AutoTuningInitDialog(mContext, R.style.dialog);
			progressDialog.show();		
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			ExitTuningActivityExit(true);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			super.onPostExecute(result);
		}
	}
	//nathan.liao 2014.11.04 add for auto tuning ANR error end
}

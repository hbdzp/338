package com.mstar.tv.tvplayer.ui.dtv;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_TvLanguage;
import com.kguan.mtvplay.tvapi.vo.K_MenuSubtitleService;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DtvSubtitleInfo;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TimeOutHelper;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.Constant;

public class SubtitleLanguageActivity extends MstarBaseActivity {
	private SubtitleAdapter adapter = null;

	private ListView subtitleLanguageListView = null;

	private static int subtitlePos = 0;

	private TimeOutHelper timeOutHelper;

	private K_ChannelModel mTvChannelManager;
	private String[] LanguageTag;
	private String[] LanguageText;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == TimeOutHelper.getTimeOutMsg()) {
				finish();
			}
		}
	};

	private static class SubtitleAdapter extends BaseAdapter {

		private DataHolder list[];

		private LayoutInflater inflater;

		public SubtitleAdapter(Activity context, DataHolder list[]) {
			this.list = list;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			if (list != null) {
				return list.length;
			}
			return 0;
		}

		@Override
		public Object getItem(int arg0) {

			return arg0;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.subtitle_item, null);
				holder = new ViewHolder();
				holder.language = (TextView) convertView
						.findViewById(R.id.language);
				holder.hoh = (ImageView) convertView.findViewById(R.id.hoh);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.language.setText(list[position].language);
			if (list[position].isHOH) {
				holder.hoh.setVisibility(View.VISIBLE);
			} else {
				holder.hoh.setVisibility(View.INVISIBLE);
			}
			return convertView;
		}

		private static class ViewHolder {
			public TextView language;

			public ImageView hoh;
		}

	}

	private static boolean isHOHSubtitle(int type) {
		/**
		 * // dvb subtitle hard of hearing E_SUBTITLING_TYPE_HH_NO(0x20), 32 //
		 * dvb subtitle hard of hearing 4:3 E_SUBTITLING_TYPE_HH_4X3(0x21), 33
		 * // dvb subtitle hard of hearing 16:9 E_SUBTITLING_TYPE_HH_16X9(0x22),
		 * 34 // dvb subtitle hard of hearing 221:100
		 * E_SUBTITLING_TYPE_HH_221X100(0x23), 35 // dvb HD subtitle hard of
		 * hearing E_SUBTITLING_TYPE_HH_HD(0x24); 36
		 */
		if (type == 32 || type == 33 || type == 34 || type == 35 || type == 36) {
			return true;
		}
		return false;
	}

	private static class DataHolder {
		public String language;

		public boolean isHOH;
	}

	private int currentSubtitleIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subtitle_language);
		subtitleLanguageListView = (ListView) findViewById(R.id.subtitle_language_list_view);

		TextView title = (TextView) findViewById(R.id.subtitle_language_title);
		String str =  getResources().getString(R.string.str_dtv_source_info_Subtitle);
		title.setText(str);

		K_DtvSubtitleInfo subtitleInfo = K_ChannelModel.getInstance().K_getSubtitleInfo();
		
		LanguageTag = getResources().getStringArray(
				R.array.str_arr_subtitle_constants);
		LanguageText = getResources().getStringArray(
				R.array.str_array_language_dtmb);
		
		Log.e("Jason", "subtitleInfo=" + subtitleInfo.getDtvSubtitleInfo().currentSubtitleIndex);
		Log.e("Jason", "subtitleServiceNumber=" + subtitleInfo.getDtvSubtitleInfo().subtitleServiceNumber);
		currentSubtitleIndex = subtitleInfo.getDtvSubtitleInfo().currentSubtitleIndex;
		if (currentSubtitleIndex == 255) {
			currentSubtitleIndex = 0;
		} else {
			currentSubtitleIndex++;
		}

		if (subtitleInfo.getDtvSubtitleInfo().subtitleServiceNumber > 0) {
			// String[] subtitleLang = new
			// String[subtitleInfo.subtitleServiceNumber + 1];
			//
			// subtitleLang[0] = "CLOSE";
			// for (short i = 0; i < subtitleInfo.subtitleServiceNumber; i++)
			// {
			// subtitleLang[i+1] = subtitleInfo.subtitleServices[i]
			// .getLanguage().name();
			// }
			// adapter = new ArrayAdapter<String>(this,
			// R.layout.pvr_menu_info_list_view_item, subtitleLang);

			K_MenuSubtitleService list = new K_MenuSubtitleService();
			list.setMenuSubtitleServices(subtitleInfo.getDtvSubtitleInfo().subtitleServices);
			

			DataHolder[] data = new DataHolder[subtitleInfo.getDtvSubtitleInfo().subtitleServiceNumber + 1];
			DataHolder tmp = null;
			for (int i = 0; i < data.length; i++) {
				tmp = new DataHolder();
				if (i == 0) {
					tmp.language = getResources().getString(R.string.str_subtitle_close);
					tmp.isHOH = false;
				} else {
					
					tmp.language = K_TvLanguage.K_getName(list.getMenuSubtitleServices()[i - 1].eLanguage);
					
					tmp.isHOH = isHOHSubtitle(list.getMenuSubtitleServices()[i - 1].enSubtitleType);
				}
				data[i] = tmp;

			}
			adapter = new SubtitleAdapter(SubtitleLanguageActivity.this, data);
			subtitleLanguageListView.setAdapter(adapter);
			subtitleLanguageListView.setDividerHeight(0);

			subtitleLanguageListView
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							subtitlePos = arg0.getSelectedItemPosition();
							System.out
									.println("\n=====>Set subtitle language index:"
											+ subtitlePos);
							K_ChannelModel.getInstance().K_closeSubtitle();
							if (subtitlePos > 0) {
								mTvChannelManager
										.K_openSubtitle((subtitlePos - 1));
							}
							SharedPreferences settings = getSharedPreferences(
									Constant.PREFERENCES_TV_SETTING,
									Context.MODE_PRIVATE);
							Editor editor = settings.edit();
							editor.putInt("subtitlePos", subtitlePos);
							editor.commit();
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});
		}

		timeOutHelper = new TimeOutHelper(handler, this);
	}

	@Override
	protected void onResume() {
		// SharedPreferences settings = getSharedPreferences("TvSetting", 0);
		// subtitlePos = settings.getInt("subtitlePos", 0);
		// subtitleLanguageListView.setSelection(subtitlePos);
		subtitleLanguageListView.setSelection(currentSubtitleIndex);
		timeOutHelper.start();
		timeOutHelper.init();
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		timeOutHelper.stop();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		timeOutHelper.reset();
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
		case KeyEvent.KEYCODE_PROG_RED:
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * nathan.liao 2013.09.23 switch the language
	 * 
	 * @param name
	 * @return
	 */
	private int getposition_language(String name) {
		for (int i = 0; i < LanguageTag.length; i++) {
			if (LanguageTag[i].equals(name))
				return i;
		}
		return -100;
	}
}

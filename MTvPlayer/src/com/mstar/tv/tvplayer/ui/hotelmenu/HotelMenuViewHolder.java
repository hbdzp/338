package com.mstar.tv.tvplayer.ui.hotelmenu;

import java.util.ArrayList;

import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;

import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.kguan.mtvplay.tvapi.K_TvManager;
import com.kguan.mtvplay.tvapi.vo.K_ProgramInfo;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.util.DataBaseUtil;
import com.mstar.util.PropHelp;

public class HotelMenuViewHolder {
	private HotelMenuActivity hotelMenuActivity;
	protected ComboButton comboBtnHotelMenu;
	protected ComboButton comboBtnCHLock;
	protected ComboButton comboBtnMaxVol;
	protected ComboButton comboBtnAutoSet;
	protected ComboButton comboBtnPicMode;
	protected ComboButton comboBtnPowerVol;
	protected ComboButton comboBtnPowerSource;
	protected ComboButton comboBtnPowerChNumber;
	protected ComboButton comboBtnLockChNumber;
	protected ComboButton comboBtnLockCh;
	protected ComboButton comboBtnKeyLock;
	protected LinearLayout llyClone;
	protected LinearLayout LineLogoReset;

	private final int TXTNAMEID = 0;
	private final int TXTINDICATORID = 2;
	private final int LEFTARROWID = 1;
	private final int RIGHTARROWID = 3;
	private String[] totalSource;

	public short Hotelmode;
	public short SearchLock;
	public short Maxvol;
	public short Autoset;
	public short PictureMode;

	public short PoweronVol;
	public short PoweronSourceType;
	public short PoweronChannel;
	private K_TvCommonManager tvCommonmanager = null;
	private K_ChannelModel tvChannelManager = null;
	private AudioManager mAudioManager;
	private DataBaseUtil mDatabaseUtil;

	private ArrayList<K_ProgramInfo> progInfoList = new ArrayList<K_ProgramInfo>();
	private ArrayList<K_ProgramInfo> progInfoList_atv = new ArrayList<K_ProgramInfo>();
	private ArrayList<K_ProgramInfo> progInfoList_dtv = new ArrayList<K_ProgramInfo>();
	private ArrayList<String> progInfoNum = new ArrayList<String>();
	private ArrayList<String> progInfoNum_atv = new ArrayList<String>();
	private ArrayList<String> progInfoNum_dtv = new ArrayList<String>();

	private ArrayList<String> availableSource = new ArrayList<String>();
	private ArrayList<Integer> availableSourceIdx = new ArrayList<Integer>();
	private int[] sourceIndex = { // input-source transform to
	// EN_TIME_ON_TIME_SOURCE index
			8,// VGA
			1,// ATV
			13,// CVBS
			14,// CVBS2
			15,// CVBS3
			20,// CVBS4
			20,// CVBS5
			20,// CVBS6
			20,// CVBS7
			20,// CVBS8
			20,// CVBS_MAX
			16,// SVIDEO
			17,// SVIDEO2
			20,// SVIDEO3
			20,// SVIDEO4
			20,// SVIDEO_MAX
			6,// YPBPR1
			7,// YPBPR2
			20,// YPBPR3
			20,// YPBPR_MAX
			4,// SCART
			5,// SCART2
			20,// SCART_MAX
			9,// HDMI1
			10,// HDMI2
			11,// HDMI3
			12,// HDMI4
			20,// HDMI_MAX
			0 // DTV
	};

	public HotelMenuViewHolder(HotelMenuActivity activity) {
		hotelMenuActivity = activity;
	}

	public void findViews() {
		tvCommonmanager = K_TvCommonManager.getInstance();
		tvChannelManager = K_ChannelModel.getInstance();
		mDatabaseUtil = DataBaseUtil.getInstance(hotelMenuActivity);
		mAudioManager = (AudioManager) hotelMenuActivity
				.getSystemService(hotelMenuActivity.AUDIO_SERVICE);
		getProgList();
		// TODO Auto-generated method stub
		if (PropHelp.newInstance().hasDtmb()) {
			totalSource = hotelMenuActivity.getResources().getStringArray(
					R.array.str_arr_input_source_dtv_vals);
		} else {
			totalSource = hotelMenuActivity.getResources().getStringArray(
					R.array.str_arr_input_source_vals);
		}
		getAvailableSource();

		comboBtnHotelMenu = new ComboButton(hotelMenuActivity,
				hotelMenuActivity.getResources().getStringArray(
						R.array.str_arr_hotelmennu_onoff),
				R.id.linearlayout_hotel_menu, TXTNAMEID, TXTINDICATORID, true) {

			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				super.doUpdate();
				if (comboBtnHotelMenu.getIdx() == 0) {
					int chIdx = comboBtnLockChNumber.getIdx();
					if (progInfoList.size() > 0) {
						K_ProgramInfo pgi = progInfoList.get(chIdx);
						tvChannelManager.K_setProgramAttribute(
								K_Constants.PROGRAM_ATTRIBUTE_LOCK,
								pgi.getProgram().number,
								pgi.getProgram().serviceType,
								pgi.getProgram().progId, false);
					}
				}
				flushUIwhenHotelmodeonoff();
			}

		};
		comboBtnCHLock = new ComboButton(hotelMenuActivity, hotelMenuActivity
				.getResources().getStringArray(R.array.str_arr_chlock_onoff),
				R.id.linearlayout_ch_lock, TXTNAMEID, TXTINDICATORID, true) {

			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				super.doUpdate();
				mDatabaseUtil.updateDatabase_systemsetting("SearchLock",
						(short) comboBtnCHLock.getIdx());
			}

		};
		comboBtnMaxVol = new ComboButton(hotelMenuActivity, null,
				R.id.linearlayout_max_vol, TXTNAMEID, TXTINDICATORID, true) {

			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				super.doUpdate();
				int values = (short) comboBtnMaxVol.getIdx();
				mDatabaseUtil.updateDatabase_systemsetting("Maxvol", values);
				mAudioManager.setMasterVolume(values, 0);
				if (values < mDatabaseUtil
						.getValueDatabase_systemsetting("PoweronVol")) {
					mDatabaseUtil.updateDatabase_systemsetting("PoweronVol",
							values);
					comboBtnPowerVol.setIdx(values);
				}
			}

		};
		comboBtnAutoSet = new ComboButton(hotelMenuActivity, hotelMenuActivity
				.getResources().getStringArray(R.array.str_arr_chlock_onoff),
				R.id.linearlayout_auto_set, TXTNAMEID, TXTINDICATORID, true) {

			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				super.doUpdate();
				flushUIwhenAutoSet();
			}
		};

		comboBtnPicMode = new ComboButton(hotelMenuActivity, hotelMenuActivity
				.getResources().getStringArray(
						R.array.str_arr_pic_picturemode_vals),
				R.id.linearlayout_picture_mode, TXTNAMEID, TXTINDICATORID, true) {

			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				super.doUpdate();
				mDatabaseUtil.updateDatabase_systemsetting("PictureMode",
						(short) comboBtnPicMode.getIdx());

			}
		};
		comboBtnPowerVol = new ComboButton(hotelMenuActivity, null,
				R.id.linearlayout_power_vol, TXTNAMEID, TXTINDICATORID, true) {

			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				super.doUpdate();
				int value = (short) comboBtnPowerVol.getIdx();
				int maxValue = mDatabaseUtil
						.getValueDatabase_systemsetting("Maxvol");

				if (value > maxValue) {
					value = maxValue;
					comboBtnPowerVol.setIdx(value);
				}
				mDatabaseUtil.updateDatabase_systemsetting("PoweronVol", value);
			}

		};
		comboBtnPowerSource = new ComboButton(hotelMenuActivity,
				availableSource.toArray(new String[availableSource.size()]),
				R.id.linearlayout_power_source, TXTNAMEID, TXTINDICATORID, true) {

			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				super.doUpdate();
				flushUIwhenSrcChange();
			}

		};
		if (!PropHelp.newInstance().hasDtmb())
			comboBtnPowerSource.setItemEnable(1, false);
		comboBtnPowerChNumber = new ComboButton(hotelMenuActivity,
				progInfoNum.size() > 0 ? progInfoNum
						.toArray(new String[progInfoNum.size()]) : null,
				R.id.linearlayout_power_ch_number, TXTNAMEID, TXTINDICATORID,
				true) {

			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				super.doUpdate();
				if (progInfoList.size() == 0)
					mDatabaseUtil.updateDatabase_systemsetting(
							"PoweronChannel", 0);
				else
					mDatabaseUtil.updateDatabase_systemsetting(
							"PoweronChannel",
							(short) getcurrentnumberfromindex(
									comboBtnPowerChNumber.getIdx(),
									progInfoList_atv));
			}

		};
		comboBtnKeyLock = new ComboButton(hotelMenuActivity, hotelMenuActivity
				.getResources().getStringArray(R.array.str_arr_keylock_onoff),
				R.id.linearlayout_key_lock, TXTNAMEID, TXTINDICATORID, true) {
			public void doUpdate() {
				super.doUpdate();
				SaveLockKeyDate();
			}
		};
		comboBtnLockChNumber = new ComboButton(hotelMenuActivity,
				progInfoNum.size() > 0 ? progInfoNum
						.toArray(new String[progInfoNum.size()]) : null,
				R.id.linearlayout_lock_ch_number, TXTNAMEID, TXTINDICATORID,
				true) {

			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				super.doUpdate();
				flushUIwhenLockChChange();
			}

		};
		comboBtnLockCh = new ComboButton(hotelMenuActivity, hotelMenuActivity
				.getResources().getStringArray(R.array.str_arr_chlock_onoff),
				R.id.linearlayout_lock_ch, TXTNAMEID, TXTINDICATORID, true) {

			@Override
			public void doUpdate() {
				// TODO Auto-generated method stub
				super.doUpdate();
				saveChLock();
				// databaseMgr.updateUserSysSetting(databaseMgr.getUsrData());
			}

		};

		llyClone = (LinearLayout) hotelMenuActivity
				.findViewById(R.id.linearlayout_clone);
		llyClone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(hotelMenuActivity,
						CloneActivity.class);
				hotelMenuActivity.startActivityForResult(intent,
						HotelMenuActivity.REQUEST_CLONE);
			}
		});
		LineLogoReset = (LinearLayout) hotelMenuActivity
				.findViewById(R.id.linearlayout_logo_set);
		if (!PropHelp.newInstance().hasLogo()) {
			LineLogoReset.setVisibility(View.GONE);
		}
		LineLogoReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(hotelMenuActivity,
						LogoSetViewActivity.class);
				hotelMenuActivity.startActivityForResult(intent,
						HotelMenuActivity.REQUEST_LOGOSET);
			}
		});
		setOnFocusChangeListeners();
		// setOnClickListeners();
		comboBtnHotelMenu.setFocused();
	}

	private void getAvailableSource() {
		// TODO Auto-generated method stub
		int[] sourcelist = K_TvCommonManager.getInstance().K_getSourceList();
		availableSource.clear();
		availableSourceIdx.clear();
		if (sourcelist != null) {
			for (int i = 0; i < sourcelist.length && i < totalSource.length; i++) {
				if (sourcelist[i] != 0) {
					if (PropHelp.newInstance().isHasT4C1Board()){
						if ((i == 16) || (i == 25)){
							continue;
						}
					}else{
						if ((i == 16) || (i == 0)){
							continue;
						}
					}
					availableSource.add(totalSource[i]);
					availableSourceIdx.add(sourceIndex[i]);
				}
			}
			// 重新排序，将DTV放在ATV后面，将VGA信号源放在最后
			String vgaSource = availableSource.remove(0);
			int vagSourceIndex = availableSourceIdx.remove(0);
			String dtvSource = availableSource
					.remove(availableSource.size() - 1);
			int dtvSourceIndex = availableSourceIdx.remove(availableSource
					.size() - 1);
			availableSource.add(1, dtvSource);
			availableSourceIdx.add(1, dtvSourceIndex);
			availableSource.add(vgaSource);
			availableSourceIdx.add(vagSourceIndex);
		}
	}

	private void setOnFocusChangeListeners() {
		// TODO Auto-generated method stub
		OnFocusChangeListener comboBtnFocusListeners = new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				LinearLayout contain = (LinearLayout) v;
				if (hasFocus) {
					contain.getChildAt(LEFTARROWID).setVisibility(View.VISIBLE);
					contain.getChildAt(RIGHTARROWID)
							.setVisibility(View.VISIBLE);
					contain.setSelected(true);
				} else {
					contain.getChildAt(LEFTARROWID).setVisibility(
							View.INVISIBLE);
					contain.getChildAt(RIGHTARROWID).setVisibility(
							View.INVISIBLE);
					contain.setSelected(false);
				}
			}
		};
		comboBtnHotelMenu.setOnFocusChangeListener(comboBtnFocusListeners);
		comboBtnCHLock.setOnFocusChangeListener(comboBtnFocusListeners);
		comboBtnMaxVol.setOnFocusChangeListener(comboBtnFocusListeners);
		comboBtnAutoSet.setOnFocusChangeListener(comboBtnFocusListeners);
		comboBtnPicMode.setOnFocusChangeListener(comboBtnFocusListeners);
		comboBtnPowerVol.setOnFocusChangeListener(comboBtnFocusListeners);
		comboBtnPowerSource.setOnFocusChangeListener(comboBtnFocusListeners);
		comboBtnPowerChNumber.setOnFocusChangeListener(comboBtnFocusListeners);
		comboBtnLockChNumber.setOnFocusChangeListener(comboBtnFocusListeners);
		comboBtnKeyLock.setOnFocusChangeListener(comboBtnFocusListeners);
		comboBtnLockCh.setOnFocusChangeListener(comboBtnFocusListeners);
	}

	private int getcurrentindex(int numberkey,
			ArrayList<K_ProgramInfo> arrayList) {
		int focusIndex = 200;
		for (int k = 0; k < arrayList.size(); k++) {
			if (numberkey == arrayList.get(k).getProgram().number) {
				focusIndex = k;
				break;
			}
		}
		if (focusIndex >= arrayList.size())
			return arrayList.get(0).getProgram().number;
		else
			return focusIndex;
	}

	private int getcurrentnumberfromindex(int index,
			ArrayList<K_ProgramInfo> arrayList) {
		Log.v("HotelMenuViewHolder", "index22== " + index);
		int currentnumber = arrayList.get(index).getProgram().number;
		Log.v("HotelMenuViewHolder", "currentnumberww== " + currentnumber);
		return currentnumber;
	}

	public void LoadDataToUI() {
		// TODO Auto-generated method stub
		comboBtnCHLock.setIdx(mDatabaseUtil
				.getValueDatabase_systemsetting("SearchLock"));
		comboBtnMaxVol.setIdx(mDatabaseUtil
				.getValueDatabase_systemsetting("Maxvol"));
		comboBtnAutoSet.setIdx(mDatabaseUtil
				.getValueDatabase_systemsetting("Autoset"));
		comboBtnPicMode.setIdx(mDatabaseUtil
				.getValueDatabase_systemsetting("PictureMode"));
		comboBtnKeyLock.setIdx(mDatabaseUtil
				.getValueDatabase_systemsetting("KeyLock"));
		comboBtnPowerVol.setIdx(mDatabaseUtil
				.getValueDatabase_systemsetting("PoweronVol"));
		comboBtnPowerSource.setIdx(Getpoweronsource());
		comboBtnHotelMenu.setIdx(mDatabaseUtil
				.getValueDatabase_systemsetting("Hotelmode"));

		int inputSource = tvCommonmanager.K_getCurrentTvInputSource();
		// 当前信源为ATV，并且频道列表不为空
		if ((inputSource == K_Constants.INPUT_SOURCE_ATV || inputSource == K_Constants.INPUT_SOURCE_DTV)
				&& progInfoNum.size() > 0) {
			int curChannelNumber = tvChannelManager.K_getCurrentChannelNumber();
			if (inputSource == K_Constants.INPUT_SOURCE_DTV) {
				comboBtnLockChNumber.setIdx(getcurrentindex(curChannelNumber,
						progInfoList_dtv));
			} else {
				comboBtnLockChNumber.setIdx(getcurrentindex(curChannelNumber,
						progInfoList_atv));
			}
			comboBtnLockCh.setIdx(0);

		} else {
			comboBtnLockChNumber.setIdx(0);
			comboBtnLockCh.setIdx(0);
		}

		flushUIwhenHotelmodeonoff();
	}

	private void SaveLockKeyDate() {
		if (comboBtnKeyLock.getIdx() == 0) {
			mDatabaseUtil.updateDatabase_systemsetting("KeyLock", 0);
		} else {
			mDatabaseUtil.updateDatabase_systemsetting("KeyLock", 1);
		}
	}

	private void flushUIwhenHotelmodeonoff() {
		if (comboBtnHotelMenu.getIdx() == 0) {
			mDatabaseUtil.updateDatabase_systemsetting("Hotelmode", 0);
			comboBtnCHLock.setEnabled(false);
			comboBtnMaxVol.setEnabled(false);
			comboBtnAutoSet.setEnabled(false);
			comboBtnPicMode.setEnabled(false);
			comboBtnPowerVol.setEnabled(false);
			comboBtnPowerSource.setEnabled(false);
			comboBtnPowerChNumber.setEnabled(false);
			comboBtnLockChNumber.setEnabled(false);
			comboBtnLockCh.setEnabled(false);
			comboBtnKeyLock.setEnabled(false);

			comboBtnCHLock.setFoucesable(false);
			comboBtnMaxVol.setFoucesable(false);
			comboBtnAutoSet.setFoucesable(false);
			comboBtnPicMode.setFoucesable(false);
			comboBtnPowerVol.setFoucesable(false);
			comboBtnPowerSource.setFoucesable(false);
			comboBtnPowerChNumber.setFoucesable(false);
			comboBtnLockChNumber.setFoucesable(false);
			comboBtnLockCh.setFoucesable(false);
			comboBtnKeyLock.setFoucesable(false);

		} else {
			comboBtnCHLock.setEnabled(true);
			comboBtnMaxVol.setEnabled(true);
			comboBtnAutoSet.setEnabled(true);
			comboBtnPowerSource.setEnabled(true);

			comboBtnCHLock.setFoucesable(true);
			comboBtnMaxVol.setFoucesable(true);
			comboBtnAutoSet.setFoucesable(true);
			comboBtnPowerSource.setFoucesable(true);
			comboBtnKeyLock.setEnabled(true);
			comboBtnKeyLock.setFoucesable(true);

			int inputSource = tvCommonmanager.K_getCurrentTvInputSource();
			// 当前信源为ATV，并且频道列表不为空
			if ((inputSource == K_Constants.INPUT_SOURCE_ATV || inputSource == K_Constants.INPUT_SOURCE_DTV)
					&& progInfoNum.size() > 0) {
				comboBtnPowerChNumber.setEnabled(true);
				comboBtnPowerChNumber.setFoucesable(true);
				comboBtnLockChNumber.setEnabled(true);
				comboBtnLockChNumber.setFoucesable(true);
				comboBtnLockCh.setEnabled(true);
				comboBtnLockCh.setFoucesable(true);
			} else {
				comboBtnPowerChNumber.setEnabled(false);
				comboBtnPowerChNumber.setFoucesable(false);
				comboBtnLockChNumber.setEnabled(false);
				comboBtnLockChNumber.setFoucesable(false);
				comboBtnLockCh.setEnabled(false);
				comboBtnLockCh.setFoucesable(false);
			}

			mDatabaseUtil.updateDatabase_systemsetting("Hotelmode", 1);
			flushUIwhenAutoSet();
			flushUIwhenSrcChange();
			flushUIwhenLockChChange();
		}
	}

	private void flushUIwhenAutoSet() {
		if (comboBtnAutoSet.getIdx() == 0) {
			comboBtnPicMode.setEnabled(false);
			comboBtnPowerVol.setEnabled(false);

			comboBtnPicMode.setFoucesable(false);
			comboBtnPowerVol.setFoucesable(false);
			mDatabaseUtil.updateDatabase_systemsetting("Autoset", 0);
		} else {
			comboBtnPicMode.setEnabled(true);
			comboBtnPowerVol.setEnabled(true);

			comboBtnPicMode.setFoucesable(true);
			comboBtnPowerVol.setFoucesable(true);
			mDatabaseUtil.updateDatabase_systemsetting("Autoset", 1);
		}
	}

	private void flushUIwhenSrcChange() {
		switch (comboBtnPowerSource.getIdx()) {
		case 0:
			mDatabaseUtil.updateDatabase_systemsetting("PoweronSourceType", 1);
			break;// atv
		case 1:
			mDatabaseUtil.updateDatabase_systemsetting("PoweronSourceType", 28);
			break;// dtv
		case 2:
			mDatabaseUtil.updateDatabase_systemsetting("PoweronSourceType", 2);
			break;// av1
		case 3:
			mDatabaseUtil.updateDatabase_systemsetting("PoweronSourceType", 3);
			break;// av2
		case 4:
			mDatabaseUtil.updateDatabase_systemsetting("PoweronSourceType", 16);
			break;// ypbpr

		case 5:
			mDatabaseUtil.updateDatabase_systemsetting("PoweronSourceType", 23);
			break;// hdmi1
		case 6:
			mDatabaseUtil.updateDatabase_systemsetting("PoweronSourceType", 24);
			break;// hdmi2
		case 7:
			mDatabaseUtil.updateDatabase_systemsetting("PoweronSourceType", 25);
			break;// hdmi2
		case 8:
			mDatabaseUtil.updateDatabase_systemsetting("PoweronSourceType", 0);
			break;// vga
		default:
			break;
		}
		if ((comboBtnPowerSource.getIdx() == 0) && progInfoNum_atv.size() > 0) {
			comboBtnPowerChNumber = new ComboButton(
					hotelMenuActivity,
					progInfoNum_atv.size() > 0 ? progInfoNum_atv
							.toArray(new String[progInfoNum_atv.size()]) : null,
					R.id.linearlayout_power_ch_number, TXTNAMEID,
					TXTINDICATORID, true) {

				@Override
				public void doUpdate() {
					// TODO Auto-generated method stub
					super.doUpdate();
					if (progInfoList_atv.size() == 0)
						mDatabaseUtil.updateDatabase_systemsetting(
								"PoweronChannel", 0);
					else
						mDatabaseUtil.updateDatabase_systemsetting(
								"PoweronChannel",
								(short) getcurrentnumberfromindex(
										comboBtnPowerChNumber.getIdx(),
										progInfoList_atv));
				}

			};
			if ((getcurrentindex(
					mDatabaseUtil
							.getValueDatabase_systemsetting("PoweronChannel"),
					progInfoList_atv) > progInfoNum_atv.size())) {
				comboBtnPowerChNumber.setIdx(0);
				mDatabaseUtil.updateDatabase_systemsetting("PoweronChannel", 0);
			} else {
				comboBtnPowerChNumber.setIdx(getcurrentindex(mDatabaseUtil
						.getValueDatabase_systemsetting("PoweronChannel"),
						progInfoList_atv));
			}
			comboBtnPowerChNumber.setEnabled(true);
			comboBtnPowerChNumber.setFoucesable(true);
		} else if ((comboBtnPowerSource.getIdx() == 1)
				&& progInfoNum_dtv.size() > 0) {
			comboBtnPowerChNumber = new ComboButton(
					hotelMenuActivity,
					progInfoNum_dtv.size() > 0 ? progInfoNum_dtv
							.toArray(new String[progInfoNum_dtv.size()]) : null,
					R.id.linearlayout_power_ch_number, TXTNAMEID,
					TXTINDICATORID, true) {

				@Override
				public void doUpdate() {
					// TODO Auto-generated method stub
					super.doUpdate();
					if (progInfoList_dtv.size() == 0)
						mDatabaseUtil.updateDatabase_systemsetting(
								"PoweronChannel", 0);
					else
						mDatabaseUtil.updateDatabase_systemsetting(
								"PoweronChannel",
								(short) getcurrentnumberfromindex(
										comboBtnPowerChNumber.getIdx(),
										progInfoList_dtv));
				}
			};
			if (((getcurrentindex(
					mDatabaseUtil
							.getValueDatabase_systemsetting("PoweronChannel"),
					progInfoList_dtv) + 1) > progInfoNum_dtv.size())) {
				comboBtnPowerChNumber.setIdx(0);
				mDatabaseUtil.updateDatabase_systemsetting("PoweronChannel", 1);
				comboBtnPowerChNumber.setIdx(getcurrentindex(1,
						progInfoList_dtv));
			} else {
				if (mDatabaseUtil
						.getValueDatabase_systemsetting("PoweronChannel") == 0) {
					mDatabaseUtil.updateDatabase_systemsetting(
							"PoweronChannel", 1);
				}
				comboBtnPowerChNumber.setIdx(getcurrentindex(mDatabaseUtil
						.getValueDatabase_systemsetting("PoweronChannel"),
						progInfoList_dtv));

			}
			comboBtnPowerChNumber.setEnabled(true);
			comboBtnPowerChNumber.setFoucesable(true);
		} else {
			comboBtnPowerChNumber.setEnabled(false);
			comboBtnPowerChNumber.setFoucesable(false);
		}

	}

	/**
	 * 切换加锁频道后，更新该频道的锁定?
	 */
	private void flushUIwhenLockChChange() {
		if (progInfoList.size() == 0)
			return;

		int chIdx = comboBtnLockChNumber.getIdx();
		K_ProgramInfo pgi = progInfoList.get(chIdx);
		if (pgi.getProgram().isLock) {
			mDatabaseUtil.updateDatabase_systemsetting("bIsBlocked", 1);
			comboBtnLockCh.setIdx(1);
			saveChLock();
		} else {
			comboBtnLockCh.setIdx(0);
		}

		int inputSource = tvCommonmanager.K_getCurrentTvInputSource();
		if (inputSource == K_Constants.INPUT_SOURCE_ATV) {
			int curChannelNumber = tvChannelManager.K_getCurrentChannelNumber();
			if (pgi.getProgram().number != curChannelNumber
					&& pgi.getProgram().number >= 0
					&& pgi.getProgram().number < 200) {
				tvChannelManager.K_selectProgram((pgi.getProgram().number),
						K_Constants.SERVICE_TYPE_ATV);
			}
		} else if (inputSource == K_Constants.INPUT_SOURCE_DTV) {
			Log.v("HotelMenuViewHolder",
					"flushUIwhenLockChChange==" + pgi.getProgram().number);
			tvChannelManager.K_selectProgram((pgi.getProgram().number),
					K_Constants.SERVICE_TYPE_DTV);
		}
	}

	/**
	 * 保存频道的锁定状?
	 */
	private void saveChLock() {
		if (progInfoList.size() == 0)
			return;

		int chIdx = (short) comboBtnLockChNumber.getIdx();
		K_ProgramInfo pgi = progInfoList.get(chIdx);
		pgi.getProgram().isLock = comboBtnLockCh.getIdx() == 0 ? false : true;

		if (pgi.getProgram().isLock) {
			mDatabaseUtil.updateDatabase_systemsetting("bIsBlocked", 1);
		}
		tvChannelManager.K_setProgramAttribute(
				K_Constants.PROGRAM_ATTRIBUTE_LOCK, pgi.getProgram().number,
				pgi.getProgram().serviceType, pgi.getProgram().progId,
				pgi.getProgram().isLock);
		int inputSource = tvCommonmanager.K_getCurrentTvInputSource();
		if ((pgi.getProgram().isLock == false)
				&& (inputSource == K_Constants.INPUT_SOURCE_ATV)) {
			try {
				K_TvManager.getInstance().K_setTvosCommonCommand(
						"SetChannelLock");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取频道列表
	 * 
	 * @param type
	 *            频道类型
	 */
	private void getProgList() {
		K_ProgramInfo pgi = null;
		int indexBase = 0;
		int m_nServiceNum = 0;
		int inputSource = tvCommonmanager.K_getCurrentTvInputSource();
		indexBase = tvChannelManager
				.K_getProgramCount(K_Constants.PROGRAM_COUNT_DTV);
		if (indexBase == 0xFFFFFFFF) {
			indexBase = 0;
		}
		m_nServiceNum = tvChannelManager
				.K_getProgramCount(K_Constants.PROGRAM_COUNT_ATV_DTV);

		progInfoList.clear();
		progInfoList_atv.clear();
		progInfoList_dtv.clear();
		progInfoNum.clear();
		progInfoNum_atv.clear();
		progInfoNum_dtv.clear();

		for (int m = 0; m < indexBase; m++) {
			pgi = tvChannelManager.K_getProgramInfoByIndex(m);
			if (pgi != null) {
				if ((pgi.getProgram().isDelete == true)
						|| (pgi.getProgram().isVisible == false)) {
					continue;
				} else {
					progInfoList_dtv.add(pgi);
					progInfoNum_dtv
							.add(String.valueOf(pgi.getProgram().number));
				}

			}
		}
		for (int k = indexBase; k < m_nServiceNum; k++) {
			pgi = tvChannelManager.K_getProgramInfoByIndex(k);
			if (pgi != null) {
				if ((pgi.getProgram().isDelete == true)
						|| (pgi.getProgram().isVisible == false)) {
					continue;
				} else {
					progInfoList_atv.add(pgi);
					progInfoNum_atv
							.add(String.valueOf(pgi.getProgram().number + 1));
				}
			}

		}
		if (inputSource == K_Constants.INPUT_SOURCE_ATV) {
			progInfoList = progInfoList_atv;
			progInfoNum = progInfoNum_atv;
		} else {
			progInfoList = progInfoList_dtv;
			progInfoNum = progInfoNum_dtv;
		}
	}

	private short Getpoweronsource() {
		switch (mDatabaseUtil
				.getValueDatabase_systemsetting("PoweronSourceType")) {

		case 1:
			return 0;// databaseMgr.getUsrData().PoweronSourceType =

		case 28:
			return 1;// databaseMgr.getUsrData().PoweronSourceType =
						// 28;break;//DTV // 1;break;//atv
		case 2:
			return 2;// databaseMgr.getUsrData().PoweronSourceType =
						// 2;break;//av1
		case 3:
			return 3;// databaseMgr.getUsrData().PoweronSourceType =
						// 3;break;//av2
		case 16:
			return 4;// databaseMgr.getUsrData().PoweronSourceType =
						// 16;break;//ypbpr

		case 23:
			return 5;// databaseMgr.getUsrData().PoweronSourceType =
						// 23;break;//hdmi1
		case 24:
			return 6;// databaseMgr.getUsrData().PoweronSourceType =
						// 24;break;//hdmi2
		case 25:
			return 7;
		case 0:
			return 8;// databaseMgr.getUsrData().PoweronSourceType =
						// 0;break;//vga
		default:
			return 0;// break;
		}
	}
}

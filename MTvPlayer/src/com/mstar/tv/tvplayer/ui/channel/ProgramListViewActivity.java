//<MStar Software>
//******************************************************************************
// MStar Software
// Copyright (c) 2010 - 2013 MStar Semiconductor, Inc. All rights reserved.
// All software, firmware and related documentation herein ("MStar Software") are
// intellectual property of MStar Semiconductor, Inc. ("MStar") and protected by
// law, including, but not limited to, copyright law and international treaties.
// Any use, modification, reproduction, retransmission, or republication of all
// or part of MStar Software is expressly prohibited, unless prior written
// permission has been granted by MStar.
//
// By accessing, browsing and/or using MStar Software, you acknowledge that you
// have read, understood, and agree, to be bound by below terms ("Terms") and to
// comply with all applicable laws and regulations:
//
// 1. MStar shall retain any and all right, ownership and interest to MStar
//    Software and any modification/derivatives thereof.
//    No right, ownership, or interest to MStar Software and any
//    modification/derivatives thereof is transferred to you under Terms.
//
// 2. You understand that MStar Software might include, incorporate or be
//    supplied together with third party's software and the use of MStar
//    Software may require additional licenses from third parties.
//    Therefore, you hereby agree it is your sole responsibility to separately
//    obtain any and all third party right and license necessary for your use of
//    such third party's software.
//
// 3. MStar Software and any modification/derivatives thereof shall be deemed as
//    MStar's confidential information and you agree to keep MStar's
//    confidential information in strictest confidence and not disclose to any
//    third party.
//
// 4. MStar Software is provided on an "AS IS" basis without warranties of any
//    kind. Any warranties are hereby expressly disclaimed by MStar, including
//    without limitation, any warranties of merchantability, non-infringement of
//    intellectual property rights, fitness for a particular purpose, error free
//    and in conformity with any international standard.  You agree to waive any
//    claim against MStar for any loss, damage, cost or expense that you may
//    incur related to your use of MStar Software.
//    In no event shall MStar be liable for any direct, indirect, incidental or
//    consequential damages, including without limitation, lost of profit or
//    revenues, lost or damage of data, and unauthorized system use.
//    You agree that this Section 4 shall still apply without being affected
//    even if MStar Software has been modified by MStar in accordance with your
//    request or instruction for your use, except otherwise agreed by both
//    parties in writing.
//
// 5. If requested, MStar may from time to time provide technical supports or
//    services in relation with MStar Software to you for your use of
//    MStar Software in conjunction with your or your customer's product
//    ("Services").
//    You understand and agree that, except otherwise agreed by both parties in
//    writing, Services are provided on an "AS IS" basis and the warranty
//    disclaimer set forth in Section 4 above shall apply.
//
// 6. Nothing contained herein shall be construed as by implication, estoppels
//    or otherwise:
//    (a) conferring any license or right to use MStar name, trademark, service
//        mark, symbol or any other identification;
//    (b) obligating MStar or any of its affiliates to furnish any person,
//        including without limitation, you and your customers, any assistance
//        of any kind whatsoever, or any information; or
//    (c) conferring any license or right under any intellectual property right.
//
// 7. These terms shall be governed by and construed in accordance with the laws
//    of Taiwan, R.O.C., excluding its conflict of law rules.
//    Any and all dispute arising out hereof or related hereto shall be finally
//    settled by arbitration referred to the Chinese Arbitration Association,
//    Taipei in accordance with the ROC Arbitration Law and the Arbitration
//    Rules of the Association by three (3) arbitrators appointed in accordance
//    with the said Rules.
//    The place of arbitration shall be in Taipei, Taiwan and the language shall
//    be English.
//    The arbitration award shall be final and binding to both parties.
//
//******************************************************************************
//<MStar Software>

package com.mstar.tv.tvplayer.ui.channel;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ListView;

import com.kguan.mtvplay.tvapi.K_ChannelModel;
import com.kguan.mtvplay.tvapi.K_Constants;
import com.kguan.mtvplay.tvapi.K_MKeyEvent;
import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.kguan.mtvplay.tvapi.listener.K_OnDtvPlayerEventListener;
import com.kguan.mtvplay.tvapi.vo.K_ProgramInfo;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DtvEventScan;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.TimeOutHelper;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tvframework.MstarBaseActivity;
import com.mstar.util.PropHelp;

public class ProgramListViewActivity extends MstarBaseActivity {

	private static final String TAG = "ProgramListViewActivity";

	private ListView proListView;

	private ArrayList<ProgramListViewItemObject> plvios = new ArrayList<ProgramListViewItemObject>();

	private ProgramListViewItemObject plvioTmp = new ProgramListViewItemObject();

	private ArrayList<K_ProgramInfo> progInfoList = new ArrayList<K_ProgramInfo>();

	private ProgramEditAdapter mProgramEditAdapter = null;

	private EditText input = null;

	private boolean moveFlag = false;

	private boolean moveble = false;

	private int position;

	private int pageSize = 10;

	private int currutPage = 1;

	private int m_u32Source = 0;

	private int m_u32Target = 0;

	private int m_nServiceNum = 0;
	private int m_dtvServiceNum = 0;

	private View ImgSkip;

	// Remove Edit key cause we have only 4 colored keys in K3.
	private View ImgEdit;

	private View ImgFavorite;

	private View ImgDelete;

	private View ImgMove;

	private TimeOutHelper timeOutHelper;

	private K_ChannelModel mTvChannelManager = null;

	private K_OnDtvPlayerEventListener mDtvEventListener = null;
	private class DtvEventListener extends K_OnDtvPlayerEventListener {

		@Override
		public boolean K_onAudioModeChange(int arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onChangeTtxStatus(int arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onCiLoadCredentialFail(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onDtvAutoTuningScanInfo(int arg0, K_DtvEventScan arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onDtvAutoUpdateScan(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onDtvChannelNameReady(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onDtvPriComponentMissing(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onDtvProgramInfoReady(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onEpgTimerSimulcast(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onGingaStatusMode(int arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onHbbtvStatusMode(int arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onMheg5EventHandler(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onMheg5ReturnKey(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onMheg5StatusMode(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onOadDownload(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onOadHandler(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onOadTimeout(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPopupScanDialogFrequencyChange(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPopupScanDialogLossSignal(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onPopupScanDialogNewMultiplex(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onRctPresence(int arg0) {
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

		@Override
		public boolean K_onTsChange(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onUiOPExitServiceList(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onUiOPRefreshQuery(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean K_onUiOPServiceList(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}
/*
		@Override
		public boolean onDtvChannelNameReady(int what) {
			return false;
		}

		@Override
		public boolean onDtvAutoTuningScanInfo(int what, DtvEventScan extra) {
			return false;
		}

		@Override
		public boolean onDtvProgramInfoReady(int what) {
			return false;
		}

		@Override
		public boolean onCiLoadCredentialFail(int what) {
			return false;
		}

		@Override
		public boolean onEpgTimerSimulcast(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onHbbtvStatusMode(int what, boolean arg1) {
			return false;
		}

		@Override
		public boolean onMheg5StatusMode(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onMheg5ReturnKey(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onOadHandler(int what, int arg1, int arg2) {
			return false;
		}

		@Override
		public boolean onOadDownload(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onDtvAutoUpdateScan(int what) {
			return false;
		}

		@Override
		public boolean onTsChange(int what) {
			return false;
		}

		@Override
		public boolean onPopupScanDialogLossSignal(int what) {
			return false;
		}

		@Override
		public boolean onPopupScanDialogNewMultiplex(int what) {
			return false;
		}

		@Override
		public boolean onPopupScanDialogFrequencyChange(int what) {
			return false;
		}

		@Override
		public boolean onRctPresence(int what) {
			return false;
		}

		@Override
		public boolean onChangeTtxStatus(int what, boolean arg1) {
			return false;
		}

		@Override
		public boolean onDtvPriComponentMissing(int what) {
			return false;
		}

		@Override
		public boolean onAudioModeChange(int what, boolean arg1) {
			return false;
		}

		@Override
		public boolean onMheg5EventHandler(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onOadTimeout(int what, int arg1) {
			return false;
		}

		@Override
		public boolean onGingaStatusMode(int what, boolean arg1) {
			return false;
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
		public boolean onUiOPRefreshQuery(int what) {
			return false;
		}

		@Override
		public boolean onUiOPServiceList(int what) {
			return false;
		}

		@Override
		public boolean onUiOPExitServiceList(int what) {
			return false;
		}*/
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == TimeOutHelper.getTimeOutMsg()) {
				finish();
			}
		}
	};

	/**
	 * @param keyCode
	 * @param selItem
	 * @return
	 */
	boolean checkChmoveble(int keyCode, int selItem) {
		K_ProgramInfo cur = null;
		K_ProgramInfo next = null;
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			if (selItem >= (progInfoList.size() - 1)) {
				return false;
			}
			cur = progInfoList.get(selItem);
			next = progInfoList.get(selItem + 1);
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			if (selItem == 0) {
				return false;
			}
			cur = progInfoList.get(selItem);
			next = progInfoList.get(selItem - 1);
		}
		if (cur.getProgram().serviceType == next.getProgram().serviceType) {
			return true;
		} else {
			return false;
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.program_list_view);
		super.setDelayMillis(15000);
		ImgSkip = findViewById(R.id.program_edit_img_skip);

		ImgEdit = findViewById(R.id.program_edit_img_edit);

		ImgFavorite = findViewById(R.id.program_edit_img_favorite);

		ImgDelete = findViewById(R.id.program_edit_img_delete);

		ImgMove = findViewById(R.id.program_edit_img_move);
		proListView = (ListView) findViewById(R.id.program_edit_list_view);


		mTvChannelManager = K_ChannelModel.getInstance();
		getProgList();
		mProgramEditAdapter = new ProgramEditAdapter(this, plvios);
		proListView.setAdapter(mProgramEditAdapter);
		proListView.setDividerHeight(0);
		proListView.setSelection(getfocusIndex());
		if (PropHelp.newInstance().hasDtmb()) {
		if (!progInfoList.isEmpty()) {
			K_ProgramInfo ProgInf = progInfoList.get(getfocusIndex());
				if (ProgInf.getProgram().serviceType != K_Constants.SERVICE_TYPE_ATV) {
					ImgEdit.setVisibility(View.INVISIBLE);
				} else {
					ImgEdit.setVisibility(View.VISIBLE);
				}
			}
		}
		proListView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
				timeOutHelper.reset();
				// KEYCODE_PROG_RED use for KEYCODE_DEL
				// KEYCODE_PROG_GREEN use for KEYCODE_E
				// KEYCODE_PROG_YELLOW use for KEYCODE_M
				// KEYCODE_PROG_BLUE use for KEYCODE_S
				int selItemIndex = (int) proListView.getSelectedItemId();
				/** start modified by jachensy.chen 2012-6-27 */
				if (((keyCode == KeyEvent.KEYCODE_DPAD_UP) && !moveFlag && (keyEvent
						.getAction() == KeyEvent.ACTION_UP))
						|| (keyCode == KeyEvent.KEYCODE_DPAD_DOWN && !moveFlag && (keyEvent
								.getAction() == KeyEvent.ACTION_UP))) {
					K_ProgramInfo ProgInf = progInfoList.get(selItemIndex);
					/*
					 * Edit by gerard.jiang for "0380586" in 2013/04/18 Change
					 * ATV to DTV.
					 */
					if (ProgInf.getProgram().serviceType != K_Constants.SERVICE_TYPE_ATV) {
						ImgEdit.setVisibility(View.INVISIBLE);
					} else {
						ImgEdit.setVisibility(View.VISIBLE);
					}
				}
				/** end modified by jachensy.chen 2012-6-27 */
				if (((keyCode == KeyEvent.KEYCODE_DPAD_UP) && moveFlag && (keyEvent
						.getAction() == KeyEvent.ACTION_DOWN))
						|| (keyCode == KeyEvent.KEYCODE_DPAD_DOWN && moveFlag && (keyEvent
								.getAction() == KeyEvent.ACTION_DOWN))) {
					if (checkChmoveble(keyCode, selItemIndex)) {
						moveble = true;
					} else {
						moveble = false;
						return true;
					}
				}
				if (((keyCode == KeyEvent.KEYCODE_DPAD_UP) && moveFlag && (keyEvent
						.getAction() == KeyEvent.ACTION_UP))
						|| (keyCode == KeyEvent.KEYCODE_DPAD_DOWN && moveble && (keyEvent
								.getAction() == KeyEvent.ACTION_UP))) {
					if (moveFlag) {
						if ((position >= plvios.size())
								|| (selItemIndex >= plvios.size())) {
							return false;
						}
						swapObject(plvios.get(position),
								plvios.get(selItemIndex));
						swapObject(plvios.get(selItemIndex), plvioTmp);
						position = selItemIndex;
						mProgramEditAdapter.notifyDataSetChanged();
						proListView.invalidate();
						return true;
					} else {
						return true;
					}
				}
				if (moveFlag) {
					return false;
				}
				if (keyCode == KeyEvent.KEYCODE_3
						&& (keyEvent.getAction() == KeyEvent.ACTION_UP)) {
					if (selItemIndex >= progInfoList.size()) {
						return false;
					}
					K_ProgramInfo ProgInf = progInfoList.get(selItemIndex);
					short bfav = ProgInf.getProgram().favorite;

						if (bfav == 0) {
							bfav = 1;
							mTvChannelManager.K_addProgramToFavorite(
									K_Constants.PROGRAM_FAVORITE_ID_1,
									ProgInf.getProgram().number, ProgInf.getProgram().serviceType, 0x00);
							mTvChannelManager.K_setProgramAttribute(
									K_Constants.PROGRAM_ATTRIBUTE_SKIP,
									ProgInf.getProgram().number, ProgInf.getProgram().serviceType, 0x00,
									false);
						} else {
							bfav = 0;
							mTvChannelManager.K_deleteProgramFromFavorite(
									K_Constants.PROGRAM_FAVORITE_ID_1,
									ProgInf.getProgram().number, ProgInf.getProgram().serviceType, 0x00);
						}
					ProgInf.getProgram().favorite = bfav;
					if (selItemIndex >= plvios.size()) {
						return false;
					}
					if (bfav != 0) {
						(plvios.get(selItemIndex)).setFavoriteImg(true);
						(plvios.get(selItemIndex)).setSkipImg(false);
					} else {
						(plvios.get(selItemIndex)).setFavoriteImg(false);
					}
					position = (int) proListView.getSelectedItemId();
					// swapObject(plvioTmp, plvios.get(position));
					mProgramEditAdapter.notifyDataSetChanged();
					proListView.invalidate();
					return true;
				} else if ((keyCode == KeyEvent.KEYCODE_L && (keyEvent
						.getAction() == KeyEvent.ACTION_UP))
						|| (keyCode == K_MKeyEvent.KEYCODE_MSTAR_HOLD && (keyEvent
								.getAction() == KeyEvent.ACTION_UP))) {
					if (selItemIndex >= progInfoList.size()) {
						return false;
					}
					K_ProgramInfo ProgInf = progInfoList.get(selItemIndex);
					boolean block = ProgInf.getProgram().isLock;
					block = !block;
					ProgInf.getProgram().isLock = block;
						mTvChannelManager.K_setProgramAttribute(
								K_Constants.PROGRAM_ATTRIBUTE_LOCK,
								ProgInf.getProgram().number, ProgInf.getProgram().serviceType, 0x00,
								block);
					if (block) {
						plvios.get(selItemIndex).setLockImg(true);
					} else {
						plvios.get(selItemIndex).setLockImg(false);
					}
					// swapObject(plvioTmp, plvios.get(position));
					mProgramEditAdapter.notifyDataSetChanged();
					proListView.invalidate();
					return true;
				} else if (keyCode == KeyEvent.KEYCODE_1
						&& (keyEvent.getAction() == KeyEvent.ACTION_UP)) {
					if (selItemIndex >= progInfoList.size()) {
						return false;
					}
					K_ProgramInfo ProgInf = progInfoList.get(selItemIndex);
					boolean bSkip = ProgInf.getProgram().isSkip;
					bSkip = !bSkip;
					ProgInf.getProgram().isSkip = bSkip;
						mTvChannelManager.K_setProgramAttribute(
								K_Constants.PROGRAM_ATTRIBUTE_SKIP,
								ProgInf.getProgram().number, ProgInf.getProgram().serviceType, 0x00,
								bSkip);
						if(ProgInf.getProgram().favorite == 1)
						{
							mTvChannelManager.K_deleteProgramFromFavorite(
									K_Constants.PROGRAM_FAVORITE_ID_1,
									ProgInf.getProgram().number, ProgInf.getProgram().serviceType, 0x00);
						}
					if (selItemIndex >= plvios.size()) {
						return false;
					}
					if (bSkip) {
						plvios.get(selItemIndex).setSkipImg(true);
						plvios.get(selItemIndex).setFavoriteImg(false);
					} else {
						plvios.get(selItemIndex).setSkipImg(false);
					}
					// swapObject(plvioTmp, plvios.get(position));
					mProgramEditAdapter.notifyDataSetChanged();
					proListView.invalidate();
					return true;
				} else if (keyCode == KeyEvent.KEYCODE_ENTER
						&& (keyEvent.getAction() == KeyEvent.ACTION_UP)) {
					if (selItemIndex >= progInfoList.size()) {
						return false;
					}
					K_ProgramInfo ProgInf = progInfoList.get(selItemIndex);
					K_ProgramInfo curProgInfo = mTvChannelManager
							.K_getCurrentProgramInfo();
						if ((curProgInfo.getProgram().number == ProgInf.getProgram().number)
								&& (curProgInfo.getProgram().serviceType == ProgInf.getProgram().serviceType)) {
							Log.d(TAG, "ProList:Select the same channel!!!");
						} else {
							mTvChannelManager.K_selectProgram(ProgInf.getProgram().number,
									ProgInf.getProgram().serviceType);
						}
					return true;
				} else {
					return false;
				}
			}
		});
		timeOutHelper = new TimeOutHelper(handler, this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		timeOutHelper.start();
		timeOutHelper.init();
		mDtvEventListener = new DtvEventListener();
		mTvChannelManager.K_registerOnDtvPlayerEventListener(mDtvEventListener);
	};

	@Override
	protected void onPause() {
		mTvChannelManager.K_registerOnDtvPlayerEventListener(mDtvEventListener);
		mDtvEventListener = null;
		timeOutHelper.stop();
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		timeOutHelper.reset();
		// KEYCODE_PROG_RED use for KEYCODE_DEL
		// KEYCODE_PROG_GREEN use for KEYCODE_E
		// KEYCODE_PROG_YELLOW use for KEYCODE_M
		if (keyCode == KeyEvent.KEYCODE_4 && (!moveFlag)
				&& plvios.size() != 0) {
			int selItemIndex = (int) proListView.getSelectedItemId();
			if (selItemIndex >= progInfoList.size()) {
				return false;
			}
			K_ProgramInfo selProgInfo = progInfoList.get(selItemIndex);
			K_ProgramInfo curProgInfo = mTvChannelManager.K_getCurrentProgramInfo();
				mTvChannelManager
						.K_setProgramAttribute(
								K_Constants.PROGRAM_ATTRIBUTE_DELETE,
								selProgInfo.getProgram().number, selProgInfo.getProgram().serviceType,
								0x00, true);
			if ((curProgInfo.getProgram().number == selProgInfo.getProgram().number)
					&& (curProgInfo.getProgram().serviceType == selProgInfo.getProgram().serviceType)) {
				if (K_Constants.E_SERVICETYPE_ATV.ordinal() == curProgInfo.getProgram().serviceType) {
					mTvChannelManager.K_changeToFirstService(
							K_Constants.FIRST_SERVICE_INPUT_TYPE_ATV,
							K_Constants.FIRST_SERVICE_DEFAULT);
				} else if (K_Constants.E_SERVICETYPE_DTV.ordinal() == curProgInfo.getProgram().serviceType) {
					mTvChannelManager.K_changeToFirstService(
							K_Constants.FIRST_SERVICE_INPUT_TYPE_DTV,
							K_Constants.FIRST_SERVICE_DEFAULT);
				}
			}
			RefreshContent();
			if (!progInfoList.isEmpty()
					&& (proListView.getSelectedItemId() <= progInfoList.size())) {
				if (proListView.getSelectedItemId() == progInfoList.size()) {
					int lastSelItemIndex = progInfoList.size() - 1;
					selItemIndex = lastSelItemIndex;
				}
			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_5) {
			if (plvios.size() == 0) // || ImgMove.getVisibility() == View.GONE)
				return true;
			moveFlag = !moveFlag;
			setMoveTip(moveFlag);
			position = (int) proListView.getSelectedItemId();
			if (position >= plvios.size()) {
				return false;
			}
			swapObject(plvioTmp, plvios.get(position));
			int curInputSource = K_TvCommonManager.getInstance().K_getCurrentTvInputSource();
			if (moveFlag) {
				if(curInputSource == K_Constants.INPUT_SOURCE_ATV)
				{
					m_u32Source = m_dtvServiceNum+position;
				}else
				{
					m_u32Source = position;
				}
			} else {
				if(curInputSource == K_Constants.INPUT_SOURCE_ATV)
				{
					m_u32Target = m_dtvServiceNum+position;
				}else
				{
					m_u32Target = position;
				}
				if (m_u32Source != m_u32Target) {
					mTvChannelManager.K_moveProgram(m_u32Source, m_u32Target);
					RefreshContent();
				}
				if (progInfoList.size() > 0) {
					if (m_u32Target >= progInfoList.size()) {
						return false;
					}
					K_ProgramInfo ProgInf = progInfoList.get(position);
					if(ProgInf.getProgram().serviceType == K_Constants.SERVICE_TYPE_ATV)
					{
					mTvChannelManager.K_selectProgram(ProgInf.getProgram().number,
							ProgInf.getProgram().serviceType);
					}else if (ProgInf.getProgram().serviceType == K_Constants.SERVICE_TYPE_DTV) {
						mTvChannelManager.K_playDtvCurrentProgram();
					}
				}
			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			if (currutPage >= 2) {
				moveFlag = false;
				setMoveTip(moveFlag);
				currutPage--;
				proListView.setSelection((currutPage - 1) * pageSize);
				return true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			if (currutPage < ((plvios.size() - 1) / pageSize) + 1) {
				moveFlag = false;
				setMoveTip(moveFlag);
				currutPage++;
				proListView.setSelection((currutPage - 1) * pageSize);
				return true;
			}
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			if (proListView.getSelectedItemPosition() == 0) {
				proListView.setSelection(plvios.size() - 1);
				return true;
			}
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			if (proListView.getSelectedItemPosition() == plvios.size() - 1) {
				proListView.setSelection(0);
				return true;
			}
		}
		if ((keyCode == KeyEvent.KEYCODE_BACK)
				|| (keyCode == KeyEvent.KEYCODE_MENU)) {
			Intent intent = new Intent(TvIntent.MAINMENU);
			intent.putExtra("currentPage", MainMenuActivity.CHANNEL_PAGE);
			startActivity(intent);
			finish();
			return true;
		}
		// edit atv program when press "KEYCODE_PROG_GREEN" key,not support dtv
		else if (keyCode == KeyEvent.KEYCODE_2)// KeyEvent.KEYCODE_PROG_GREEN
														// to edit
		{
			int selItemIndex = (int) proListView.getSelectedItemId();
			if (selItemIndex >= progInfoList.size()) {
				return false;
			}

			// Add : will not do onPause
			timeOutHelper.stop();
			super.onRemoveMessage();
			LayoutInflater factory = LayoutInflater.from(this);
			final View textEntryView = factory.inflate(
					R.layout.program_dialog_edit_text, null);
			new AlertDialog.Builder(this)
					.setTitle(R.string.str_program_edit_dialog_input)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(textEntryView)
					.setPositiveButton(R.string.str_program_edit_dialog_ok,
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									int selItemIndex = (int) proListView
											.getSelectedItemId();
									timeOutHelper.start();
									timeOutHelper.init();
									if (selItemIndex >= progInfoList.size()) {
										return;
									}
									if (selItemIndex >= plvios.size()) {
										return;
									}
									K_ProgramInfo ProgInf = progInfoList
											.get(selItemIndex);
									input = (EditText) textEntryView
											.findViewById(R.id.program_edit_text);
									String Tvmame = input.getText().toString();
									String finalName = splitString(Tvmame, 27);// sn:MAX_STATION_NAME=30
									(plvios.get(selItemIndex))
											.setTvName(finalName);
									mProgramEditAdapter.notifyDataSetChanged();
									proListView.invalidate();
										mTvChannelManager.K_setProgramName(
												ProgInf.getProgram().number,
												ProgInf.getProgram().serviceType, finalName);
								}
							})
					.setNegativeButton(R.string.str_program_edit_dialog_cancel,
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									timeOutHelper.start();
									timeOutHelper.init();
								}
							}).show().setOnDismissListener(new OnDismissListener() {
								
								@Override
								public void onDismiss(DialogInterface arg0) {
									// TODO Auto-generated method stub
									sendMessage();
								}
							});// show this for atv program
			return true;
		}
		if(keyCode == KeyEvent.KEYCODE_0 ||keyCode == KeyEvent.KEYCODE_1||keyCode == KeyEvent.KEYCODE_2
				||keyCode == KeyEvent.KEYCODE_3||keyCode == KeyEvent.KEYCODE_4||keyCode == KeyEvent.KEYCODE_5
				||keyCode == KeyEvent.KEYCODE_6||keyCode == KeyEvent.KEYCODE_7||keyCode == KeyEvent.KEYCODE_8
				||keyCode == KeyEvent.KEYCODE_9)
		{
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public String splitString(String str, int len) {
		if (str == null) {
			return "";
		}
		int k = 0;
		String new_str = "";
		for (int i = 0; i < str.length(); i++) {
			byte[] b = (str.charAt(i) + "").getBytes();
			k = k + b.length;
			if (k > len) {
				break;
			}
			new_str = new_str + str.charAt(i);
		}
		return new_str;
	}

	private void swapObject(ProgramListViewItemObject obj1,
			ProgramListViewItemObject obj2) {
		obj1.setTvName(obj2.getTvName());
		obj1.setTvNumber(obj2.getTvNumber());
		obj1.setFavoriteImg(obj2.isFavoriteImg());
		obj1.setSkipImg(obj2.isSkipImg());
		obj1.setSslImg(obj2.isSslImg());
		obj1.setServiceType(obj2.getServiceType());
	}

	private void RefreshContent() {
		plvios.clear();
		progInfoList.clear();
		getProgList();
		mProgramEditAdapter.notifyDataSetChanged();
		proListView.invalidate();
		if (!progInfoList.isEmpty()) {
			K_ProgramInfo ProgInf = progInfoList.get(getfocusIndex());

			if (ProgInf.getProgram().serviceType != K_Constants.SERVICE_TYPE_ATV) {
				ImgEdit.setVisibility(View.INVISIBLE);
			} else {
				ImgEdit.setVisibility(View.VISIBLE);
			}
		}
	}

	private void addOneListViewItem(K_ProgramInfo pgi) {
		boolean flag = false;
		if (pgi != null) {
			ProgramListViewItemObject plvio = new ProgramListViewItemObject();
				plvio.setTvName(pgi.getProgram().serviceName);
			if (pgi.getProgram().serviceType == K_Constants.SERVICE_TYPE_ATV) {
					plvio.setTvNumber(String.valueOf(pgi.getProgram().number + 1));
			} else {
					plvio.setTvNumber(String.valueOf(pgi.getProgram().number));
			}
			flag = false;
			if (pgi.getProgram().favorite != 0) {
				flag = true;
			}
			plvio.setFavoriteImg(flag);
			flag = pgi.getProgram().isSkip;
			plvio.setSkipImg(flag);
			flag = pgi.getProgram().isLock;
			plvio.setLockImg(flag);
			flag = pgi.getProgram().isScramble;
			plvio.setSslImg(flag);
			plvio.setServiceType(pgi.getProgram().serviceType);
			plvios.add(plvio);
		}
	}

	private int getfocusIndex() {
		int focusIndex = 0;
		K_ProgramInfo cpi = mTvChannelManager.K_getCurrentProgramInfo();
		for (K_ProgramInfo pi : progInfoList) {
				if (cpi.getProgram().number == pi.getProgram().number) {
					focusIndex = progInfoList.indexOf(pi);
					break;
				}
		}
		return focusIndex;
	}

	private void getProgList() {
		K_ProgramInfo pgi = null;
		m_nServiceNum = mTvChannelManager
				.K_getProgramCount(K_Constants.PROGRAM_COUNT_ATV_DTV);
		m_dtvServiceNum = mTvChannelManager.K_getProgramCount(K_Constants.PROGRAM_COUNT_DTV);
		int curInputSource = K_TvCommonManager.getInstance().K_getCurrentTvInputSource();
		for (int k = 0; k < m_nServiceNum; k++) {
				pgi = mTvChannelManager.K_getProgramInfoByIndex(k);
			if (pgi != null) {
				if (!((K_Constants.INPUT_SOURCE_ATV == curInputSource &&
						K_Constants.SERVICE_TYPE_ATV == pgi.getProgram().serviceType) ||
                        (K_Constants.INPUT_SOURCE_DTV == curInputSource &&
                        		K_Constants.SERVICE_TYPE_DTV == pgi.getProgram().serviceType))) {
                    continue;
                }
				if ((pgi.getProgram().isDelete == true) || (pgi.getProgram().isVisible == false)) {
					continue;
				} else {
					progInfoList.add(pgi);
					addOneListViewItem(pgi);
				}
			}
		}
	}

	private void setMoveTip(boolean b)

	{

		if (b)

		{

			ImgDelete.setVisibility(View.INVISIBLE);

			ImgEdit.setVisibility(View.INVISIBLE);

			ImgFavorite.setVisibility(View.INVISIBLE);

			ImgMove.setVisibility(View.VISIBLE);

			ImgSkip.setVisibility(View.INVISIBLE);

		}

		else

		{

			ImgDelete.setVisibility(View.VISIBLE);

			ImgEdit.setVisibility(View.VISIBLE);

			ImgFavorite.setVisibility(View.VISIBLE);

			ImgMove.setVisibility(View.VISIBLE);

			ImgSkip.setVisibility(View.VISIBLE);

		}

	}
	
	@Override
	public void onTimeOut() {
		// TODO Auto-generated method stub
		finish();
		super.onTimeOut();
	}
	public void sendMessage()
	{
		super.onSendMessage();
	}
}

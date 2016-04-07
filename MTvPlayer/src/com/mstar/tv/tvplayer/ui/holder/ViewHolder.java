//<MStar Software>
//******************************************************************************
// MStar Software
// Copyright (c) 2010 - 2014 MStar Semiconductor, Inc. All rights reserved.
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

package com.mstar.tv.tvplayer.ui.holder;

import com.mstar.tv.tvplayer.ui.tuning.ATVManualTuning;
import com.mstar.tv.tvplayer.ui.tuning.ChannelTuning;
import com.mstar.tv.tvplayer.ui.tuning.DTVManualTuning;
import com.mstar.tv.tvplayer.ui.tuning.ExitTuningInfoDialog;
import com.mstar.tv.tvplayer.ui.R;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.LinearLayout;

public class ViewHolder {

	private ChannelTuning channeltune;

	@SuppressWarnings("unused")
	private ExitTuningInfoDialog exittune;

	private DTVManualTuning dtvmanualtune;

	private ATVManualTuning atvmanualtune;

	/*----------------for channelActivity------------------*/
	protected TextView text_cha_antennatype_val;

	protected LinearLayout linearlayout_cha_antennatype;

	protected LinearLayout linearlayout_cha_autotuning;

	protected LinearLayout linearlayout_cha_dtvmanualtuning;

	protected LinearLayout linearlayout_cha_atvmanualtuning;

	protected LinearLayout linearlayout_cha_programedit;

	protected LinearLayout linearlayout_cha_ciinformation;

	/*---------------for channeltuning-----------------------*/
	public LinearLayout linear_cha_mainlinear;

	public TextView text_cha_tvprogram_val;

	public TextView text_cha_dtvprogram_val;

	public LinearLayout linear_cha_radioprogram;

	public TextView text_cha_radioprogram_val;

	public TextView text_cha_tuningprogress_num;

	public LinearLayout linear_cha_dataprogram;

	public TextView text_cha_dataprogram_val;

	public TextView text_cha_tuningprogress_rf;

	public TextView text_cha_tuningprogress_ch;

	public TextView text_cha_tuningprogress_val;

	public TextView text_cha_tuningprogress_type;

	public LinearLayout linear_cha_airdtv;

	public LinearLayout linear_cha_airtv;

	public LinearLayout linear_cha_cabletv;

	public LinearLayout lineaR_cha_tvprogram;

	public LinearLayout lineaR_cha_dtvprogram;

	public ProgressBar progressbar_cha_tuneprogress;

	/*-------------for dtvmanualtuning----------------------*/
	public LinearLayout linear_cha_dtvmanualtuning_channelnum;
	public LinearLayout linear_cha_dtvmanualtuning_channelfreq;

	public TextView text_cha_dtvmanualtuning_channelnum_val;

	public TextView text_cha_dtvmanualtuning_modulation_val;

	public LinearLayout linear_cha_dtvmanualtuning_signalstrength_val;

	public LinearLayout linear_cha_dtvmanualtuning_signalquality_val;

	public TextView text_cha_dtvmanualtuning_tuningresult_dtv_val;

	public TextView text_cha_dtvmanualtuning_tuningresult_data_val;

	public TextView text_cha_dtvmanualtuning_tuningresult_radio_val;

	public TextView text_cha_dtvmanualtuning_symbol_val;

	public TextView text_cha_dtvmanualtuning_frequency_val;

	/*-------------for atvmanualtuning----------------------*/
	public TextView text_cha_atvmanualtuning_channelnum_val;

	public LinearLayout linear_cha_atvmanualtuning_colorsystem;

	public TextView text_cha_atvmanualtuning_colorsystem_val;

	public LinearLayout linear_cha_atvmanualtuning_soundsystem;

	public TextView text_cha_atvmanualtuning_soundsystem_val;

	public TextView text_cha_atvmanualtuning_freqency_val;

	/*----------------for TimeActivity----------------------*/
	protected TextView text_time_sleep_val;

	protected TextView text_time_autotime_val;

	protected LinearLayout linear_time_schedule;

	/*----------------for scheduletime----------------------*/
	protected TextView text_time_schedule_ontime_val;

	protected TextView text_time_schedule_hour_val;

	protected TextView text_time_schedule_minute_val;

	protected TextView text_time_schedule_source_val;

	protected TextView text_time_schedule_channel_val;

	protected TextView text_time_schedule_volume_val;

	/*------------------------for hdmicec---------------------*/
	protected TextView text_hdmicec_devicelist_val;

	protected TextView text_set_hdmicec_onoff_val;

	protected TextView text_set_hdmicec_autostandby_val;

	protected TextView text_set_hdmicec_arc_val;

	/*---------------for oad scan-----------------------*/
	public TextView text_oadscan;

	public LinearLayout linear_oadscan_progress;

	public LinearLayout linear_oadscan_progress_text;

	public TextView text_oadscan_progress_percent;

	public TextView text_oadscan_ch;

	public TextView text_oadscan_num;

	public ProgressBar bar_oadscan_progress;
	//===============ktc add start==========
	public TextView text_cha_tv;
	public TextView text_cha_curch_val;
	public TextView text_cha_dtvmanualtuning_start_state;
    //===============ktc add end
	// public ViewHolder(ChannelActivity activity) {
	// this.channelactivity = activity;
	// }

	public ViewHolder(ChannelTuning activity) {
		this.channeltune = activity;
	}

	public ViewHolder(ExitTuningInfoDialog activity) {
		this.exittune = activity;
	}

	public ViewHolder(DTVManualTuning activity) {
		this.dtvmanualtune = activity;
	}

	public ViewHolder(ATVManualTuning activity) {
		this.atvmanualtune = activity;
	}

	/*---------------for channeltuning-----------------------*/
	public void findViewForChannelTuning() {
		linear_cha_mainlinear = (LinearLayout) channeltune
				.findViewById(R.id.linearlayout_cha_mainlinear);
		text_cha_tvprogram_val = (TextView) channeltune
				.findViewById(R.id.textview_cha_tvprogram_val);
		text_cha_dtvprogram_val = (TextView) channeltune
				.findViewById(R.id.textview_cha_dtvprogram_val);
		linear_cha_radioprogram = (LinearLayout) channeltune
				.findViewById(R.id.linearlayout_cha_radioprogram);
		text_cha_radioprogram_val = (TextView) channeltune
				.findViewById(R.id.textview_cha_radioprogram_val);
		linear_cha_dataprogram = (LinearLayout) channeltune
				.findViewById(R.id.linearlayout_cha_dataprogram);
		text_cha_dataprogram_val = (TextView) channeltune
				.findViewById(R.id.textview_cha_dataprogram_val);
		text_cha_tuningprogress_rf = (TextView) channeltune
				.findViewById(R.id.textview_cha_tuningprogress_rf);
		text_cha_tuningprogress_ch = (TextView) channeltune
				.findViewById(R.id.textview_cha_tuningprogress_ch);
		text_cha_tuningprogress_val = (TextView) channeltune
				.findViewById(R.id.textview_cha_tuningprogress_percent);
		text_cha_tuningprogress_num = (TextView) channeltune
				.findViewById(R.id.textview_cha_tuningprogress_num);
		text_cha_tuningprogress_type = (TextView) channeltune
				.findViewById(R.id.textview_cha_tuningprogress_type);
		progressbar_cha_tuneprogress = (ProgressBar) channeltune
				.findViewById(R.id.progressbar_cha_tuningprogress);
		lineaR_cha_tvprogram = (LinearLayout) channeltune
				.findViewById(R.id.linearlayout_cha_tvprogram);
		lineaR_cha_dtvprogram = (LinearLayout) channeltune
				.findViewById(R.id.linearlayout_cha_dtvprogram);
		text_cha_tv = (TextView)channeltune.findViewById(R.id.textview_cha_tv);
		text_cha_curch_val = (TextView)channeltune.findViewById(R.id.textview_cha_tuningprogress_ch);

	}
	/*--------------for dtvmanualtuning-----------------------*/
	public void findViewForDtvManualTuning() {
		linear_cha_dtvmanualtuning_channelnum = (LinearLayout) dtvmanualtune
				.findViewById(R.id.linearlayout_cha_dtvmanualtuning_channelnum);
		linear_cha_dtvmanualtuning_channelfreq = (LinearLayout) dtvmanualtune
				.findViewById(R.id.linearlayout_cha_dtvmanualtuning_frequency);
		text_cha_dtvmanualtuning_channelnum_val = (TextView) dtvmanualtune
				.findViewById(R.id.textview_cha_dtvmanualtuning_channelnum_val);
		text_cha_dtvmanualtuning_modulation_val = (TextView) dtvmanualtune
				.findViewById(R.id.textview_cha_dtvmanualtuning_modulation_val);
		linear_cha_dtvmanualtuning_signalstrength_val = (LinearLayout) dtvmanualtune
				.findViewById(R.id.linearlayout_cha_dtvmanualtuning_signalstrength_val);
		linear_cha_dtvmanualtuning_signalquality_val = (LinearLayout) dtvmanualtune
				.findViewById(R.id.linearlayout_cha_dtvmanualtuning_signalquality_val);
		text_cha_dtvmanualtuning_tuningresult_dtv_val = (TextView) dtvmanualtune
				.findViewById(R.id.textview_cha_dtvmanualtuning_tuningresult_dtv_val);
		text_cha_dtvmanualtuning_tuningresult_data_val = (TextView) dtvmanualtune
				.findViewById(R.id.textview_cha_dtvmanualtuning_tuningresult_data_val);
		text_cha_dtvmanualtuning_tuningresult_radio_val = (TextView) dtvmanualtune
				.findViewById(R.id.textview_cha_dtvmanualtuning_tuningresult_radio_val);
		text_cha_dtvmanualtuning_symbol_val = (TextView) dtvmanualtune
				.findViewById(R.id.textview_cha_dtvmanualtuning_symbol_val);
		text_cha_dtvmanualtuning_frequency_val = (TextView) dtvmanualtune
				.findViewById(R.id.textview_cha_dtvmanualtuning_frequency_val);
		text_cha_dtvmanualtuning_start_state = (TextView)dtvmanualtune.findViewById(R.id.textview_cha_dtvmanualtuning_starttuning_state);
	}

	/*--------------for atvmanualtuning-----------------------*/
	public void findViewForAtvManualTuning() {
		text_cha_atvmanualtuning_channelnum_val = (TextView) atvmanualtune
				.findViewById(R.id.textview_cha_atvmanualtuning_channelnum_val);
		linear_cha_atvmanualtuning_colorsystem = (LinearLayout) atvmanualtune
				.findViewById(R.id.linearlayout_cha_atvmanualtuning_colorsystem);
		text_cha_atvmanualtuning_colorsystem_val = (TextView) atvmanualtune
				.findViewById(R.id.textview_cha_atvmanualtuning_colorsystem_val);
		linear_cha_atvmanualtuning_soundsystem = (LinearLayout) atvmanualtune
				.findViewById(R.id.linearlayout_cha_atvmanualtuning_soundsystem);
		text_cha_atvmanualtuning_soundsystem_val = (TextView) atvmanualtune
				.findViewById(R.id.textview_cha_atvmanualtuning_soundsystem_val);
		text_cha_atvmanualtuning_freqency_val = (TextView) atvmanualtune
				.findViewById(R.id.textview_cha_atvmanualtuning_frequency_val);
	}

}

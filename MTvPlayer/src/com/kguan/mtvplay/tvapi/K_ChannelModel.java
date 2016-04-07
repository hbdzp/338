package com.kguan.mtvplay.tvapi;

import com.kguan.mtvplay.tvapi.listener.K_OnAtvPlayerEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnDtvPlayerEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnTvPlayerEventListener;
import com.kguan.mtvplay.tvapi.vo.K_ProgramInfo;
import com.kguan.mtvplay.tvapi.vo.common.K_DtvProgramSignalInfo;
import com.kguan.mtvplay.tvapi.vo.common.K_ProgramInfoQueryCriteria;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DtvAudioInfo;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DtvSubtitleInfo;
import com.kguan.mtvplay.tvapi.vo.dtv.K_DvbMuxInfo;
import com.kguan.mtvplay.tvapi.vo.dtv.K_RfInfo;
import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvDvbChannelManager;
//import com.mstar.android.tv.TvIsdbChannelManager;

/**
 * 
 * @ClassName: K_ChannelModel
 * @Description: TV频道
 * @author: zengjf
 * @date: 2015-9-15
 */
public class K_ChannelModel {
	public class K_DvbcScanParam extends TvChannelManager.DvbcScanParam {

		public K_DvbcScanParam() {
			tvChannelManager.super();
		}

	}

	public static K_ChannelModel k_ChannelModel = null;
	public TvChannelManager tvChannelManager = null;
	public TvAtscChannelManager tvAtscChannelManager = null;
	public TvDvbChannelManager tvDvbChannelManager = null;
	//public TvIsdbChannelManager tvIsdbChannelManager = null;
	private int mTvSystem;

	/**
	 * 
	 * @Title:K_ChannelModel
	 * @Description:初始化
	 * @param context
	 */
	private K_ChannelModel() {
		tvChannelManager = TvChannelManager.getInstance();
		tvDvbChannelManager = TvDvbChannelManager.getInstance();
		mTvSystem = K_TvCommonManager.getInstance().K_getCurrentTvSystem();
		//tvIsdbChannelManager = TvIsdbChannelManager.getInstance();
		if (mTvSystem == K_Constants.TV_SYSTEM_ATSC)
			tvAtscChannelManager = TvAtscChannelManager.getInstance();
	}

	/**
	 * 
	 * @Title: getInstance
	 * @Description: 获取K_ChannelModel实例
	 * @param context
	 * @return
	 * @return: K_ChannelModel
	 */
	public static K_ChannelModel getInstance() {
		if (k_ChannelModel == null) {
			k_ChannelModel = new K_ChannelModel();
		}
		return k_ChannelModel;
	}

	/*
	 * 定义相关get方法，引用相应的API
	 */

	/**
	 * 
	 * @Title: K_getDtvAntennaType
	 * @Description: 获取DTV天线类型(mTvSystem == K_Constants.TV_SYSTEM_ATSC时调用)
	 * @return
	 * @return: int
	 */
	public int K_getDtvAntennaType() {
		return tvAtscChannelManager.getDtvAntennaType();
	}

	/**
	 * 
	 * @Title: K_getProgramCount
	 * @Description: 获取节目个数
	 * @param arg0
	 * @return
	 * @return: int
	 */
	public int K_getProgramCount(int arg0) {
		return tvChannelManager.getProgramCount(arg0);
	}

	/**
	 * 
	 * @Title: K_getCurrentDtvRouteIndex
	 * @Description: 获取当前DTV通道
	 * @return
	 * @return: int
	 */
	public int K_getCurrentDtvRouteIndex() {
		return tvChannelManager.getCurrentDtvRouteIndex();
	}

	/**
	 * 
	 * @Title: K_getSystemCountryId
	 * @Description: 获取系统的国家ID参数
	 * @return
	 * @return: int
	 */
	public int K_getSystemCountryId() {
		return tvChannelManager.getSystemCountryId();
	}

	public int K_getSpecificDtvRouteIndex(int arg0) {
		return tvChannelManager.getSpecificDtvRouteIndex(arg0);
	}

	/**
	 * 
	 * @Title: K_setDtvAntennaType_TvDv
	 * @Description: 设置DTV天线类型（TvDv）(mTvSystem == K_Constants.TV_SYSTEM_ATSC时调用)
	 * @param arg0
	 * @return: void
	 */
	public void K_setDtvAntennaType_TvDv(int arg0) {
		if (tvDvbChannelManager != null)
			tvDvbChannelManager.setDtvAntennaType(arg0);
	}

	public K_ProgramInfo K_getProgramInfo(K_ProgramInfoQueryCriteria criteria, int programInfoType) {
		//K_ProgramInfo programInfo = K_ProgramInfo.getInstance();
		K_ProgramInfo programInfo = new K_ProgramInfo();
		programInfo.setProgram(tvDvbChannelManager.getProgramInfo(criteria.getProgramInfoQueryCriteria(), programInfoType));
		return programInfo;
	}

	/**
	 * 
	 * @Title: K_setDtvAntennaType_TvAt
	 * @Description: 设置DTV天线类型（TvAt）(mTvSystem == K_Constants.TV_SYSTEM_ATSC时调用)
	 * @param arg0
	 * @return: void
	 */
	public void K_setDtvAntennaType_TvAt(int arg0) {
		if (tvAtscChannelManager != null)
			tvAtscChannelManager.setDtvAntennaType(arg0);
	}

	/**
	 * 
	 * @Title: K_setDtvAntennaType
	 * @Description: 设置DTV的天线类型
	 * @param arg0
	 * @return: void
	 */
	public void K_setDtvAntennaType(int arg0) {
		if (tvDvbChannelManager != null)
			tvDvbChannelManager.setDtvAntennaType(arg0);
	}

	/**
	 * 
	 * @Title: K_changeToFirstService
	 * @Description: 修改为优先服务等级
	 * @param arg0
	 * @param arg1
	 * @return: void
	 */
	public void K_changeToFirstService(int arg0, int arg1) {
		if (tvChannelManager != null)
			tvChannelManager.changeToFirstService(arg0, arg1);
	}

	public void K_setUserScanType(int arg0) {
		if (tvChannelManager != null)
			tvChannelManager.setUserScanType(arg0);
	}

	public void K_switchMSrvDtvRouteCmd(int arg0) {
		if (tvChannelManager != null)
			tvChannelManager.switchMSrvDtvRouteCmd(arg0);
	}

	public void K_setSystemCountryId(int arg0) {
		if (tvChannelManager != null)
			tvChannelManager.setSystemCountryId(arg0);
	}

	/**
	 * 
	 * @Title: K_getAudioLanguageDefaultValue
	 * @Description: 获取音频的默认语言
	 * @return
	 * @return: int
	 */
	public int K_getAudioLanguageDefaultValue() {
		return tvChannelManager.getAudioLanguageDefaultValue();
	}

	/**
	 * 
	 * @Title: K_getAudioLanguageSecondaryValue
	 * @Description: 获取音频的第二语言
	 * @return
	 * @return: int
	 */
	public int K_getAudioLanguageSecondaryValue() {
		return tvChannelManager.getAudioLanguageSecondaryValue();
	}

	/**
	 * 
	 * @Title: K_setAudioLanguageDefaultValue
	 * @Description: 设置音频默认语言
	 * @param arg0
	 * @return: void
	 */
	public void K_setAudioLanguageDefaultValue(int arg0) {
		if (tvChannelManager != null)
			tvChannelManager.setAudioLanguageDefaultValue(arg0);
	}

	/**
	 * 
	 * @Title: K_setAudioLanguageSecondaryValue
	 * @Description: 设置音频第二语言
	 * @param arg0
	 * @return: void
	 */
	public void K_setAudioLanguageSecondaryValue(int arg0) {
		if (tvChannelManager != null)
			tvChannelManager.setAudioLanguageSecondaryValue(arg0);
	}

	/**
	 * 
	 * @Title: K_setAtvChannelSwitchMode
	 * @Description: 设置ATV频道的切换模式
	 * @param arg0
	 * @return: void
	 */
	public void K_setAtvChannelSwitchMode(int arg0) {
		if (tvChannelManager != null)
			tvChannelManager.setAtvChannelSwitchMode(arg0);
	}

	public int K_getAtvChannelSwitchMode() {
		return tvChannelManager.getAtvChannelSwitchMode();
	}

	public void K_setChannelChangeFreezeMode(boolean b) {
		if (tvChannelManager != null)
			tvChannelManager.setChannelChangeFreezeMode(b);
	}

	public K_ProgramInfo K_getCurrentProgramInfo() {
		//K_ProgramInfo programInfo = K_ProgramInfo.getInstance();
		K_ProgramInfo programInfo = new K_ProgramInfo();
		programInfo.setProgram(tvChannelManager.getCurrentProgramInfo());
		return programInfo ;
	}

	public int K_getCurrentChannelNumber() {
		return tvChannelManager.getCurrentChannelNumber();
	}

	public void K_setAtvChannel(int curChannelNumber) {
		if (tvChannelManager != null)
			tvChannelManager.setAtvChannel(curChannelNumber);
	}

	public void K_playDtvCurrentProgram() {
		if (tvChannelManager != null)
			tvChannelManager.playDtvCurrentProgram();
	}

	public void K_registerOnDtvPlayerEventListener(K_OnDtvPlayerEventListener mDtvPlayerEventListener) {
		if (tvChannelManager != null)
			tvChannelManager.registerOnDtvPlayerEventListener(mDtvPlayerEventListener);
	}

	public void K_registerOnTvPlayerEventListener(K_OnTvPlayerEventListener mTvPlayerEventListener) {
		if (tvChannelManager != null)
			tvChannelManager.registerOnTvPlayerEventListener(mTvPlayerEventListener);
	}

	public void K_registerOnAtvPlayerEventListener(K_OnAtvPlayerEventListener mAtvPlayerEventListener) {
		if (tvChannelManager != null)
			tvChannelManager.registerOnAtvPlayerEventListener(mAtvPlayerEventListener);
	}

	public void K_unregisterOnDtvPlayerEventListener(K_OnDtvPlayerEventListener listener) {
		if (tvChannelManager != null)
			tvChannelManager.unregisterOnDtvPlayerEventListener(listener);
	}
	
	public void K_unregisterOnTvPlayerEventListener(K_OnTvPlayerEventListener mTvPlayerEventListener){
		if (tvChannelManager != null)
			tvChannelManager.unregisterOnTvPlayerEventListener(mTvPlayerEventListener);
	}

	public void K_unregisterOnAtvPlayerEventListener(K_OnAtvPlayerEventListener listener) {
		if (tvChannelManager != null)
			tvChannelManager.unregisterOnAtvPlayerEventListener(listener);
	}

	public void K_programDown() {
		if (tvChannelManager != null)
			tvChannelManager.programDown();
	}

	public void K_programUp() {
		if (tvChannelManager != null)
			tvChannelManager.programUp();

	}

	public void K_returnToPreviousProgram() {
		if (tvChannelManager != null)
			tvChannelManager.returnToPreviousProgram();
	}

	public K_ProgramInfo K_getProgramInfoByIndex(int k) {
		//K_ProgramInfo programInfo = K_ProgramInfo.getInstance();
		K_ProgramInfo programInfo = new K_ProgramInfo();
		programInfo.setProgram(tvChannelManager.getProgramInfoByIndex(k));
		return programInfo;
	}

	public boolean K_openTeletext(int mode) {
		return tvChannelManager.openTeletext(mode);
	}

	public boolean K_isTeletextSubtitleChannel() {
		return tvChannelManager.isTeletextSubtitleChannel();
	}

	public boolean K_isTeletextDisplayed() {
		return tvChannelManager.isTeletextDisplayed();
	}

	public void K_sendTeletextCommand(int cmd) {
		if (tvChannelManager != null)
			tvChannelManager.sendTeletextCommand(cmd);
	}

	public boolean K_hasTeletextClockSignal() {
		return tvChannelManager.hasTeletextClockSignal();
	}

	public boolean K_isTtxChannel() {
		return tvChannelManager.isTtxChannel();
	}

	public int K_getProgramCtrl(int command, int baseProgNum, int isSkipped) {
		return tvChannelManager.getProgramCtrl(command, baseProgNum, isSkipped);
	}

	public void K_selectProgram(int mPreChannelNumber, int serviceTypeAtv) {
		if (tvChannelManager != null)
			tvChannelManager.selectProgram(mPreChannelNumber, serviceTypeAtv);
	}

	public void K_stopDtvScan() {
		if (tvChannelManager != null)
			tvChannelManager.stopDtvScan();
	}

	public void K_genMixProgList(boolean backup) {
		//if (tvIsdbChannelManager != null)
			//tvIsdbChannelManager.genMixProgList(backup);
	}

	public void K_setChannel(int u16channelnum, boolean bcheckblock) {
		//if (tvIsdbChannelManager != null)
			//tvIsdbChannelManager.setChannel(u16channelnum, bcheckblock);
	}

	public void K_addProgramToFavorite(int favoriteId, int programNo, short programType, int programId) {
		if (tvChannelManager != null)
			tvChannelManager.addProgramToFavorite(favoriteId, programNo, programType, programId);
	}

	public void K_setProgramAttribute(int enpa, int programNo, short programType, int programId, boolean bv) {
		if (tvChannelManager != null)
			tvChannelManager.setProgramAttribute(enpa, programNo, programType, programId, bv);
	}

	public void K_deleteProgramFromFavorite(int favoriteId, int programNo, short programType, int programId) {
		if (tvChannelManager != null)
			tvChannelManager.deleteProgramFromFavorite(favoriteId, programNo, programType, programId);
	}

	public void K_moveProgram(int progSourcePosition, int progTargetPosition) {
		if (tvChannelManager != null)
			tvChannelManager.moveProgram(progSourcePosition, progTargetPosition);
	}

	public void K_setProgramName(int programNum, short programType, String porgramName) {
		if (tvChannelManager != null)
			tvChannelManager.setProgramName(programNum, programType, porgramName);
	}

	public String K_setProgramName(int programNo, int progrmType, int programId) {
		return tvChannelManager.getProgramName(programNo, progrmType, programId);
	}

	public int K_getAtvAudioStandard() {
		return tvChannelManager.getAtvAudioStandard();
	}

	public boolean K_isSignalStabled() {
		return tvChannelManager.isSignalStabled();
	}

	public K_DtvAudioInfo K_getAudioInfo() {
		K_DtvAudioInfo k_DtvAudioInfo = new K_DtvAudioInfo();
		k_DtvAudioInfo.setDtvAudioInfo(tvChannelManager.getAudioInfo());
		return k_DtvAudioInfo;
	}

	public int K_getAvdVideoStandard() {
		return tvChannelManager.getAvdVideoStandard();
	}
// Hch 20160309 add for ATV 	manual color system display 
	public int K_getAtvVideoSystem() {
		return tvChannelManager.getAtvVideoSystem().ordinal();
	}
// add end 

	public K_DtvSubtitleInfo K_getSubtitleInfo() {
		K_DtvSubtitleInfo k_DtvSubtitleInfo = new K_DtvSubtitleInfo();
		k_DtvSubtitleInfo.setDtvSubtitleInfo(tvChannelManager.getSubtitleInfo());
		return k_DtvSubtitleInfo;
	}

	public String K_getProgramName(int programNo, int progrmType, int programId) {

		return tvChannelManager.getProgramName(programNo, progrmType, programId);
	}

	public String K_getLanguageNameByCode(String languageCode) {
		return tvChannelManager.getLanguageNameByCode(languageCode);
	}

	public void K_switchAudioTrack(int track) {
		if (tvChannelManager != null)
			tvChannelManager.switchAudioTrack(track);
		;
		;
	}

	public int K_getLanguageIdByCode(String languageCode) {
		return tvChannelManager.getLanguageIdByCode(languageCode);
	}

	public void K_closeSubtitle() {
		if (tvChannelManager != null)
			tvChannelManager.closeSubtitle();
	}

	public void K_openSubtitle(int index) {
		if (tvChannelManager != null)
			tvChannelManager.openSubtitle(index);
	}

	public void K_closeTeletext() {
		if (tvChannelManager != null)
			tvChannelManager.closeTeletext();
	}

	public void K_stopAtvManualTuning() {
		if (tvChannelManager != null)
			tvChannelManager.stopAtvAutoTuning();
	}

	public int K_getTuningStatus() {
		return tvChannelManager.getTuningStatus();
	}

	public void K_setAtvVideoStandard(int videoSystem) {
		if (tvChannelManager != null)
			tvChannelManager.setAtvVideoStandard(videoSystem);
	}

	public void K_setAtvAudioStandard(int soundSystem) {
		if (tvChannelManager != null)
			tvChannelManager.setAtvAudioStandard(soundSystem);
	}

	public void K_saveAtvProgram(int currentProgramNo) {
		if (tvChannelManager != null)
			tvChannelManager.saveAtvProgram(currentProgramNo);
	}

	public int K_getAtvCurrentFrequency() {
		return tvChannelManager.getAtvCurrentFrequency();
	}

	public void K_startAtvManualTuning(int EventIntervalMs, int Frequency, int eMode) {
		if (tvChannelManager != null)
			tvChannelManager.startAtvManualTuning(EventIntervalMs, Frequency, eMode);
	}

	public void K_dvbcgetScanParam(K_DvbcScanParam sp) {
		if (tvChannelManager != null)
			tvChannelManager.dvbcgetScanParam(sp);
	}

	public K_DvbMuxInfo K_getCurrentMuxInfo() {
		K_DvbMuxInfo k_DvbMuxInfo = new K_DvbMuxInfo();
		k_DvbMuxInfo.setDvbMuxInfo(tvChannelManager.getCurrentMuxInfo()); 
		return k_DvbMuxInfo;
	}

	public void K_setDvbcScanParam(short symbolRate, int constellationType, int nitFrequency, int endFrequency,
			short networkId) {
		if (tvChannelManager != null)
			tvChannelManager.setDvbcScanParam(symbolRate, constellationType, nitFrequency, endFrequency, networkId);
	}

	public void K_startQuickScan() {
		if (tvChannelManager != null)
			tvChannelManager.startQuickScan();
	}

	public int K_getUserScanType() {
		return tvChannelManager.getUserScanType();
	}

	public void K_startDtvAutoScan() {
		if (tvChannelManager != null)
			tvChannelManager.startDtvAutoScan();
	}

	public void K_startDtvFullScan() {
		if (tvChannelManager != null)
			tvChannelManager.startDtvFullScan();
	}

	public void K_setBandwidth(int bandwidth) {
		if (tvChannelManager != null)
			tvChannelManager.setBandwidth(bandwidth);
	}

	public boolean K_startAtvAutoTuning(int EventIntervalMs, int FrequencyStart, int FrequencyEnd) {
		return tvChannelManager.startAtvAutoTuning(EventIntervalMs, FrequencyStart, FrequencyEnd);
	}

	public void K_pauseAtvAutoTuning() {
		if (tvChannelManager != null)
			tvChannelManager.pauseAtvAutoTuning();
	}

	public void K_pauseDtvScan() {
		if (tvChannelManager != null)
			tvChannelManager.pauseDtvScan();
	}

	public Boolean K_stopAtvAutoTuning() {
		return tvChannelManager.stopAtvAutoTuning();
	}

	public K_DtvProgramSignalInfo K_getCurrentSignalInformation() {
		K_DtvProgramSignalInfo k_DtvProgramSignalInfo = new K_DtvProgramSignalInfo();
		k_DtvProgramSignalInfo.setDtvProgramSignalInfo(tvChannelManager.getCurrentSignalInformation());
		return k_DtvProgramSignalInfo;
	}

	public K_RfInfo K_getRfInfo(int rfSignalInfoType, int rfChNo) {
		K_RfInfo k_RfInfo = new K_RfInfo();
		k_RfInfo.setRfInfo(tvChannelManager.getRfInfo(rfSignalInfoType, rfChNo));	
		return k_RfInfo;
	}

	public void K_setDtvManualScanByFreq(int FrequencyKHz) {
		if (tvChannelManager != null)
			tvChannelManager.setDtvManualScanByFreq(FrequencyKHz);
	}

	public void K_startDtvManualScan() {
		if (tvChannelManager != null)
			tvChannelManager.startDtvManualScan();
	}

	public void K_setDtvManualScanByRF(int RFNum) {
		if (tvChannelManager != null)
			tvChannelManager.setDtvManualScanByRF(RFNum);
	}

	public void K_resumeAtvAutoTuning() {
		if (tvChannelManager != null)
			tvChannelManager.resumeAtvAutoTuning();
	}

	public void K_resumeDtvScan() {
		if (tvChannelManager != null)
			tvChannelManager.resumeDtvScan();
	}

}

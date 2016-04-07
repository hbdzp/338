package com.kguan.mtvplay.tvapi;

import com.kguan.mtvplay.tvapi.listener.K_OnTvEventListener;
import com.kguan.mtvplay.tvapi.vo.common.K_EnumAvdVideoStandardType;
import com.kguan.mtvplay.tvapi.vo.common.K_TvTypeInfo;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
//import com.mstar.android.tvapi.common.vo.TvTypeInfo;

/**
 * 
 * @ClassName: K_PictureModel
 * @Description: TvCommon
 * @author: zengjf
 * @date: 2015-9-11
 */
public class K_TvCommonManager {
	public static K_TvCommonManager k_TvCommonModel = null;
	public TvCommonManager tvCommonManager = null;

	private K_TvCommonManager() {
		tvCommonManager = TvCommonManager.getInstance();
	}

	/**
	 * 
	 * @Title: getInstance
	 * @Description: 获取K_PictureModel实例
	 * @return
	 * @return: K_PictureMode
	 */
	public static K_TvCommonManager getInstance() {
		if (k_TvCommonModel == null) {
			k_TvCommonModel = new K_TvCommonManager();
		}
		return k_TvCommonModel;
	}

	/**
	 * 
	 * @Title: K_getCurrentTvInputSource
	 * @Description: 获得当前信源类型
	 * @return
	 * @return: int
	 */
	public int K_getCurrentTvInputSource() {
		return tvCommonManager.getCurrentTvInputSource();
	}

	public K_EnumAvdVideoStandardType K_getVideoStandard() {
		//EnumAvdVideoStandardType eAVST = null;
		K_EnumAvdVideoStandardType k_EnumAvdVideoStandardType = new K_EnumAvdVideoStandardType();
		try {
			k_EnumAvdVideoStandardType.setEnumAvdVideoStandardType(TvManager.getInstance().getPlayerManager().getVideoStandard());
		} catch (TvCommonException e) {
			e.printStackTrace();
		}
		return k_EnumAvdVideoStandardType;
	}

	/*
	 * 定义相关boolean型方法，引用相应的API接口等
	 */
	/**
	 * 
	 * @Title: K_isSignalStable
	 * @Description: 判断信号是否稳定
	 * @param inputSrc
	 * @return
	 * @return: boolean
	 */
	public boolean K_isSignalStable(int inputSrc) {
		return tvCommonManager.isSignalStable(inputSrc);
	}

	/**
	 * 
	 * @Title: K_isHdmiSignalMode
	 * @Description: 判断HDMI信号模式
	 * @return
	 * @return: boolean
	 */
	public boolean K_isHdmiSignalMode() {
		return tvCommonManager.isHdmiSignalMode();
	}

	/**
	 * 
	 * @Title: K_getCurrentTvSystem
	 * @Description: 获取当前Tv系统参数
	 * @return
	 * @return: int
	 */
	public int K_getCurrentTvSystem() {
		return tvCommonManager.getCurrentTvSystem();
	}

	/**
	 * 
	 * @Title: K_getOsdDuration
	 * @Description: 获取Osd的持续时间
	 * @return
	 * @return: int
	 */
	public int K_getOsdDuration() {
		return tvCommonManager.getOsdDuration();
	}

	/**
	 * 
	 * @Title: K_getBlueScreenMode
	 * @Description: 获得蓝屏模式
	 * @return
	 * @return: boolean
	 */
	public boolean K_getBlueScreenMode() {
		return tvCommonManager.getBlueScreenMode();
	}

	public int K_getOsdTimeoutInSecond() {
		return tvCommonManager.getOsdTimeoutInSecond();
	}

	/**
	 * 
	 * @Title: K_getSubtitlePrimaryLanguage
	 * @Description: 获取字幕为第一语言
	 * @return
	 * @return: int
	 */
	public int K_getSubtitlePrimaryLanguage() {
		return tvCommonManager.getSubtitlePrimaryLanguage();
	}

	/**
	 * 
	 * @Title: K_getSubtitleSecondaryLanguage
	 * @Description: 获取字幕为第二语言
	 * @return
	 * @return: int
	 */
	public int K_getSubtitleSecondaryLanguage() {
		return tvCommonManager.getSubtitleSecondaryLanguage();
	}

	/**
	 * 
	 * @Title: K_setSourceIdentState
	 * @Description: 设置信源识别状态
	 * @param arg0
	 * @return: void
	 */
	public void K_setSourceIdentState(int arg0) {
		if (tvCommonManager != null)
			tvCommonManager.setSourceIdentState(arg0);
	}

	/**
	 * 
	 * @Title: K_setSourcePreviewState
	 * @Description: 设置信源预览状态
	 * @param arg0
	 * @return: void
	 */
	public void K_setSourcePreviewState(int arg0) {
		if (tvCommonManager != null)
			tvCommonManager.setSourcePreviewState(arg0);
	}

	/**
	 * 
	 * @Title: K_setSourceSwitchState
	 * @Description: 设置信源切换状态
	 * @param arg0
	 * @return: void
	 */
	public void K_setSourceSwitchState(int arg0) {
		if (tvCommonManager != null)
			tvCommonManager.setSourceSwitchState(arg0);
	}

	/**
	 * 
	 * @Title: K_setBlueScreenMode
	 * @Description: 设置蓝屏模式
	 * @param tf
	 * @return: void
	 */
	public void K_setBlueScreenMode(boolean tf) {
		if (tvCommonManager != null)
			tvCommonManager.setBlueScreenMode(tf);
	}

	/**
	 * 
	 * @Title: K_setOsdDuration
	 * @Description: 设置Osd持续时间
	 * @param arg0
	 * @return: void
	 */
	public void K_setOsdDuration(int arg0) {
		if (tvCommonManager != null)
			tvCommonManager.setOsdDuration(arg0);
	}

	/**
	 * 
	 * @Title: K_setSubtitlePrimaryLanguage
	 * @Description: 设置字幕第一语言
	 * @param arg0
	 * @return: void
	 */
	public void K_setSubtitlePrimaryLanguage(int arg0) {
		if (tvCommonManager != null)
			tvCommonManager.setSubtitlePrimaryLanguage(arg0);
	}

	/**
	 * 
	 * @Title: K_setSubtitleSecondaryLanguage
	 * @Description: 设置字幕第二语言
	 * @param arg0
	 * @return: void
	 */
	public void K_setSubtitleSecondaryLanguage(int arg0) {
		if (tvCommonManager != null)
			tvCommonManager.setSubtitleSecondaryLanguage(arg0);
	}

	/**
	 * 
	 * @Title: K_rebootSystem
	 * @Description: 重启系统
	 * @param str
	 * @return: void
	 */
	public void K_rebootSystem(String str) {
		if (tvCommonManager != null)
			tvCommonManager.rebootSystem(str);
	}

	/**
	 * 
	 * @Title: K_isSupportModule
	 * @Description: 判断是否支持该模块
	 * @param arg0
	 * @return
	 * @return: boolean
	 */
	public boolean K_isSupportModule(int arg0) {
		return tvCommonManager.isSupportModule(arg0);
	}
	/**
	 * 
	 * @Title: K_getTvInfo
	 * @Description: 获取Tv的类型信息
	 * @return
	 * @return: K_TvTypeInfo
	 */
	public K_TvTypeInfo K_getTvInfo() {
		K_TvTypeInfo k_TvTypeInfo = new K_TvTypeInfo();
		k_TvTypeInfo.setTvTypeInfo(tvCommonManager.getTvInfo());
		return k_TvTypeInfo;
	}

	/**
	 * 
	 * @Title: K_getRoutePath
	 * @Description: 获取TvTypeInfo的路径参数
	 * @return
	 * @return: int[]
	 */
	public int[] K_getRoutePath() {
		return tvCommonManager.getTvInfo().routePath;
	}

	public int K_getOsdLanguage() {
		return tvCommonManager.getOsdLanguage();
	}

	public int[] K_setTvosCommonCommand(String tvEventListenerReady) {
		// TODO Auto-generated method stub
		return tvCommonManager.setTvosCommonCommand(tvEventListenerReady);
	}

	public void K_setInputSource(int mPreviousInputSource) {
		if (tvCommonManager != null)
		tvCommonManager.setInputSource(mPreviousInputSource);
	}

	public boolean unregisterOnTvEventListener(K_OnTvEventListener mTvEventListener) {
		return tvCommonManager.unregisterOnTvEventListener(mTvEventListener);
	}

	public boolean registerOnTvEventListener(K_OnTvEventListener mTvEventListener) {
		return tvCommonManager.registerOnTvEventListener(mTvEventListener);
	}

	public void standbySystem(String string) {
		if (tvCommonManager != null)
			tvCommonManager.standbySystem(string);
	}

	public EnumInputSource K_getCurrentSubInputSource() {
		// TODO Auto-generated method stub
		return tvCommonManager.getCurrentSubInputSource();
	}

	public void K_enterSleepMode(boolean bMode, boolean bNoSignalPwDn) {
		if (tvCommonManager != null)
			tvCommonManager.enterSleepMode(bMode, bNoSignalPwDn);
	}

	public void K_setOsdLanguage(int value) {
		if (tvCommonManager != null)
			tvCommonManager.setOsdLanguage(value);
	}

	public void K_disableTvosIr() {
		if (tvCommonManager != null)
			tvCommonManager.disableTvosIr();
	}

	public int K_getATVMtsMode() {
		return tvCommonManager.getATVMtsMode();
	}

	public int[] K_getSourceList() {
		// TODO Auto-generated method stub
		return tvCommonManager.getSourceList();
	}

	public int K_setToNextATVMtsMode() {
			return tvCommonManager.setToNextATVMtsMode();
	}

}

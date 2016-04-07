package com.kguan.mtvplay.tvapi;

import android.content.Context;

import com.mstar.android.tv.TvCcManager;
import com.mstar.android.tv.TvGingaManager;
import com.mstar.android.tv.TvHbbTVManager;
import com.mstar.android.tv.TvOadManager;
import com.mstar.android.tv.TvParentalControlManager;
import com.mstar.android.tv.TvPipPopManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.EnumCecDeviceLa;
import com.mstar.android.tvapi.common.vo.EnumPipModes;
import com.mstar.android.tvapi.common.vo.EnumPowerOnLogoMode;
import com.mstar.android.tvapi.common.vo.EnumSleepTimeState;

/**
 * 
 * @ClassName: K_SettingModel
 * @Description: TV设置
 * @author: zengjf
 * @date: 2015-9-15
 */
public class K_SettingModel {

	public static K_SettingModel k_SettingModel = null;
	public TvPipPopManager tvPipPopManager = null;
	public TvParentalControlManager tvParentalControlManager = null;
	public TvOadManager tvOadManager = null;
	public TvGingaManager tvGingaManager = null;
	public TvHbbTVManager tvHbbTVManager = null;
	public TvCcManager tvCcManager = null;

	/**
	 * 
	 * @Title:K_SettingModel
	 * @Description:初始化
	 * @param context
	 */
	private K_SettingModel() {
		tvPipPopManager = TvPipPopManager.getInstance();
		tvParentalControlManager = TvParentalControlManager.getInstance();
		tvOadManager = TvOadManager.getInstance();
		tvGingaManager = TvGingaManager.getInstance();
		tvHbbTVManager = TvHbbTVManager.getInstance();
		tvCcManager = TvCcManager.getInstance();

	}

	/**
	 * 
	 * @Title: getInstance
	 * @Description: 获取K_SettingModel实例
	 * @param context
	 * @return
	 * @return: K_SettingModel
	 */
	public static K_SettingModel getInstance() {
		if (k_SettingModel == null) {
			k_SettingModel = new K_SettingModel();
		}
		return k_SettingModel;
	}

	/**
	 * 
	 * @Title: K_getParentalPassword
	 * @Description: 获取家长控制密码
	 * @return
	 * @return: int
	 */
	public int K_getParentalPassword() {
		return tvParentalControlManager.getParentalPassword();
	}

	public EnumCecDeviceLa[] K_getCECDeviceList() {
		EnumCecDeviceLa[] eCecD = new EnumCecDeviceLa[EnumCecDeviceLa.values().length];
		return eCecD;
	}

	// 新增2个
	public int K_getOrdinal_CECDeviceList(int arg0) {
		return K_getCECDeviceList()[arg0].ordinal();
	}

	public EnumCecDeviceLa K_getEnumCecDeviceLa_ETV() {
		return EnumCecDeviceLa.E_TV;
	}

	public EnumCecDeviceLa[] K_getValuesOfEnumCecDev() {
		return EnumCecDeviceLa.values();
	}

	public int K_getLengthOfEnumCecDev() {
		return EnumCecDeviceLa.values().length;
	}

	public int K_getOrdinal_E_UNREGISTERED() {
		return EnumCecDeviceLa.E_UNREGISTERED.ordinal();
	}

	public int K_getOrdinal_E_TV() {
		return EnumCecDeviceLa.E_TV.ordinal();
	}

	public EnumPowerOnLogoMode K_getValues(int arg0) {
		return EnumPowerOnLogoMode.values()[arg0];
	}

	public EnumSleepTimeState K_getValues_EnumSleepTimeState(int Index) {
		return EnumSleepTimeState.values()[Index];
	}

	/*
	 * 定义相关set方法，引用相应的API接口等
	 */

	public void K_setTvosCommonCommand(String str) {
		try {
				K_TvManager.getInstance().K_setTvosCommonCommand(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 定义相关boolean型方法，引用相应的API接口等
	 */

	/**
	 * 
	 * @Title: K_isSystemLock
	 * @Description: 判断系统是否锁定
	 * @return
	 * @return: boolean
	 */
	public boolean K_isSystemLock() {
		return tvParentalControlManager.isSystemLock();
	}

	// 数据操作
	/**
	 * 
	 * @Title: K_getSwitchModeIndex
	 * @Description: 获取ATV切换模式
	 * @return
	 * @return: int
	 */
	public int K_getAtvChannelSwitchModeIndex(Context context) {
		int switchmodeindex = 0;
		switchmodeindex = K_DataBaseHelper.getInstance(context).K_getValueDatabase_systemsetting("bATVChSwitchFreeze");
		return switchmodeindex;
	}

	/**
	 * 
	 * @Title: K_getAjustBacklightIndex
	 * @Description: 获取背光值
	 * @return
	 * @return: int
	 */
	public int K_getAjustBacklightIndex(Context context) {
		int ajustBacklightIndex = 0;
		ajustBacklightIndex = K_DataBaseHelper.getInstance(context)
				.K_getValueDatabase_systemsetting("u8EnergyEfficiencyBacklight_Max");
		return ajustBacklightIndex;
	}

	/**
	 * 
	 * @Title: K_setAutonewBackLight
	 * @Description: 调节背光
	 * @param u8EnergyEfficiencyBacklightmax
	 * @return: void
	 */
	public void K_setAutonewBackLight(Context context, short u8EnergyEfficiencyBacklightmax) {
		K_DataBaseHelper.getInstance(context).K_updateDatabase_systemsetting("u8EnergyEfficiencyBacklight_Max",
				u8EnergyEfficiencyBacklightmax);
		K_setTvosCommonCommand("SetEnergyEfficiencyImprove");
	}

	/**
	 * 
	 * @Title: K_getAutoBacklightIndex
	 * @Description: 获取智慧节能值
	 * @return
	 * @return: int
	 */
	public int K_getAutoBacklightIndex(Context context) {
		int backlightIndex = 0;
		backlightIndex = K_DataBaseHelper.getInstance(context)
				.K_getValueDatabase_systemsetting("bEnergyEfficiencyImprove");
		return backlightIndex;
	}

	/**
	 * 
	 * @Title: K_setAutoBackLight
	 * @Description: 设置智慧节能
	 * @param sign
	 * @return: void
	 */
	public void K_setAutoBackLight(Context context, short sign) {
		K_DataBaseHelper.getInstance(context).K_updateDatabase_systemsetting("bEnergyEfficiencyImprove", sign);
		K_setTvosCommonCommand("SetEnergyEfficiencyImprove");
	}

	/**
	 * 
	 * @Title: K_getSoftwareUpdateState
	 * @Description: 获取软件更新状态
	 * @return
	 * @return: int
	 */
	public int K_getSoftwareUpdateState() {
		return tvOadManager.getSoftwareUpdateState();
	}

	/**
	 * 
	 * @Title: K_getOadTime
	 * @Description: 获取Oad时间参数
	 * @return
	 * @return: int
	 */
	public int K_getOadTime() {
		return tvOadManager.getOadTime();
	}

	public boolean K_getOadViewerPrompt() {
		return tvOadManager.getOadViewerPrompt();
	}

	/**
	 * 
	 * @Title: K_setOadOn
	 * @Description: 开启Oad
	 * @return: void
	 */
	public void K_setOadOn() {
		if (tvOadManager != null)
			tvOadManager.setOadOn();
	}

	/**
	 * 
	 * @Title: K_setOadOff
	 * @Description: 关闭Oad
	 * @return: void
	 */
	public void K_setOadOff() {
		if (tvOadManager != null)
			tvOadManager.setOadOff();
	}

	/**
	 * 
	 * @Title: K_setSoftwareUpdateState
	 * @Description: 设置软件更新状态
	 * @param arg0
	 * @return: void
	 */
	public void K_setSoftwareUpdateState(int arg0) {
		if (tvOadManager != null)
			tvOadManager.setSoftwareUpdateState(arg0);
	}

	/**
	 * 
	 * @Title: K_setOadTime
	 * @Description: 设置Oad时间
	 * @param arg0
	 * @return: void
	 */
	public void K_setOadTime(int arg0) {
		if (tvOadManager != null)
			tvOadManager.setOadTime(arg0);
	}

	public void K_setOadScanTime(int arg0) {
		if (tvOadManager != null)
			tvOadManager.setOadScanTime(arg0);
	}

	/**
	 * 
	 * @Title: K_resetOad
	 * @Description: 复位Oad
	 * @return: void
	 */
	public void K_resetOad() {
		if (tvOadManager != null)
			tvOadManager.resetOad();
	}

	public void K_setOadViewerPrompt(boolean tf) {
		if (tvOadManager != null)
			tvOadManager.setOadViewerPrompt(tf);
	}

	public boolean K_isGingaRunning() {
		return tvGingaManager.isGingaRunning();
	}

	public boolean K_processKey(int keyCode, boolean bPressed) {
		return tvGingaManager.processKey(keyCode, bPressed);
	}

	public boolean K_isHbbTVEnabled() {
		return tvHbbTVManager.isHbbTVEnabled();
	}

	public boolean K_hbbtvKeyHandler(int keyCode) {
		return tvHbbTVManager.hbbtvKeyHandler(keyCode);
	}

	public boolean K_isPipModeEnabled() {
		return tvPipPopManager.isPipModeEnabled();
	}

	public EnumPipModes K_getPipMode() {
		// TODO Auto-generated method stub
		return tvPipPopManager.getPipMode();
	}

	public int K_getNextClosedCaptionMode() {
		return tvCcManager.getNextClosedCaptionMode();
	}

	public void K_setClosedCaptionMode(int closedCaptionMode) {
		if (tvCcManager != null)
			tvCcManager.setClosedCaptionMode(closedCaptionMode);
	}

	public void K_stopCc() {
		if (tvCcManager != null)
			tvCcManager.stopCc();
	}

	public void K_startCc() {
		if (tvCcManager != null)
			tvCcManager.startCc();
	}

}

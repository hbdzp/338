package com.kguan.mtvplay.tvapi;

import android.content.Context;
import android.content.res.Configuration;

import com.kguan.mtvplay.tvapi.listener.K_OnCecCtrlEventListener;
import com.kguan.mtvplay.tvapi.vo.common.K_CecSetting;
import com.mstar.android.tv.TvCecManager;
import com.mstar.android.tvapi.common.vo.CecSetting;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumLanguage;

public class K_TvCecManager {
	public short cecStatus;
	public short autoStandby;
	public short arcStatus;
	
	public static K_TvCecManager k_TvCecManager = null;
	public TvCecManager tvCecManager = null;

	private K_TvCecManager() {
		tvCecManager = TvCecManager.getInstance();
		
		cecStatus = K_getCecStatus();
		autoStandby = K_getAutoStandby();
		arcStatus = K_getArcStatus();
	}

	public static K_TvCecManager getInstance() {
		if (k_TvCecManager == null) {
			k_TvCecManager = new K_TvCecManager();
		}
		return k_TvCecManager;
	}

	public K_CecSetting K_getCecConfiguration() {
		K_CecSetting k_CecSetting = new K_CecSetting();
		k_CecSetting.setCecSetting(tvCecManager.getCecConfiguration());
		return k_CecSetting;
	}


	/**
	 * 
	 * @Title: K_getCecStatus
	 * @Description: 获取Cec状态参数
	 * @return
	 * @return: short
	 */
	public short K_getCecStatus() {
		return K_getCecConfiguration().getCecSetting().cecStatus;
	}

	public short K_getAutoStandby() {
		return K_getCecConfiguration().getCecSetting().autoStandby;
	}


	/**
	 * 
	 * @Title: K_getArcStatus
	 * @Description: 获取Arc状态参数
	 * @return
	 * @return: short
	 */
	public short K_getArcStatus() {
		return K_getCecConfiguration().getCecSetting().arcStatus;
	}

	public void K_getRoutingChangeInDeviceListSetting(int arg0) {
		tvCecManager.routingChangeInDeviceListSetting(arg0);
	}
	/**
	 * 
	 * @Title: K_getDeviceName
	 * @Description: 获取设备名称
	 * @param arg0
	 * @return
	 * @return: String
	 */
	public String K_getDeviceName(int arg0) {
		return tvCecManager.getDeviceName(arg0);
	}
	public void K_setCecConfiguration(CecSetting cecsetting) {
		if (tvCecManager != null)
			tvCecManager.setCecConfiguration(cecsetting);
	}
	public void K_disableDeviceMenu() {
		if (tvCecManager != null)
		tvCecManager.disableDeviceMenu();
	}

	public boolean K_enableDeviceMenu() {
		return tvCecManager.enableDeviceMenu();
	}

	public boolean K_sendCecKey(int keyCode) {
		return tvCecManager.sendCecKey(keyCode);
	}

	public void K_unregisterOnCecCtrlEventListener(K_OnCecCtrlEventListener mCecCtrlEventListener) {
		if (tvCecManager != null)
			tvCecManager.unregisterOnCecCtrlEventListener(mCecCtrlEventListener);
	}

	public void K_registerOnCecCtrlEventListener(K_OnCecCtrlEventListener mCecCtrlEventListener) {
		if (tvCecManager != null)
			tvCecManager.registerOnCecCtrlEventListener(mCecCtrlEventListener);
	}

	public void K_setMenuLanguage(Context context) {
		if (tvCecManager != null)
			tvCecManager.setMenuLanguage(getCurrentLangToEnum(context));
	}
	private EnumLanguage getCurrentLangToEnum(Context context) {
		Configuration conf = context.getResources().getConfiguration();
		String language = conf.locale.getLanguage();

		if (language.equals("en_CA")) {
			return EnumLanguage.E_ENGLISH;
		} else if (language.equals("fr_CA")) {
			return EnumLanguage.E_ENGLISH;
		} else if (language.equals("zh_CN")) {
			return EnumLanguage.E_MANDARIN;
		} else if (language.equals("zh")) {
			return EnumLanguage.E_CHINESE;
		} else if (language.equals("en")) {
			return EnumLanguage.E_ENGLISH;
		} else if (language.equals("fr_FR")) {
			return EnumLanguage.E_FRENCH;
		} else if (language.equals("fr")) {
			return EnumLanguage.E_FRENCH;
		} else if (language.equals("de")) {
			return EnumLanguage.E_GERMAN;
		} else if (language.equals("de_DE")) {
			return EnumLanguage.E_GERMAN;
		} else if (language.equals("it")) {
			return EnumLanguage.E_ITALIAN;
		} else if (language.equals("it_IT")) {
			return EnumLanguage.E_ITALIAN;
		} else if (language.equals("ja_JP")) {
			return EnumLanguage.E_JAPANESE;
		} else if (language.equals("ja")) {
			return EnumLanguage.E_JAPANESE;
		} else if (language.equals("ko_KR")) {
			return EnumLanguage.E_KOREAN;
		} else if (language.equals("ko")) {
			return EnumLanguage.E_KOREAN;
		} else if (language.equals("zh_CN")) {
			return EnumLanguage.E_ENGLISH;
		} else if (language.equals("ROOT")) {
			return EnumLanguage.E_ENGLISH;
		} else if (language.equals("zh_CN")) {
			return EnumLanguage.E_MANDARIN;
		} else if (language.equals("zh_TW")) {
			return EnumLanguage.E_CHINESE;
		} else if (language.equals("en_GB")) {
			return EnumLanguage.E_MANDARIN;
		} else if (language.equals("en_US")) {
			return EnumLanguage.E_ENGLISH;
		}
		return EnumLanguage.E_UNKNOWN;
	}
}

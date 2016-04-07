package com.kguan.mtvplay.tvapi;

import com.mstar.android.tv.TvFactoryManager;
import com.mstar.android.tvapi.common.vo.EnumPowerOnLogoMode;
import com.mstar.android.tvapi.factory.vo.EnumAcOnPowerOnMode;

/**
 * 
 * @ClassName: k_SoundModel
 * @Description: TV声音
 * @author: zengjf
 * @date: 2015-9-11
 */
public class K_TvFactoryManager {
	public static K_TvFactoryManager k_TvFactoryModel = null;
	public TvFactoryManager tvFactoryManager = null;

	private K_TvFactoryManager() {
		tvFactoryManager = TvFactoryManager.getInstance();
	}

	/**
	 * 
	 * @Title: getInstance
	 * @Description: 获取k_SoundModel实例
	 * @return
	 * @return: k_SoundModel
	 */
	public static K_TvFactoryManager getInstance() {
		if (k_TvFactoryModel == null) {
			k_TvFactoryModel = new K_TvFactoryManager();
		}
		return k_TvFactoryModel;
	}
	public EnumPowerOnLogoMode K_getEnvironmentPowerOnLogoMode() {
		return tvFactoryManager.getEnvironmentPowerOnLogoMode();
	}

	public int K_getOrdinal_TvFactory() {
		return tvFactoryManager.getEnvironmentPowerOnLogoMode().ordinal();
	}
	/**
	 * 
	 * @Title: K_restoreToDefault
	 * @Description: 恢复默认设置
	 * @return
	 * @return: boolean
	 */
	public boolean K_restoreToDefault() {
		return tvFactoryManager.restoreToDefault();
	}
	public void K_setEnvironmentPowerOnLogoMode(EnumPowerOnLogoMode eLogoMode) {
		if (tvFactoryManager != null)
			tvFactoryManager.setEnvironmentPowerOnLogoMode(eLogoMode);
	}
	public void K_setEnvironmentPowerOnMusicVolume(int arg0) {
		tvFactoryManager.setEnvironmentPowerOnMusicVolume(arg0);
	}

	public void K_setPowerOnMode(EnumAcOnPowerOnMode eAconPoweronDirect) {
		tvFactoryManager.setPowerOnMode(eAconPoweronDirect);
	}

}

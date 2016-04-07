package com.kguan.mtvplay.tvapi;

import android.util.Log;

import com.mstar.android.tv.TvAudioManager;
import com.mstar.android.tvapi.common.vo.EnumSoundMode;

/**
 * 
 * @ClassName: k_SoundModel
 * @Description: TV声音
 * @author: zengjf
 * @date: 2015-9-11
 */
public class K_TvAudioManager {
	public TvAudioManager tvAudioManager = null;
	public static K_TvAudioManager k_SoundModel = null;

	private K_TvAudioManager() {
		tvAudioManager = TvAudioManager.getInstance();
	}

	/**
	 * 
	 * @Title: getInstance
	 * @Description: 获取k_SoundModel实例
	 * @return
	 * @return: k_SoundModel
	 */
	public static K_TvAudioManager getInstance() {
		if (k_SoundModel == null) {
			k_SoundModel = new K_TvAudioManager();
		}
		return k_SoundModel;
	}

	public int K_getOrdinal_E_SPORTS() {
		return EnumSoundMode.E_SPORTS.ordinal();
	}

	// 新增2个
	public int K_getOrdinal_E_ONSITE1() {
		return EnumSoundMode.E_ONSITE1.ordinal();
	}

	public int K_getOrdinal_E_ONSITE2() {
		return EnumSoundMode.E_ONSITE2.ordinal();
	}

	/*
	 * 定义相关set/get方法，引用相应的API
	 */
	/**
	 * 
	 * @Title: K_setBass
	 * @Description: 设置低音
	 * @param arg0
	 * @return: void
	 */
	public void K_setBass(int arg0) {
		if (tvAudioManager != null)
			tvAudioManager.setBass(arg0);
	}

	/**
	 * 
	 * @Title: K_getBass
	 * @Description: 获取低音参数
	 * @return
	 * @return: int
	 */
	public int K_getBass() {
		return tvAudioManager.getBass();
	}

	/**
	 * 
	 * @Title: K_setTreble
	 * @Description: 设置高音
	 * @param arg0
	 * @return: void
	 */
	public void K_setTreble(int arg0) {
		if (tvAudioManager != null)
			tvAudioManager.setTreble(arg0);
	}

	/**
	 * 
	 * @Title: K_getTreble
	 * @Description: 获取高音参数
	 * @return
	 * @return: int
	 */
	public int K_getTreble() {
		return tvAudioManager.getTreble();
	}
	
	/**
	 * 
	 * @Title: K_setEqBand120
	 * @Description: Set Equilizer 120HZ EQBand value
	 * @return
	 * @return: int
	 */
	public void K_setEqBand120(int arg0) {
		if (tvAudioManager != null)
			tvAudioManager.setEqBand120(arg0);
	}
	
	/**
	 * 
	 * @Title: K_getEqBand120
	 * @Description: Get Equilizer 120HZ EQBand value
	 * @return
	 * @return: int
	 */
	public int K_getEqBand120() {
		return tvAudioManager.getEqBand120();
	}
	
	/**
	 * 
	 * @Title: K_setEqBand500
	 * @Description: Set Equilizer 500HZ EQBand value
	 * @return
	 * @return: int
	 */
	public void K_setEqBand500(int arg0) {
		if (tvAudioManager != null)
			tvAudioManager.setEqBand500(arg0);
	}
	
	/**
	 * 
	 * @Title: K_getEqBand500
	 * @Description: Get Equilizer 500HZ EQBand value
	 * @return
	 * @return: int
	 */
	public int K_getEqBand500() {
		return tvAudioManager.getEqBand500();
	}
	
	/**
	 * 
	 * @Title: K_setEqBand1500
	 * @Description: Set Equilizer 1500HZ EQBand value
	 * @return
	 * @return: int
	 */
	public void K_setEqBand1500(int arg0) {
		if (tvAudioManager != null)
			tvAudioManager.setEqBand1500(arg0);
	}
	
	/**
	 * 
	 * @Title: K_getEqBand1500
	 * @Description: Get Equilizer 1500HZ EQBand value
	 * @return
	 * @return: int
	 */
	public int K_getEqBand1500() {
		return tvAudioManager.getEqBand1500();
	}
	
	/**
	 * 
	 * @Title: K_setEqBand5k
	 * @Description: Set Equilizer 5kHZ EQBand value
	 * @return
	 * @return: int
	 */
	public void K_setEqBand5k(int arg0) {
		if (tvAudioManager != null)
			tvAudioManager.setEqBand5k(arg0);
	}
	
	/**
	 * 
	 * @Title: K_getEqBand5k()
	 * @Description: Get Equilizer 5kHZ EQBand value
	 * @return
	 * @return: int
	 */
	public int K_getEqBand5k() {
		return tvAudioManager.getEqBand5k();
	}
	
	/**
	 * 
	 * @Title: K_setEqBand10k
	 * @Description: Set Equilizer 10kHZ EQBand value
	 * @return
	 * @return: int
	 */
	public void K_setEqBand10k(int arg0) {
		if (tvAudioManager != null)
			tvAudioManager.setEqBand10k(arg0);
	}
	
	/**
	 * 
	 * @Title: K_getEqBand10k
	 * @Description: Get Equilizer 10kHZ EQBand value
	 * @return
	 * @return: int
	 */
	public int K_getEqBand10k() {
		return tvAudioManager.getEqBand10k();
	}

	/**
	 * 
	 * @Title: K_setAudioSoundMode
	 * @Description: 设置音频模式
	 * @param arg0
	 * @return: void
	 */
	public void K_setAudioSoundMode(int soundMode) {
		if (tvAudioManager != null)
			tvAudioManager
					.setAudioSoundMode(K_Constants.K_MAPPING_SOUND_MODE_ENUM
							.values()[soundMode].getMstValue());
	}

	/**
	 * 
	 * @Title: K_getAudioSoundMode
	 * @Description: 获取音频模式
	 * @return: void
	 */
	public int K_getAudioSoundMode() {
		int audioSoundMode;
		audioSoundMode = K_Constants.K_MAPPING_SOUND_MODE_ENUM
				.getKTCOrdinalThroughValue(tvAudioManager.getAudioSoundMode());
		return audioSoundMode;
	}

	/**
	 * 
	 * @Title: K_setBalance
	 * @Description: 设置声音均衡
	 * @param arg0
	 * @return: void
	 */
	public void K_setBalance(int arg0) {
		if (tvAudioManager != null){
			tvAudioManager.setBalance(arg0);
		}
		
	}

	/**
	 * 
	 * @Title: K_getBalance
	 * @Description: 获取声音均衡
	 * @return: void
	 */
	public int K_getBalance() {
		return tvAudioManager.getBalance();
	}

	/**
	 * 
	 * @Title: K_setAvcMode
	 * @Description: 设置视频编码格式
	 * @param tf
	 * @return: void
	 */
	public void K_setAvcMode(boolean tf) {
		if (tvAudioManager != null)
			tvAudioManager.setAvcMode(tf);
	}

	/**
	 * 
	 * @Title: K_getAvcMode
	 * @Description: 视频编码格式
	 * @return: void
	 */
	public boolean K_getAvcMode() {
		return tvAudioManager.getAvcMode();
	}

	/**
	 * 
	 * @Title: K_setADEnable
	 * @Description: 启用AD
	 * @param tf
	 * @return: void
	 */
	public void K_setADEnable(boolean tf) {
		if (tvAudioManager != null)
			tvAudioManager.setADEnable(tf);
	}

	public boolean K_getADEnable() {
		return tvAudioManager.getADEnable();
	}

	/**
	 * 
	 * @Title: K_setADAbsoluteVolume
	 * @Description: 设置AD的绝对音量
	 * @param arg0
	 * @return: void
	 */
	public void K_setADAbsoluteVolume(int arg0) {
		if (tvAudioManager != null)
			tvAudioManager.setADAbsoluteVolume(arg0);
	}

	/**
	 * 
	 * @Title: K_getADAbsoluteVolume
	 * @Description: 获取AD的绝对音量
	 * @return: void
	 */
	public void K_getADAbsoluteVolume() {
		if (tvAudioManager != null)
			tvAudioManager.getADAbsoluteVolume();
	}

	/**
	 * 
	 * @Title: K_setHOHStatus
	 * @Description: 设置HOH状态
	 * @param tf
	 * @return: void
	 */
	public void K_setHOHStatus(boolean tf) {
		if (tvAudioManager != null)
			tvAudioManager.setHOHStatus(tf);
	}

	/**
	 * 
	 * @Title: K_getHOHStatus
	 * @Description: 获取HOH状态
	 * @return
	 * @return: boolean
	 */
	public boolean K_getHOHStatus() {
		return tvAudioManager.getHOHStatus();
	}

	/**
	 * 
	 * @Title: K_setHDMITx_HDByPass
	 * @Description: TODO
	 * @param tf
	 * @return: void
	 */
	public void K_setHDMITx_HDByPass(boolean tf) {
		if (tvAudioManager != null)
			tvAudioManager.setHDMITx_HDByPass(tf);
	}

	/**
	 * 
	 * @Title: K_getHDMITx_HDByPass
	 * @Description: TODO
	 * @return: void
	 */
	public void K_getHDMITx_HDByPass() {
		if (tvAudioManager != null)
			tvAudioManager.getHDMITx_HDByPass();
	}

	/**
	 * 
	 * @Title: K_setAudioSurroundMode
	 * @Description: 设置环绕立体音效模式
	 * @param arg0
	 * @return: void
	 */
	public void K_setAudioSurroundMode(int arg0) {
		if (tvAudioManager != null)
			tvAudioManager.setAudioSurroundMode(arg0);
	}

	/**
	 * 
	 * @Title: K_getAudioSurroundMode
	 * @Description: 获取环绕立体音效模式
	 * @return: void
	 */
	public int K_getAudioSurroundMode() {
		return tvAudioManager.getAudioSurroundMode();
	}

	public void K_enableSRS(boolean tf) {
		if (tvAudioManager != null)
			tvAudioManager.enableSRS(tf);
	}

	public boolean K_isEnableSRS() {
		return tvAudioManager.isSRSEnable();
	}

	/**
	 * 
	 * @Title: K_setAudioSpdifOutMode
	 * @Description: 设置音频同轴输出模式
	 * @param arg0
	 * @return: void
	 */
	public void K_setAudioSpdifOutMode(int arg0) {
		if (tvAudioManager != null)
			tvAudioManager.setAudioSpdifOutMode(arg0);
	}

	/**
	 * 
	 * @Title: K_getAudioSpdifOutMode
	 * @Description: 获取音频同轴输出模式
	 * @return
	 * @return: int
	 */
	public int K_getAudioSpdifOutMode() {
		return tvAudioManager.getAudioSpdifOutMode();
	}

}

package com.kguan.mtvplay.tvapi;

import com.mstar.android.tv.TvTimerManager;
import com.mstar.android.tvapi.common.vo.EnumSleepTimeState;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumTimeZone;

public class K_TvTimerManager {
	public static K_TvTimerManager kTvTimerManager = null;
    /**
     * Sleep time state.
     */
    /** Sleep timer off */
    public static final int K_SLEEP_TIME_OFF = TvTimerManager.SLEEP_TIME_OFF;
    /** 10 mins to sleep */
    public static final int K_SLEEP_TIME_10MIN = TvTimerManager.SLEEP_TIME_10MIN;
    /** 20 mins to sleep */
    public static final int K_SLEEP_TIME_20MIN = TvTimerManager.SLEEP_TIME_20MIN;
    /** 30 mins to sleep */
    public static final int K_SLEEP_TIME_30MIN = TvTimerManager.SLEEP_TIME_30MIN;
    /** 60 mins to sleep */
    public static final int K_SLEEP_TIME_60MIN = TvTimerManager.SLEEP_TIME_60MIN;
    /** 90 mins to sleep */
    public static final int K_SLEEP_TIME_90MIN = TvTimerManager.SLEEP_TIME_90MIN;
    /** 120 mins to sleep */
    public static final int K_SLEEP_TIME_120MIN = TvTimerManager.SLEEP_TIME_120MIN;
    /** 180 mins to sleep */
    public static final int K_SLEEP_TIME_180MIN = TvTimerManager.SLEEP_TIME_180MIN;
    /** 240 mins to sleep */
    public static final int K_SLEEP_TIME_240MIN = TvTimerManager.SLEEP_TIME_240MIN;

	public TvTimerManager tvTimerManager = null;

	private K_TvTimerManager() {
		tvTimerManager = TvTimerManager.getInstance();
	}

	public static K_TvTimerManager getInstance() {
		if (kTvTimerManager == null) {
			kTvTimerManager = new K_TvTimerManager();
		}
		return kTvTimerManager;
	}
	/**
	 * 
	 * @Title: K_getSleepMode
	 * @Description: 获取系统睡眠模式参数
	 * @return
	 * @return: EnumSleepTimeState
	 */
/*	public EnumSleepTimeState K_getSleepMode() {
		return tvTimerManager.getSleepMode();
	}

	public int K_getOrdinal_TvTimer() {
		return tvTimerManager.getSleepMode().ordinal();
	}
*/
	/**
	 * 
	 * @Title: K_setSleepMode
	 * @Description: 设置睡眠模式
	 * @param eMode
	 * @return: void
	 */
	@Deprecated
	public void K_setSleepMode(EnumSleepTimeState eMode) {
		if (tvTimerManager != null)
			tvTimerManager.setSleepMode(eMode);
	}
	
	public void K_setSleepTimeMode(int mode){
		if (tvTimerManager != null)
			tvTimerManager.setSleepTimeMode(mode);
	}

	public int K_getSleepTimeMode(){
		return tvTimerManager.getSleepTimeMode();
	}
	
	public int K_getSleepTimeRemainMins(){
		return tvTimerManager.getSleepTimeRemainMins();
	}
	
	public void setTimeZone(EnumTimeZone eTimezoneGmtMinus11Start, boolean isSaved) {
		if (tvTimerManager != null)
			tvTimerManager.setTimeZone(eTimezoneGmtMinus11Start, isSaved);
	}
}

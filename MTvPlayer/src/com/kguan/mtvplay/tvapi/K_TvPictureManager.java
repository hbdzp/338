package com.kguan.mtvplay.tvapi;

import com.kguan.mtvplay.tvapi.vo.common.K_VideoInfo;
import com.mstar.android.tv.TvPictureManager;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.EnumAvdVideoStandardType;
import com.mstar.android.tvapi.common.vo.VideoInfo.EnumScanType;

/**
 * 
 * @ClassName: K_PictureModel
 * @Description: TV图像
 * @author: zengjf
 * @date: 2015-9-11
 */
public class K_TvPictureManager {
	public static K_TvPictureManager k_PictureModel = null;
	public TvPictureManager tvPictureManager = null;

	private K_TvPictureManager() {
		tvPictureManager = TvPictureManager.getInstance();
	}

	/**
	 * 
	 * @Title: getInstance
	 * @Description: 获取K_PictureModel实例
	 * @return
	 * @return: K_PictureMode
	 */
	public static K_TvPictureManager getInstance() {
		if (k_PictureModel == null) {
			k_PictureModel = new K_TvPictureManager();
		}
		return k_PictureModel;
	}

	/**
	 * 
	 * @Title: K_getPictureMode
	 * @Description: 获取当前图像模式
	 * @return
	 * @return: int
	 */
	public int K_getPictureMode() {
		return tvPictureManager.getPictureMode();
	}

	/**
	 * 
	 * @Title: K_setPictureMode
	 * @Description: 设置图像模式
	 * @param arg0
	 * @return: void
	 */
	public void K_setPictureMode(int arg0) {
		if (tvPictureManager != null)
			tvPictureManager.setPictureMode(arg0);
	}

	/**
	 * 
	 * @Title: K_getITC
	 * @Description: 获取ITC参数
	 * @return
	 * @return: int
	 */
	public int K_getITC() {
		return tvPictureManager.getITC();
	}

	/**
	 * 
	 * @Title: K_setITC
	 * @Description: 设置ITC
	 * @param nf
	 * @return: void
	 */
	public void K_setITC(short nf) {
		if (tvPictureManager != null)
			tvPictureManager.setITC(nf);
	}

	/**
	 * 
	 * @Title: K_getBacklight
	 * @Description: 获取背光参数
	 * @return
	 * @return: int
	 */
	public int K_getBacklight() {
		return tvPictureManager.getBacklight();
	}

	/**
	 * 
	 * @Title: K_setBackLight
	 * @Description: 设置背光参数
	 * @param arg0
	 * @return: void
	 */
	public void K_setBackLight(short arg0) {
		if (tvPictureManager != null)
			tvPictureManager.setBacklight(arg0);
	}

	/**
	 * 
	 * @Title: K_getNoiseReduction
	 * @Description: 获取降噪参数
	 * @return
	 * @return: int
	 */
	public int K_getNoiseReduction() {
		return tvPictureManager.getNoiseReduction();
	}

	/**
	 * 
	 * @Title: K_setNoiseReduction
	 * @Description: 设置降噪参数
	 * @param arg0
	 * @return: void
	 */
	public void K_setNoiseReduction(int arg0) {
		if (tvPictureManager != null)
			tvPictureManager.setNoiseReduction(arg0);
	}

	/**
	 * 
	 * @Title: K_getMpegNoiseReduction
	 * @Description: 获取Mpeg图像降噪参数
	 * @return
	 * @return: int
	 */
	public int K_getMpegNoiseReduction() {
		return tvPictureManager.getMpegNoiseReduction();
	}

	/**
	 * 
	 * @Title: K_setMpegNoiseReduction
	 * @Description: 设置Mpeg图像降噪
	 * @param arg0
	 * @return: void
	 */
	public void K_setMpegNoiseReduction(int arg0) {
		if (tvPictureManager != null)
			tvPictureManager.setNoiseReduction(arg0);
	}

	public EnumAvdVideoStandardType K_getVideoStandard() {
		EnumAvdVideoStandardType eAVST = null;
		try {
			eAVST = TvManager.getInstance().getPlayerManager().getVideoStandard();
		} catch (TvCommonException e) {
			e.printStackTrace();
		}
		return eAVST;
	}

	/**
	 * 
	 * @Title: K_getVideoItem
	 * @Description: 获取EnumAvd视频标准
	 * @param arg0
	 * @return
	 * @return: int
	 */
	public int K_getVideoItem(int arg0) {
		return tvPictureManager.getVideoItem(arg0);
	}

	/**
	 * 
	 * @Title: K_setVideoItem
	 * @Description: TODO
	 * @param arg0
	 * @param arg1
	 * @return: void
	 */
	public void K_setVideoItem(int arg0, int arg1) {
		if (tvPictureManager != null)
			tvPictureManager.setVideoItem(arg0, arg1);
	}

	// 新增
	/**
	 * 
	 * @Title: K_getVideoInfo
	 * @Description: 获取视频信息
	 * @return
	 * @return: VideoInfo
	 */
	public K_VideoInfo K_getVideoInfo() {
		K_VideoInfo k_VideoInfo = new K_VideoInfo();
		k_VideoInfo.setVideoInfo(tvPictureManager.getVideoInfo());
		return k_VideoInfo;
	}

	public int K_getvResolution() {
		return K_getVideoInfo().getVideoInfo().vResolution;
	}

	public EnumScanType K_getScanType() throws TvCommonException {
		return K_getVideoInfo().getVideoInfo().getScanType();
	}

	public void K_enableXvyccCompensation(boolean tf, int arg0) {
		tvPictureManager.enableXvyccCompensation(tf, arg0);
	}

	/**
	 * 
	 * @Title: K_getVideoArcType
	 * @Description: 获取图像回传类型
	 * @return
	 * @return: int
	 */
	public int K_getVideoArcType() {
		int arcType;
		arcType = K_Constants.K_MAPPING_VIDEO_ARC_ENUM
				.getKTCOrdinalThroughValue(tvPictureManager.getVideoArcType());
		return arcType;
	}

	/**
	 * 
	 * @Title: K_setVideoArcType
	 * @Description: 设置图像回传类型
	 * @param arg0
	 * @return: void
	 */
	public void K_setVideoArcType(int arcType) {
		if (tvPictureManager != null)
			tvPictureManager.setVideoArcType(
					K_Constants.K_MAPPING_VIDEO_ARC_ENUM.values()[arcType].getMstValue());
	}

	// 新增

	public int K_getColorTemprature() {
		return tvPictureManager.getColorTempIdx().ordinal();
	}

	public void K_setColorTemprature(int arg0) {
		if (tvPictureManager != null)
			tvPictureManager.setColorTemprature(arg0);
	}

	public int K_getFilm() {
		return tvPictureManager.getFilm();
	}

	/**
	 * 
	 * @Title: K_setColorRange
	 * @Description: 设置颜色范围
	 * @param arg0
	 * @return: void
	 */
	public void K_setColorRange(byte arg0) {
		if (tvPictureManager != null)
			tvPictureManager.setColorRange(arg0);
	}

	public void K_setFilm(int arg0) {
		if (tvPictureManager != null)
			tvPictureManager.setFilm(arg0);
	}

	public boolean K_freezeImage() {
		return tvPictureManager.freezeImage();
	}

	public boolean isImageFreezed() {
		return tvPictureManager.isImageFreezed();
	}
	
	public boolean K_unFreezeImage() {
		return tvPictureManager.unFreezeImage();
	}

	public boolean K_is4K2KMode(boolean bEn) {
		return tvPictureManager.is4K2KMode(bEn);
	}

	public void K_setDynamicBackLightThreadSleep(boolean bFlag) {
		if (tvPictureManager != null)
			tvPictureManager.setDynamicBackLightThreadSleep(bFlag);
	}

	public void K_setPCClock(short clock) {
		if (tvPictureManager != null)
			tvPictureManager.setPCClock(clock);
	}

	public void K_setPCPhase(short phase) {
		if (tvPictureManager != null)
			tvPictureManager.setPCPhase(phase);
	}

	public void K_setPCHPos(short hpos) {
		if (tvPictureManager != null)
			tvPictureManager.setPCHPos(hpos);
	}

	public void K_setPCVPos(short vpos) {
		if (tvPictureManager != null)
			tvPictureManager.setPCVPos(vpos);
	}

	public int K_getPCClock() {
		// TODO Auto-generated method stub
		return tvPictureManager.getPCClock();
	}

	public int K_getPCPhase() {
		// TODO Auto-generated method stub
		return tvPictureManager.getPCPhase();
	}

	public int K_getPCHPos() {
		// TODO Auto-generated method stub
		return tvPictureManager.getPCHPos();
	}

	public int K_getPCVPos() {
		// TODO Auto-generated method stub
		return tvPictureManager.getPCVPos();
	}

	public boolean K_execAutoPc() {
		// TODO Auto-generated method stub
		return tvPictureManager.execAutoPc();
	}

	public void K_disableBacklight() {
		if (tvPictureManager != null)
			tvPictureManager.disableBacklight();
	}

	public void K_enableBacklight() {
		if (tvPictureManager != null)
			tvPictureManager.enableBacklight();
	}

}

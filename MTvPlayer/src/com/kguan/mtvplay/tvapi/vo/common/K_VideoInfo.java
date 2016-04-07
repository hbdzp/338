package com.kguan.mtvplay.tvapi.vo.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.VideoInfo;

public class K_VideoInfo {
	private VideoInfo videoInfo;

	public VideoInfo getVideoInfo() {
		return videoInfo;
	}

	public void setVideoInfo(VideoInfo videoInfo) {
		this.videoInfo = videoInfo;
	}
	
	public int K_getScanType(){
		if (videoInfo != null){
			try {
				return videoInfo.getVideoScanType();
			} catch (TvCommonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;
	}
}

package com.kguan.mtvplay.tvapi;

import com.kguan.mtvplay.tvapi.enumeration.K_EnumSetProgramInfo;
import com.mstar.android.tvapi.atv.AtvManager;
import com.mstar.android.tvapi.atv.AtvScanManager;
import com.mstar.android.tvapi.atv.vo.EnumGetProgramInfo;
import com.mstar.android.tvapi.atv.vo.EnumSetProgramInfo;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.AtvSystemStandard.EnumAtvSystemStandard;

public class K_AtvManager {
	public static K_AtvManager kAtvManager = null;
	public AtvManager atvManager = null;

	private K_AtvManager() {
		atvManager = new AtvManager();
	}

	public static K_AtvManager getInstance() {
		if (kAtvManager == null) {
			kAtvManager = new K_AtvManager();
		}
		return kAtvManager;
	}

	public static AtvScanManager K_getAtvScanManager() {
		// TODO Auto-generated method stub
		return AtvManager.getAtvScanManager();
	}
	
	public boolean K_setAtvProgramInfo(K_EnumSetProgramInfo arg0, int arg1, int mSoundSystemIndex){
		try {
			//return K_getAtvScanManager().setAtvProgramInfo(EnumSetProgramInfo.values()[arg0.ordinal()], arg1, arg2);
			return K_getAtvScanManager().setAtvProgramInfo(EnumSetProgramInfo.values()[arg0.ordinal()], arg1, (EnumAtvSystemStandard.values()[mSoundSystemIndex]).getValue());
			
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public int  K_getAtvProgramInfo(EnumGetProgramInfo arg0, int arg1){
		try {
			return K_getAtvScanManager().getAtvProgramInfo(arg0, arg1);
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

}

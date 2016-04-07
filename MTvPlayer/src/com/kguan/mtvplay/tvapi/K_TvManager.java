package com.kguan.mtvplay.tvapi;

import com.kguan.mtvplay.tvapi.listener.K_OnMhlEventListener;
import com.kguan.mtvplay.tvapi.vo.common.K_EnumAvdVideoStandardType;
import com.mstar.android.tvapi.common.DatabaseManager;
import com.mstar.android.tvapi.common.MhlManager;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.TvPlayer;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.factory.vo.EnumAcOnPowerOnMode;

public class K_TvManager {
	public static K_TvManager k_TvManager = null;
	public TvManager tvManager = null;

	private K_TvManager() {
		tvManager = TvManager.getInstance();
	}

	public static K_TvManager getInstance() {
		if (k_TvManager == null) {
			k_TvManager = new K_TvManager();
		}
		return k_TvManager;
	}

	public void K_setTvosCommonCommand(String interfaceCommand)  {		
			try {
				if (tvManager != null)
					tvManager.setTvosCommonCommand(interfaceCommand);
			} catch (TvCommonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public boolean K_setEnvironmentPowerMode(EnumAcOnPowerOnMode ePowerMode) {
		try {
			return tvManager.getFactoryManager().setEnvironmentPowerMode(ePowerMode);
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean K_setEnvironment(String name, String value)  {
		try {
			return tvManager.setEnvironment(name, value);
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public MhlManager K_getMhlManager() {
		return tvManager.getMhlManager();
	}

	public TvPlayer K_getPlayerManager() {
		return tvManager.getPlayerManager();
	}

	public DatabaseManager K_getDatabaseManager() {
		return tvManager.getDatabaseManager();
	}
	
	public K_EnumAvdVideoStandardType K_getVideoStandard(){
		K_EnumAvdVideoStandardType k_EnumAvdVideoStandardType = new K_EnumAvdVideoStandardType();
		try {
			k_EnumAvdVideoStandardType.setEnumAvdVideoStandardType(tvManager.getPlayerManager().getVideoStandard());
			return k_EnumAvdVideoStandardType;
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void K_setDatabaseDirtyByApplication(short arg0){
		
		try {
			K_getDatabaseManager().setDatabaseDirtyByApplication(arg0);
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean K_isMheg5Running(){
		try {
			return K_getPlayerManager().isMheg5Running();
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean K_sendMheg5Key(int key){
		try {
			return K_getPlayerManager().sendMheg5Key(key);
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void K_registerOnMhlEventListener(K_OnMhlEventListener mTvPlayerEventListener) {
		if (K_getMhlManager() != null)
			K_getMhlManager().setOnMhlEventListener(mTvPlayerEventListener);
	}
	
	public void K_unregisterOnMhlEventListener(K_OnMhlEventListener mTvPlayerEventListener){
	/*	if (K_getMhlManager() != null)
			K_getMhlManager().setOnMhlEventListener(mTvPlayerEventListener);*/
	}

}

package com.kguan.mtvplay.tvapi;

import com.kguan.mtvplay.tvapi.listener.K_OnCiStatusChangeEventListener;
import com.kguan.mtvplay.tvapi.listener.K_OnUiEventListener;
import com.mstar.android.tv.TvCiManager;

public class K_TvCiManager {
	public static K_TvCiManager k_TvCiManager = null;
	public TvCiManager tvCiManager = null;

	private K_TvCiManager() {
		tvCiManager = TvCiManager.getInstance();
	}

	public static K_TvCiManager getInstance() {
		if (k_TvCiManager == null) {
			k_TvCiManager = new K_TvCiManager();
		}
		return k_TvCiManager;
	}

	public void K_sendCiOpSearchStart(boolean bUnattendedFlag) {
		tvCiManager.sendCiOpSearchStart(bUnattendedFlag);
	}

	public void K_registerOnUiEventListener(K_OnUiEventListener mUiEventListener) {
		if(tvCiManager != null)
			tvCiManager.registerOnUiEventListener(mUiEventListener);
	}
	public void K_unregisterOnUiEventListener(K_OnUiEventListener mUiEventListener) {
		if(tvCiManager != null)
		tvCiManager.unregisterOnUiEventListener(mUiEventListener);
	}
	public void K_registerOnCiStatusChangeEventListener(K_OnCiStatusChangeEventListener mCiStatusChangeEventListener){
		if(tvCiManager != null)
			tvCiManager.registerOnCiStatusChangeEventListener(mCiStatusChangeEventListener);
	}

	public void K_unregisterOnCiStatusChangeEventListener(K_OnCiStatusChangeEventListener mCiStatusChangeEventListener) {
		if(tvCiManager != null)
			tvCiManager.unregisterOnCiStatusChangeEventListener(mCiStatusChangeEventListener);	}

	public void ciClearOPSearchSuspended() {
		if(tvCiManager != null)
			tvCiManager.ciClearOPSearchSuspended();
	}

	public boolean K_isOpMode() {
		// TODO Auto-generated method stub
		return tvCiManager.isOpMode();
	}

	public void K_enterCiOperatorProfile(short Index) {
		if(tvCiManager != null)
		tvCiManager.enterCiOperatorProfile(Index);
	}

	public String K_getOpProfileNameByIndex(short Index) {
		// TODO Auto-generated method stub
		return tvCiManager.getOpProfileNameByIndex(Index);
	}

	public void K_deleteOpCacheByIndex(short Index) {
		if(tvCiManager != null)
			tvCiManager.deleteOpCacheByIndex(Index);
	}

	public short K_getOpCacheCount() {
		// TODO Auto-generated method stub
		return tvCiManager.getOpCacheCount();
	}

	public short K_getOpDtvSysTypeByIndex(short Index) {
		// TODO Auto-generated method stub
		return tvCiManager.getOpDtvSysTypeByIndex(Index);
	}

	public void K_exitCiOperatorProfile() {
		if(tvCiManager != null)
			tvCiManager.exitCiOperatorProfile();
	}

	public void K_answerMenu(short index) {
		if(tvCiManager != null)
			tvCiManager.answerMenu(index);
	}

	public void K_close() {
		if(tvCiManager != null)
			tvCiManager.close();
	}

	public void K_backMenu() {
		if(tvCiManager != null)
			tvCiManager.backMenu();
	}

	public short K_getEnqAnsLength() {
		// TODO Auto-generated method stub
		return tvCiManager.getEnqAnsLength();
	}

	public void K_answerEnq(String password) {
		if(tvCiManager != null)
			tvCiManager.answerEnq(password);
	}

	public boolean K_isDataExisted() {
		// TODO Auto-generated method stub
		return tvCiManager.isDataExisted();
	}

	public int K_getCiMmiType() {
		// TODO Auto-generated method stub
		return tvCiManager.getCiMmiType();
	}

	public int K_getMenuTitleLength() {
		// TODO Auto-generated method stub
		return tvCiManager.getMenuTitleLength();
	}

	public String K_getMenuTitleString() {
		return tvCiManager.getMenuTitleString();
	}

	public int K_getMenuSubtitleLength() {
		// TODO Auto-generated method stub
		return tvCiManager.getMenuSubtitleLength();
	}

	public String K_getMenuSubtitleString() {
		// TODO Auto-generated method stub
		return tvCiManager.getMenuSubtitleString();
	}

	public int K_getMenuBottomLength() {
		// TODO Auto-generated method stub
		return tvCiManager.getMenuBottomLength();
	}

	public String K_getMenuBottomString() {
		// TODO Auto-generated method stub
		return tvCiManager.getMenuBottomString();
	}

	public int K_getMenuChoiceNumber() {
		// TODO Auto-generated method stub
		return tvCiManager.getMenuChoiceNumber();
	}

	public int K_getListChoiceNumber() {
		// TODO Auto-generated method stub
		return tvCiManager.getListChoiceNumber();
	}

	public String K_getMenuSelectionString(int index) {
		// TODO Auto-generated method stub
		return tvCiManager.getMenuSelectionString(index);
	}

	public int K_getListTitleLength() {
		// TODO Auto-generated method stub
		return tvCiManager.getListTitleLength();
	}

	public String K_getListTitleString() {
		// TODO Auto-generated method stub
		return tvCiManager.getListTitleString();
	}

	public int K_getListSubtitleLength() {
		// TODO Auto-generated method stub
		return tvCiManager.getListSubtitleLength();
	}

	public String K_getListSubtitleString() {
		// TODO Auto-generated method stub
		return tvCiManager.getListSubtitleString();
	}

	public int K_getListBottomLength() {
		// TODO Auto-generated method stub
		return tvCiManager.getListBottomLength();
	}

	public String K_getListBottomString() {
		// TODO Auto-generated method stub
		return tvCiManager.getListBottomString();
	}


	public String K_getListSelectionString(int index) {
		// TODO Auto-generated method stub
		return tvCiManager.getListSelectionString(index);
	}

	public int K_getEnqLength() {
		// TODO Auto-generated method stub
		return tvCiManager.getEnqLength();
	}

	public String K_getEnqString() {
		// TODO Auto-generated method stub
		return tvCiManager.getEnqString();
	}


	
}

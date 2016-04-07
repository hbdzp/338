package com.kguan.mtvplay.tvapi.vo;

import java.util.ArrayList;

import com.mstar.android.tvapi.dtv.vo.MenuSubtitleService;

public class K_MenuSubtitleService {
	private MenuSubtitleService menuSubtitleService;
	private MenuSubtitleService[] menuSubtitleServices;

	public MenuSubtitleService getMenuSubtitleService() {
		return menuSubtitleService;
	}

	public void setMenuSubtitleService(MenuSubtitleService menuSubtitleService) {
		this.menuSubtitleService = menuSubtitleService;
	}
	
	public void setMenuSubtitleServices(MenuSubtitleService[] menuSubtitleServices) {
		this.menuSubtitleServices = menuSubtitleServices;
	}
	
	public MenuSubtitleService[] getMenuSubtitleServices() {
		return menuSubtitleServices;
	}
}

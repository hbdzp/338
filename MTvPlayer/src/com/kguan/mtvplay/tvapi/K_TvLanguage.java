package com.kguan.mtvplay.tvapi;

import java.util.Locale;

import com.mstar.android.tv.TvLanguage;

public class K_TvLanguage {
	
	public static String K_getName(int language) {
		return TvLanguage.getName(language);
	}
	
	public static int K_getLanguageByLocale(Locale locale){
		return TvLanguage.getLanguageByLocale(locale);
	}
	
	public static Locale K_getLocale(int language, Locale currentLocale){
		return TvLanguage.getLocale(language, currentLocale);
	}
}

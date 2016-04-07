package com.mstar.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import android.text.TextUtils;

public class PropHelp {
	
	//Default factory menu password
	private static final String DEFAULT_FACTORY_PASSWD = "8202";
	//Default softinfo menu password
	private static final String DEFAULT_SOFTINFO_PASSWD = "2013";
	//Default hotel menu password
	private static final String DEFAULT_HOTEL_PASSWD = "4578";	
	//Default hotel channel lock password
	private static final String DEFAULT_HOTEL_CHLOCK_PASSWD = "9898";

	private static final String DEFAULT_VERSION_CHLOCK_PASSWD="9991";
	
	private static final String KEY_LANGUAGE = "persist.sys.language";
    private static final String KEY_LOGO = "ktc.logo";
    private static final String KEY_3D = "ktc.3d";
    private static final String KEY_DTMB = "ktc.dtmb";
    private static final String KEY_FACTORY = "ktc.factory";
    private static final String KEY_FACTORY_PASSWD = "ktc.factory.passwd";
    private static final String KEY_SOFTINFO_PASSWD= "ktc.softinfo.passwd"; //zjd
    private static final String KEY_HOTEL = "ktc.hotel";
    private static final String KEY_HOTEL_PASSWD = "ktc.hotel.passwd";
    private static final String KEY_HOTEL_CHLOCK_PASSWD = "ktc.hotel.chlock.passwd";
    private static final String KEY_PANEL_DYNAMIC_BACKLIGHT = "ktc.panel.dynamic_backlight";
    //nathan.liao 2015.03.03 add for T4C1 board start
    private static final String KEY_BOARD_T4C1 = "ktc.board.t4c1";
    //nathan.liao 2015.03.03 add for T4C1 board end
    private static PropHelp mPropHelp;
    
    private boolean isZh;
    private boolean hasLogo;
    private boolean has3d;
    private boolean hasDtmb;
    private boolean hasFactory;
    private boolean hasSoftInfo;
    private String factoryPasswd;
    private String softInfoPasswd; //zjd,20140714
    private boolean hasHotel;
    private String hotelPasswd;
    private String hotelChlockPasswd;
	private String versionPasswd;
	//nathan.liao 2015.03.03 add for T4C1 board start
	private boolean hasT4C1Board;
	//nathan.liao 2015.03.03 add for T4C1 board end

    private boolean hasDynamicBacklight;
    
    public static PropHelp newInstance() {
    	if (mPropHelp == null) {
    		mPropHelp = new PropHelp();
    	}
    	
    	return mPropHelp;
    }

    private PropHelp() {
        Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream("/system/build.prop"));
			props.load(in);
			String value = props.getProperty(KEY_LANGUAGE);
			isZh = value != null && value.equals("zh");
			
			value = props.getProperty(KEY_LOGO);
			hasLogo = value != null && value.equals("true");
			
			value = props.getProperty(KEY_3D);
			has3d = value != null && value.equals("true");
			
			value = props.getProperty(KEY_DTMB);
			hasDtmb = value != null && value.equals("true");
			
			value = props.getProperty(KEY_FACTORY);
			hasFactory = value != null && value.equals("true");
			
			value = props.getProperty(KEY_FACTORY_PASSWD);
			factoryPasswd = !TextUtils.isEmpty(value) ? value : DEFAULT_FACTORY_PASSWD; //?,DEFAULT_HOTEL_PASSWD->DEFAULT_FACTORY_PASSWD
			
			value = props.getProperty(KEY_SOFTINFO_PASSWD);//zjd
			softInfoPasswd = !TextUtils.isEmpty(value) ? value : DEFAULT_SOFTINFO_PASSWD; //zjd,20140714
			
			value = props.getProperty(KEY_HOTEL);
			hasHotel = value != null && value.equals("true");
			
			value = props.getProperty(KEY_HOTEL_PASSWD);
			hotelPasswd = !TextUtils.isEmpty(value) ? value : DEFAULT_HOTEL_PASSWD;
			
			value = props.getProperty(KEY_HOTEL_CHLOCK_PASSWD);
			hotelChlockPasswd = !TextUtils.isEmpty(value) ? value : DEFAULT_HOTEL_CHLOCK_PASSWD;
			
			value = props.getProperty(KEY_PANEL_DYNAMIC_BACKLIGHT);
			hasDynamicBacklight = value != null && value.equals("true");
			
			//nathan.liao 2015.03.03 add for T4C1 board start 
			value = props.getProperty(KEY_BOARD_T4C1);
			hasT4C1Board = value != null && value.equals("true");
			//nathan.liao 2015.03.03 add for T4C1 board end

			versionPasswd=DEFAULT_VERSION_CHLOCK_PASSWD;
		} catch (Exception e) {
			hasFactory = true;
			factoryPasswd = DEFAULT_FACTORY_PASSWD;
			softInfoPasswd = DEFAULT_SOFTINFO_PASSWD; //zjd,20140714
			hasHotel = true;
			hotelPasswd = DEFAULT_HOTEL_PASSWD;
			hotelChlockPasswd = DEFAULT_HOTEL_CHLOCK_PASSWD;
			//nathan.liao 2015.03.03 add for T4C1 board start
			hasT4C1Board = false;
			//nathan.liao 2015.03.03 add for T4C1 board end
		}
    }
    
    /**
     * Default chinese
     * @return
     */
    public boolean isZh() {
    	return this.isZh;
    }
 
    /**
     * Has logo
     * @return
     */
    public boolean hasLogo() {
    	return hasLogo;
    }
    
    /**
     * Has 3D feature
     * @return
     */
    public boolean has3d() {
    	return this.has3d;
    }
    
    /**
     * Has dtv feature
     * @return
     */
    public boolean hasDtmb() {
    	return this.hasDtmb;
    }
    
    /**
     * Has factory menu feature
     * @return
     */
    public boolean hasFactory() {
    	return this.hasFactory;
    }
    
    /**
     * password of Factory menu
     * @return
     */
    public String getFactoryPasswd() {
    	return this.factoryPasswd;
    } 
    
    //zjd,20140714
    /**
     * password of softinfo menu
     * @return
     */
    public String getSoftInfoPasswd() {
    	return this.softInfoPasswd;
    }
    
    /**
     * Has hotel menu feature
     * @return
     */
    public boolean hasHotel() {
    	return this.hasHotel;
    }
    
    /**
     * password of hotel menu
     * @return
     */
    public String getHotelPasswd() {
    	return this.hotelPasswd;
    }
    
    /**
     * password of hotel channel lock
     * @return
     */
    public String getHotelChlockPasswd() {
    	return this.hotelChlockPasswd;
    }
    
    /**
     * panel has dynamic backlight
     * @return
     */
    public boolean hasDynamicBacklight() {
    	return this.hasDynamicBacklight;
    }

	public String getVersionPasswd() {
    	return this.versionPasswd;
    }
	//nathan.liao 2015.03.03 add for T4C1 board start
	public boolean isHasT4C1Board() {
		return hasT4C1Board;
	}	
	//nathan.liao 2015.03.03 add for T4C1 board end
	 
	
	
}

package com.mstar.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import com.kguan.mtvplay.tvapi.K_TvManager;

/**
 * Manager Database
 * @author liaomz
 *
 */
public class DataBaseUtil {
	
	private static DataBaseUtil mDataBaseUtil;
	
	private static Context mContext = null;
	
	//user_setting.db systemsetting table index
	public final static short T_SystemSetting_IDX = 0x19;
	
	public static DataBaseUtil getInstance(Context context) {
		mContext = context;
    	if (mDataBaseUtil == null) {
    		mDataBaseUtil = new DataBaseUtil();
    	}
    	return mDataBaseUtil;
    }

	public DataBaseUtil() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * get the user_setting.db systemsetting table tag value
	 * @param tag
	 * @return
	 */
	public int getValueDatabase_systemsetting(String tag) {
		int bAutoBacklightSign = 0;
		Cursor cursor = mContext.getContentResolver().query(
				Uri.parse("content://mstar.tv.usersetting/systemsetting"),
				null, null, null, null);
		if (cursor.moveToNext())
			bAutoBacklightSign = cursor.getInt(cursor
					.getColumnIndex(tag));
		cursor.close();
		return bAutoBacklightSign;
	}
	/**
	 * set the user_setting.db systemsetting table tag value
	 * @param tag
	 * @param values
	 */
	public void updateDatabase_systemsetting(String tag,int values) {
		int ret = -1;
		ContentValues vals = new ContentValues();
		vals.put(tag, values);
		try {
			ret = mContext.getContentResolver().update(
					Uri.parse("content://mstar.tv.usersetting/systemsetting"),
					vals, null, null);
		} catch (SQLException e) {
		}
		if (ret == -1) {
			System.out.println("update tbl_SystemSetting ignored");
		}
		
        K_TvManager.getInstance().K_setDatabaseDirtyByApplication(T_SystemSetting_IDX);
	}
}

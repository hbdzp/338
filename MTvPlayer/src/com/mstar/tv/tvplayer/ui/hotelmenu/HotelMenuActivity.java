package com.mstar.tv.tvplayer.ui.hotelmenu;




import com.mstar.tv.tvplayer.ui.R;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

public class HotelMenuActivity extends Activity {
	
	public static final int REQUEST_CLONE = 10;
	public static final int REQUEST_LOGOSET = 11;
	
	protected HotelMenuViewHolder hotelViewHolder;
	private View container;
	
	private boolean backFromeClone = false;
	private boolean backFromeLogoSet = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotelmenu);
		container = findViewById(R.id.hotelmenu_container);
		hotelViewHolder = new HotelMenuViewHolder(HotelMenuActivity.this);
		hotelViewHolder.findViews();
		hotelViewHolder.LoadDataToUI();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		container.setVisibility(View.VISIBLE);
		if (backFromeClone) {
			findViewById(R.id.linearlayout_clone).requestFocus();
			backFromeClone = false;
		}
		if (backFromeLogoSet) {
			findViewById(R.id.linearlayout_logo_set).requestFocus();
			backFromeLogoSet = false;
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		container.setVisibility(View.INVISIBLE);
		super.onPause();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQUEST_CLONE) {
			backFromeClone = true;
		}
		else if(requestCode == REQUEST_LOGOSET)
		{
			backFromeLogoSet=true;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		View view = getCurrentFocus();
		switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_UP: {
				if (view != null 
						&& view.getId() == R.id.linearlayout_hotel_menu) {
					findViewById(R.id.linearlayout_clone).requestFocus();
					return true;
				}
			} break;
			
			case KeyEvent.KEYCODE_DPAD_DOWN: {
				if (view != null
						&& view.getId() == R.id.linearlayout_clone) {
					findViewById(R.id.linearlayout_hotel_menu).requestFocus();
					return true;
				}
			} break;
			
			default:break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}

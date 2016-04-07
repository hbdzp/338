package com.mstar.tv.tvplayer.ktc.factoryremote;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.util.Iterator;

import com.mstar.tv.tvplayer.ui.R;
import com.mstar.util.SettingTools;




import android.app.Activity;
import android.content.Context;

import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.RouteInfo;
import android.net.ethernet.EthernetDevInfo;
import android.net.ethernet.EthernetManager;
import android.net.pppoe.PPPOE_STA;
import android.net.pppoe.PppoeManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;


public class FactoryConnectWire extends Activity {
	PppoeManager mPPPOE = null;
	WifiManager mWifiManager;
	EthernetManager mEthernetManager;
	TextView content_text;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.wireconnect);
		findView();
		init();
		initCurrentNetStatus();
	}
	private void init()
	{
		mPPPOE = PppoeManager.getInstance();
		mWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		mEthernetManager = EthernetManager.getInstance();
	}
	private void findView() {
		
		content_text = (TextView)findViewById(R.id.text_content);
		
	}
	/**
	 * 初始化当前的网络状态
	 */
	private void initCurrentNetStatus()
	{
		if(isPppoeConnected())
		{
			showPppoeStatus();
		}else if(isWifiEnabled())
		{
			showWiFiState();
		}else
		{
			showWireState();
		}
	}
	
	public boolean isPppoeConnected(){
		
		if (android.net.pppoe.PPPOE_STA.CONNECTED == mPPPOE.PppoeGetStatus())
		{
			mPPPOE.PppoeGetStatus();
			return true;
		}
		return false;
	}
	
	public boolean isWifiEnabled(){
		
		if(mWifiManager.isWifiEnabled())
		{
			return true;
		}
		return false;
	}
	
	
	///[2012-3-13add]show pppoe ip ,route ,mask ,dnss
	public void showPppoeStatus(){
		PPPOE_STA currentStatus = mPPPOE.PppoeGetStatus();
		if (currentStatus == PPPOE_STA.CONNECTED){
			String ip = mPPPOE.getIpaddr();
			String netmask = mPPPOE.getMask();
			String defaultWay = mPPPOE.getRoute();
			String dns1 = mPPPOE.getDns1();
			String dns2 = mPPPOE.getDns2();
			
			
			String txt = getString(R.string.pppoe_connect_success);
			String str = "\n\n";
			
			if (null != ip){
				str += getString(R.string.ip_address);
		    	str += ":  ";
				str += (ip + "\n");
			}
				
			if (null != netmask){
				str += getString(R.string.subnet_mask);
		    	str += ":  ";
				str += (netmask + "\n");
			}
				
			if (null != defaultWay){
				str += getString(R.string.default_geteway);
		    	str += ":  ";
				str += (defaultWay + "\n");
			}
				
			if (null != dns1){
				str += getString(R.string.first_dns);
		    	str += ":  ";
				str += (dns1 + "\n");
			}
			
			if (null != dns2){
				str += getString(R.string.second_dns);
		    	str += ":  ";
				str += (dns2 + "\n");
			}
			content_text.setText(txt + str);
		}
	}
	
	public void showWiFiState()
	{
		String txt = "";
		if(isWifiEnabled())
		{
			WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
			if(null != wifiInfo && null != wifiInfo.getSSID())
			{
				showCurrentWifiIP();
			}else{
				content_text.setText(getString(R.string.is_scanning));
			}
			
		}
	}
	
	
	public void showWireState()
	{
		if(isNetInterfaceAvailable("eth0"))
		{
			showWireIP();
		}else {
			content_text.setText(getString(R.string.eth_enable));
		}
	}
	public void showCurrentWifiIP() {
		
		String ip = null;
		String mask = null;
		String gateway = null;
		String dns1 = null;
		String dns2 = null;
		
		String [] ips = null;
		String [] gateways = null;
		
		WifiConfiguration config = getCurrentWifiCfg();
		
		if (config != null) {
            LinkProperties linkProperties = config.linkProperties;
            Iterator<LinkAddress> iterator = linkProperties.getLinkAddresses().iterator();
            if (iterator.hasNext()) {
                LinkAddress linkAddress = iterator.next();
                String tmp = linkAddress.getAddress().getHostAddress();
                
                if (SettingTools.matchIP(tmp)){
                	ip = tmp;
                	ips = SettingTools.resolutionIP(ip);
                }
            }
            
            //route
            for (RouteInfo route : linkProperties.getRoutes()) {
                if (route.isDefaultRoute()) {
                	String tmp = route.getGateway().getHostAddress();
                	
                	if (SettingTools.matchIP(tmp)){
                		gateway = tmp;
                		gateways = SettingTools.resolutionIP(gateway);
                	}
                    break;
                }
            }

            //dns
            Iterator<InetAddress> dnsIterator = linkProperties.getDnses().iterator();
            if (dnsIterator.hasNext()) {
            	String tmp = dnsIterator.next().getHostAddress();
            	
            	if (SettingTools.matchIP(tmp)){
            		dns1 = tmp;
            	}
            }
            if (dnsIterator.hasNext()) {
            	String tmp = dnsIterator.next().getHostAddress();
            	
            	if (SettingTools.matchIP(tmp)){
            		dns2= tmp;
            	}
            }
            
           // Log.e(TAG, "###===>MAC="+config.);
            
            if (null != ips && null != gateways){
            	if (ips[0].equals(gateways[0])) {
            		mask = "255";
            	}else{
            		mask = "0";
            	}
            	if (ips[1].equals(gateways[1])) {
            		mask += ".255";
            	}else{
            		mask += ".0";
            	}
            	if (ips[2].equals(gateways[2])) {
            		mask += ".255";
            	}else{
            		mask += ".0";
            	}
            	
            	if (ips[3].equals(gateways[3])) {
            		mask += ".255";
            	}else{
            		mask += ".0";
            	}
            }
            
            String str = "\n\n" ;
            
           
            if (null != ip){
            	str += getString(R.string.ip_address);
            	str += ":  ";
            	str += (ip + "\n");
            }
            if (null != mask){
            	str += getString(R.string.subnet_mask);
            	str += ":  ";
            	str += (mask + "\n");
            }
            if (null != gateway){
            	str += getString(R.string.default_geteway);
            	str += ":  ";
            	str += (gateway + "\n");
            }
            if (null != dns1){
            	str += getString(R.string.first_dns);
            	str += ":  ";
            	str += (dns1 + "\n");
            }
            if (null != dns2){
            	str += getString(R.string.second_dns);
            	str += ":  ";
            	str += (dns2 + "\n") ;
            }
            
            String macAddr = null;
            WifiInfo currWifiInfo = mWifiManager.getConnectionInfo();
            if (null != currWifiInfo){
            	macAddr = currWifiInfo.getMacAddress();
            }
            
            if (null != macAddr){
				str += "mac:  ";
				str += (macAddr+"\n");
			}
            WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
            content_text.setText(getString(R.string.connect_state)
					+ wifiInfo.getSSID() + str);
        }
	}
	
	public WifiConfiguration getCurrentWifiCfg(){
		//WifiConfiguration config = null;
		if (mWifiManager.isWifiEnabled()){
			WifiInfo currWifiInfo = mWifiManager.getConnectionInfo();
			
			String ssid = "\"" + currWifiInfo.getSSID()+"\"";
			//List<WifiConfiguration> configs = mNetSettingActivity.mWifiManager.getConfiguredNetworks();
			for(WifiConfiguration tmp : mWifiManager.getConfiguredNetworks()){
				if (ssid.equals(tmp.SSID)){
					return tmp;
				}
			}
		}
		return null;
	}
	
	public boolean isNetInterfaceAvailable(String netif) {
		String netInterfaceStatusFile = "/sys/class/net/" + netif + "/carrier";
		boolean bStatus = isStatusAvailable(netInterfaceStatusFile);
		return bStatus;
	}
	
	private boolean isStatusAvailable(String statusFile) {
		char st = readStatus(statusFile);
		if (st == '1') {
			return true;
		}
		return false;
	}
	
	private synchronized char readStatus(String filePath) {
		File file = new File(filePath);
		int tempChar = 0;
		if (file.exists()) {
			Reader reader = null;
			try {
				reader = new InputStreamReader(new FileInputStream(file));
				try {
					tempChar = reader.read();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return (char) tempChar;
	}
	
	public void showWireIP() {

		String str = "\n\n";
		if (mEthernetManager.isConfigured()) {
			EthernetDevInfo mEthInfo = mEthernetManager
					.getSavedConfig();
			String ifName = mEthInfo.getIfName();
			String macAddr = mEthInfo.getMacAddress(ifName);
			System.out.println("ifName  " + ifName);
			System.out.println("mac="+mEthInfo.getMacAddress(ifName));

			String ip = mEthInfo.getIpAddress();
			String netmask = mEthInfo.getNetMask();
			String defaultWay = mEthInfo.getRouteAddr();
			String firstdns = mEthInfo.getDnsAddr();
			String secDns = mEthInfo.getDns2Addr(); //haier

			if (null != ip){
				str += getString(R.string.ip_address);
            	str += ":  ";
				str += (ip + "\n");
			}
				
			if (null != netmask){
				str += getString(R.string.subnet_mask);
            	str += ":  ";
				str += (netmask + "\n");
			}
				
			if (null != defaultWay){
				str += getString(R.string.default_geteway);
            	str += ":  ";
				str += (defaultWay + "\n");
			}
			
			if (null != firstdns){
				str += getString(R.string.first_dns);
            	str += ":  ";
				str += (firstdns + "\n");
			}
			
			/* haier*/
			if (null != secDns){
				str += getString(R.string.second_dns);
            	str += ":  ";
				str += (secDns + "\n");
			}
			
			
			
			if (null != macAddr){
				str += "mac:  ";
				str += (macAddr+"\n");
			}
		}
		
		content_text.setText(getString(R.string.eth_able) + str);
	}

}

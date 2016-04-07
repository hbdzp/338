package com.mstar.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Command {
	private static final String CHMOD = "/system/bin/busybox chmod 777";
	private static final String CP = "/system/bin/busybox cp -f";
	private static final String RM = "/system/bin/busybox rm -rf";
	private static final String SYNC = "/system/bin/busybox sync";

	public static void chmod(String path)
	{
		String cmd = CHMOD + " " + path;

		String s;
		Process process;
		try {
			process = Runtime.getRuntime().exec(cmd);

			BufferedReader buff = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			while ((s = buff.readLine()) != null) {
				System.out.println(s);
				process.waitFor();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void rm(String path)
	{
		String cmd = RM + " " + "-r" + " " + path;

		String s;
		Process process;
		try {
			process = Runtime.getRuntime().exec(cmd);

			BufferedReader buff = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			while ((s = buff.readLine()) != null) {
				System.out.println(s);
				process.waitFor();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cp(String uri, String path)
	{
//		File conf_path = new File(path);
//		if (!conf_path.exists()) {
//			conf_path.mkdir();
//			chmod(path);
//		}
		String cmd = CP + " " + uri + " " + path;
		String s;
		Process process;
		try {
			process = Runtime.getRuntime().exec(cmd);
			BufferedReader buff = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			while ((s = buff.readLine()) != null) {
				System.out.println(s);
				process.waitFor();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sync()
	{
		String cmd = SYNC;
		String s;
		Process process;
		try {
			process = Runtime.getRuntime().exec(cmd);

			BufferedReader buff = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			while ((s = buff.readLine()) != null) {
				System.out.println(s);
				process.waitFor();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

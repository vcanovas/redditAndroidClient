package com.obs.redditclient.utils;
/**
 * 
 * @author Pedro Cánovas
 * @mail wcanovas@gmail.com
 * 
 */
public class Log {
	private final static String TAG = "REDDITCLIENT";
	public static void e(String m){
		escribir(m);
	}
	public static void e(String m1, String m2){
		escribir(m1, m2);
	}
	public static void escribir(String message){
		android.util.Log.d(TAG, message);
	}	
	public static void escribir(String metodo, String message){
		android.util.Log.d(TAG, "metodo: "+metodo+" - "+message);
	}

}

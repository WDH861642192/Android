package com.wdh.threes.Application;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import android.app.Application;

public class BaseApplication extends Application {
	private Map Cookies = null;
	private String Cookie = null;
	private ExecutorService threadpool;
	private static BaseApplication instance;

	public BaseApplication() {

	}

	public static BaseApplication getInstance() {
		if (instance == null) {
			instance = new BaseApplication();
		}
		return instance;

	}

	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		threadpool =Executors.newFixedThreadPool(5);	
	}

	// 存入cookie
	public void setCookies(Map cookies) {
		Cookies = cookies;
	}

	// 获取cookie
	public Map getCookies() {
		return Cookies;
	}

	public void setCookie(String cookie) {
		Cookie = cookie;
	}

	// 获取cookie
	public String getCookie() {
		return Cookie;
	}
	public ExecutorService get_threadpool()
	{
		return threadpool;
	}
	
}

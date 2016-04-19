package com.wdh.threes.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wdh.threes.R;
import com.wdh.threes.view.ChartView;

import HttpHelp.HttpClientJSON;
import HttpHelp.SimpleHttpClient;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class MainAcitivity extends Activity {
	// 农业项目
	String url = "http://www.airbob.org/chart_data.php";
	String urlcheck = "http://192.168.1.101/check.php";
	// 可见光项目
	String urllogin = "http://xml.iceash.com/login.php";
	String urlreg = "http://xml.iceash.com/reg.php";
	String urldemo = "http://www.baidu.com";
	private int msgStatue = 233;
	private Button mButton;
	LinearLayout mChartLinearout;
	ChartView myView;
	EditText mEditText;
	Spinner mSpinner;
	String kind, device, position;
	String[] times = {"","","","","","",""};
	String[] datas = {"","","","","","",""};
	private HashMap<String, String> map = new HashMap<>();
	private JSONObject jsonObject = new JSONObject();
	private Context context;
	String[] titles = {  "空气温度", "空气湿度","光照强度", "土壤温度", "土壤湿度" };
	String[] lig = { "", "200", "400", "600", "800", "1000" };
	String[] hum = { "", "20", "40", "60", "80", "100" };
	String[] tem = { "", "8", "16", "24", "32", "40" };
	ArrayList<String[]> arr = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postactivity);
		// mChartLinearout = (LinearLayout) findViewById(R.id.myCharLinearout);
		// LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
		//
		// LinearLayout.LayoutParams.MATCH_PARENT,
		//
		// LinearLayout.LayoutParams.MATCH_PARENT
		//
		// );
		// p.gravity=Gravity.CENTER_VERTICAL;
		// ChartView myView = new ChartView(this);
		context = this;
//		String cc="31.6";
//		String[] ccc=new String[10];
//		ccc=cc.split(".");
		mSpinner = (Spinner) findViewById(R.id.mySpinner);
		mEditText = (EditText) findViewById(R.id.mimaedit);
		mButton = (Button) findViewById(R.id.submit);
		mButton.setFocusable(true);
		
		arr.add(tem);
		arr.add(hum);
		arr.add(lig);
		arr.add(tem);
		arr.add(hum);
		// myView.SetInfo(new String[] { "7-11", "7-12", "7-13", "7-14", "7-15",
		// "7-16", "7-17" }, // X轴刻度
		// new String[] { "", "50", "100", "150", "200", "250" }, // Y轴刻度
		// new String[] { "15", "23", "10", "36", "45", "40", "12" }, // 数据
		// "图标的标题");
		// mSpinner.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// // TODO Auto-generated method stub
		// kind =
		// context.getResources().getStringArray(R.array.spingarr)[position];
		// }
		//
		// });
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				device = mEditText.getText().toString();
				position = String.valueOf(mSpinner.getSelectedItemPosition()+2);
				// if(device.equals("")||kind.equals("")){
				map.put("data", "10000001");
				map.put("data1", position);
				// map.put("data3", "bobair");
				new HttpThread().start();
				// }
			}
		});

		// mChartLinearout.addView(myView,p);

		// mButton=(Button) findViewById(R.id.netbtn);
		// try {
		// jsonObject.put("data", "device1");
		// jsonObject.put("data1", "0");
		// jsonObject.put("data2", "552b19bd26d698fa7bfe10eafbc906d5");
		// jsonObject.put("data3", "bobair");
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// map.put("data", "device1");
		// map.put("data1", "0");
		// map.put("data2", "552b19bd26d698fa7bfe10eafbc906d5");
		// map.put("data3", "bobair");
		// map.put("username", "bobair");
		// map.put("password", "Glj1234");

		// map.put("phone", "15240137524");
		// map.put("password", "123456");
		// map.put("password2", "123456");
		// mButton.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// new HttpThread().start();
		// }
		// });
	}

	private class HttpThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// super.run();
			try {
				// String res=HttpClientJSON.httpClientJSON.request(url,
				// jsonObject);
				String res = SimpleHttpClient.simplePost(url, map);
				Message message = Message.obtain();
				message.what = msgStatue;
				message.obj = res;
				mHandler.sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == msgStatue) {
				String jsonString = (String) msg.obj;
				try {
					JSONObject json = new JSONObject(jsonString);
					JSONArray arraytime = json.optJSONArray("time");
					JSONArray arraydata = json.optJSONArray("data");
					int size = arraytime.length();
					for (int i = 0; i < size; i++) {
						times[i] = arraytime.get(size-1-i).toString();
						datas[i] = arraydata.get(size-1-i).toString();
					}

					int pos = Integer.valueOf(position)-2;
					if (myView == null)
						myView = (ChartView) findViewById(R.id.myCHartView);

					myView.SetInfo(times, arr.get(pos), datas, titles[pos]);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	};
}

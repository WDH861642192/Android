package HttpHelp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.wdh.threes.Application.BaseApplication;

import android.graphics.drawable.Drawable;
import android.util.Log;


public class SimpleHttpClient {

	private static final String TAG = SimpleHttpClient.class.getName();
	static String x = "";
	/**
	 * 
	 * @param requestUrl
	 * @param params
	 * @return
	 * @throws IOException 
	 * @throws  
	 */
	public static String simpleRequest(String requestUrl, String params) throws IOException{
		try {
			return WebBrowser.webBrowser.requestAsString(requestUrl+"?"+params, null, "GET");
		} catch (HttpException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	static DefaultHttpClient httpclient;
	public static String simplePost(String url, Map<String,String> variables) throws IOException{
		try {
			return WebBrowser.webBrowser.requestAsString(url, variables, "POST");
		} catch (HttpException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(),e);
		}
	}
    
    public static String convertStreamToString(InputStream is) {
         BufferedReader reader = new BufferedReader(new InputStreamReader(is));
         StringBuilder sb = new StringBuilder();

         String line = null;
         try {
        	 int i = 0;
              while ((line = reader.readLine()) != null) {
            	  if(i>0)sb.append("\n");
            	  i++;
                   sb.append(line);
              }
         } catch (IOException e) {
              e.printStackTrace();
         } finally {
              try {
                   is.close();
              } catch (IOException e) {
                   e.printStackTrace();
              }
         }

         return sb.toString();
    }
    
	public static void uploadFile(String actionUrl,String typeNameStr, File myFile) throws IOException {
		if (!myFile.exists()) {
			Log.i(TAG, "照片文件不存在！");
			return;
		} else {
			// 存在则读数据
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(myFile);
				uploadFile(actionUrl, typeNameStr, inputStream);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e.getMessage(),e);
			}finally{
				try {
					if(inputStream != null)
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void uploadFile(String actionUrl,String fileNameStr, InputStream inputStream) throws IOException {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传�?的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file\";filename=\"" + fileNameStr + "\"" + end);
			ds.writeBytes("Content-Type: image/gif" + end);
			ds.writeBytes(end);

				/* 取得文件的FileInputStream */

				/* 设置每次写入1024bytes */
				int bufferSize = 1024;
				byte[] buffer = new byte[bufferSize];

				int length = -1;
				/* 从文件读取数据至缓冲�?*/
				while ((length = inputStream.read(buffer)) != -1) {
					/* 将资料写入DataOutputStream�?*/
					ds.write(buffer, 0, length);
				}
				ds.writeBytes(end);
				ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

				ds.flush();
				Log.i(TAG, "发送完成!");
				/* 取得Response内容 */
				InputStream is = con.getInputStream();
				int ch;
				StringBuffer b = new StringBuffer();
				while ((ch = is.read()) != -1) {
					b.append((char) ch);
				}
				System.out.println(b);
				ds.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public static String uploadFile1(String actionUrl,String fileNameStr, InputStream inputStream) throws IOException {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			con.setRequestProperty( "Cookie",  "JSESSIONID="+JSESSIONID);
			/* 设置DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file\";filename=\"" + fileNameStr + "\"" + end);
			ds.writeBytes("Content-Type: image/gif" + end);
			ds.writeBytes(end);

				/* 取得文件的FileInputStream */

				/* 设置每次写入1024bytes */
				int bufferSize = 1024;
				byte[] buffer = new byte[bufferSize];

				int length = -1;
				/* 从文件读取数据至缓冲区 */
				while ((length = inputStream.read(buffer)) != -1) {
					/* 将资料写入DataOutputStream中 */
					ds.write(buffer, 0, length);
				}
				ds.writeBytes(end);
				ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

				ds.flush();
				Log.i(TAG, "发送完成!");
				/* 取得Response内容 */
				InputStream is = con.getInputStream();
//				int ch;
//				StringBuffer b = new StringBuffer();
//				while ((ch = is.read()) != -1) {
//					b.append((char) ch);
//				}
//				ds.close();
				InputStreamReader isr = new InputStreamReader(is, "utf-8");
	            BufferedReader br = new BufferedReader(isr);
	            // 上传返回值
	            String sl;
	            String result="";
	            while((sl = br.readLine()) != null)
	            	result = result+sl; 
	            br.close(); 
	            is.close(); 
	            return result; 

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "网络出错！";
		}
	}
	
	public static String JSESSIONID = null;
	public static InputStream simplePost2(String url, Map<String,String> variables) throws IOException{
	     ArrayList<BasicNameValuePair> pairs;
	     HttpPost httppost;
	     InputStream content = null;
	     String returnConnection = null;
	     
	     try{
	    	 //http://topic.csdn.net/u/20110420/13/19320579-0ba1-4c7e-898a-1336bc81764e.html
	         if(httpclient == null){
		    	 httpclient = WebBrowser.webBrowser.httpClient; 
	         }
	         //httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 4000);
	         
	         httppost = new HttpPost(url);
	         pairs = new ArrayList();
	         if(variables != null){
	              Set keys = variables.keySet();
	              for(Iterator i = keys.iterator(); i.hasNext();) {
	                   String key = (String)i.next();
	                   pairs.add(new BasicNameValuePair(key, variables.get(key)));
	              }
	         }
            UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs, "UTF-8");
            
            /** 将POST数据放入http请求*/
            httppost.setEntity(p_entity);
        
            /** 发出实际的HTTP POST请求 */
            HttpResponse response = httpclient.execute(httppost);
            
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
           	 HttpEntity entity = response.getEntity();
           	 content = entity.getContent();
                CookieStore mCookieStore = httpclient.getCookieStore();
                List<Cookie> cookies = mCookieStore.getCookies();
                for (int i = 0; i < cookies.size(); i++) {
               	//这里是读取Cookie['JSPSESSID']的值存在静态变量中，保证每次都是同一个值
                    if ("JSESSIONID".equals(cookies.get(i).getName())) {
                   	 JSESSIONID = cookies.get(i).getValue();
                        break;
                    }
                }
            }
            Log.d("HttpPostConnection",">>>>>>>>>>>>>>> " + returnConnection);

        } catch (MalformedURLException e) {
       	 throw new RuntimeException(e.getMessage(),e);
        } catch (UnsupportedEncodingException e) {
       	 throw new RuntimeException(e.getMessage(),e);
		} catch (IllegalStateException e) {
			throw new RuntimeException(e.getMessage(),e);
		} catch(HttpHostConnectException e){//服务器端未启动
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			return content;
		}
	}

	public static Drawable DownloadPic(String url) throws IOException{
		
		try {
			Callable c1=new DownloadPicTask(url);
			ExecutorService pool = BaseApplication.getInstance().get_threadpool();
			Future f1=pool.submit(c1);
			return (Drawable) f1.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	static class DownloadPicTask implements Callable<Drawable>{  //simpleRequest 函数的线程类。 

	    String requestUrl;
	    public DownloadPicTask(String requestUrl){   
	        this.requestUrl=requestUrl;
	    }  
	      
	    public Drawable call(){  
	        try {
				return DownloadPicreal(requestUrl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	        return null;
	    }  
	}
	
	static Drawable DownloadPicreal(String url) throws Exception{
		Drawable d = null;
		InputStream in = null;
		try{
			in = simplePost2(url, null);
	        System.out.println("流量："+in.available());  
	        d = Drawable.createFromStream(in, "src");        
	    }catch (Exception e) {
	    	System.out.println("图片下载时出错："+e.toString());
	    	e.printStackTrace();
	    }finally{
	    	if(in!=null)
	    		in.close();	    	
	    }
        return d;    
	} 

	
} 

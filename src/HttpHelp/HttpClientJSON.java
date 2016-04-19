package HttpHelp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpClientJSON {
  public static HttpClientJSON httpClientJSON=new HttpClientJSON();
  HttpPost httpPost;
  HttpResponse httpResponse;
  String res;
 public String request(String url,JSONObject jsonObject) throws UnsupportedEncodingException{
	httpPost=new HttpPost(url);
	httpPost.setEntity(new StringEntity(jsonObject.toString()));
	try {
		 httpResponse=new DefaultHttpClient().execute(httpPost);
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		 res=EntityUtils.toString(httpResponse.getEntity());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return res;
	 
 } 
}

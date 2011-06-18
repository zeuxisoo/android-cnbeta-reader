package com.zeuxislo.cnbeta.helper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class WebHelper {
	
	public static String fetch(String url, String chartset, String referer) throws ClientProtocolException, IOException {
		return fetch(url, chartset, referer, null);
	}
	
	public static Bitmap fetchImage(String url) throws IOException {
		return fetchImage(url, null);
	}

	public static Bitmap fetchImage(String url, String referer) throws IOException {
		return fetchImage(url, referer, null);
	}

	public static String fetch(String url, String chartset, String referer, CookieStore cookieStore) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept-Encoding", "gzip,deflate");
		httpGet.addHeader("Accept", "*/*");
	    httpGet.addHeader("User-Agent", "Mozilla/5.0 AppleWebKit/533.1 (KHTML, like Gecko)");
		
	    if (referer != null && referer.equals("") == false) {
			httpGet.addHeader("Referer", referer);
	    }
	    
	    BasicHttpParams basicHttpParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(basicHttpParams, 50000);
	    HttpConnectionParams.setSoTimeout(basicHttpParams, 300000);
	    DefaultHttpClient defaultHttpClient = new DefaultHttpClient(basicHttpParams);
	    
	    if (cookieStore != null) {
	    	defaultHttpClient.setCookieStore(cookieStore);
	    }
	    
	    HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
	    
	    Object object = httpResponse.getEntity().getContent();
	    Header header = httpResponse.getFirstHeader("Content-Encoding");
	    
	    if (header != null && header.getValue().equalsIgnoreCase("gzip")) {
	    	object = new GZIPInputStream((InputStream)object);
	    }
	    
	    return UtilityHelper.convertStreamToString((InputStream)object, chartset);
	}
	
	public static Bitmap fetchImage(String url, String referer, CookieStore cookieStore) throws IOException {
		URL _url = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) _url.openConnection();
		connection.setConnectTimeout(50000);
		connection.setReadTimeout(300000);
		connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
		connection.setRequestProperty("Accept", "*/*");
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 AppleWebKit/533.1 (KHTML, like Gecko)");		
		connection.setDoInput(true);
	    
		if (referer != null && referer.equals("") == false) {
	    	connection.setRequestProperty("Referer", referer);
	    }
	    
	    if (cookieStore != null) {
	    	String cookieString = cookieStoreToString(cookieStore);
	    	connection.setRequestProperty("Cookie", cookieString);
	    }
	    
		connection.connect();
		
		InputStream input = connection.getInputStream();
		
		String encoding = connection.getHeaderField("Content-Encoding");
	    if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
	    	input = new GZIPInputStream((InputStream)input);
	    }
		
		Bitmap bitmap = BitmapFactory.decodeStream(input);
        
        return bitmap;
	}
	
	private static String cookieStoreToString(CookieStore cookieStrore) {
		StringBuilder cookieString = new StringBuilder();
		List<Cookie> list = cookieStrore.getCookies();
		
		for(int i=0; i<list.size(); i++) {
			cookieString.append(((Cookie)list.get(i)).getName());
			cookieString.append("=");
			cookieString.append(((Cookie)list.get(i)).getValue());
			cookieString.append(";");
		}
		
		return cookieString.toString();
	}
	
}

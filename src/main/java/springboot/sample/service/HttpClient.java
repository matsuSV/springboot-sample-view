package springboot.sample.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import springboot.sample.utility.PropertyUtil;

public class HttpClient {

	private static Logger log = Logger.getLogger(HttpClient.class);

	public static final int GET_REQUEST_METHOD = 0;

	public boolean request(int     method,
						   String  clazz,
						   String  url,
						   String  user,
						   String  password,
						   boolean db2v10driver) {

		boolean result = false;
		try {

			PropertyUtil.init();

			switch(method){
			case 0:
				result = requestGet(clazz,url,user,password,db2v10driver);
				break;
			}
		} catch (IOException e) {
			log.error(e.fillInStackTrace());
			result = false;
		}
		return result;
	}

	/**
	 * HTTPのGETメソッドをリクエストする処理
	 *
	 * @param clazz         :
	 * @param url           :
	 * @param user          :
	 * @param password      :
	 * @param db2v10driver  :
	 * @return
	 * @throws IOException
	 */
	private boolean requestGet(String  clazz,
							   String  url,
							   String  user,
							   String  password,
							   boolean db2v10driver) throws IOException{

		String sendData = "clazz="    + clazz    + "&" +
						  "url="      + url      + "&" +
						  "user="     + user     + "&" +
						  "password=" + password;

		// URLオブジェクトの生成
		URL connectUrl = null;
		if( clazz.contains("oracle") ) {
			connectUrl = new URL(PropertyUtil.getOracle11gUrl()+"?"+sendData);
		} else if( db2v10driver ) {
			connectUrl = new URL(PropertyUtil.getDb2v10Url()+"?"+sendData);
		} else {
			connectUrl = new URL(PropertyUtil.getDb2v8Url()+"?"+sendData);
		}

		// 対象URLへ接続
		HttpURLConnection urlconn = (HttpURLConnection) connectUrl.openConnection();
		urlconn.setRequestMethod("GET");
		urlconn.connect();
		headersLog(urlconn);

		// body部の値取得
		boolean body = getBooleanBody(urlconn);
		urlconn.disconnect();

		return body;
	}

	/**
	 * HTTPレスポンスのbody情報を判定し真偽値を返す。
	 *
	 * @param urlconn : URLオブジェクト
	 * @return body部の値として取得した真偽値
	 */
	private boolean getBooleanBody(HttpURLConnection urlconn) {
		// body部の取得
		BufferedReader br = null;
		boolean value     = false;
		String body       = "";
		try{
			br   = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			while( null != (body = br.readLine()) ) {
				if( body.equals("true") ) {
					value = true;
				}
			}
		} catch (IOException e) {
			log.error(e.fillInStackTrace());
		} finally {
			if( null != br ) {
				try {
					br.close();
				} catch (IOException e) {
					log.error(e.fillInStackTrace());
				}
			}
		}
		return value;
	}

	/**
	 * HTTPレスポンスのヘッダー情報をログ出力する。
	 *
	 * @param urlconn : URLオブジェクト
	 */
	private void headersLog(HttpURLConnection urlconn) {
		Map<String, List<String>> headerFields = urlconn.getHeaderFields();
		log.info(" ----- Headers ---- ");
		for( Entry<String, List<String>> hf : headerFields.entrySet()) {
			String key = hf.getKey();
			List<String> value = hf.getValue();
			if( null != key ){
				log.info(key + " : " + value);
			} else {
				log.info(value);
			}
		}
	}
}

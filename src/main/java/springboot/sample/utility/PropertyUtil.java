package springboot.sample.utility;

import java.io.IOException;
import java.util.Properties;

public final class PropertyUtil {

	private static String oracle11gUrl;
	private static String db2v8Url;
	private static String db2v10Url;
	private static Properties properties = null;

	/**
	 * サービスプロセスへアクセスするためのURLをプロパティファイルから取得する処理
	 *
	 * @throws IOException
	 */
	public static void init() throws IOException {
		if( null == properties ) {
			properties = new Properties();
			properties.load(PropertyUtil.class.getResourceAsStream("/connectiontester.properties"));
			oracle11gUrl = properties.getProperty("url.oracle11g");
			db2v8Url     = properties.getProperty("url.db2v8");
			db2v10Url    = properties.getProperty("url.db2v10");
		}
	}

	public static String getOracle11gUrl() {
		return oracle11gUrl;
	}

	public static String getDb2v8Url() {
		return db2v8Url;
	}

	public static String getDb2v10Url() {
		return db2v10Url;
	}
}

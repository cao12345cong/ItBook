/*
 * 文件名：ItBookApp.java
 * 修改人：王鹏
 * 修改时间：上午11:48:03
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.main;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.app.Application;
import android.util.Log;

import com.xtx.itbook.ui.R;
import com.xtx.itbook.util.ApkUtil;
import com.xtx.itbook.util.Const;
import com.xtx.itbook.util.ConstantInterface;

/**
 * ItBook application.
 * @author Abelart.
 */
public class ItBookApp extends Application
{
    private static final String TAG = ItBookApp.class.getName();

    private static final String APP_TAG = "android_mi";

    private HttpClient httpClient = null;

    private static ItBookApp itBookApp = null;
    
    /**本应用程序设置的语言.*/
    private String language = null ; 
    
    public static ItBookApp instance()
    {
        return itBookApp;
    }

    public void onCreate()
    {
        super.onCreate();

        itBookApp = this;
       // httpClient = this.createHttpClient();

        initAFinal();
        Log.i(TAG, "ItBookApp onCreate()");
    }
    
    /**
     * 若没设置语言则 根据系统语言
     * 
     */
    public String getLanguage()
    {
    	if(language == null)
    	{
    		return ApkUtil.getSysLanguage();
    	}
    	return language;
    }

	public void setLanguage(String language) {
		this.language = language;
	}

	/**
     * 支持http https协议.
     * @return.
     */
	@Deprecated
    private HttpClient createHttpClient()
    {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setHttpElementCharset(params, HTTP.UTF_8);
        HttpProtocolParams.setUseExpectContinue(params, true);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory
                .getSocketFactory(), 443));

        ClientConnectionManager clientConnectionManager = new ThreadSafeClientConnManager(
                params, schemeRegistry);
        return new DefaultHttpClient(clientConnectionManager, params);
    }

    /**
     * 关闭连接 释放资源
     * .
     */
    private void shoutDownHttpConnect()
    {
        if (httpClient != null && httpClient.getConnectionManager() != null)
        {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public HttpClient getHttpClient()
    {
        return httpClient;
    }

    /** 
     * 见父类.
     * 当内存过低的时候触发此事件.
     * @see android.app.Application#onLowMemory().
     */
    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        shoutDownHttpConnect();
    }

    /** 
     * 见父类.
     * @see android.app.Application#onTerminate().
     */
    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }

    /**
     * 初始化aFianl 框架相关信息.
     * .
     */
    public void initAFinal()
    {
        initFinalBitMap();
        initFinalDb();
    }

    /**初始化aFinal 框架中的 bitMap.*/
    public void initFinalBitMap()
    {
        FinalBitmap finalBitmap = FinalBitmap.create(this);
//        finalBitmap.configDiskCachePath(Const.IT_BOOK_IMG_CACHE);
//        finalBitmap.configDiskCacheSize(Const.IT_BOOK_DISK_CACHE);
//        finalBitmap.configMemoryCacheSize(Const.IT_BOOK_MEMORY_CACHE);
//        finalBitmap.configBitmapLoadThreadSize(16);
        finalBitmap.configLoadingImage(R.drawable.logo_email);
        finalBitmap.configLoadfailImage(R.drawable.logo_facebook);
    }

    /**初始化afinal框架中的 db.*/
    public void initFinalDb()
    {
        FinalDb finalDb = FinalDb.create(this,ConstantInterface.DB_NAME,true);
       
    }


}

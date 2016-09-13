/*
 * 文件名：NetUtil.java
 * 描述：网络连接
 * 修改人：王鹏
 * 修改时间：上午10:38:53
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import net.tsz.afinal.FinalHttp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.xtx.itbook.main.ItBookApp;
import com.xtx.itbook.util.LogUtil;

/**
 * netutil.
 * @author Abelart.
 */
public class NetUtil
{
    private static final String TAG = "NetUtil";

    /**
     * 网络连接是否可用
     */
    public static boolean isConnnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager)
        {
            NetworkInfo networkInfo[] = connectivityManager.getAllNetworkInfo();

            if (null != networkInfo)
            {
                for (NetworkInfo info : networkInfo)
                {
                    if (info.getState() == NetworkInfo.State.CONNECTED)
                    {
                        LogUtil.i(TAG, "net is ok");
                        return true;
                    }
                }
            }
        }
        toast((Activity) context, "网络不可用");
        return false;
    }

    /**
     * 获取mac地址.
     * @param activity
     * @return.
     */
    public static String macAddress(Activity activity)
    {
        WifiManager wifi = (WifiManager) activity
                .getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();
        
        return info.getMacAddress();
    }

    /**
     * 网络可用状态下，通过post方式向server端发送请求，并返回响应数据
     * 
     * @param url
     *            请求网址
     * @param valuePairs
     *            参数信息
     * @param context
     *            上下文
     * @return 响应数据
     */
    public static JSONObject getResponseForPost(String url,
            List<NameValuePair> valuePairs, Context context)
    {
        if (isConnnected(context))
        {
            return getResponseForPost(url, valuePairs);
        }
        return null;
    }

    /**
     * 通过post方式向服务器发送请求，并返回响应数据
     * 
     * @param strUrl
     *            请求网址
     * @param nameValuePairs
     *            参数信息
     * @return 响应数据
     */
    public static JSONObject getResponseForPost(String url,
            List<NameValuePair> nameValuePairs)
    {
        if (null == url || "" == url)
        {
            return null;
        }
        HttpPost request = new HttpPost(url);
        try
        {
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs,
                    HTTP.UTF_8));
            return getRespose(request);
        }
        catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * 网络可用状态下，通过get方式向server端发送请求，并返回响应数据
     * 
     * @param strUrl
     *            请求网址
     * @param context
     *            上下文
     * @return 响应数据
     */
    public static JSONObject getResponseForGet(String strUrl,
            HttpParams httpParams, Context context)
    {
        if (isConnnected(context))
        {
            return getResponseForGet(strUrl, httpParams);
        }
        return null;
    }

    /**
     * 通过Get方式处理请求，并返回相应数据
     * 
     * @param strUrl
     *            请求网址
     * @param params 请求的参数封装  允许传入 null
     * @return 响应的JSON数据
     */
    public static JSONObject getResponseForGet(String strUrl, HttpParams params)
    {
        if (null == strUrl || "" == strUrl)
        {
            return null;
        }

        HttpGet httpRequest = new HttpGet(strUrl);

        if (null != params)
        {
            httpRequest.setParams(params);
        }

        return getRespose(httpRequest);
    }

    /**
     * 响应客户端请求
     * 
     * @param request
     *            客户端请求get/post
     * @return 响应数据
     */
    public static JSONObject getRespose(HttpUriRequest request)
    {
        try
        {

            HttpResponse httpResponse = ItBookApp.instance().getHttpClient()
                    .execute(request);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode)
            {
                String result = EntityUtils.toString(httpResponse.getEntity(),
                        "UTF-8");

                Log.i(TAG, "results= " + result);
                return new JSONObject(result);
            }
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /***
     * 获取finalhttp.
     * @return.
     */
    public static FinalHttp getHttp()
    {
        FinalHttp finalHttp = new FinalHttp();

        finalHttp.configCharset("UTF-8");
        finalHttp.configTimeout(5 * 1000); // 5 秒超时.
        finalHttp.configRequestExecutionRetryCount(0); 
        return finalHttp;
    }
    
    /**获取httpentity.*/
    public static HttpEntity getEntity(JSONObject jsonObject)
    {
        
        try
        {
            return new StringEntity(jsonObject.toString());
        }
        catch (UnsupportedEncodingException e)
        {  
            Log.i(TAG, "数据编码转化异常.");
            e.printStackTrace();
        }
        return null;
    }

    /** toast显示情况. */
    public static void toast(final Activity activity, final String msg)
    {
        activity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }
    
    /**
     * 请求参数是JSONObject
     */
    private static HttpClient httpClient;

    public static String connServerForResult(String strUrl, JSONObject jsObject) throws ParseException, IOException
    {

        HttpPost httppost = new HttpPost(strUrl);

            HttpEntity requestEntity = new StringEntity(jsObject.toString(),
                    HTTP.UTF_8);
            httppost.setEntity(requestEntity);


        String strResult = "";
            httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httppost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                strResult = EntityUtils.toString(httpResponse.getEntity());
            }

        return strResult;
    }

    public static String connServerForResult(String strUrl) throws ParseException, IOException
    {


        System.out.println(strUrl);

        HttpPost httpRequest = new HttpPost(strUrl);

        String strResult = "";
            httpClient = new DefaultHttpClient();

            HttpResponse httpResponse = httpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                strResult = EntityUtils.toString(httpResponse.getEntity());
            }

        return strResult;
    }


}
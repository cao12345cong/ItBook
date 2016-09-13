/*
 * 文件名：SharedPreferenceSevice.java
 * 描述：TODO
 * 修改人：王鹏
 * 修改时间：下午5:47:53
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.util.Const;

/**
 * 
 * 统一管理所有 xml存储.
 * @author Abelart.
 */
public class SharedPreferenceSevice
{
    private Context context;
    
    private static final String ITBOOK_SHARE="itbook_share";
    /**
     * 构造方法 @param context.
     */
    public SharedPreferenceSevice(Context context)
    {
        super();
        this.context = context;
    }

    /**
     * Product
     * 主要存储 产品模块请求记录.
     * 保存联网请求的记录.
     * @param language
     * @param requestMsg
     */
    public void setProductResponse(String language,String requestMsg)
    {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getResources().getString(R.string.directory),
                Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(language, requestMsg);
        editor.commit();
    }

    /**
     * Product
     * 根据语言获取其存储在xml中的 请求返回数据 [json字符串.]
     * @param language
     * @return
     */
    public String getProductResponse(String language)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getResources().getString(R.string.directory),
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(language, Const.DEFAULT);
    }

    /**
     * Product
     * 获取xml中 json字符串中的 time.
     * 如果是第一次请求 则直接返回 time为0.
     * @return
     */
    public long getProductResponseTime(String language)
    {
        if (getProductResponse(language).equals(Const.DEFAULT))
        {
            return 0;
        }
        JSONObject jsonObject = JSON.parseObject(getProductResponse(language));
        return jsonObject.getLongValue("time");
    }

    /**
     * 获取产品列表 请求所返回的状态记录.
     * @return state   0|1|2|3 
     */
    public int getProductResponseState(String language)
    {
        String responseJson = getProductResponse(language);
        if (responseJson.equals(Const.DEFAULT))
        {
            return 0;
        }
        JSONObject jsonObject = JSON.parseObject(getProductResponse(language));
        return jsonObject.getIntValue("state");
    }

    
    /****************************it术语*******************************************/
    /**
     * 产品术语
     * 记录xml中 json字符串中的 time.
     * @param language
     * @param responseMsg
     */
    public void setTerminologyResponseTime(String language,long time)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                ITBOOK_SHARE,
                Context.MODE_PRIVATE);
        String key=null;
        if("cn".equalsIgnoreCase(language)){
            key=context.getResources().getString(R.string.terminology_last_update_time_cn);
        }else if("en".equalsIgnoreCase(language)){
            key=context.getResources().getString(R.string.terminology_last_update_time_en);
        }
        Editor editor = sharedPreferences.edit();
        editor.putLong(key,time);
        editor.commit();
    } 
   
    
    /**
     * 产品术语
     * 获取xml中 json字符串中的 time.
     * 如果是第一次请求 则直接返回 time为0.
     * @return 
     */
    public long getTerminologyResponseTime(String language)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                ITBOOK_SHARE,
                Context.MODE_PRIVATE);
        String key=null;
        if("cn".equalsIgnoreCase(language)){
            key=context.getResources().getString(R.string.terminology_last_update_time_cn);
        }else if("en".equalsIgnoreCase(language)){
            key=context.getResources().getString(R.string.terminology_last_update_time_en);
        }
        return sharedPreferences.getLong(key, 0L);
    }
    
    /**
     * 产品术语
     * 设置最近一次访问服务器的日期
     * @param language
     * @param responseMsg
     */
    public void setTerminologyLastAccessDate(String language,String date)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                ITBOOK_SHARE,
                Context.MODE_PRIVATE);
        String key=null;
        if("cn".equalsIgnoreCase(language)){
            key=context.getResources().getString(R.string.terminology_last_access_date_cn);
        }else if("en".equalsIgnoreCase(language)){
            key=context.getResources().getString(R.string.terminology_last_access_date_en);
        }
        Editor editor = sharedPreferences.edit();
        editor.putString(key,date);
        editor.commit();
    } 
    /**
     * 产品术语
     * 获取最近一次访问服务器的日期
     * @return 
     */
    public String getTerminologyLastAccessDate(String language)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                ITBOOK_SHARE,
                Context.MODE_PRIVATE);
        String key=null;
        if("cn".equalsIgnoreCase(language)){
            key=context.getResources().getString(R.string.terminology_last_access_date_cn);
        }else if("en".equalsIgnoreCase(language)){
            key=context.getResources().getString(R.string.terminology_last_access_date_en);
        }
        return sharedPreferences.getString(key,"");
    }
    
    /****************************faq*******************************************/
    /**
    * faq
    * 记录xml中 json字符串中的 time.
    * @param language
    * @param responseMsg
    */
   public void setFaqResponseTime(String language,long time)
   {
       SharedPreferences sharedPreferences = context.getSharedPreferences(
               ITBOOK_SHARE,
               Context.MODE_PRIVATE);
       String key=null;
       if("cn".equalsIgnoreCase(language)){
           key=context.getResources().getString(R.string.faq_last_update_time_cn);
       }else if("en".equalsIgnoreCase(language)){
           key=context.getResources().getString(R.string.faq_last_update_time_en);
       }
       Editor editor = sharedPreferences.edit();
       editor.putLong(key,time);
       editor.commit();
   } 
  
   
   /**
    * faq
    * 获取xml中 json字符串中的 time.
    * 如果是第一次请求 则直接返回 time为0.
    * @return 
    */
   public long getFaqResponseTime(String language)
   {
       SharedPreferences sharedPreferences = context.getSharedPreferences(
               ITBOOK_SHARE,
               Context.MODE_PRIVATE);
       String key=null;
       if("cn".equalsIgnoreCase(language)){
           key=context.getResources().getString(R.string.faq_last_update_time_cn);
       }else if("en".equalsIgnoreCase(language)){
           key=context.getResources().getString(R.string.faq_last_update_time_en);
       }
       return sharedPreferences.getLong(key, 0L);
   }
   
   /**
    * 产品术语
    * 设置最近一次访问服务器的日期
    * @param language
    * @param responseMsg
    */
   public void setFaqLastAccessDate(String language,String date)
   {
       SharedPreferences sharedPreferences = context.getSharedPreferences(
               ITBOOK_SHARE,
               Context.MODE_PRIVATE);
       String key=null;
       if("cn".equalsIgnoreCase(language)){
           key=context.getResources().getString(R.string.faq_last_access_date_cn);
       }else if("en".equalsIgnoreCase(language)){
           key=context.getResources().getString(R.string.faq_last_access_date_en);
       }
       Editor editor = sharedPreferences.edit();
       editor.putString(key,date);
       editor.commit();
   } 
   /**
    * 产品术语
    * 获取最近一次访问服务器的日期
    * @return 
    */
   public String getFaqLastAccessDate(String language)
   {
       SharedPreferences sharedPreferences = context.getSharedPreferences(
               ITBOOK_SHARE,
               Context.MODE_PRIVATE);
       String key=null;
       if("cn".equalsIgnoreCase(language)){
           key=context.getResources().getString(R.string.faq_last_access_date_cn);
       }else if("en".equalsIgnoreCase(language)){
           key=context.getResources().getString(R.string.faq_last_access_date_en);
       }
       return sharedPreferences.getString(key,null);
   }
    
}

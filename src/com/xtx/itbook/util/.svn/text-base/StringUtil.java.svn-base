/*
 * 文件名：StringUtil.java
 * 描述：TODO
 * 修改人：王鹏
 * 修改时间：下午2:28:52
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.util;

import android.content.res.Resources;

import com.xtx.itbook.main.ItBookApp;
import com.xtx.itbook.ui.R;

/**
 * @author Abelart.
 */
public class StringUtil
{   
    /**
     * 获取接口的url.
     * @param resId
     * @return.
     */
    public static String getUrl(int resId)
    {  
       Resources resources =ItBookApp.instance().getApplicationContext().getResources();

       return resources.getString(R.string.web_ip)+resources.getString(resId);
    
    }
    
    /**
     * 根据分号拆分 url地址.
     * @param urls
     * @return
     */
    public static String[] getUrls(String urls)
    {
    	return urls.split(";");
    }
    
}


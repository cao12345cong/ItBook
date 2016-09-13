/*
 * 文件名：InitService.java
 * 描述：TODO
 * 修改人：王鹏
 * 修改时间：上午11:59:10
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.service;

import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.xtx.itbook.entity.Directory;
import com.xtx.itbook.entity.ListMenuEntry;
import com.xtx.itbook.net.NetUtil;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.util.AccessUtil;
import com.xtx.itbook.util.Const;
import com.xtx.itbook.util.ConstantInterface;
import com.xtx.itbook.util.LogUtil;
import com.xtx.itbook.util.StringUtil;

/**
 * FinalDb 对于同一条数据重复插入.
 *  联网 获取初始化的数据.
 *  获取相关目录.
 * @author Abelart.
 */
public class InitService implements ConstantInterface
{
    private static final String TAG = InitService.class.getName();
    
    private Context context;
    
    private FinalDb finalDb;
    
    
    /**
     * 构造方法 @param context.
     */
    public InitService(Context context)
    {
        super();
        this.context = context;
        finalDb = FinalDb.create(context,DB_NAME,true);
    }
    
    
    
    /**
     * 根据语言删除所有数据.
     * @param language
     * @return
     */
    public boolean delete(String language)
    {
    	if(!language.equals("") && language != null)
    	{
    	   finalDb.deleteByWhere(ListMenuEntry.class, "language = '"+language+"'");
    	   return true;
    	}
    	return false;
    }
    
    /**
     * 根据kind清除相关数据，包括英文数据.
     * @param kind
     * @return
     */
    public boolean delete(int kind)
    {
    	if(kind>0)
    	{
    	   finalDb.deleteByWhere(ListMenuEntry.class, "kind = "+kind);
      	   return true;
    	}
    	return false;
    }
    
    /**
     * 根据类型删除所有数据.
     * @param kind
     * @param language
     * @return
     */
    public boolean delete(int kind ,String language)
    {
    	if(kind >0 && !language.equals("") && language != null)
    	{
    	   finalDb.deleteByWhere(ListMenuEntry.class, "kind = "+kind+" and language = '"+language+"'");
           return true;
    	}
    	return false;
    }


    /**
     * kind 类型.
     * 根据kind和language 获取请求的 信息记录.
     * @return
     */
    public List<Directory> getDirectory(int kind, String language)
    {
        return finalDb.findAllByWhere(Directory.class, "kind = " + kind
                + " and language = '" + language + "'");
    }

    
    
    
}

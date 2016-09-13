/*
 * 文件名：InitDbService.java
 * 描述：TODO
 * 修改人：王鹏
 * 修改时间：上午11:31:01
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.db;

import java.util.List;

import net.tsz.afinal.FinalDb;
import android.content.Context;

import com.google.zxing.oned.rss.FinderPattern;
import com.xtx.itbook.entity.ListMenuEntry;
import com.xtx.itbook.entity.Product;
import com.xtx.itbook.entity.RequestRecord;
import com.xtx.itbook.util.ConstantInterface;
import com.xtx.itbook.util.LogUtil;

/**
 * 本地数据库 查询.
 * 初始化目录结构，下拉菜单 底部菜单等相关信息.
 * @author Abelart.
 */
public class InitDbService implements ConstantInterface
{
    private static final String TAG = InitDbService.class.getName();
	
    private FinalDb finalDb = null;

    /**
     * 构造方法 @param context.
     */
    public InitDbService(Context context)
    {
        super();
        finalDb = FinalDb.create(context,DB_NAME,true);
    }

    /**
     * 根据  层级/语言   获取本地数据.
     * 查询menu表
     * @return.
     */
    public List<ListMenuEntry> getListMenuEntry(int level, String language)
    {

        return finalDb.findAllByWhere(ListMenuEntry.class, "level = " + level
                + " and language = '" + language + "'");
    }
    
    /**
     * 获取目录列表 ，
     * 根据 目录 类型、层级、语言   获取本地数据.
     * 查询menu表
     * @return.
     */
    public List<ListMenuEntry> getListMenuEntry(int kind,int level, String language)
    {

        return finalDb.findAllByWhere(ListMenuEntry.class, "kind = "+kind+
        		                              " and level = " + level+
        		                              " and language = '" + language + "'");
    }
    
    /**
     * 根据底部某一项菜单的 ID、 语言 查询下拉列表.
     * @param id  底部目录ID
     * @param language     语言.
     * @return.
     */
    public List<ListMenuEntry> getSecondCatalog(long id, String language)
    {
        return finalDb.findAllByWhere(ListMenuEntry.class, "upid = " + id
                + " and language = '" + language + "'");
    }
    
    /**
     * 产品列表.
     * @param pid 本机目录ID.
     * @param language 语言.
     * @return
     */
    public List<Product> getProductList(long pid,String language)
    {
    	return finalDb.findAllByWhere(Product.class, "pid = "+pid+" and language = '"+language+"'");
    }
    
    /**
     * 获取 请求返回的记录.
     * 每一条下拉列表item目录对应着唯一 一条请求.
     * @param pid
     * @param language
     * @return
     */
    public List<RequestRecord> getRecord(long pid,String language)
    {
    	return finalDb.findAllByWhere(RequestRecord.class, "pid = "+pid+" and language = '"+language+"'");
    }
    
    
    /**
     * 如果记录已经存在 则验证新纪录与数据库中记录时间是否一致
     * 如果不一致则修改时间.
     * 如果记录不存在 则保存记录.
     * @param requestRecord
     */
    public void save(RequestRecord requestRecord)
    {
    	List<RequestRecord> liRecords = getRecord(requestRecord.getPid(),requestRecord.getLanguage());
    	/**如果根据language 和pid能够查询到 信息 则不做保存.*/
    	if(liRecords.size()>0)
    	{
    		if(liRecords.get(0).getTime()!=requestRecord.getTime())
    		{
    			finalDb.update(RequestRecord.class, "pid = "+requestRecord.getPid()+" and language = '"+requestRecord.getLanguage()+"'");
    		    LogUtil.i(TAG, "修改记录返回信息");
    		    
    		}
    	}else{
    		finalDb.save(requestRecord);
    		LogUtil.i(TAG, "保存记录返回信息");
    	}
    }

}

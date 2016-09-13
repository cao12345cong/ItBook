package com.xtx.itbook.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.xtx.itbook.entity.ListMenuEntry;
import com.xtx.itbook.util.LogUtil;

/**
 * 将各个目录的数据 在进入程序的时候 
 * 加载内容，以便快速访问.
 * @author Abelart
 *
 */
public class CacheDirectory
{
	private static final String TAG =CacheDirectory.class.getName();
	
	private static CacheDirectory cacheMenu = null;
	
	private CacheDirectory(){}
	
	/**单例模式.*/
	public static CacheDirectory instance()
	{
		if(cacheMenu == null)
		{
			cacheMenu = new CacheDirectory();
			LogUtil.i(TAG, "instance()");
		}
		return cacheMenu;
	}
	/**
	 * 产品
	 * 此映射关系为 各个类型的底部菜单.
	 */
    public LinkedHashMap<ListMenuEntry,ArrayList<ListMenuEntry>> productBottomMenu = new LinkedHashMap<ListMenuEntry, ArrayList<ListMenuEntry>>();
    
    /**解决方案.*/
    public  LinkedHashMap<ListMenuEntry,ArrayList<ListMenuEntry>> solutionBottomMenu= new LinkedHashMap<ListMenuEntry, ArrayList<ListMenuEntry>>();
    
    /** 一问一答.*/
    public  LinkedHashMap<ListMenuEntry,ArrayList<ListMenuEntry>> faqBottomMenu = new LinkedHashMap<ListMenuEntry, ArrayList<ListMenuEntry>>();
    
    /** 行业案例.*/
    public  LinkedHashMap<ListMenuEntry,ArrayList<ListMenuEntry>> businessBottomMenu =new LinkedHashMap<ListMenuEntry, ArrayList<ListMenuEntry>>();
    
    /** 故障案例.*/
    public  LinkedHashMap<ListMenuEntry,ArrayList<ListMenuEntry>> faultCaseBottomMenu = new LinkedHashMap<ListMenuEntry, ArrayList<ListMenuEntry>>();
    
    
    /**产品列表菜单集合.*/
    public  LinkedHashMap<ListMenuEntry,ArrayList<ListMenuEntry>> getProductMenu()
    {
    	return productBottomMenu;
    }
    
    /**解决方案方案菜单集合.*/
    public  LinkedHashMap<ListMenuEntry,ArrayList<ListMenuEntry>> getSolutionMenu()
    {
    	return solutionBottomMenu;
    }
    
    /**faq菜单菜单集合.*/
    public  LinkedHashMap<ListMenuEntry,ArrayList<ListMenuEntry>> getFaqMenu()
    {
    	return faqBottomMenu;
    }
    
    /**行业案例菜单集合.*/
    public  LinkedHashMap<ListMenuEntry,ArrayList<ListMenuEntry>> getBusinessMenu()
    {
    	return businessBottomMenu;
    }
    
    /**故障案例菜单集合.*/
    public  LinkedHashMap<ListMenuEntry,ArrayList<ListMenuEntry>> getFaultCaseMenu()
    {
    	return faultCaseBottomMenu;
    }
    
    
    
    public void addProductMap(ListMenuEntry key,ArrayList<ListMenuEntry> value)
    {
    	productBottomMenu.put(key, value);
    }
    
    public void addSolutionMap(ListMenuEntry key,ArrayList<ListMenuEntry> value)
    {
    	solutionBottomMenu.put(key, value);
    }
    
    public void addFaqMap(ListMenuEntry key,ArrayList<ListMenuEntry> value)
    {
    	faqBottomMenu.put(key, value);
    }
    
    public void addBusinessMap(ListMenuEntry key,ArrayList<ListMenuEntry> value)
    {
    	businessBottomMenu.put(key, value);
    }
    
    public void addFaultCaseMap(ListMenuEntry key,ArrayList<ListMenuEntry> value)
    {
    	faultCaseBottomMenu.put(key, value);
    }
    
    
}

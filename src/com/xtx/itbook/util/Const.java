package com.xtx.itbook.util;

/**
 * 常量类
 * @author caocong
 *
 */
public class Const
{   
    /**http请求中 请求返回的 类型 【2进制字节流】*/
    public static final String HEAD_TYPE = "application/octet-stream";
    
    /**It book 相关文件存储的.*/
    public static final String ITBOOK_CACHE = "/mnt/sdcard/itbook/cache/";
    
    
    /**所有图片缓存地址*/
    public static String IT_BOOK_IMG_CACHE = ITBOOK_CACHE+"img_cache";
    
    /**硬盘缓存设置为30M*/
    public static int IT_BOOK_DISK_CACHE = 1024*1024*30;      
    
    /**手机内存缓存设置 3M .*/
    public static int IT_BOOK_MEMORY_CACHE = 1024*1024*3;  
    
    /**
     * intent相关的key
     */
    public static String KEY_FAQ="key_faq";
    
    public static String KEY_FAULTCASE="key_faultcase";
    
    public static String KEY_TERMINOLOGY = "key_terminology";

    public static String KEY_TRADCASE = "key_tardcase";
    /**对于xml中存储的数据当不存在的时候取此默认值.*/
    public static String DEFAULT = "nothing";

    public static final String KEY_SLTN_DTL_SHOW = "key_sltn_dtl_show";

    /**
     * json获取地址
     */
    
    public static final String URL = "http://192.168.0.101:8080/it_book/json/directory.do";

    public static final String FAQ_URL = "http://192.168.0.101:8080/it_book/json/faq.do";
    //解决方案
    public static final String SLTN_URL = "http://192.168.0.101:8080/it_book/json/solutionlist.do";
    //解决方案详情
    public static final String SLTN_DTL_URL = "http://192.168.0.101:8080/it_book/json/solution.do";
    
    
}

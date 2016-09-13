package com.xtx.itbook.util;

import java.io.File;

import android.os.Environment;
import android.util.Log;

/**
 * 文件操作
 * @author Abelart.
 */
public final class FileUtil
{
    
    public static final String TAG = FileUtil.class.getName();
    
    /**是否有sdCard.*/
    public static boolean hasSdCard()
    {
        String status = Environment.getExternalStorageState();
        if(status.equals(Environment.MEDIA_MOUNTED))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 根据uri获取 文件名字
     * /mnt/sdcard/itbook/cache/img_cache/xxdf#ss23.jpg 。
     * */
    public static String getNameFromUri(String uri)
    {
    	File file = new File(uri);
    	return Const.IT_BOOK_IMG_CACHE+"/"+file.getName();
    }
    
    
    
    /***
     * 根据路径创建文件.
     * @param path
     * @return.
     */
    public static boolean createPath(String path)
    {
        File file = new File(path);
        if(!file.exists())
        {
            Boolean b = file.mkdirs();
            Log.i(TAG,"create File ["+path+"]"+b.toString());
            return b;
        }
        return true;
    }
    
    /**
     * 删除指定文件
     * @param dir.
     */
    public static void deleteFiles(File dir)
    {
        if (dir != null && dir.exists() && dir.isDirectory())
        {
            for (File item : dir.listFiles())
            {
                item.delete();
            }
        }
    }

    /**
     * 删除指定路径下的 指定后缀的文件
     * @param dir
     * @return.
     */
    public static void deletFileSuffix(File dir, String sufix)
    {
        if (dir != null && dir.exists() && dir.isDirectory())
        {
            for (File item : dir.listFiles())
            {
                if (item.getPath().endsWith(sufix))
                {
                    item.delete();
                }
            }
        }
    }
}

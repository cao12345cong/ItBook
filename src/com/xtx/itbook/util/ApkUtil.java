package com.xtx.itbook.util;

import java.io.File;
import java.util.Locale;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 对于apk的相关操作.
 * @author Abelart.
 */
public final class ApkUtil
{
    /**日志标签.*/
    private static final String TAG = ApkUtil.class.getName();

    /**
     * 安装apk /插件.
     * @param context 上下文.
     * @param filePath apk的文件路径.
     */
    public static void installApk(Context context, String filePath)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(filePath)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**清除数据库中的数据.*/
    public static void clearDataBase(Context context)
    {
        Log.i(TAG, "clearDataBase:");
        FileUtil.deleteFiles(new File("/data/data/" + context.getPackageName()
                + "/databases"));
    }

    /**清除缓存中的数据 .*/
    public static void clearCache(Context contx)
    {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
        {
            FileUtil.deleteFiles(contx.getExternalCacheDir());
        }
    }

    /** 需要2.3以上版本支持返回serial值.*/
    @Deprecated
    public static String serialNumber()
    {
        // return android.os.Build.SERIAL;
        return null;
    }

    /**设备Id号码 唯一标示.*/
    public static String deivceId(Context context)
    {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        Log.i(TAG, "deivceId:" + tm.getDeviceId());
        return tm.getDeviceId();
    }

    /***
     * 获取应用插件的的ICON.
     * @param context  上下文.
     * @param activityName 如 com.xtx.itbook.faq.FaqActivity
     * @return.
     */
    public static Drawable pluginIcon(Context context,
            ComponentName activityName)
    {
        PackageManager packageManager = context.getPackageManager();
        try
        {
            return packageManager.getActivityIcon(activityName);
        }
        catch (NameNotFoundException e)
        {
            e.printStackTrace();
            Log.i(TAG, "pluginIcon: ComponentName not found.");
        }
        return null;
    }
    
    /**
     * 获取系统语言 如果不是中文 则 默认为英文.
     * @return
     */
    public static String getSysLanguage()
    {
    	String language = Locale.getDefault().getLanguage();
    	if(language.equalsIgnoreCase("zh"))
    	{
    		return ConstantInterface.CN;
    	}
    	return ConstantInterface.EN;
    }
}

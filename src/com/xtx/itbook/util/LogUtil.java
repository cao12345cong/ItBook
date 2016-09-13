package com.xtx.itbook.util;

import android.util.Log;

/**
 * 统一管理Log的打印和不打印，当项目做完后，应该设置不再打印
 * @author caocong
 * 2013.10.28
 */
public class LogUtil
{
    private static boolean isOutputLog = true;// 当设置为false,不打印

    public static void i(String tag, String msg)
    {
        if (isOutputLog == false)
        {
            return;
        }
        Log.i(tag, msg);
    }

    public static void i(String tag, int msg)
    {
        i(tag, String.valueOf(msg));
    }

    public static void i(String tag, boolean msg)
    {
        i(tag, String.valueOf(msg));
    }

    public static void i(String tag, long msg)
    {
        i(tag, String.valueOf(msg));
    }

}

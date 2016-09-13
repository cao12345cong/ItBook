/*
 * 文件名：ScreenManager.java
 * 描述：TODO
 * 修改人：王鹏
 * 修改时间：上午10:34:35
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * 获取屏幕分辨率
 * @author Abelart.
 */
public final class ScreenManager
{
    private DisplayMetrics dMetrics = new DisplayMetrics();

    public ScreenManager(Activity activity)
    {
        activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
    }

    /**获取屏幕分辨率宽度.*/
    public int getScreenW()
    {

        return dMetrics.heightPixels;
    }

    /**获取屏幕分辨率高度.*/
    public int getScreenH()
    {
        return dMetrics.widthPixels;
    }
}

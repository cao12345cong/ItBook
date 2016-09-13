/*
 * 文件名：PdfService.java
 * 描述：PDF阅览.
 * 修改人：王鹏
 * 修改时间：下午2:55:12
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.pdf;

import java.io.File;

//import org.vudroid.pdfdroid.PdfViewerActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**阅读
 * PDF. 
 * @author Abelart.
 */
public final class PdfService
{
    /** 
     * 打开PDF文件.
     * @param activity 打开pdf的activity.
     * @param filePath pdf文件的路径
     */
    public void openPDF(final Activity activity, String filePath)
    {
        final Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.fromFile(new File(filePath)));
       // intent.setClass(activity, PdfViewerActivity.class);
        activity.startActivity(intent);
    }
}

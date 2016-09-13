package com.xtx.itbook.ui;

import java.io.File;

import net.tsz.afinal.http.AjaxCallBack;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.xtx.itbook.net.NetUtil;
import com.xtx.itbook.util.FileUtil;
import com.xtx.itbook.util.LogUtil;

/**
 * 加载html并显示其中的 图片.
 * @author Abelart
 *
 */
public class ImageTextView extends TextView 
{
    
	private static final String TAG =ImageTextView.class.getName();
	
	public ImageTextView(Context context,String html)
	{
		super(context);
		setText(spanned(html), BufferType.SPANNABLE);
	}
	
	
	@Override
	public void setGravity(int gravity) {
		super.setGravity(gravity);
	}


	@Override
	public void setHeight(int pixels) {
		super.setHeight(pixels);
	}


	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text, type);
		
	
		
	}

	@Override
	public void setTextColor(int color) {
		super.setTextColor(color);
	}


	@Override
	public void setTextSize(float size) 
	{
		super.setTextSize(size);
	}


	@Override
	public void setTypeface(Typeface tf, int style) 
	{
		super.setTypeface(tf, style);
	}
    
	/**
	 * 返回经过处理的 html文本.使其中的图片链接 加载图片.
	 * @param html
	 * @return
	 */
	public Spanned spanned(String html)
    {
		Spanned sp = Html.fromHtml(html, new Html.ImageGetter() {
			public Drawable getDrawable(String source) {
				downLoadImg(source);
				
				Drawable drawable = Drawable.createFromPath(FileUtil
						.getNameFromUri(source));
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight());
				return drawable;
			}
		}, null);
		
		return sp;
	}
	
	/**
	 * 下载给定的 uri资源图片.
	 * @param uri
	 */
	private void downLoadImg(String uri)
	{
		NetUtil.getHttp().download(uri,
				FileUtil.getNameFromUri(uri),
				new AjaxCallBack<File>() {

					public void onSuccess(File t) {
						super.onSuccess(t);
						LogUtil.i(TAG,
								"download success: " + t.getAbsolutePath());
					}

					public void onFailure(Throwable t, String strMsg) {
						LogUtil.i(TAG, "Fail "+t +strMsg);
					}
					
					
				});
	}

	

}

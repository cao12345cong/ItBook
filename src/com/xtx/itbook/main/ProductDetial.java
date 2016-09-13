package com.xtx.itbook.main;

import org.json.JSONObject;

import net.tsz.afinal.http.AjaxCallBack;

import com.xtx.itbook.net.NetUtil;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.ui.TitleBarView;
import com.xtx.itbook.ui.TitleBarView.TitleBarListener;
import com.xtx.itbook.util.Const;
import com.xtx.itbook.util.LogUtil;
import com.xtx.itbook.util.StringUtil;

import android.os.Bundle;
import android.view.View;

public class ProductDetial extends BaseActivity {
	
	private static final String TAG = ProductDetial.class.getName();
	
	private TitleBarView titleBar ;
    
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail);
		
		init();
	}
	
	public void onClick(View v) {}

	public void init()
	{
         initView();
         addListener();
	}

	public void initView()
	{
		titleBar = (TitleBarView) findViewById(R.id.product_title);
	}
	
	private void addListener()
	{
		titleListener();
	}
	
	private void titleListener()
	{
		titleBar.setTitleBarListener(new TitleBarListener() {
			
			public void onSearchTextChanged(CharSequence s, int start, int before,
					int count) {
				
			}
			
			public void onClickTitleText(View v) {
				
			}
			
			public void onClickRightImg(View v) {
				
			}
			
			public void onClickLeftImg(View v) {
				ProductDetial.this.finish();
			}
		});
		
	}
	
	private void loadData()
	{
		
	}
	
	
	
	/**获取数据.*/
	private void getData(JSONObject jsonObject)
    {
		NetUtil.getHttp().post(StringUtil.getUrl(R.string.product),
				NetUtil.getEntity(jsonObject), Const.HEAD_TYPE,
				new AjaxCallBack<String>() {
					public void onSuccess(String t) {
						super.onSuccess(t);
						LogUtil.i(TAG, "getData:" + t);

					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);

						try {
							throw t;
						} catch (Throwable e) {
							LogUtil.i(TAG, errorNo + ":" + strMsg);
						} finally {
							NetUtil.toast(ProductDetial.this, errorNo+" 请求失败 ");

						}
					}

				});
	}
	
	/**
	 * 获取列表界面传递过来的 产品ID。
	 * @return
	 */
	public int getProductId()
	{
		return this.getIntent().getIntExtra("id", DEFAULT_VALUE);
	}

}

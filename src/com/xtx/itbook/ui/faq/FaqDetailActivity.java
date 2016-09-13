package com.xtx.itbook.ui.faq;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.xtx.itbook.entity.Faq;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.ui.TitleBarView;
import com.xtx.itbook.ui.TitleBarView.TitleBarListener;
import com.xtx.itbook.util.Const;

/**
 * Faq详细页面
 * @author caocong
 *
 */
public class FaqDetailActivity extends Activity
{
    private static final String TAG="FaqDetailActivity";
    
    private TitleBarView titlebar;// 标题栏

    private TextView tvContent;// 内容
    
    private WebView wvContent;// 内容
    
    private String url;//html 地址
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_detail);
        initView();
        initData();
        initListener();
    }

    private void initView()
    {
        // 标题栏
        titlebar = (TitleBarView) findViewById(R.id.titlebar);
        // faq内容
        tvContent = (TextView) findViewById(R.id.tv_faq_detail);
        
        wvContent=(WebView) findViewById(R.id.wv_faq_detail);
    }

    private void initData()
    {
        Faq faq = (Faq) getIntent().getSerializableExtra(Const.KEY_FAQ);
        // 设置标题
        titlebar.setTitle(faq.getQuestion() == null ? "" : faq.getQuestion());
        // 设置内容
        String path=faq.getAnswer() == null ? "" : faq.getAnswer();
        this.url=getResources().getString(R.string.host)+path;
        this.wvContent.loadUrl(url);
//        NetUtil.getHttp().get(url, new AjaxCallBack<String>(){
//            //下面的回调方法，在主线程中执行
//            @Override
//            public void onFailure(Throwable t, String strMsg)
//            {
//                
//            }
//            @Override
//            public void onSuccess(String t)
//            {
//                LogUtil.i(TAG, t);
//                tvContent.setText(Html.fromHtml(t));
//            }
//        });
        
        
        
    }

    private void initListener()
    {
        titlebar.setTitleBarListener(new TitleBarListener()
        {

            @Override
            public void onClickTitleText(View v)
            {
            }

            @Override
            public void onClickRightImg(View v)
            {

            }

            @Override
            public void onClickLeftImg(View v)
            {
            }

            @Override
            public void onSearchTextChanged(CharSequence s, int start,
                    int before, int count)
            {
                // TODO Auto-generated method stub

            }

        });
    }
}

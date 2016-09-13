package com.xtx.itbook.ui.faultcase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtx.itbook.entity.Faq;
import com.xtx.itbook.entity.Faultcase;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.util.Const;

/**
 * Faq详细页面
 * @author caocong
 *2013.10.31
 */
public class FaultcaseDetailActivity extends Activity
{
    private ImageView ivGoBack, ivShare;// 标题栏按钮

    private TextView tvTitle;// 标题

    private TextView tvContent;// 内容

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
        ivGoBack = (ImageView) findViewById(R.id.iv_titlebar_goback);
        ivShare = (ImageView) findViewById(R.id.iv_titlebar_right);
        ivShare.setBackgroundResource(R.drawable.ic_share);
        tvTitle = (TextView) findViewById(R.id.tv_titlebar_title);

    }

    private void initData()
    {
        Faultcase faultcase = (Faultcase) getIntent().getSerializableExtra(
                Const.KEY_FAULTCASE);
        // 设置标题
        tvTitle.setText("故障案例底层" + "");
        // 设置内容
        tvContent.setText(faultcase.getResolve() == null ? " " : faultcase
                .getResolve());

    }

    private void initListener()
    {
        ivGoBack.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(FaultcaseDetailActivity.this,
                        FaultcaseActivity.class));
                finish();
            }
        });
    }
}

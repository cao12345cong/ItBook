package com.xtx.itbook.ui.terminology;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xtx.itbook.entity.Terminology;
import com.xtx.itbook.main.BaseActivity;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.ui.TitleBarView;
import com.xtx.itbook.ui.TitleBarView.TitleBarListener;
import com.xtx.itbook.util.Const;

/**
 * 产品术语详细首页
 * @author caocong
 * 2013.10.26
 */
public class TerminologyDetailActivity extends BaseActivity
{

    private TitleBarView titlebar;// 标题栏

    private TextView tvContent;// 详细内容

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terminology_detail);
        initView();
        initListener();
        initData();
    }

    public void initView()
    {
        // 标题栏
        titlebar = (TitleBarView) findViewById(R.id.titlebar);
        // 详细介绍
        tvContent = (TextView) findViewById(R.id.tv_terminology_detail);
    }

    private void initListener()
    {
        titlebar.setTitleBarListener(new TitleBarListener()
        {

            @Override
            public void onSearchTextChanged(CharSequence s, int start,
                    int before, int count)
            {

            }

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
                // 返回上一页
                skip(TerminologyActivity.class);
            }
        });
    }

    private void initData()
    {
        Intent intent = getIntent();
        Terminology term = (Terminology) intent
                .getSerializableExtra(Const.KEY_TERMINOLOGY);
        // 设置标题
        titlebar.setTitle(term.getWord() == null ? "" : term.getWord());
        // 设置内容
        tvContent.setText(term.getExplain());
    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void init()
    {
        // TODO Auto-generated method stub

    }

}

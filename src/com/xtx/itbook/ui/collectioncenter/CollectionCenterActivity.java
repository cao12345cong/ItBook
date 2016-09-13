package com.xtx.itbook.ui.collectioncenter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.xtx.itbook.main.BaseActivity;
import com.xtx.itbook.ui.R;

/**
 * 收藏中心首页
 * caocong
 * 2013.11.01
 */
public class CollectionCenterActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_center);
        initView();
    }

    @Override
    public void onClick(View v)
    {

    }

    @Override
    public void init()
    {

    }

    @Override
    public void initView()
    {

        initTitleBarDefault("收藏中心", R.drawable.ic_finder, listenerLeft,
                listenerRight);
    }

    OnClickListener listenerLeft = new OnClickListener()
    {

        @Override
        public void onClick(View v)
        {

        }
    };

    OnClickListener listenerRight = new OnClickListener()
    {

        @Override
        public void onClick(View v)
        {

        }
    };
}

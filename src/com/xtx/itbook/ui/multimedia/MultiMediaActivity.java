package com.xtx.itbook.ui.multimedia;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xtx.itbook.main.BaseActivity;
import com.xtx.itbook.ui.MediaShowView;
import com.xtx.itbook.ui.MediaShowView.OnClickedListener;
import com.xtx.itbook.ui.R;

public class MultiMediaActivity extends BaseActivity
{
    MediaShowView mediaView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mulitmedia_index);
        initView();
        initListener();
        initData();
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
        mediaView = (MediaShowView) findViewById(R.id.msv_multimedia_1);
        mediaView.addView(getResources().getDrawable(R.drawable.img_faultcase),
                "宇宙奥秘在云端");
    }

    private void initData()
    {
        // TODO Auto-generated method stub

    }

    private void initListener()
    {
        mediaView.setOnClickedListener(new OnClickedListener()
        {

            @Override
            public void doClick(String name)
            {
                Toast.makeText(MultiMediaActivity.this, name, 1000).show();
            }
        });
    }
}

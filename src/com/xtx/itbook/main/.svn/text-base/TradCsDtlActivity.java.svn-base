package com.xtx.itbook.main;

import com.xtx.itbook.entity.TradeCase;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.util.Const;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 
 * @author ChenHuigang
 * 行业案例详情
 */
public class TradCsDtlActivity extends BaseActivity
{
    private TextView titleTv;
    private TextView contentTv;
    private TextView introTv;
    private TradeCase tradeCase;
    private ImageView backIv,shareIv,collectIv,talkIv;
    
    
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tradcase_detail);
        initView();
        init();
        initListener();
        
    }
    private void initListener()
    {
       backIv.setOnClickListener(this);
       shareIv.setOnClickListener(this);
        
    }
    private void initData()
    {
        tradeCase = (TradeCase) getIntent().getSerializableExtra(Const.KEY_TRADCASE);
        titleTv.setText((null == tradeCase.getTitle() ? "" :tradeCase.getTitle())) ;
        contentTv.setText(null == tradeCase.getContent() ? "" : tradeCase.getContent() );
        introTv.setText(null == tradeCase.getIntro() ? "" : tradeCase.getIntro() );
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            //返回
            case R.id.tradcase_dtl_goback:
                skip(TradCsActivity.class);
                break;
                //分享
            case R.id.tradcase_dtl_share:
                break;
        }

    }

    @Override
    public void init()
    {
        initData();
    }

    @Override
    public void initView()
    {
        titleTv = (TextView) findViewById(R.id.tradcase_dtl_title);
        contentTv = (TextView) findViewById(R.id.tradcase_dtl_content);
        introTv = (TextView) findViewById(R.id.tradcase_dtl_intro);
        backIv = (ImageView) findViewById(R.id.tradcase_dtl_goback);
        shareIv = (ImageView) findViewById(R.id.tradcase_dtl_share);
        //没给id
        //collectIv = (ImageView) findViewById(R.id.tradcase_dtl_goback);
        //talkIv = (ImageView) findViewById(R.id.tradcase_dtl_goback);
    }

}

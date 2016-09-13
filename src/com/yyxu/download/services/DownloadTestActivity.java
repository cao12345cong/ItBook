package com.yyxu.download.services;

import com.xtx.itbook.ui.R;
import com.yyxu.download.utils.MyIntents;
import com.yyxu.download.utils.Utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/*
 * 作者：陈建林
 * 时间：2013-10-14
 * 用途：TODO
 * 文件名：DownloadTestActivity.java
 * 描述： 
 * 修改时间：下午2:53:13
 * 修改内容：待定.
 */
public class DownloadTestActivity extends Activity
{
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    private Button downButton;

    private MyReceiver mReceiver;

    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down_test);
        mReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.yyxu.download.progess");
        registerReceiver(mReceiver, filter);
        mTV = (TextView) findViewById(R.id.main_txt);
        downButton = (Button) findViewById(R.id.main_btn);
        downButton.setOnClickListener(new OnClickListener()
        {

            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent downloadIntent = new Intent(
                        "com.yyxu.download.services.IDownloadService");
                downloadIntent.putExtra(MyIntents.TYPE, MyIntents.Types.ADD);
                downloadIntent.putExtra(MyIntents.URL, Utils.url[1]);
                startService(downloadIntent);
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public class MyReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {

            handleIntent(intent);

        }

        private void handleIntent(Intent intent)
        {

            System.out.println(intent.getAction());
            if (intent != null
                    && intent.getAction().equals("com.yyxu.download.progess"))
            {
                int type = intent.getIntExtra(MyIntents.TYPE, -1);
                String url;

                switch (type)
                {

                    case MyIntents.Types.PROCESS:
                        url = intent.getStringExtra(MyIntents.URL);
                        if (url.equals(Utils.url[1]))
                        {
                            mTV.setText(intent
                                    .getStringExtra(MyIntents.PROCESS_PROGRESS));
                        }
                        break;
                    case MyIntents.Types.ERROR:
                        url = intent.getStringExtra(MyIntents.URL);
                        // int errorCode =
                        // intent.getIntExtra(MyIntents.ERROR_CODE,
                        // DownloadTask.ERROR_UNKONW);
                        // handleError(url, errorCode);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}

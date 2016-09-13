/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import com.xtx.itbook.ui.R;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.twitter.Twitter;
import m.framework.ui.widget.slidingmenu.SlidingMenu;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * 项目的入口类，是侧栏控件的外壳
 * <p>
 * 侧栏的UI或者逻辑控制基本上都在{@link MainAdapter}中进行
 */
public class MainActivity extends Activity implements Callback,
        OnClickListener, PlatformActionListener
{
    private static final String FILE_NAME = "/pic.jpg";

    public static String TEST_IMAGE;

    private Handler handler;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_demo);
        findViewById(R.id.btnShareAll).setOnClickListener(this);

        ShareSDK.initSDK(this);
        // 去除注释，可以即可使用应用后台配置的应用信息，否则默认使用ShareSDK.conf中的信息
        // ShareSDK.setNetworkDevInfoEnable(true);
        Handler handler = new Handler(this);
    }

    private void initImagePath()
    {
        try
        {
            if (Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState())
                    && Environment.getExternalStorageDirectory().exists())
            {
                TEST_IMAGE = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + FILE_NAME;
            }
            else
            {
                TEST_IMAGE = getApplication().getFilesDir().getAbsolutePath()
                        + FILE_NAME;
            }
            File file = new File(TEST_IMAGE);
            if (!file.exists())
            {
                file.createNewFile();
                Bitmap pic = BitmapFactory.decodeResource(getResources(),
                        R.drawable.pic);
                FileOutputStream fos = new FileOutputStream(file);
                pic.compress(CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
            TEST_IMAGE = null;
        }
    }

    protected void onDestroy()
    {
        ShareSDK.stopSDK(this);
        super.onDestroy();
    }

    /** 将action转换为String */
    public static String actionToString(int action)
    {
        switch (action)
        {
            case Platform.ACTION_AUTHORIZING:
                return "ACTION_AUTHORIZING";
            case Platform.ACTION_GETTING_FRIEND_LIST:
                return "ACTION_GETTING_FRIEND_LIST";
            case Platform.ACTION_FOLLOWING_USER:
                return "ACTION_FOLLOWING_USER";
            case Platform.ACTION_SENDING_DIRECT_MESSAGE:
                return "ACTION_SENDING_DIRECT_MESSAGE";
            case Platform.ACTION_TIMELINE:
                return "ACTION_TIMELINE";
            case Platform.ACTION_USER_INFOR:
                return "ACTION_USER_INFOR";
            case Platform.ACTION_SHARE:
                return "ACTION_SHARE";
            default:
            {
                return "UNKNOWN";
            }
        }
    }

    // 使用快捷分享完成分享（请务必仔细阅读位于SDK解压目录下Docs文件夹中OnekeyShare类的JavaDoc）
    private void showShare(boolean silent, String platform)
    {
        OnekeyShare oks = new OnekeyShare();
        oks.setNotification(R.drawable.ic_launcher,
                this.getString(R.string.app_name));
        oks.setAddress("12345678901");
        oks.setTitle(this.getString(R.string.share));
        oks.setTitleUrl("http://sharesdk.cn");
        oks.setText(this.getString(R.string.share_content));
        oks.setImagePath(MainActivity.TEST_IMAGE);
        oks.setImageUrl("http://img.appgo.cn/imgs/sharesdk/content/2013/07/25/1374723172663.jpg");
        oks.setUrl("http://sharesdk.cn");
        oks.setAppPath(MainActivity.TEST_IMAGE);
        oks.setComment(this.getString(R.string.share));
        oks.setSite(this.getString(R.string.app_name));
        oks.setSiteUrl("http://sharesdk.cn");
        oks.setVenueName("Southeast in China");
        oks.setVenueDescription("This is a beautiful place!");
        oks.setAppName("ShareSDK");
        oks.setLatitude(23.122619f);
        oks.setLongitude(113.372338f);
        oks.setSilent(silent);
        if (platform != null)
        {
            oks.setPlatform(platform);
        }
        // 去除注释，则快捷分享的分享加过将听过OneKeyShareCallback回调
        // oks.setCallback(new OneKeyShareCallback());
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());

        oks.show(this);
    }

    /** 操作演示的代码集中于此方法 */
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.btnShareAll:
            {
                // 直接分享
                showShare(true, null);
            }
                break;
        }
    }

    public void onComplete(Platform plat, int action,
            HashMap<String, Object> res)
    {

        Message msg = new Message();
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = plat;
        handler.sendMessage(msg);
    }

    public void onCancel(Platform palt, int action)
    {
        Message msg = new Message();
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = palt;
        handler.sendMessage(msg);
    }

    public void onError(Platform palt, int action, Throwable t)
    {
        t.printStackTrace();

        Message msg = new Message();
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = palt;
        handler.sendMessage(msg);
    }

    /** 处理操作结果 */
    public boolean handleMessage(Message msg)
    {
        Platform plat = (Platform) msg.obj;
        String text = MainActivity.actionToString(msg.arg2);
        switch (msg.arg1)
        {
            case 1:
            { // 成功
                text = plat.getName() + " completed at " + text;
            }
                break;
            case 2:
            { // 失败
                text = plat.getName() + " caught error at " + text;
            }
                break;
            case 3:
            { // 取消
                text = plat.getName() + " canceled at " + text;
            }
                break;
        }

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        return false;
    }
}

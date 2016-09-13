package com.xtx.itbook.ui.faq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.ParseException;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.xtx.itbook.adapter.FaqListAdapter;
import com.xtx.itbook.data.CacheDirectory;
import com.xtx.itbook.db.InitDbService;
import com.xtx.itbook.entity.Faq;
import com.xtx.itbook.entity.ListMenuEntry;
import com.xtx.itbook.main.BaseActivity;
import com.xtx.itbook.main.MainActivity;
import com.xtx.itbook.service.FaqService;
import com.xtx.itbook.ui.BottomMenuScrollView;
import com.xtx.itbook.ui.BottomMenuScrollView.OnSelectedItemListener;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.ui.R.menu;
import com.xtx.itbook.ui.TitleBarView;
import com.xtx.itbook.ui.TitleBarView.TitleBarListener;
import com.xtx.itbook.util.Const;
import com.xtx.itbook.util.LogUtil;

/**
 * Faq主页面，显示产品分类
 * @author caocong
 */
public class FaqActivity extends BaseActivity
{
    private static String TAG = "FaqActivity";

    public static final int MSG_UPDATE_VIEW = 0; // 初始化界面

    public static final int MSG_UPDATA_BOTTOM_MENU = 1;// 更新底部菜单

    public static final int MSG_UPDATA_LISTVIEW = 3; // 更新listview

    public static final int MSG_DOWNLOAD_FAIL = 5; // 下载数据失败

    private TitleBarView titlebar; // 标题栏

    private BottomMenuScrollView scrollview; // 底部菜单

    private PopupWindow popTopMenu; // 下拉菜单

    private ListView mListView; // listview

    private FaqListAdapter adapter; // 适配器

    private List<Faq> faqs; // faq数据集

    private InitDbService initDbService; // 获取底部菜单目录

    private FaqService service; // 服务类

    private List<ListMenuEntry> rootDirs, childDirs; // 一级目录，二级目录

    private ListMenuEntry currentRootDir; // 当前一级目录

    private ListMenuEntry currentSecondDir;// 当前二级目录

    private ArrayList<ArrayList<ListMenuEntry>> listMenus = null;
    // 底部菜单对应 下拉列表的映射
    private LinkedHashMap<ListMenuEntry, ArrayList<ListMenuEntry>> menuMap;

    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {

                case MSG_UPDATE_VIEW:
                    adapter.updateView(faqs);
                    break;
                case MSG_UPDATA_LISTVIEW:
                    faqs =(List<Faq>) msg.obj;
                    if (faqs != null || faqs.size() > 0)
                    {
                        adapter.updateView(faqs);
                    }
                    break;
                case MSG_UPDATA_BOTTOM_MENU:// 底部菜单更新
                    titlebar.setTitle(currentSecondDir.getName());
                    scrollview.addView(rootDirs);
                    break;

                case MSG_DOWNLOAD_FAIL:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_index);
        this.faqs = new ArrayList<Faq>();
        initDbService = new InitDbService(FaqActivity.this);
        service = new FaqService(this, mHandler);
        menuMap=CacheDirectory.instance().getFaqMenu();
        initView();
        initListener();
        initData();
    }

    @Override
    public void initView()
    {
        // 标题栏
        titlebar = (TitleBarView) findViewById(R.id.titlebar);
        // listview
        mListView = (ListView) findViewById(R.id.lv_faq_index);
        adapter = new FaqListAdapter(this, faqs);
        mListView.setAdapter(adapter);
        // 底部菜单
        scrollview = (BottomMenuScrollView) findViewById(R.id.sv_faq_bottom_menu);

    }

    private void initListener()
    {
        // 标题栏
        titlebar.setTitleBarListener(new TitleBarListener()
        {

            @Override
            public void onClickTitleText(View v)
            {
                createAndShowTopMenu(v);
            }

            @Override
            public void onClickRightImg(View v)
            {
                Toast.makeText(FaqActivity.this, "onClickRightImg", 1000)
                        .show();

            }

            @Override
            public void onClickLeftImg(View v)
            {
                skip(MainActivity.class);
            }

            @Override
            public void onSearchTextChanged(CharSequence s, int start,
                    int before, int count)
            {

            }

        });
        // listview
        mListView.setOnItemClickListener(new OnItemClickListener()
        {

            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                Intent intent = new Intent(FaqActivity.this,
                        FaqDetailActivity.class);
                Faq Faq = faqs.get(position);
                intent.putExtra(Const.KEY_FAQ, Faq);
                startActivity(intent);
            }
        });
        // 底部菜单
        scrollview.setOnSelectedItemListener(new OnSelectedItemListener()
        {

            @Override
            public void doSelected(Object dir)
            {

                try
                {
                    // 清空列表数据
                    faqs.clear();
                    adapter.updateView(faqs);
                    // 1.当前根目录改变　　　　　
                    ListMenuEntry selectedDir = (ListMenuEntry) dir;
                    if (selectedDir.getId() == currentRootDir.getId())
                    {
                        return;
                    }
                    // 2.根目录改变，子目录改变
                    currentRootDir = selectedDir;
                    childDirs = new InitDbService(FaqActivity.this)
                            .getSecondCatalog(currentRootDir.getId(), "cn");
                    if (childDirs != null && !childDirs.isEmpty())
                    {
                        currentSecondDir = childDirs.get(0);
                    }
                    titlebar.setTitle(currentSecondDir.getName());
                    LogUtil.i(TAG, "currentRootDir" + currentRootDir.getName());
                    LogUtil.i(TAG,
                            "currentSecondDir" + currentSecondDir.getName());
                    LogUtil.i(TAG, "childDirs" + currentSecondDir.getName());
                    // 3.下拉菜单设置为空
                    popTopMenu = null;
                    // 4.faq数据集改变
                    getListData();

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }

        });
    }

    /**
     * 显示或隐藏下拉菜单，第一次时创建
     * @param v
     */
    private void createAndShowTopMenu(View v)
    {
        // 创建下拉菜单
        if (popTopMenu == null)
        {
            OnItemClickListener listener = new OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    try
                    {
                        // 改变标题
                        currentSecondDir = childDirs.get(position);
                        titlebar.setTitle(currentSecondDir.getName());
                        // 隐藏
                        popTopMenu.dismiss();
                        // 清空列表数据
                        faqs.clear();
                        adapter.updateView(faqs);
                        LogUtil.i(TAG,
                                "currentRootDir" + currentRootDir.getName());
                        LogUtil.i(TAG,
                                "currentSecondDir" + currentSecondDir.getName());
                        getListData();
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }

            };
            List<String> list = new ArrayList<String>();
            if (childDirs != null && childDirs.size() > 0)
            {
                for (ListMenuEntry childDir : childDirs)
                {
                    list.add(childDir.getName());
                }
            }
            popTopMenu = creatPopupWindow(200, 300, list, listener);
        }

        // 显示或隐藏下拉菜单
        if (popTopMenu.isShowing())
        {
            popTopMenu.dismiss();
        }
        else
        {
            int xoff = popTopMenu.getWidth() / 2 - v.getWidth() / 2;
            popTopMenu.update();
            popTopMenu.showAsDropDown(v, -xoff, 0);
        }
    }

    /**
     * 初始化数据
     */
    private void initData()
    {
        new Thread()
        {
            public void run()
            {

                try
                {
                    // 1.从数据库中获取目录信息
                    getBottomMenuData();

                    // 2.联网获取faq数据
                    getListData();

                }
                catch (Exception e)
                {
                    Message msg = Message.obtain(mHandler, MSG_DOWNLOAD_FAIL);
                    msg.sendToTarget();
                }
            }
        }.start();

    }

    /**
     * 初始化目录
     * @return
     */
    private void getBottomMenuData()
    {
        //获得一级目录
        List<ListMenuEntry> listMenuEntry=new ArrayList<ListMenuEntry>(menuMap.keySet());
        rootDirs = initDbService.getListMenuEntry(3, 1, "cn");
        LogUtil.i(TAG, rootDirs.toString());
        if (rootDirs == null || rootDirs.isEmpty())
        {
            return;
        }
        // 当前选中的一级目录
        currentRootDir = rootDirs.get(0);
        childDirs = initDbService
                .getSecondCatalog(currentRootDir.getId(), "cn");
        // 当前选中的二级目录
        currentSecondDir = childDirs.get(0);
        LogUtil.i(TAG, "currentRootDir" + currentRootDir.getName());
        LogUtil.i(TAG, "currentSecondDir" + currentSecondDir.getName());
        LogUtil.i(TAG, "childDirs" + currentSecondDir.getName());

        Message msg = Message.obtain(mHandler, MSG_UPDATA_BOTTOM_MENU);
        msg.sendToTarget();
    }

    /**
     * 获取faq数据集
     * @param obj
     * @throws JSONException 
     */
    protected void getListData() throws JSONException
    {
        if (currentSecondDir != null)
        {
            try
            {
                // service.startService("", currentSecondDir.getId(), 1, 10,
                // "cn");
                service.startService("", 4, 1, 10, "cn");
                Message msg2 = Message.obtain(mHandler, MSG_UPDATA_LISTVIEW);
                msg2.sendToTarget();
            }
            catch (Exception e)
            {
                Message msg = Message.obtain(mHandler, MSG_DOWNLOAD_FAIL);
                msg.sendToTarget();
            }

        }

    }

    /**
     * 获取linkedHashMap对应位置 的 value ArrayList<ListMenuEntry>
     * 即对应上方的下拉列表.
     * @param position 位置、
     * @return 
     */
    private ArrayList<ListMenuEntry> getMenus(int position)
    {
        List<ListMenuEntry> listKey = new ArrayList(menuMap.keySet());

        return menuMap.get(listKey.get(position));
    }

    @Override
    public void onClick(View v)
    {

    }

    @Override
    public void init()
    {

    }

}

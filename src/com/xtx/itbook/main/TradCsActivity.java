package com.xtx.itbook.main;

import java.util.ArrayList;
import java.util.List;

import com.xtx.itbook.adapter.SltnPopAdapter;
import com.xtx.itbook.adapter.TradeCaseListAdapter;
import com.xtx.itbook.entity.SglSolutions;
import com.xtx.itbook.entity.TradeCase;
import com.xtx.itbook.ui.BottomMenuScrollView;
import com.xtx.itbook.ui.BottomMenuScrollView.OnSelectedItemListener;
import com.xtx.itbook.ui.LoaderListView;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.ui.TitleBarView;
import com.xtx.itbook.ui.TitleBarView.TitleBarListener;
import com.xtx.itbook.util.Const;
import com.xtx.itbook.util.LogUtil;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author ChenHuigang
 *行业案例首页
 */

public class TradCsActivity extends BaseActivity implements OnItemClickListener
{

    private static final int SEND_TRDCS_LIST_TAG = 1;

    private TitleBarView titleBarView; // 标题栏

    private LoaderListView tradCsListView;// 列表

    private List<TradeCase> trdCsListData;// 列表数据

    private TradeCaseListAdapter trdCsAdapter;// 列表适配器

    private BottomMenuScrollView bottomMenuScrollView;// 底部滑动菜单

    private PopupWindow poPMenu;// 下拉菜单

    private List<String> poPListData;// 下拉列表数据

    private ListView poPListView;// 下拉列表listView

    private LayoutInflater layoutInflater;

    private SltnPopAdapter poPAdapter;// 下拉列表适配器
    
    TextView titleTv;

    public List<String> getPoPListData()
    {
        List<String> list = new ArrayList<String>();
        list.add("下拉列表1");
        list.add("下拉列表2");
        list.add("下拉列表3下拉列表3下拉列表3下拉列表3下拉列表3");
        list.add("下拉列表4");
        list.add("下拉列表5");
        list.add("下拉列表6");
        list.add("下拉列表7");
        return list;

    }

    Handler mHandler = new Handler()
    {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case SEND_TRDCS_LIST_TAG:
                    List<TradeCase> list = new ArrayList<TradeCase>();
                    list = (List<TradeCase>) msg.obj;
                    trdCsAdapter.changeDataUpdateView(list);
                    break;

                default:
                    break;
            }

        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tradecase_list);
        getData();

        initView();
        init();
        initListener();
    }

    public void getData()
    {
        // 案例列表数据初始化
        if (null != getTrdCsListData() && !getTrdCsListData().isEmpty())
        {
            trdCsListData = getTrdCsListData();
        }
        else
        {
            trdCsListData = new ArrayList<TradeCase>();
        }

        // 下拉列表数据初始化
        if (null != getPoPListData() && !getPoPListData().isEmpty())
        {
            poPListData = getPoPListData();
        }
        else
        {
            poPListData = new ArrayList<String>();
        }
    }

    @Override
    public void initView()
    {
        // 标题栏
        titleBarView = (TitleBarView) findViewById(R.id.titlebar_tradecase);
        // 标题下拉菜单
        layoutInflater = LayoutInflater.from(TradCsActivity.this);
        poPListView = (ListView) layoutInflater.inflate(
                R.layout.sltn_pop_listview, null);
        titleTv = (TextView) titleBarView.findViewById(R.id.tv_titlebar_title);
        poPAdapter = new SltnPopAdapter(TradCsActivity.this, poPListData);
        poPListView.setAdapter(poPAdapter);
        // 中间listView
        tradCsListView = (LoaderListView) findViewById(R.id.tradecase_listview);
        trdCsAdapter =  new TradeCaseListAdapter(TradCsActivity.this,
                trdCsListData);
        tradCsListView.setAdapter(trdCsAdapter );

        // 底部滑动菜单
        bottomMenuScrollView = (BottomMenuScrollView) findViewById(R.id.tradecase_bottom_menu_id);

    }

    /**
     * 监听端口
     */
    private void initListener()
    {
        poPListView.setOnItemClickListener(this);
        tradCsListView.setOnItemClickListener(this);
        // 放这里还是放初始化 下拉菜单里里面
        // poPListView.setOnItemClickListener(this);
        // 标题栏
        titleBarView.setTitleBarListener(new TitleBarListener()
        {

            @Override
            public void onSearchTextChanged(CharSequence s, int start,
                    int before, int count)
            {
                LogUtil.i("TradCsActivity", "正在搜索行业案例列表");
                findCaseByText(s);

            }

            @Override
            public void onClickTitleText(final View v)
            {

                // 下拉菜单
                if (null == poPMenu)
                {
                    // 初始化下拉菜单
                    int width = titleBarView.getWidth()/2;
                    int height = LayoutParams.WRAP_CONTENT;
                    poPMenu = new PopupWindow(poPListView, width, height, true);
                    poPMenu.setBackgroundDrawable(new ColorDrawable(0xffffffff));

                }
                if (!poPMenu.isShowing())
                {
                    poPMenu.showAsDropDown(titleBarView, titleBarView.getWidth()/4, 0);
                }
                else
                {
                    poPMenu.dismiss();
                }
               

            }

            @Override
            public void onClickRightImg(View v)
            {
                LogUtil.i("TradCsActivity", "标题栏右按钮被点击");
            }

            @Override
            public void onClickLeftImg(View v)
            {
                LogUtil.i("TradCsActivity", "标题栏左按钮被点击，跳至首页");
                skip(MainActivity.class);
                
            }
        });
        
        //下拉列表Item监听
//        poPListView.setOnItemClickListener(new OnItemClickListener()
//        {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent,
//                    View listView, int position, long id)
//            {
//                LogUtil.i("TradCsActivity", "position= "+position);
//                String clickItemText = poPListData.get(position);
//                titleTv.setText(clickItemText);
//                poPMenu.dismiss();
//                // 更新中间行业案例列表
//                // sendMessage(showList,tag);//showList点击item后要显示的showList,tag指定要更新的list
//            }
//        });
       
//        // 中间listView监听
//        tradCsListView.setOnItemClickListener(new OnItemClickListener()
//        {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View listView,
//                    int position, long id)
//            {
//
//                
//
//            }
//        });

        // 底部滑动菜单监听
        bottomMenuScrollView
                .setOnSelectedItemListener(new OnSelectedItemListener()
                {

                    @Override
                    public void doSelected(Object obj)
                    {
                        // TODO Auto-generated method stub

                    }
                });

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View listView, int position, long id)
    {
       switch (parent.getId())
    {
           //下拉列表
        case R.id.sltn_pop_listview_id:
            LogUtil.i("TradCsActivity", "position= "+position);
            String clickItemText = poPListData.get(position);
            titleTv.setText(clickItemText);
            poPMenu.dismiss();
            // 更新中间行业案例列表
            // sendMessage(showList,tag);//showList点击item后要显示的showList,tag指定要更新的list
            break;
            //中间案例列表
        case R.id.tradecase_listview:
            Intent intent = new Intent(TradCsActivity.this,
                    TradCsDtlActivity.class);
            intent.putExtra(Const.KEY_TRADCASE,
                    trdCsListData.get(position - 1));
            startActivity(intent);
            break;
    }
        
    }

    private void findCaseByText(CharSequence s)
    {
        // 编辑文本搜索
        List<TradeCase> showList = new ArrayList<TradeCase>();
        if (null != s.toString().trim())
        {
            for (TradeCase tradeCase : trdCsListData)
            {
                if (tradeCase.getTitle().contains(s))
                {
                    showList.add(tradeCase);
                }
            }
            if (null != showList && !showList.isEmpty())
            {
                sendMessage(showList, SEND_TRDCS_LIST_TAG);
            }
            else
            {
                Toast.makeText(TradCsActivity.this, "没有符合条件的案例", 3000);
            }
        }
        else
        {
            Toast.makeText(TradCsActivity.this, "请输入关键字", 3000);
        }

    }

    private void sendMessage(List<TradeCase> showList, int sendTrdcsListTag)
    {

        Message msg = Message.obtain();
        msg.obj = showList;
        msg.what = sendTrdcsListTag;
       mHandler.sendMessage(msg);

    }

    @Override
    public void onClick(View arg0)
    {

    }

    @Override
    public void init()
    {
        // TODO Auto-generated method stub

    }

   
    private List<TradeCase> getTrdCsListData()
    {

        List<TradeCase> list = new ArrayList<TradeCase>();

        TradeCase tradeCase = new TradeCase();
        tradeCase
                .setImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        tradeCase.setTitle("行业案例");
        tradeCase.setContent("行业案例内容");
        tradeCase.setIntro("行业案例简介");

        TradeCase tradeCase1 = new TradeCase();
        tradeCase1
                .setImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        tradeCase1.setTitle("行业案例1");
        tradeCase1.setContent("行业案例内容1");
        tradeCase1.setIntro("行业案例简介1");

        TradeCase tradeCase2 = new TradeCase();
        tradeCase2
                .setImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        tradeCase2.setTitle("行业案例2");
        tradeCase2.setContent("行业案例内容2");
        tradeCase2.setIntro("行业案例简介2");

        TradeCase tradeCase3 = new TradeCase();
        tradeCase3
                .setImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        tradeCase3.setTitle("行业案例3");
        tradeCase3.setContent("行业案例内容3");
        tradeCase3.setIntro("行业案例简介3");

        TradeCase tradeCase4 = new TradeCase();
        tradeCase4
                .setImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        tradeCase4.setTitle("行业案例4");
        tradeCase4.setContent("行业案例内容4");
        tradeCase4.setIntro("行业案例简介4");

        TradeCase tradeCase5 = new TradeCase();
        tradeCase5
                .setImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        tradeCase5.setTitle("行业案例5");
        tradeCase5.setContent("行业案例内容5");
        tradeCase5.setIntro("行业案例简介5");

        TradeCase tradeCase6 = new TradeCase();
        tradeCase6
                .setImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        tradeCase6.setTitle("行业案例6");
        tradeCase6.setContent("行业案例内容6");
        tradeCase6.setIntro("行业案例简介6");

        TradeCase tradeCase7 = new TradeCase();
        tradeCase7
                .setImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        tradeCase7.setTitle("行业案例7");
        tradeCase7.setContent("行业案例内容7");
        tradeCase7.setIntro("行业案例简介7");

        TradeCase tradeCase8 = new TradeCase();
        tradeCase8
                .setImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        tradeCase8.setTitle("行业案例8");
        tradeCase8.setContent("行业案例内容8");
        tradeCase8.setIntro("行业案例简介8");

        TradeCase tradeCase9 = new TradeCase();
        tradeCase9
                .setImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        tradeCase9.setTitle("行业案例9");
        tradeCase9.setContent("行业案例内容9");
        tradeCase9.setIntro("行业案例简介9");

        list.add(tradeCase1);
        list.add(tradeCase2);
        list.add(tradeCase3);
        list.add(tradeCase4);
        list.add(tradeCase5);
        list.add(tradeCase6);
        list.add(tradeCase7);
        list.add(tradeCase8);
        list.add(tradeCase9);

        return list;
    }

  

}

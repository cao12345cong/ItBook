package com.xtx.itbook.ui.terminology;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xtx.itbook.adapter.TerminologyAdapter;
import com.xtx.itbook.entity.Terminology;
import com.xtx.itbook.main.BaseActivity;
import com.xtx.itbook.main.MainActivity;
import com.xtx.itbook.service.TerminologyService;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.ui.TitleBarView;
import com.xtx.itbook.ui.TitleBarView.TitleBarListener;
import com.xtx.itbook.ui.terminology.SideBarView.OnTouchingLetterChangedListener;
import com.xtx.itbook.util.AccessUtil;
import com.xtx.itbook.util.Const;
import com.xtx.itbook.util.PinYinUtil;
import com.xtx.itbook.util.TimeMemoryUtil;

/**
 * 产品术语首页
 * @author caocong
 * 2013.10.24
 */
public class TerminologyActivity extends BaseActivity
{
    private static final String TAG = "ProductTermActivity";
    
    public static final int MSG_START_LOADING=0;//开始下载数据

    public static final int MSG_UPDATE_LISTVIEW = 1;// 加载数据成功，更新listview

    public static final int MSG_FAILURE_LISTVIEW = 2;// 加载数据失败
    
    public static final int MSG_SEARCH_LISTVIEW=3;//查找数据后，更新界面

    private TitleBarView titlebar;// 标题栏

    private ListView sortListView;//listView

    private SideBarView sideBar;// 侧边A-Z框

    private TextView Letterdialog;// 被选中的zif弹出提示
    
    private ProgressBar progressBar;//进度条

    private TerminologyAdapter adapter;// listView适配器

    private List<Terminology> sourceDateList;// 数据

    private TerminologyService service;

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case MSG_START_LOADING://开始加载
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case MSG_UPDATE_LISTVIEW:// 获取数据成功，更新listview界面
                    sourceDateList=(List<Terminology>) msg.obj;
                    if(sourceDateList!=null){
                        TimeMemoryUtil.getStartTimeMemory();
                        adapter.updateView(sourceDateList);
                        TimeMemoryUtil.showTimeMemory("updateView");
                    }
                    //进度条隐藏
                    progressBar.setVisibility(View.GONE);
                    break;
                case MSG_FAILURE_LISTVIEW://下载失败
                    //进度条隐藏
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(TerminologyActivity.this,
                            msg.obj.toString(),
                            1000).show();
                    break;
                
                case MSG_SEARCH_LISTVIEW://查找数据后，更新界面
                    if(sourceDateList!=null){
                        adapter.updateView(sourceDateList);
                    }
                    break;
            }
        };
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terminology_index);
        sourceDateList = new ArrayList<Terminology>();
        init();
    }
    /**
     * 初始化
     */
    @Override
    public void init()
    {
        initView();
        initListener();
        initData();
    }
    /**
     * 初始化控件
     */
    @Override
    public void initView()
    {
        // 标题栏
        titlebar = (TitleBarView) findViewById(R.id.titlebar);
        // 初始化侧边A-Z框
        sideBar = (SideBarView) findViewById(R.id.sb_terminology_sidrbarview);
        Letterdialog = (TextView) findViewById(R.id.tv_terminology_dialog);
        sideBar.setTextView(Letterdialog);
        // 初始化listview
        sortListView = (ListView) findViewById(R.id.lv_terminology);
        adapter = new TerminologyAdapter(this, sourceDateList);
        sortListView.setAdapter(adapter);
        
        //进度条
        progressBar=(ProgressBar) findViewById(R.id.pb_terminology);
    }
    /**
     * 初始化监听器
     */
    private void initListener()
    {
        // 标题栏
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
                skip(MainActivity.class);
            }

            @Override
            public void onSearchTextChanged(CharSequence s, int start,
                    int before, int count)
            {
                final String str=s.toString();
                new Thread()
                {
                    public void run()
                    {
                        filterData(str);
                    }
                }.start();

            }
        });
        // A-Z索引
        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener()
        {

            @Override
            public void onTouchingLetterChanged(String s)
            {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1)
                {
                    sortListView.setSelection(position);
                }

            }
        });
        // listView点击
        sortListView.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {

                Intent intent = new Intent(TerminologyActivity.this,
                        TerminologyDetailActivity.class);
                intent.putExtra(Const.KEY_TERMINOLOGY,
                        adapter.getItem(position));
                startActivity(intent);
            }
        });

    }
    /**
     * 初始化数据
     */
    private void initData()
    {
        
        service = new TerminologyService(this, mHandler);
        new Thread(){
            public void run() {
                //显示progreebar
                Message msg=Message.obtain(mHandler, MSG_START_LOADING);
                msg.sendToTarget();
                try
                {
                    service.startService(AccessUtil.instance().getSessionId(),CN);
                }
                catch (Exception e1)
                {
                    //显示错误提示
                    Message msg2=Message.obtain(mHandler, MSG_FAILURE_LISTVIEW,"加载数据失败");
                    msg2.sendToTarget();
                } 
            }
        }.start();
       

    }

    

    /**
     * 根据关键字搜索数据
     * 三种查询方式：1.汉字查询，2.全拼查询，3.汉字拼音首字母查询
     * @param filterStr 搜索关键字
     * @return
     */
    private void filterData(String filterStr)
    {
        List<Terminology> filterDateList = new ArrayList<Terminology>();
        long startTime = System.currentTimeMillis();
        if (TextUtils.isEmpty(filterStr))
        {
            // 关键字为空，可能是清空
            filterDateList = sourceDateList;
            for (Terminology term : filterDateList)
            {
                if (term.getWord() != null)
                {
                    term.setWord(
                            term.getWord().toString());
                }

            }

        }
        else
        {
            // 清空数据集
            filterDateList.clear();
            // 判断输入的搜索字符串中是否含有中文
            boolean isContainChinese = PinYinUtil.isContainChinese(filterStr);
            // 遍历并查找符合条件的数据集
            for (Terminology terminology : sourceDateList)
            {
                if (terminology.getWord() == null)
                {
                    break;
                }
                String word = terminology.getWord()
                        .toString();
                String fullSpell = terminology.getFullSpell().toLowerCase();
                String firstSpell = terminology.getFirstSpell()
                        .toLowerCase();
                ArrayList<String> spellList = PinYinUtil.getFullSpellList(word);
                // 三种查询方式：1.汉字查询，2.全拼查询，3.拼音首字母查询
                int start = 0;
                int end = 0;
                if (isContainChinese)
                {
                    // 1.汉字查找
                    if (word.indexOf(filterStr) != -1)
                    {
                        // 让匹配的结果变色
                        // start = word.indexOf(filterStr);
                        // end = start + filterStr.length();
                        // terminologySort.getTerminology().setWord(
                        // PinYinUtil
                        // .changeColorOfString(word, start, end));
                        filterDateList.add(terminology);
                    }
                }
                else
                {
                    boolean canFindByPinYin = false;
                    // 2.全拼查询，条件有两个：输入的第一个字母必须是术语中某个字的首字母，并且被包含在术语全拼中
                    boolean isStart = false;
                    for (int i = 0; i < spellList.size(); i++)
                    {
                        String strlower = spellList.get(i).toLowerCase();
                        if (strlower.startsWith(filterStr.substring(0, 1)
                                .toLowerCase()))
                        {
                            start = i;
                            isStart = true;
                            break;
                        }
                    }
                    if (isStart
                            && fullSpell.indexOf(filterStr.toLowerCase()) != -1)
                    {
                        end = getEndOfResult(spellList, filterStr, start);
                        // terminologySort.getTerminology().setWord(
                        // PinYinUtil
                        // .changeColorOfString(word, start, end));
                        canFindByPinYin = true;

                    }
                    // 3.拼音首字母查询
                    if (firstSpell.indexOf(filterStr.toLowerCase()) != -1)// 拼音首字母查询
                    {

                        // start = firstSpell.indexOf(filterStr.toLowerCase());
                        // end = start + filterStr.length();
                        // terminologySort.getTerminology().setWord(
                        // PinYinUtil
                        // .changeColorOfString(word, start, end));
                        canFindByPinYin = true;

                    }
                    if (canFindByPinYin)
                    {
                        filterDateList.add(terminology);
                    }

                }

            }

        }

        Collections.sort(filterDateList, new PinyinComparator());
        sourceDateList=filterDateList;
        Message msg=Message.obtain(mHandler, MSG_SEARCH_LISTVIEW);
        msg.sendToTarget();
    }

    /**
     * 搜索字符串占搜索结果的最后的一位
     * @param spellList
     * @param filterStr
     * @param start
     * @return
     */
    private int getEndOfResult(ArrayList<String> spellList, String filterStr,
            int start)
    {
        int count = spellList.size();
        int end = start + 1;
        StringBuffer sb = new StringBuffer();
        for (int i = start; i < count; i++)
        {

            sb = sb.append(spellList.get(i));
            if (sb.toString().toLowerCase().indexOf(filterStr.toLowerCase()) != -1)
            {
                end = i + 1;
                break;
            }

        }
        return end;
    }

    @Override
    public void onClick(View v)
    {

    }

   

}

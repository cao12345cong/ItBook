package com.xtx.itbook.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xtx.itbook.adapter.SltnPopAdapter;
import com.xtx.itbook.adapter.SolutionListAdapter;
import com.xtx.itbook.data.CacheDirectory;
import com.xtx.itbook.db.InitDbService;
import com.xtx.itbook.entity.ListMenuEntry;
import com.xtx.itbook.entity.RequestRecord;
import com.xtx.itbook.entity.SglSolutions;
import com.xtx.itbook.entity.SltnRltd;
import com.xtx.itbook.net.NetUtil;
import com.xtx.itbook.ui.BottomMenuScrollView;
import com.xtx.itbook.ui.BottomMenuScrollView.OnSelectedItemListener;
import com.xtx.itbook.ui.ListMenu;
import com.xtx.itbook.ui.ListMenu.DataItemPostion;
import com.xtx.itbook.ui.LoaderListView;
import com.xtx.itbook.ui.LoaderListView.LoadNotifyer;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.util.AccessUtil;
import com.xtx.itbook.util.Const;
import com.xtx.itbook.util.ConstantInterface;
import com.xtx.itbook.util.LogUtil;
import com.xtx.itbook.util.SltnUtil;
import com.xtx.itbook.util.StringUtil;

/**
 * 解决方案首页
 * @author chenhg
 *
 */
public class SolutionsActivity extends BaseActivity implements LoadNotifyer,
        OnItemClickListener, OnClickListener, DataItemPostion,
        ConstantInterface
{

    private static final String TAG = ProductActivity.class.getName();

    private int cur_page = 1; // 请求第几页

    private static final int page_size = 12; // 一页有多少条数据 .

    private ImageView sltnBackIv;// 标题栏返回

    private ImageView sltnSearchIv;// 标题栏搜索

    private EditText sltnSearchEt;// 搜索编辑框

    // 解决方案标题栏下拉列表
    private LinearLayout poPLL;// 下拉触发布局

    private TextView poPTv;// 下拉文本

    PopupWindow popWnd = null;

    private ListView popListView;

    private SltnPopAdapter sltnPopAdapter;

    private LayoutInflater layoutInflater;

    private int count = 0;// 计算点击搜索按钮次数，第一次点击显示搜索编辑框，否则执行搜索

    private LoaderListView solutionListView;// 方案列表

    private SolutionListAdapter solutionAdapter = null;// 方案列表适配器

    private List<SglSolutions> solutionListData;// 联网获取的数据集合

    private List<SglSolutions> showList;// 中间listView要显示的解决方案集合

    private ArrayList<SltnRltd> sglRltdListData;

    private List<String> list;// 底部滑动菜单数据初始化

    private BottomMenuScrollView bottomMenu;// 底部滑动菜单

    private InitDbService initDbService = null;

    private FinalDb finalDb = null;

    private List<ListMenuEntry> listMenus;// 二级菜单

    private ListMenu listMenu;// 顶端下拉菜单

    private long id;// 当前 菜单的id

    /**底部 菜单对应下拉列表的映射*/
    private LinkedHashMap<ListMenuEntry, ArrayList<ListMenuEntry>> menu = CacheDirectory
            .instance().getSolutionMenu();

    /**
     * 获取menuMap的Value值 ArrayList<ListMenuEntry>
     * 即对应的下拉列表
     * @param position 位置
     * @return
     */
    public ArrayList<ListMenuEntry> getMenus(int position)
    {
        List<ListMenuEntry> listKey = new ArrayList<ListMenuEntry>(
                menu.keySet());
        return menu.get(listKey.get(position));
    }

    @ViewInject(id = R.id.solution_titlebar)
    LinearLayout titleLinearLayout;

    /**
     * 滑动菜单元素数组
     */
    private static String [] array =
        {
                "服务器", "交换机", "路由器", "服务器", "计算机", "手机"
        };

    /**
     * Handler更新界面
     */
    Handler mHandler = new Handler()
    {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case CONSTANT_0:
                    List<SglSolutions> list = new ArrayList<SglSolutions>();
                    list = (List<SglSolutions>) msg.obj;
                    solutionAdapter.changeDataUdapteView(list);
                    break;
                case CONSTANT_1: // 更新顶端下拉菜单

                    break;
                case CONSTANT_2:
                    stopLoadData();
                    solutionListView.stopLoadMore();
                    break;

            }

        };
    };

    private void stopLoadData()
    {
        // progress.setVisibility(View.GONE);
        solutionListView.setVisibility(View.VISIBLE);
        bottomMenu.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soluiton);
        LogUtil.i("SolutionsActivity", "menu.size=="+menu.size());
      
        initDbService = new InitDbService(SolutionsActivity.this);
        finalDb = FinalDb.create(this, DB_NAME, true);
        onLoadMore();

        init();
        initView();
        initData();

    }

    @Override
    public void init()
    {

        list = getPopList();// 后面会随滑动菜单改变
        solutionListData = getSltnListData();// 会随着下拉菜单改变
        showList = solutionListData;

    }

    @Override
    public void initView()
    {
        // 底部滑动菜单
        bottomMenu = (BottomMenuScrollView) findViewById(R.id.sltn_bottom_menu_id);

        layoutInflater = LayoutInflater.from(SolutionsActivity.this);

        // 标题下拉列表
        popListView = (ListView) layoutInflater.inflate(
                R.layout.sltn_pop_listview, null);
        sltnPopAdapter = new SltnPopAdapter(SolutionsActivity.this, list);
        popListView.setAdapter(sltnPopAdapter);
        poPLL = (LinearLayout) titleLinearLayout
                .findViewById(R.id.sltn_title_ll);
        poPTv = (TextView) titleLinearLayout
                .findViewById(R.id.sltn_titlebar_title);
        // 标题栏控件
        sltnBackIv = (ImageView) titleLinearLayout
                .findViewById(R.id.sltn_titlebar_goback);
        sltnSearchIv = (ImageView) titleLinearLayout
                .findViewById(R.id.sltn_search_btn);
        sltnSearchEt = (EditText) titleLinearLayout
                .findViewById(R.id.sltn_search_et);

        // 中间listView

        solutionListView = (LoaderListView) findViewById(R.id.list_solutions);
    }

    /**
     * 拿到数据进行处理
     */
    private void initData()
    {
        initListener();
        initLoaderData();
        initMenuData();// 第一层滑动菜单数据

    }

    /**实例化解决方案数据.*/
    public void initLoaderData()
    {
        solutionListView.setPullRefreshEnable(false);
        solutionListView.setPullLoadEnable(true);

        solutionListView.setLoadNotifyer(this);
        solutionListData = new ArrayList<SglSolutions>();
        solutionListData = getSltnListData();// 会随着下拉菜单改变
        showList = solutionListData;
        solutionAdapter = new SolutionListAdapter(SolutionsActivity.this,
                solutionListData);
        solutionListView.setAdapter(solutionAdapter);
    }

    /**实例化 底部 菜单 数据.*/
    public void initMenuData()
    {
//        menu = getBottomMenuMap();
        List<ListMenuEntry> listMenuEntry = new ArrayList<ListMenuEntry>(
                menu.keySet());
        // listMenuEntry = getBottomListData();
        bottomMenu.addView(listMenuEntry);
        LogUtil.i("SolutionsActivity", "底部菜单数据已经获取listMenuEntry.size()=="
                + listMenuEntry.size());
        /**同时加载底部第一个菜单和顶部第一个下拉菜单出现的数据.*/
        listMenus = initDbService.getSecondCatalog(listMenuEntry.get(CONSTANT_0).getId(), CN);//
        
        LogUtil.i("SolutionsActivity", "设置第一个底部菜单数据listMenus.size() =="+listMenus.size());
        poPTv.setText(listMenus.get(CONSTANT_0).getName());

    }

    public List<String> getPopList()
    {
        List<String> popList = new ArrayList<String>();
        // 滑动菜单元素集合

        for (String s : array)
        {
            popList.add(s);
        }
        return popList;

    }

    private void initListener()
    {
        Log.i("SolutionsActivity", "进入initListener");

        // 标题栏返回、搜索
        sltnBackIv.setOnClickListener(this);
        sltnSearchIv.setOnClickListener(this);
        poPLL.setOnClickListener(this);
        solutionListView.setOnItemClickListener(this);
        popListView.setOnItemClickListener(this);
        initBottomListener();

    }

    /**底部菜单的 监听事件.*/
    private void initBottomListener()
    {
        bottomMenu.setOnSelectedItemListener(new OnSelectedItemListener()
        {

            @Override
            public void doSelected(Object obj)
            {
                ListMenuEntry listMenuEntry = (ListMenuEntry) obj;
                listMenus = initDbService.getSecondCatalog(
                        listMenuEntry.getId(), CN);
                final String menu[] = new String [listMenus.size()];
                for (int i = 0; i < listMenus.size(); i++)
                {
                    menu[i] = listMenus.get(i).getName();
                }
                listMenu = new ListMenu(SolutionsActivity.this, LayoutInflater
                        .from(SolutionsActivity.this).inflate(
                                R.layout.product_list, null), 200, 300, menu);
                listMenu.setDataPostion(new DataItemPostion()
                {

                    @Override
                    public int getPostion(int position)
                    {
                        poPTv.setText(menu[position]);

                        data(listMenus.get(position).getId());

                        hiddenListMenu();

                        return position;
                    }
                });
            }
        });
    }

    /***
     * PopWindow 点击消失. 顶端下拉列表菜单.
     * TODO.
     * 见父类.
     */
    public boolean onTouchEvent(MotionEvent event)
    {
        hiddenListMenu();

        return super.onTouchEvent(event);
    }

    /**隐藏顶端的下拉列表.*/
    private void hiddenListMenu()
    {
        if (listMenu != null && listMenu.isShowing())
        {
            listMenu.dismiss();
            listMenu = null;
        }

    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {

            case R.id.sltn_title_ll:
                if (null == popWnd)
                {
                    initPop();
                }
                if (!popWnd.isShowing())
                {
                    popWnd.showAsDropDown(titleLinearLayout,
                            titleLinearLayout.getWidth() / 4, 0);
                }
                else
                {
                    popWnd.dismiss();
                }
                break;
            case R.id.sltn_titlebar_goback:

                skip(MainActivity.class);

                break;

            case R.id.sltn_search_btn:
                LogUtil.i("SolutionsActivity", "搜索被点击");
                if (count == 0)
                {
                    // 隐藏标题栏
                    poPLL.setVisibility(View.GONE);
                    // 显示输入框
                    sltnSearchEt.setVisibility(View.VISIBLE);
                    // sltnSearchEt.setBackgroundDrawable(new
                    // ColorDrawable(0xffffffff));
                    LogUtil.i("SolutionsActivity", "输入框已经显示");
                    count++;
                }
                else
                {

                    if (null != sltnSearchEt.getText()
                            && sltnSearchEt.getText().toString().trim()
                                    .length() != 0)
                    {
                        findSltnListByEt(sltnSearchEt.getText().toString()
                                .trim());
                        LogUtil.i(
                                "SolutionsActivity",
                                "搜索完毕：sltnSearchEt.getText()=="
                                        + sltnSearchEt.getText());

                    }
                    else
                    {

                        // 隐藏输入框
                        sltnSearchEt.setVisibility(View.GONE);
                        // 显示标题栏
                        poPLL.setVisibility(View.VISIBLE);
                        LogUtil.i("SolutionsActivity", "搜索内容为空，显示标题栏");
                        count = 0;
                    }

                }
                break;
        }
    }

    public void findSltnListByEt(String etText)
    {

        // 遍历集合拿到对象,用标题和sltnSearchEt.getText()进行比较
        // 搜索到的符合条件的解决方案的集合后用handle执行更新
        showList = new ArrayList<SglSolutions>();

        // 拿数组中都元素和sltnSearchEt.getText()进行比较
        for (SglSolutions sglSolutions : solutionListData)
        {

            if (sglSolutions.getSltnTitle().contains(etText.toString().trim()))
            {
                showList.add(sglSolutions);
            }

        }

        if (null != showList && !showList.isEmpty())
        {

            // 发送消息，更新列表
            sendMessage(showList, CONSTANT_0);

        }
        else
        {
            Toast.makeText(SolutionsActivity.this, "没有符合条件的方案", 3000).show();
        }
    }

    /**
     * 
     * @param showList更新后列表
     * @param tag 更新标志，指定要更新的列表
     * 
     */
    public void sendMessage(List<SglSolutions> showList, int tag)
    {
        Message msg = Message.obtain();
        msg.what = tag;
        msg.obj = showList;
        mHandler.sendMessage(msg);
    }

    /**
     * 初始化下拉菜单
     */
    public void initPop()
    {
        int width = (int) (titleLinearLayout.getWidth() / 2);
        int height = LayoutParams.WRAP_CONTENT;
        popWnd = new PopupWindow(popListView, width, height, true);
        popWnd.setBackgroundDrawable(new ColorDrawable(0xffffffff));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View listView, int position,
            long id)
    {
        switch (parent.getId())
        {
        // 解决方案中间listView
            case R.id.list_solutions:
                SglSolutions sglSolutions = showList.get(position - 1);
                SltnUtil.sglSltnMap.remove(Const.KEY_SLTN_DTL_SHOW);
                SltnUtil.sglSltnMap.put(Const.KEY_SLTN_DTL_SHOW, sglSolutions);
                skip(SglSltnActivity.class);
                break;
            // 标题下拉列表
            case R.id.sltn_pop_listview_id:

                String clickItemText = list.get(position);

                poPTv.setText(clickItemText);
                popWnd.dismiss();
                // 更新中间解决方案列表
                // sendMessage(showList,tag);//showList点击item后要显示的showList,tag指定要更新的list
                break;
        }

    }

    /**
     * 第一次根据一二级目录加载初始.
     */
    public void data(long pid)
    {
        List<RequestRecord> requestRecord = initDbService.getRecord(pid, CN);
        LogUtil.i("SolutionsActivity", "requestRecord.size() == "
                + requestRecord.size());

        if (requestRecord.size() > 0)
        {
            LogUtil.i(
                    "SolutionsActivity",
                    "获取jsonParam == "
                            + getRequestJson(pid, requestRecord.get(CONSTANT_0)
                                    .getTime()));

            getLoaderData(getRequestJson(pid, requestRecord.get(CONSTANT_0)
                    .getTime()));
            return;
        }
        else
        {
            getLoaderData(getRequestJson(pid, 0));
        }

    }

    /**
     * 联网加载数据.
     * @param jsonObject
     */
    public void getLoaderData(JSONObject jsonObject)
    {
        NetUtil.getHttp().post(StringUtil.getUrl(R.string.sltn_url),
                NetUtil.getEntity(jsonObject), Const.HEAD_TYPE,
                new AjaxCallBack<String>()
                {
                    public void onSuccess(String t)
                    {
                        LogUtil.i("SolutionsActivity", "联网加载参数t ==" + t);
                        super.onSuccess(t);
                        parseJson(t);

                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                            String strMsg)
                    {
                        super.onFailure(t, errorNo, strMsg);

                        try
                        {
                            throw t;
                        }
                        catch (Throwable e)
                        {
                            LogUtil.i("SolutionsActivity", errorNo + ":" + strMsg);
                        }
                        finally
                        {
                            NetUtil.toast(SolutionsActivity.this, "请求失败");

                        }
                    }

                });
    }

    /**处理请求参数.*/
    public JSONObject getRequestJson(long pid, long time)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            LogUtil.i("SolutionsActivity", "请求参数jsonId 数据异常pid=="+pid);
            jsonObject.put("pid", pid);
            jsonObject.put("curPage", cur_page);
            jsonObject.put("pageSize", page_size);
            jsonObject.put("language", CN);
            jsonObject.put("time", time);
           jsonObject.put("sessionId", AccessUtil.instance().getSessionId());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            LogUtil.i("SolutionsActivity", "json 数据异常 getRequestJson(...)");
        }
        return jsonObject;
    }

    /**
     * 解析json头部信息.
     * @param json
     */
    public void parseJson(String t)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(t);
            if (jsonObject.getBoolean("succ")) // 请求成功.
            {
                LogUtil.i("SolutionsActivity", "成功获取数据 jsonObject=="
                        + jsonObject.toString());
                String jsonList = jsonObject.optString("list");
                if(null != jsonList && jsonList.trim().length() != 0){
                    LogUtil.i("SolutionsActivity",
                            "获取list数据jsonList ==" + jsonList);
                    LogUtil.i("SolutionsActivity",
                            "获取list数据jsonList.trim().length() ==" + jsonList.trim().length());
                    List<SglSolutions> list = JSON.parseArray(jsonList,
                            SglSolutions.class);
                    LogUtil.i("SolutionsActivity",
                            "获取数据list.size() ==" + list.size());
                    addData(list);

                    // 信息请求保存
                    RequestRecord record = new RequestRecord();
                    record.setKind(CONSTANT_2);
                    record.setPid(jsonObject.getLong("pid"));
                    record.setLanguage(CN);
                    record.setTime(jsonObject.getLong("time"));
                    initDbService.save(record);
                }else
                {
                    // 信息请求保存
                    RequestRecord record = new RequestRecord();
                    record.setKind(CONSTANT_2);
                    record.setPid(2);//jsonObject.getLong("pid")
                    record.setLanguage(jsonObject.getString("language"));
                    record.setTime(jsonObject.getLong("time"));
                   
                    initDbService.save(record);
                    
                }
                
            }
            else
            { // 失败
                Message message = mHandler.obtainMessage();
                message.what = CONSTANT_2;
                message.sendToTarget();

            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 向listView 中添加信息.
     * @param list
     */
    public void addData(List<SglSolutions> list)
    {
        if (list != null)
        {
            if (list.size() > 0)
            {
                for (SglSolutions sglSolutions : list)
                {
                    solutionListData.add(sglSolutions);
                }
                Message message = mHandler.obtainMessage();
                message.what = CONSTANT_1;
                message.sendToTarget();
            }

        }

    }

    /**
     * 获取解决方案中间列表集合
     * @return
     */

    public List<SglSolutions> getSltnListData()
    {
        // listView数据初始化,在第一次加载界面时联网获取
        List<SglSolutions> sltnList = new ArrayList<SglSolutions>();

        SglSolutions sglSolution1 = new SglSolutions();
        sglSolution1
                .setSltnImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        sglSolution1.setSltnTitle("解决方案1");
        sglSolution1.setSltnContent("解决方案详细内容");
        sglSolution1.setSltnRltdList(getSglRltdListData());

        SglSolutions sglSolution2 = new SglSolutions();
        sglSolution2
                .setSltnImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        sglSolution2.setSltnTitle("解决方案2");
        sglSolution2.setSltnContent("解决方案详细内容");
        sglSolution2.setSltnRltdList(getSglRltdListData());

        SglSolutions sglSolution3 = new SglSolutions();
        sglSolution3
                .setSltnImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        sglSolution3.setSltnTitle("解决方案3");
        sglSolution3.setSltnContent("解决方案详细内容");
        sglSolution3.setSltnRltdList(getSglRltdListData());

        SglSolutions sglSolution4 = new SglSolutions();
        sglSolution4
                .setSltnImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        sglSolution4.setSltnTitle("解决方案4");
        sglSolution4.setSltnContent("解决方案详细内容");
        sglSolution4.setSltnRltdList(getSglRltdListData());

        SglSolutions sglSolution5 = new SglSolutions();
        sglSolution5
                .setSltnImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        sglSolution5.setSltnTitle("解决方案5");
        sglSolution5.setSltnContent("解决方案详细内容");
        sglSolution5.setSltnRltdList(getSglRltdListData());

        SglSolutions sglSolution6 = new SglSolutions();
        sglSolution6
                .setSltnImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        sglSolution6.setSltnTitle("解决方案6");
        sglSolution6.setSltnContent("解决方案详细内容");
        sglSolution6.setSltnRltdList(getSglRltdListData());

        SglSolutions sglSolution7 = new SglSolutions();
        sglSolution7
                .setSltnImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        sglSolution7.setSltnTitle("解决方案7");
        sglSolution7.setSltnContent("解决方案详细内容");
        sglSolution7.setSltnRltdList(getSglRltdListData());

        SglSolutions sglSolution8 = new SglSolutions();
        sglSolution8
                .setSltnImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        sglSolution8.setSltnTitle("解决方案8");
        sglSolution8.setSltnContent("解决方案详细内容");
        sglSolution8.setSltnRltdList(getSglRltdListData());

        SglSolutions sglSolution9 = new SglSolutions();
        sglSolution9
                .setSltnImageUrl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        sglSolution9.setSltnTitle("解决方案9");
        sglSolution9.setSltnContent("解决方案详细内容");
        sglSolution9.setSltnRltdList(getSglRltdListData());

        sltnList.add(sglSolution1);
        sltnList.add(sglSolution2);
        sltnList.add(sglSolution3);
        sltnList.add(sglSolution4);
        sltnList.add(sglSolution5);
        sltnList.add(sglSolution6);
        sltnList.add(sglSolution7);
        sltnList.add(sglSolution8);
        sltnList.add(sglSolution9);
        return sltnList;
    }

    /**
     * 拿到解决方案相关详细内容的集合
     * @return
     */
    public List<SltnRltd> getSglRltdListData()
    {
        sglRltdListData = new ArrayList<SltnRltd>();
        SltnRltd sltnRltd1 = new SltnRltd();
        sltnRltd1.setSltnDtlTitle("背景/挑战");
        sltnRltd1
                .setSltnDtlContentUrl("http://www.eoeandroid.com/thread-182416-1-1.html");
        sglRltdListData.add(sltnRltd1);

        SltnRltd sltnRltd2 = new SltnRltd();
        sltnRltd2.setSltnDtlTitle("解决方案");
        sltnRltd2
                .setSltnDtlContentUrl("http://www.eoeandroid.com/thread-182416-1-1.html");
        sglRltdListData.add(sltnRltd2);

        SltnRltd sltnRltd3 = new SltnRltd();
        sltnRltd3.setSltnDtlTitle("方案亮点");
        sltnRltd3
                .setSltnDtlContentUrl("http://www.eoeandroid.com/thread-182416-1-1.html");
        sglRltdListData.add(sltnRltd3);
        return sglRltdListData;
    }

    public LinkedHashMap<ListMenuEntry,ArrayList<ListMenuEntry>> getBottomMenuMap()
    {
        List<ListMenuEntry> list = new ArrayList<ListMenuEntry>();
        ListMenuEntry listMenuEntry = new ListMenuEntry();
        listMenuEntry
                .setImageurl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        listMenuEntry.setName("服务器1");
        listMenuEntry.setId(1);
        
        ListMenuEntry listMenuEntry1 = new ListMenuEntry();
        listMenuEntry1
                .setImageurl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        listMenuEntry1.setName("服务器2");
        listMenuEntry1.setId(2);

        ListMenuEntry listMenuEntry2 = new ListMenuEntry();
        listMenuEntry2
                .setImageurl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        listMenuEntry2.setName("服务器3");
        listMenuEntry2.setId(3);

        ListMenuEntry listMenuEntry3 = new ListMenuEntry();
        listMenuEntry3
                .setImageurl("http://img1.bdstatic.com/img/image/9448718367adab44aed19135dceb11c8701a18bfbbf.jpg");
        listMenuEntry3.setName("服务器4");
        listMenuEntry3.setId(4);
        
        list.add(listMenuEntry);
        list.add(listMenuEntry1);
        list.add(listMenuEntry2);
        list.add(listMenuEntry3);
        LinkedHashMap<ListMenuEntry,ArrayList<ListMenuEntry>> map = new LinkedHashMap<ListMenuEntry, ArrayList<ListMenuEntry>>();
        map.put(listMenuEntry1, (ArrayList<ListMenuEntry>) list);
        map.put(listMenuEntry2, (ArrayList<ListMenuEntry>) list);
        map.put(listMenuEntry3, (ArrayList<ListMenuEntry>) list);
        return map;
    }

    /**
     * 底部滑动菜单监听端口
     * @param obj
     */

    @Override
    public void onRefresh()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLoadMore()
    {
        /**初始化数据*/
        data(2);

    }

    /***
     * 实现接口中的 数据. 得到点击popWindow时候的菜单是哪一项..
     * TODO.
     * 见父类.
     */
    public int getPostion(int position)
    {

        Log.i("SolutionsActivity", "获取到数据，菜单项为：" + position);
        return position;
    }

}

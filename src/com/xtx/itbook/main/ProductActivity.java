package com.xtx.itbook.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xtx.itbook.adapter.ProductListAdapter;
import com.xtx.itbook.data.CacheDirectory;
import com.xtx.itbook.db.InitDbService;
import com.xtx.itbook.entity.Directory;
import com.xtx.itbook.entity.ListMenuEntry;
import com.xtx.itbook.entity.Product;
import com.xtx.itbook.entity.RequestRecord;
import com.xtx.itbook.net.NetUtil;
import com.xtx.itbook.ui.BottomMenuScrollView;
import com.xtx.itbook.ui.BottomMenuScrollView.OnSelectedItemListener;
import com.xtx.itbook.ui.ListMenu;
import com.xtx.itbook.ui.ListMenu.DataItemPostion;
import com.xtx.itbook.ui.LoaderListView;
import com.xtx.itbook.ui.LoaderListView.LoadNotifyer;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.ui.TitleBarView;
import com.xtx.itbook.ui.TitleBarView.TitleBarListener;
import com.xtx.itbook.util.AccessUtil;
import com.xtx.itbook.util.Const;
import com.xtx.itbook.util.ConstantInterface;
import com.xtx.itbook.util.LogUtil;
import com.xtx.itbook.util.StringUtil;

/**
 * 产品列表界面.
 * @author Abelart.
 */
public class ProductActivity extends BaseActivity implements LoadNotifyer,
        ConstantInterface
{

    private static final String TAG = ProductActivity.class.getName();

    private List<Product> mListItems;

    private LoaderListView loaderListView; // 产品列表

    private ProductListAdapter productListAdapter;

    private int cur_page = 1; // 请求第几页

    private static final int page_size = 12; // 一页有多少条数据 .

    private TitleBarView productTitle; // 顶端动作条.

    private ListMenu listMenu; // 顶端下拉列表.

    private BottomMenuScrollView bottomMenuScrollView; // 底部的动态

    private InitDbService initDbService = null;

    private ProgressBar progress = null;

    private long id; // 当前菜单的ID。

    private ArrayList<ArrayList<ListMenuEntry>> listMenus = null;

    /**底部菜单对应 下拉列表的映射.*/
    private LinkedHashMap<ListMenuEntry, ArrayList<ListMenuEntry>> menuMap = CacheDirectory
            .instance().getProductMenu();

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

    /**
     * 相关的消息 数据处理.
     */
    private Handler postHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case CONSTANT_0: // 加载数据 信息是存在的.
                    stopLoadData();
                    LogUtil.i(TAG, "加载数据");
                    productListAdapter.notifyDataSetChanged();
                    cur_page = cur_page + 1;
                    break;
                case CONSTANT_1: // 更新顶端下拉菜单.
                    break;
                case CONSTANT_2: // 没有数据 或者请求失败
                    NetUtil.toast(ProductActivity.this, "信息有误");
                    stopLoadData();
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);
        init();
    }

    public void init()
    {
        initView();
        initDbService = new InitDbService(this);

        addListener();
        initLoaderData();
        initMenuData();
    }

    /**查找控件.*/
    public void initView()
    {
        productTitle = (TitleBarView) findViewById(R.id.product_title);

        loaderListView = (LoaderListView) findViewById(R.id.list_product);

        /**底部视图列表.*/
        bottomMenuScrollView = (BottomMenuScrollView) findViewById(R.id.menu_bottom_scroll);

        progress = (ProgressBar) findViewById(R.id.product_bar);
        progress.setVisibility(View.VISIBLE);
    }

    /**实例化产品数据.*/
    public void initLoaderData()
    {
        loaderListView.setPullRefreshEnable(false);
        loaderListView.setPullLoadEnable(true);

        loaderListView.setLoadNotifyer(this);
        mListItems = new ArrayList<Product>();

        productListAdapter = new ProductListAdapter(this, mListItems);
        loaderListView.setAdapter(productListAdapter);
        data(getMenus(CONSTANT_0).get(CONSTANT_0).getId());

        id = getMenus(CONSTANT_0).get(CONSTANT_0).getId();
    }

    /**实例化 底部 菜单 数据.*/
    public void initMenuData()
    {
        List<ListMenuEntry> listMenuEntry = new ArrayList<ListMenuEntry>(
                menuMap.keySet());

        bottomMenuScrollView.addView(listMenuEntry);

        /**同时加载底部第一个菜单和顶部第一个下拉菜单出现的数据.*/
        productTitle.setTitle(getMenus(CONSTANT_0).get(CONSTANT_0).getName());
    }

    /**相关的监听事件.*/
    private void addListener()
    {
        initTitleListener();
        initListListener();
        initBottomListener();
    }

    /**顶端动作条的监听事件.*/
    private void initTitleListener()
    {
        productTitle.setTitleBarListener(new TitleBarListener()
        {

            public void onSearchTextChanged(CharSequence s, int start,
                    int before, int count)
            {

            }

            public void onClickTitleText(View v)
            {

            }

            public void onClickRightImg(View v)
            {

            }

            public void onClickLeftImg(View v)
            {
                ProductActivity.this.finish();
            }
        });
    }

    /**中间list数据的点击事件.*/
    private void initListListener()
    {
        loaderListView.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3)
            {
                productListAdapter.getItem(arg2);
            }
        });
    }

    /**底部菜单的 监听事件.*/
    private void initBottomListener()
    {
        bottomMenuScrollView
                .setOnSelectedItemListener(new OnSelectedItemListener()
                {

                    @Override
                    public void doSelected(Object obj)
                    {
                        final ListMenuEntry listMenuEntry = (ListMenuEntry) obj;
                        final String menu[] = new String [menuMap.get(
                                listMenuEntry).size()];
                        for (int i = 0; i < menuMap.get(listMenuEntry).size(); i++)
                        {
                            menu[i] = menuMap.get(listMenuEntry).get(i)
                                    .getName();
                        }
                        listMenu = new ListMenu(ProductActivity.this,
                                LayoutInflater.from(ProductActivity.this)
                                        .inflate(R.layout.product_list, null),
                                200, 300, menu);
                        listMenu.setDataPostion(new DataItemPostion()
                        {

                            @Override
                            public int getPostion(int position)
                            {
                                productTitle.setTitle(menuMap
                                        .get(listMenuEntry).get(position)
                                        .getName());

                                data(menuMap.get(listMenuEntry).get(position)
                                        .getId());
                                id = menuMap.get(listMenuEntry).get(position)
                                        .getId();
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

    public void onRefresh()
    {

    }

    public void onLoadMore()
    {
        data(id);
    }

    private void stopLoadData()
    {
        progress.setVisibility(View.GONE);
        loaderListView.stopLoadMore();
        loaderListView.setVisibility(View.VISIBLE);
        bottomMenuScrollView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub

    }

    /**
     * 第一次根据二级目录加载初始.
     */
    public void data(long pid)
    {
        List<RequestRecord> requestRecord = new ArrayList<RequestRecord>();
        requestRecord = initDbService.getRecord(pid, CN);

        if (requestRecord.size() > 0)
        {
            getLoaderData(getRequestJson(pid, requestRecord.get(CONSTANT_0)
                    .getTime()));
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
        NetUtil.getHttp().post(StringUtil.getUrl(R.string.productlist),
                NetUtil.getEntity(jsonObject), Const.HEAD_TYPE,
                new AjaxCallBack<String>()
                {
                    public void onSuccess(String t)
                    {
                        super.onSuccess(t);
                        LogUtil.i(TAG, "getLoaderData:" + t);
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
                            LogUtil.i(TAG, errorNo + ":" + strMsg);
                        }
                        finally
                        {
                            NetUtil.toast(ProductActivity.this, "请求失败");

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
            jsonObject.put("pid", pid);
            jsonObject.put("kind", CONSTANT_1);
            jsonObject.put("curPage", cur_page);
            jsonObject.put("pageSize", page_size);
            jsonObject.put("language", CN);
            jsonObject.put("time", time);
            jsonObject.put("sessionId", AccessUtil.instance().getSessionId());
            LogUtil.i(TAG, "getRequestJson:" + jsonObject.toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            LogUtil.i(TAG, "json 数据异常 getRequestJson(...)");
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
                String jsonList = jsonObject.optString("list", null);
                if (jsonList == null)
                {
                    sendFailMsg();
                }
                List<Product> list = JSON.parseArray(jsonList, Product.class);
                addData(list);

                // 信息请求保存
                RequestRecord record = new RequestRecord();
                record.setKind(CONSTANT_1);
                record.setPid(jsonObject.getLong("pid"));
                record.setLanguage(CN);
                record.setTime(jsonObject.getLong("time"));
                initDbService.save(record);
            }
            else
            { // 失败
                sendFailMsg();
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            LogUtil.i(TAG, "parseJson(..) json 异常.");
        }

    }

    /**
     * 向listView 中添加信息.
     * @param list
     */
    public void addData(List<Product> list)
    {
        if (list != null)
        {
            if (list.size() > 0)
            {
                for (Product product : list)
                {
                    mListItems.add(product);
                }

                Message message = Message.obtain(postHandler, CONSTANT_0);
                message.sendToTarget();
            }
            else
            {
                sendFailMsg();
            }

        }

    }

    private void sendFailMsg()
    {
        Message message = Message.obtain(postHandler, CONSTANT_2);
        message.sendToTarget();
    }

}

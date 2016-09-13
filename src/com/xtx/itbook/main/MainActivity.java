package com.xtx.itbook.main;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.xtx.itbook.adapter.MainMenuAdapter;
import com.xtx.itbook.data.CacheDirectory;
import com.xtx.itbook.db.InitDbService;
import com.xtx.itbook.entity.Directory;
import com.xtx.itbook.entity.ListMenuEntry;
import com.xtx.itbook.net.NetUtil;
import com.xtx.itbook.service.InitService;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.ui.terminology.TerminologyActivity;
import com.xtx.itbook.util.AccessUtil;
import com.xtx.itbook.util.ApkUtil;
import com.xtx.itbook.util.Const;
import com.xtx.itbook.util.LogUtil;
import com.xtx.itbook.util.StringUtil;

/**
 * 主界面.
 * @author Abelart.
 */
public class MainActivity extends BaseActivity
{

    private static final String TAG = MainActivity.class.getName();

    private MainMenuAdapter menuAdapter = null;

    private GridView menuGridView;
    
    private FinalDb finalDb;
    
    private InitService initService;
    
    private InitDbService initDbService;
    
    private int count = 0;
    
    private ProgressBar progress = null;
    /**
     * 处理 目录结构数据.
     */
    private Handler handler = new Handler()
    {
    	public void handleMessage(Message msg) 
    	{
    		switch (msg.what) {
			case 5:
				secondLevel();
				progress.setVisibility(View.GONE);
				break;

			default:
				break;
			}
    	};
    };
    

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        init();
    }

    /** 
     * TODO.
     * 见父类.
     */
    @Override
    public void init()
    {
    	finalDb = FinalDb.create(this,DB_NAME,true);
    	initService = new InitService(this);
    	initDbService = new InitDbService(this);
        initView();
        initDirectory();
    }

    /** 
     * TODO.
     * 见父类.
     */
    @Override
    public void initView()
    {
        menuGridView = (GridView) findViewById(R.id.main_grid_menu);
        menuAdapter = new MainMenuAdapter(MainActivity.this);
        menuGridView.setAdapter(menuAdapter);
        menuGridView.setOnItemClickListener(new ItemListener());
        
        progress = (ProgressBar) findViewById(R.id.load_catalog);
    }

    /**
     * gridView点击 菜单.
     * @author Abelart
     *
     */
    class ItemListener implements OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                long id)
        {
            menuAdapter.setClickPositon(position);
            menuAdapter.notifyDataSetChanged();

            menuClick(position);
        }

    }

    /**
     * 主菜单点击事件.
     * @param position
     */
    public void menuClick(int position)
    {
        switch (position)
        {
            case CONSTANT_0: // 新闻
                // skip(activityClass);
                break;
            case CONSTANT_1: // 产品介绍
                skip(ProductActivity.class);
                overridePendingTransition(R.anim.activity_right_in, R.anim.hold);
                break;
            case CONSTANT_2: // 解决方案
                skip(SolutionsActivity.class);
                overridePendingTransition(R.anim.activity_right_in, R.anim.hold);

                break;
            case CONSTANT_3: // 成功案例
                skip(TradCsActivity.class);
                overridePendingTransition(R.anim.activity_right_in, R.anim.hold);
                break;
            case CONSTANT_4: // it术语
                skip(TerminologyActivity.class);
                overridePendingTransition(R.anim.activity_right_in, R.anim.hold);
                break;
            case CONSTANT_5: // 多媒体中心.

                break;
            default:
                break;
        }
    }

    public void onClick(View v)
    {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // 处理扫描结果（在界面上显示）
        if (resultCode == RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            // resultTextView.setText(scanResult);
            Log.i(TAG, scanResult);
        }
    }

    /** 
     * TODO.
     * 见父类.
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }

    /**初始化目录结构.*/
    private void initDirectory()
    {
        getCatalog(ItBookApp.instance().getLanguage(), CONSTANT_1);
        getCatalog(ItBookApp.instance().getLanguage(), CONSTANT_2);
        getCatalog(ItBookApp.instance().getLanguage(), CONSTANT_3);
        getCatalog(ItBookApp.instance().getLanguage(), CONSTANT_4);
        getCatalog(ItBookApp.instance().getLanguage(), CONSTANT_5);
    }
    
    /**
     * 处理目录结构.
     */
    private void secondLevel()
    {
        /**获取一级目录.*/
        List<ListMenuEntry> list1 = initDbService.getListMenuEntry(CONSTANT_1,ItBookApp.instance().getLanguage());
        /**获取所有的二级目录*/
        List<ListMenuEntry> list2 = initDbService.getListMenuEntry(CONSTANT_2,ItBookApp.instance().getLanguage());
        LogUtil.i(TAG, "list1"+list1.size()+"  list2:"+list2.size());
        for (int i = 0; i < list1.size(); i++)
        {   
        	ArrayList<ListMenuEntry> listMenu = new ArrayList<ListMenuEntry>();
        	for(int n=0;n < list2.size(); n++)
        	{
        		/**二级目录upId和一级目录ID相同.*/
        	  if(list1.get(i).getId() == list2.get(n).getUpid())
        	  {
        		  listMenu.add(list2.get(n));
        	  }
        	}
        	addMenuMap(list1.get(i).getKind(), list1.get(i),listMenu);
        }
    }

    /**相关的目录结构加入缓存.*/
    private void addMenuMap(int kind, ListMenuEntry listMenu,ArrayList<ListMenuEntry> listMenus)
    {
        switch (kind)
        {
            case CONSTANT_1:
            	LogUtil.i(TAG, "测试"+listMenus.size());
                CacheDirectory.instance().addProductMap(listMenu, listMenus);
                break;
            case CONSTANT_2:
            	CacheDirectory.instance().addSolutionMap(listMenu, listMenus);
                break;
            case CONSTANT_3:
            	CacheDirectory.instance().addFaqMap(listMenu, listMenus);
                break;
            case CONSTANT_4:
            	CacheDirectory.instance().addBusinessMap(listMenu, listMenus);
                break;
            case CONSTANT_5:
            	CacheDirectory.instance().addFaultCaseMap(listMenu, listMenus);
                break;
            default:
                break;
        }
    }
    
    /**
     * 
     * 注意监测 是否有重复插入的 数据.
     * 获取目录结构.并保存进数据库.
     * 
     * @param language
     * @param kind
     * @param time
     * @return
     * @throws JSONException.
     */
    private void getCatalog(final String language, final int kind)
    {
    	final List<Directory> listDir = initService.getDirectory(kind, language);
        
    	JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("kind", kind);
            jsonObject.put("language",language);
            jsonObject.put("sessionId",AccessUtil.instance().getSessionId());
            
            /**检测是否已经有过请求记录 若有则获取本地的时间,若没有 则取值为 0.*/
            if(listDir !=null )
            {
            	if(listDir.size()!=0)
            	{
                 jsonObject.put("time",listDir.get(0).getTime());
            	}else{
            		jsonObject.put("time",0L);
            	}
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            LogUtil.i(TAG, "getCatalog() JSON 参数 异常 ");
        }
        NetUtil.getHttp().post(StringUtil.getUrl(R.string.directory),
                NetUtil.getEntity(jsonObject),Const.HEAD_TYPE,
                new AjaxCallBack<String>()
                {
                    public void onSuccess(String t)
                    {
                        super.onSuccess(t);
                        LogUtil.i(TAG, t);
                     
                        
                        Directory catalog  = JSON.parseObject(t, Directory.class);
                        AccessUtil.instance().setSessionId(t);
                        //如果返回的时间和 请求的时间相同 (第一次时间0L不算),则代表数据没变.则不用更新数据库.
                        //state 若为1 则同样代表数据需要更新 保存.
                       // if(catalog.getState().equals("1")) {   //需要更新 则保存更新 此次的请求记录.
                        	/**先删除 再保存请求记录*/
                        	finalDb.deleteByWhere(Directory.class, "kind = "+kind+" and language = '"+language+"'");
                        	finalDb.save(catalog);
                        	
                        	/**先删除 再保存菜单信息记录.*/
                        	initService.delete(kind,language);
                        	for (int i=0;i<catalog.getList().size();i++)
                            {
                        		  catalog.getList().get(i).setKind(kind);
                                  catalog.getList().get(i).setLanguage(language);
                                  finalDb.save(catalog.getList().get(i));
                      //      }
                            }
                       count = count+1;
                       LogUtil.i(TAG, "count="+count);
                       Message message = Message.obtain(handler, count);
                       message.sendToTarget();
                    }
                    public void onFailure(Throwable t, int errorNo,
                    		String strMsg) {
                    	super.onFailure(t, errorNo, strMsg);
                        try {
							throw t;
						} catch (Throwable e) {
							
						}
                    }
                });
    }
    

}

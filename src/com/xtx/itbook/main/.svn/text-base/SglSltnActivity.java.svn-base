package com.xtx.itbook.main;

/**
 * 解决方案实例显示
 * 
 */
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xtx.itbook.adapter.SglSltnAdapter;
import com.xtx.itbook.db.InitDbService;
import com.xtx.itbook.entity.RequestRecord;
import com.xtx.itbook.entity.SglSolutions;
import com.xtx.itbook.entity.SltnRltd;
import com.xtx.itbook.net.NetUtil;

import com.xtx.itbook.ui.R;
import com.xtx.itbook.util.AccessUtil;
import com.xtx.itbook.util.Const;
import com.xtx.itbook.util.LogUtil;
import com.xtx.itbook.util.SltnUtil;
import com.xtx.itbook.util.StringUtil;

public class SglSltnActivity extends BaseActivity implements OnClickListener
{
   
    //日志标志
    private static String TAG = "SglSltnActivity";

    // 返回解决方案首页
    @ViewInject(id = R.id.sgl_sltn_btn_back)
    ImageView sglSBackBtn;

    // 分享
    @ViewInject(id = R.id.sgl_sltn_btn_share)
    ImageView sglSShareBtn;

    // 方案标题
    @ViewInject(id = R.id.sgl_sltn_tv_title)
    TextView sglSTitleTv;

    // 解决方案内容
    @ViewInject(id = R.id.sgl_sltn_content_tv)
    TextView sglSContentTv;

    // 收藏
    @ViewInject(id = R.id.sgl_sltn_img_collect)
    ImageView sglSCollectBtn;

    // 评论
    @ViewInject(id = R.id.sgl_sltn_img_critique)
    ImageView sglScritiqueBtn;

    // 解决方案相关内容标题
    TextView sglSDtlTitleTv;
    //解决方案相关内容详情内容
    TextView sglSDtlCntntTv;
    //解决方案相关内容详情图片
    ImageView sglSDtlImgIv;
    
    //解决方案
    SglSolutions sglSolutions;
    //解决方案中间相关内容ListView
    ListView sltnRltdListView = null;
    List<SltnRltd> sglRltdListData = null;
   SglSltnAdapter sglSltnAdapter = null;
  
   private InitDbService initDbService = null;
   
   private FinalDb finalDb = null;
   
   Handler mHandler = new Handler(){
     public void handleMessage(Message msg) {
         switch (msg.what)
        {
           case CONSTANT_1:
             
                //解析异常
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
        setContentView(R.layout.single_solutions_info);
        initDbService = new InitDbService(SglSltnActivity.this);
        finalDb = FinalDb.create(SglSltnActivity.this, DB_NAME, true);
        
        init();
        initView();
        initLintener();
       
       
    }

    @Override
    public void init()
    {
        //sglSolutions对象数据在解决方案首页点击item设置到SltnUtil.sglSltnMap
        
        sglSolutions = SltnUtil.sglSltnMap.get(Const.KEY_SLTN_DTL_SHOW);
        sglRltdListData = sglSolutions.getSltnRltdList();
        data(sglSolutions.getPid());
        
        
    }

    @Override
    public void initView()
     
    {
        
        sglSTitleTv.setText(sglSolutions.getSltnTitle() == null ? "":sglSolutions.getSltnTitle());
        sglSContentTv.setText(sglSolutions.getSltnContent() == null ? "":sglSolutions.getSltnContent());
        
        sltnRltdListView = (ListView) findViewById(R.id.sltn_list);
        sglSltnAdapter = new SglSltnAdapter(SglSltnActivity.this, sglRltdListData);  
        sltnRltdListView.setAdapter(sglSltnAdapter);
       
        
    }
   
    void initLintener(){
        sglSBackBtn.setOnClickListener(this); 
        sglSShareBtn.setOnClickListener(this);
        sglSCollectBtn.setOnClickListener(this);
        sglScritiqueBtn.setOnClickListener(this);
        
        sltnRltdListView.setOnItemClickListener(new OnItemClickListener()
        {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id)
            {
                
                /**
                 * 找到解决方案相关内容的两个布局,初始状态一个显示，一个隐藏
                 */
              
              RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.rl_sgl_sltn_showbtn);
              LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_sgl_sltn_showbtn_dtl);
              //点击显示或隐藏布局
               getStateAndShowDial(relativeLayout, linearLayout);
                
            }
        });
    }
   

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        /*
         * 点击跳转
         */
            case R.id.sgl_sltn_btn_back:
                // 跳转到解决方案首页
                 Intent sltnIntent=new Intent(SglSltnActivity.this, SolutionsActivity.class);
               // sltnIntent.setData(data);设置数据
                 this.startActivity(sltnIntent);
                Log.i(TAG, "跳转到解决方案首页");
                break;

            case R.id.sgl_sltn_btn_share:
                // 跳转到分享
                Log.i(TAG, "跳转到分享");
                break;
            case R.id.sgl_sltn_img_collect:
                // 跳转到收藏
                Log.i(TAG, "跳转到收藏");
                break;
            case R.id.sgl_sltn_img_critique:
                // 跳转到评论
                Log.i(TAG, "跳转到评论");
                break;

          
           
        }
    }

   /**
    * 
    * 根据View2的当前状态判断是否显示View2,根据显示隐藏View2两种状态确定View1上的ImageView
    * @param linearLayout 点击的布局
    * @param v  要显示的布局，即解决方案相关内容详情
    * @return
    */
 
    public void getStateAndShowDial(View view1,View view2)
    {
        //找到布局对应的图片
        ImageView showBtnImg = (ImageView) view1.findViewById(R.id.sgl_sltn_showbtn_image);
        
        if (view2.getVisibility() == View.GONE)
        {
            
          
            view2.setVisibility(View.VISIBLE);
          
            showBtnImg.setImageResource(R.drawable.ic_arrow_down);
        }
        else if(view2.getVisibility() == View.VISIBLE)
        {
           
            view2.setVisibility(View.GONE);
            showBtnImg.setImageResource(R.drawable.ic_arrow_right);
        }
       return;
    }
    
    /**
     * 第一次根据一二级目录加载初始.
     */
    public void data(long pid)
    {
        List<RequestRecord> requestRecord = initDbService.getRecord(pid, CN);
        LogUtil.i(TAG, "requestRecord.size() == "
                + requestRecord.size());

        if (requestRecord.size() > 0)
        {
            LogUtil.i(
                    TAG,
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
        NetUtil.getHttp().post(StringUtil.getUrl(R.string.sltn_dtl_url),
                NetUtil.getEntity(jsonObject), Const.HEAD_TYPE,
                new AjaxCallBack<String>()
                {
                    public void onSuccess(String t)
                    {
                        LogUtil.i(TAG, "联网加载参数t ==" + t);
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
                            LogUtil.i(TAG, errorNo + ":" + strMsg);
                        }
                        finally
                        {
                            NetUtil.toast(SglSltnActivity.this, "请求失败");

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
            LogUtil.i(TAG, "请求参数jsonId 数据异常pid=="+pid);
            jsonObject.put("pid", pid);
            jsonObject.put("language", CN);
            jsonObject.put("time", time);
           jsonObject.put("sessionId", AccessUtil.instance().getSessionId());
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
                LogUtil.i(TAG, "成功获取数据 jsonObject=="
                        + jsonObject.toString());
                String jsonList = jsonObject.getString("list");

                List<SltnRltd> list = JSON.parseArray(jsonList,
                        SltnRltd.class);
                LogUtil.i(TAG,
                        "获取数据list.size() ==" + list.size());
                addData(list);

                // 信息请求保存
                RequestRecord record = new RequestRecord();
                record.setKind(CONSTANT_2);
                record.setPid(jsonObject.getLong("pid"));
                record.setLanguage(CN);
                record.setTime(jsonObject.getLong("time"));
                initDbService.save(record);
            }
            else
            { // 失败
                Message message = mHandler.obtainMessage();
                message.what = CONSTANT_1;
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
    public void addData(List<SltnRltd> list)
    {
        if (list != null)
        {
            if (list.size() > 0)
            {
                for (SltnRltd sltnRltd : list)
                {
                    sglRltdListData.add(sltnRltd);
                    LogUtil.i(TAG,
                            "正在追加相关内容sglRltdListData.size() ==" + sglRltdListData.size());  
                }
              
            }

        }

    }

}

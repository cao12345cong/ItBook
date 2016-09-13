package com.xtx.itbook.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.tsz.afinal.http.AjaxCallBack;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.xtx.itbook.dao.FaqDao;
import com.xtx.itbook.entity.Faq;
import com.xtx.itbook.entity.FaqResponse;
import com.xtx.itbook.net.NetUtil;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.ui.faq.FaqActivity;
import com.xtx.itbook.ui.terminology.TerminologyActivity;
import com.xtx.itbook.util.Const;
import com.xtx.itbook.util.LogUtil;
import com.xtx.itbook.util.StringUtil;

/**
 * FaqService接口实现类
 * @author caocong
 *
 */
public class FaqService extends InitService
{
    private static final String TAG = "FaqService";
    public static final class state{
        public static final int ZERO=0;//时间相同，第一次请求，不用更新
        public static final int ONE=1;//时间为0，第一次请求，更新数据
        public static final int TWO=2;//时间错误，删除手机数据，更新
        public static final int THREE=3;//时间相同，不是第一次请求，追加数据
    }

    private Context context;

    private FaqDao dao;

    private List<Faq> faqs;

    private final int PAGE_SIZE = 20;// 每次请求多少条

    private Handler mHandler;

    private SharedPreferenceSevice sharedPreference;// 偏好设置

    public FaqService(Context context, Handler mHandler)
    {
        super(context);
        this.context = context;
        this.dao = new FaqDao(context);
        this.faqs = new ArrayList<Faq>();
        this.mHandler = mHandler;
        this.sharedPreference = new SharedPreferenceSevice(context);
    }

    /**
     * 开始服务
     * @param upid        目录的id
     * @param currentPage 请求第几页  
     * @param pageSize    一页多少条
     * @param language    语言
     * @return
     * @throws Exception 
     */
    public void startService(String sessionId, long upid, int currentPage,
            int pageSize, String language) throws Exception
    {
        faqs = dao.findByPageAndSize(upid, currentPage, pageSize);
        if (faqs == null || faqs.isEmpty())
        {
            // 第一次访问
            if (currentPage == 1)
            {
                FaqResponse fr = getFaqsFromNet(sessionId, upid, language, 0L,
                        currentPage, pageSize);
                handlerData(language,true,upid,currentPage,pageSize, fr);
                Message msg = Message.obtain(mHandler,
                        FaqActivity.MSG_UPDATA_LISTVIEW, faqs);
                msg.sendToTarget();
            }
            else
            {
                FaqResponse fr = getFaqsFromNet(sessionId, upid, language,
                        sharedPreference.getFaqResponseTime(language), currentPage, pageSize);
                handlerData(language,true,upid,currentPage,pageSize, fr);
                Message msg = Message.obtain(mHandler,
                        FaqActivity.MSG_UPDATA_LISTVIEW, faqs);
                msg.sendToTarget();
            }
            return;

        }
        if (isTheSameDate(language))
        {

        }
        else
        {
            FaqResponse fr = getFaqsFromNet(sessionId, upid, language,
                    dao.getLastUpdateTime(), currentPage, pageSize);
            handlerData(language,true,upid,currentPage,pageSize, fr);
        }

        Message msg = Message.obtain(mHandler,
                TerminologyActivity.MSG_UPDATE_LISTVIEW,faqs);
        msg.sendToTarget();
    }


    /**
     * 处理数据
     * @param isFirst
     * @param str
     */

    private void handlerData(String language,boolean isFirst,long upid,
            int currentPage, int pageSize, FaqResponse response)
    {

        if (response == null||!response.isSucc())
        {
            if (response.getMsg() != null)
            {
                Message msg=Message.obtain(mHandler, FaqActivity.MSG_DOWNLOAD_FAIL, response.getMsg());
                msg.sendToTarget();
            }
            return;
        }
        if (isFirst)
        {
            List<Faq> data = null;
            // 第一次，第一页
            if (response.getState() == state.ONE)
            {
                // 语言更换时，先清空数据
                dao.deleteAll();
                //解析json数据
                data = parseJsonData(response.getList());
                //保存到数据库
                dao.saveAll(data);
                //追加到数据集中
                faqs.addAll(data);
                //保存返回的时间
                sharedPreference.setFaqResponseTime(language, response.getTime());
                //保存访问的日期
                saveLastUpdateDate(language);

            }
            // 第一次，第n(n>1)页
            else if (response.getState() == state.THREE)
            {
                //解析json数据
                data = parseJsonData(response.getList());
                //保存到数据库
                dao.saveAll(data);
                //追加到数据集中
                faqs.addAll(data);

            }
        }
        else
        {
            LogUtil.i(TAG, response.getState());
            // 不是第一次
            // 服务器数据发生了变化
            switch (response.getState())
            {
                case state.ZERO:
                    // 直接从数据库中获取
                    faqs = dao.findByPageAndSize(upid, currentPage, pageSize);
                    
                case state.TWO:
                    dao.deleteAll();
                    faqs.addAll(parseJsonData(response.getList()));
                    //保存返回的时间
                    sharedPreference.setFaqResponseTime(language, response.getTime());
                    //保存访问的日期
                    saveLastUpdateDate(language);
                    break;

                case state.THREE:
                    faqs.addAll(parseJsonData(response.getList()));
                    break;
            }
        }
    }

    /**
     * 从服务器获取数据，解析，存入本地数据库
     * @param upid
     * @param language
     * @param time
     * @param currentPage
     * @param pageSize
     * @param isFirst    是否第一次访问
     * @throws JSONException
     * @throws IOException 
     * @throws ParseException 
     */
    public FaqResponse getFaqsFromNet(String sessionId, long upid,
            String language, long time, int currentPage, int pageSize)
            throws Exception
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionId", sessionId);
        jsonObject.put("pid", upid);
        jsonObject.put("language", language);
        jsonObject.put("time", time);
        jsonObject.put("curPage", currentPage);
        jsonObject.put("pageSize", pageSize);

        String str = NetUtil.connServerForResult(
                StringUtil.getUrl(R.string.faq_url), jsonObject);
        if (str != null || !"".equals(str.trim()))
        {
            // handlerData(isFirst, str);
            return null;
        }
        FaqResponse response = JSON.parseObject(str, FaqResponse.class);
        return response;

    }

    /**
     * 解析并存入数据
     * @param jsonStr
     */
    private List<Faq> parseJsonData(String jsonStr)
    {
        if (TextUtils.isEmpty(jsonStr))
        {
            return null;
        }
        return JSON.parseArray(jsonStr, Faq.class);
    }

    /**
     * 持久化最新访问的日期
     * @param language
     */
    private void saveLastUpdateDate(String language)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sharedPreference.setTerminologyLastAccessDate(language,
                sdf.format(new Date()));
    }

    /**
     *  判断本地访问的日期和最近一次访问的日期是否一样,如果不一样，更新最新访问时间
     * @param language
     * @return
     */
    private boolean isTheSameDate(String language)
    {
        String lastAccesDate = sharedPreference
                .getTerminologyLastAccessDate(language);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(new Date());
        boolean isTheSameDate = nowDate.equals(lastAccesDate);
        if (!isTheSameDate)
        {
            sharedPreference.setTerminologyLastAccessDate(language, nowDate);
        }
        return isTheSameDate;
    }

}

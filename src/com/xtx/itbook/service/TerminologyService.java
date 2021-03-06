package com.xtx.itbook.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.xtx.itbook.dao.TerminologyDao;
import com.xtx.itbook.entity.Terminology;
import com.xtx.itbook.entity.TerminologyResponse;
import com.xtx.itbook.net.NetUtil;
import com.xtx.itbook.ui.R;
import com.xtx.itbook.ui.terminology.PinyinComparator;
import com.xtx.itbook.ui.terminology.TerminologyActivity;
import com.xtx.itbook.util.Const;
import com.xtx.itbook.util.LogUtil;
import com.xtx.itbook.util.PinYinUtil;
import com.xtx.itbook.util.StringUtil;
import com.xtx.itbook.util.TimeMemoryUtil;

/**
 * 产品术语服务类
 * @author caocong
 *
 */
public class TerminologyService
{
    private static final String TAG = "TerminologyService";

    private Context context;

    private TerminologyDao dao;             //数据访问对象

    private List<Terminology> terminologys; //数据集

    private Handler mHandler;               //activity的hanlder引用

    private SharedPreferenceSevice sharedPreference;//偏好设置

    public TerminologyService(Context context, Handler mHandler)
    {
        this.context = context;
        this.dao = new TerminologyDao(context);
        this.mHandler = mHandler;
        this.sharedPreference = new SharedPreferenceSevice(context);
    }


    /**
     * 启动服务：判断是否第一次，从服务器还是从数据库取数据，服务器是否更新，做不同的处理
     * @param language
     * @throws Exception
     */
    public void startService(String sessionId, String language)
            throws Exception
    {

        terminologys = dao.findAll();
        LogUtil.i(TAG, "dao" + terminologys.toString());
        // 判断本地数据库是否有数据,没有则是第一次访问,有则从数据库中直接获取
        if (terminologys == null || terminologys.isEmpty())
        {
            // 第一次访问
            TerminologyResponse tr = getTerminologyFromNet(sessionId, 0L, language, true);
            handlerData(language, 0L, true, tr);
            Message msg = Message.obtain(mHandler,
                    TerminologyActivity.MSG_UPDATE_LISTVIEW,terminologys);
            msg.sendToTarget();
            return;

        }
        // 判断当前访问日期和最近一次访问日期是否同一天。如果是，直接从本地获取；如果不是，访问服务器判断是否更新
        if (isTheSameDate(language))
        {
            //什么也不需要做
        }
        else
        {
            TerminologyResponse tr = getTerminologyFromNet(sessionId,
                    sharedPreference.getTerminologyResponseTime(language),
                    language, false);
            handlerData(language, sharedPreference.getTerminologyResponseTime(language), false, tr);
            
        }
        Message msg = Message.obtain(mHandler,
                TerminologyActivity.MSG_UPDATE_LISTVIEW,terminologys);
        msg.sendToTarget();

    }

    

    /**
     * 处理从服务器获取的数据
     * @param time
     * @param isFirst
     * @param t
     * @throws JSONException 
     */
    private void handlerData(String language, Long time, boolean isFirst,
            TerminologyResponse response) throws JSONException
    {
        // 返回数据失败,终止
        if (response == null || !response.isSucc())
        {
            if (response.getMsg() != null)
            {
                Message msg=Message.obtain(mHandler, TerminologyActivity.MSG_FAILURE_LISTVIEW, response.getMsg());
                msg.sendToTarget();
            }
            return;
        }
        //判断是否第一次访问
        if (isFirst)
        {
            
            // 语言更换时，先清空数据
            dao.deleteAll();
            //解析json数据
            terminologys = JSON.parseArray(response.getList(), Terminology.class);
            //给数据集加上拼音
            decorateData(terminologys);
            //保存到数据库
            dao.saveAll(terminologys);
            // 将返回的time记录下来
            sharedPreference.setTerminologyResponseTime(language,
                    response.getTime());
            //记录最近访问的日期
            saveLastUpdateDate(language);
            
        }
        else
        {
            if (time == response.getTime())
            {
                //记录最近访问的日期
                saveLastUpdateDate(language);
            }
            else if (time < response.getTime())
            {
               
                // 第二次判断is_delete判断修改还是删除数据
                List<Terminology> terms = JSON.parseArray(response.getList(),
                        Terminology.class);
                ArrayList<Terminology> termss = new ArrayList<Terminology>();
                for (Terminology terminology : terms)
                {
                    switch (terminology.getIs_delete())
                    {
                        case 0:// 修改了数据
                            termss.add(terminology);
                            // 加上拼音
                            decorateData(termss);
                            // 更新
                            dao.update(terminology);

                            break;

                        case 1:// 删除数据
                            dao.deleteById(terminology.getId());
                            break;
                    }
                }
                terminologys = dao.findAll();
                // 将返回的time记录下来
                sharedPreference.setTerminologyResponseTime(language,
                        response.getTime());
                //记录最近访问的日期
                saveLastUpdateDate(language);
                
               
            }
        }
    }


    /**
     * 将Terminology数据集加上拼音,并按拼音排序
     * @param data
     * @return
     */
    private void decorateData(List<Terminology> sourcedata)
    {
        if (sourcedata == null || sourcedata.isEmpty())
        {
            return;
        }
        // 将获取到的数据加工成TerminologySort的集合
        for (Terminology terminology : sourcedata)
        {
            String word = terminology.getWord();
            if (word != null && !"".equals(word))
            {
                terminology.setFullSpell(PinYinUtil.getFullSpell(word));
                terminology.setFirstSpell(PinYinUtil.getFirstSpell(word));
                String firstLetter = PinYinUtil.getFirstLetter(word);
                if (firstLetter.matches("[A-Z]"))
                {
                    terminology.setFirstLetter(firstLetter.toUpperCase());
                }
                else
                {
                    terminology.setFirstLetter("#");
                }
            }
            else
            {
                terminology.setFullSpell("#");
                terminology.setFirstSpell("#");
                terminology.setFirstLetter("#");
            }

        }
        Collections.sort(terminologys, new PinyinComparator());

    }
    
    /**
     * 访问服务器获取数据
     * @param time
     * @param language
     * @param isFirst 是否是第一次访问
     * @throws JSONException
     */
    public TerminologyResponse getTerminologyFromNet(String sessionId, Long time,
            String language, boolean isFirst) throws Exception
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionId", sessionId);
        jsonObject.put("language", language);
        jsonObject.put("time", time);
        LogUtil.i(TAG, "getTerminologyFromNet"
                + Thread.currentThread().getName());
        String str = NetUtil.connServerForResult(
                StringUtil.getUrl(R.string.terminology_url), jsonObject);
        LogUtil.i(TAG, "getTerminologyFromNet" + str);
        if(str==null||"".equals(str)){
            return null;
        }
        TerminologyResponse response = JSON.parseObject(str,
                TerminologyResponse.class);
        
        return response;

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

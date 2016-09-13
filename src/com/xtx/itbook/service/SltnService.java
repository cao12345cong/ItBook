package com.xtx.itbook.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.xtx.itbook.entity.Directory;
import com.xtx.itbook.entity.SglSolutions;
import com.xtx.itbook.entity.SltnRltd;
import com.xtx.itbook.net.NetUtil;
import com.xtx.itbook.util.Const;

/**
 * 
 * @author chenhg
 *
 */
public class SltnService extends InitService
{
    public SltnService(Context context)
    {
        super(context);
    }

    /**
     * 获取解决方案Json数据
     */
    public List<SglSolutions> getSltnListData(long pid, String language, long time,String sessionId)
    {
        JSONObject param = null;
        try
        {
            param = new JSONObject();
            param.put("sessionId",sessionId);
            param.put("pid", pid);
            param.put("language", language);
            param.put("time", time);
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

       String jsonParam = null;
       if (jsonParam == null || "".equals(jsonParam.trim()))
       {
       return getSltnListData(jsonParam);
       }else
       {
           return null;
       }
    }

    /**
     * 获取解决方案对象数据
     */
    public List<SglSolutions> getSltnListData(String data)
    {
        List<SglSolutions> sltnListData = null;
        try
        {
           
            com.alibaba.fastjson.JSONObject jObj = JSON.parseObject(data);
            if (null != jObj)
            {
                SltnDtlResponse response = new SltnDtlResponse();
                response.succ = jObj.getBoolean("succ");
                response.list = jObj.getString("list");
                response.sltnDtltotal = jObj.getIntValue("total");
                if (!response.succ)
                {

                    throw new JSONException("解决方案Json数据解析异常:" + response.succ);

                }
                if (response.sltnDtltotal >= 0)
                {
                    JSONArray array = new JSONArray(response.list);
                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject obj = array.getJSONObject(i);
                        SglSolutions sglSolutions = new SglSolutions();
                        sglSolutions.setSltnTitle(obj.getString("name"));
                        sglSolutions.setSltnContent(obj.getString("intro"));
                        sglSolutions.setSltnImageUrl(obj.getString("imageUrl"));
                        // 获取解决方案详情相关内容list集合 ，前提是Json数据能不能一次性获取

                        // 待字段确定

                        sltnListData.add(sglSolutions);
                    }
                }
            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return sltnListData;
    }

    /**
     * 获取解决方案详情Json数据
     */
    public String getSltnDtlJsonData(int id, String language, long time)
    {
        JSONObject param = new JSONObject();
        try
        {
            param.put("id", id);
            param.put("language", language);
            param.put("time", time);
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取解决方案详情数据 
     */
    @SuppressWarnings("null")
    public List<SltnRltd> getSltnDtlListData(int id, String language,
            long time, int curPage, int pageSize)
    {
        List<SltnRltd> sltnDtlListData = null;
        try
        {
            String data = getSltnDtlJsonData(id, language, time);
            com.alibaba.fastjson.JSONObject sltnDtlObj = JSON.parseObject(data);
            if (null != sltnDtlObj)
            {
                SltnDtlResponse response = new SltnDtlResponse();

                response.succ = sltnDtlObj.getBoolean("succ");
                response.sltnDtltotal = sltnDtlObj.getIntValue("total");
                response.list = sltnDtlObj.getString("list");
                response.colorpage = sltnDtlObj.getString("colorpage");// 存放pdf的官网Url
                if (!response.succ)
                {

                    throw new JSONException("解决方案详情解析异常:" + response.succ);

                }
                if (response.sltnDtltotal >= 0)
                {
                    JSONArray array = new JSONArray(response.list);
                    for (int i = 0; i <= array.length(); i++)
                    {
                        JSONObject obj = array.getJSONObject(i);

                        SltnRltd sltnRltd = new SltnRltd();
                        sltnRltd.setSltnDtlTitle(obj.getString("title"));
                        sltnRltd.setSltnDtlContentUrl(obj.getString("content"));

                        sltnDtlListData.add(sltnRltd);

                    }

                }

            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return sltnDtlListData;

    }

    /**
     *  获取解决方案目录Json数据
     */
    public String getSltnDirJson(String language, long time)
    {

        JSONObject param = new JSONObject();
        try
        {
            param.put("language", language);

            param.put("time", time);
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取解决方案目录
     */
    public List<Directory> getSltnDirData(String language,long time)
    {
        List<Directory> sltnDirDataList = null;
        String data = getSltnDirJson(language, time);
        
        com.alibaba.fastjson.JSONObject dirObj = JSON.parseObject(data);
        if(null != dirObj)
        {
            DirResponse dirResponse = new DirResponse();
            dirResponse.succ = dirObj.getBoolean("succ");
            dirResponse.dirtotal = dirObj.getIntValue("total");
            dirResponse.time = dirObj.getLong("time");
            dirResponse.state = dirObj.getIntValue("state");
            dirResponse.list = dirObj.getString("list");
      
            if(!dirResponse.succ)
            {
                try
                {
                    throw new JSONException("目录解析异常：dirResponse.succ="+dirResponse.succ);
                }
                catch (JSONException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }
            if(dirResponse.dirtotal >= 0)
            {
                JSONArray array;
                try
                {
                    array = new JSONArray(dirResponse.list);
               
                for(int i = 0;i<array.length();i ++)
                {
                   Directory directory = new Directory();
                   JSONObject obj = array.getJSONObject(i);
                   directory.set_id(obj.getInt("id"));
                   directory.setKind(obj.getInt("kind"));
                   directory.setLanguage(obj.getString("language"));
                  sltnDirDataList.add(directory);
                }
                }
                catch (JSONException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        
        }
       
        
        return sltnDirDataList;
        
    }
    
    
    // 解决方案返回字段
    class SltnResponse
    {
        boolean succ;

        String language;

        int sltnTotal;

        long time;

        int state;

        int number;

        String list;

        int upId;

    }

    // 解决方案详情返回字段
    class SltnDtlResponse
    {
        boolean succ;

        String language;

        int sltnDtltotal;

        long time;

        int state;

        String list;

        int upId;

        String name;

        String intro;

        String imageUrl;

        String colorpage;// 存放pdf的官网Url

    }
    class DirResponse{
        
        long time;
        int state;
        int dirtotal;
        boolean succ;
        String list;
    }

}

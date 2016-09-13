package com.xtx.itbook.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 相关的权限校验.
 * @author Abelart
 *
 */
public class AccessUtil
{
	private static final String TAG = AccessUtil.class.getName();
	
	/**sessionId接口数据合法性认证 初始的json数据.*/
    private static String sessionId = "itbookandroid2013xtx";
    
    private static AccessUtil accessUtil = null;
    
    private AccessUtil()
    {
    	
    }
    
    /**
     * 单例模式.
     * @return
     */
    public static AccessUtil instance()
    {
    	if(accessUtil == null)
    	{
    		accessUtil = new AccessUtil();
    	}
    	return accessUtil;
    }

	public  String getSessionId() {
		return sessionId;
	}
    
	/**将sessionId保存起来.*/
	public void setSessionId(String json) {
		
		this.sessionId = getSessionFromJson(json);
	} 
	
	/**从json数据中获取 json.*/
	private static String getSessionFromJson(String json)
	{
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			sessionId = jsonObject.getString("sessionId");
		} catch (JSONException e) {
			e.printStackTrace();
			LogUtil.i(TAG, "getSessionFromJson"+json);
		}
		return sessionId;
	}
	
	
	
     
}

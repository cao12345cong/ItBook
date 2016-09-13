/*
 * 文件名：AccessModule.java
 * 描述：权限控制  编码
 * 修改人：王鹏
 * 修改时间：上午10:01:54
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.access;

import java.util.HashMap;

/**
 * 权限模块编码.
 * @author Abelart.
 */
public final class AccessModule
{
    /**新闻.*/
    private static final String NEWS = "1003";

    /**产品.*/
    private static final String PRODUCT = "1004";

    /**解决方案.*/
    private static final String SOLVE = "1005";

    /**行业案例.*/
    private static final String TRADE_CASE = "1006";

    /**it术语*/
    private static final String IT_TERM = "1007";

    /**FAQ*/
    private static final String FAQ = "1008";

    /**故障案例.*/
    private static final String BUG_CASE = "1010";

    /**多媒体中心.*/
    private static final String MEDIA_CASE = "1011";

    private static HashMap<String, String> accessMap = null;

    static
    {
        accessMap = new HashMap<String, String>();

        accessMap.put(NEWS, "新闻");
        accessMap.put(PRODUCT, "产品介绍");
        accessMap.put(SOLVE, "解决方案");
        accessMap.put(TRADE_CASE, "行业案例");
        accessMap.put(IT_TERM, "IT术语");
        accessMap.put(FAQ, "FAQ");
        accessMap.put(BUG_CASE, "故障案例");
        accessMap.put(MEDIA_CASE, "多媒体中心");

    }

    public HashMap<String, String> accessMod()
    {
        return accessMap;
    }

}

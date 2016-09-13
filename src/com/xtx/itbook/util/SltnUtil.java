package com.xtx.itbook.util;

import java.util.HashMap;
import java.util.Map;

import com.xtx.itbook.entity.SglSolutions;
/**
 * 解决方案工具类
 * @author chenhuigang
 *
 */
public class SltnUtil
{
    private SltnUtil(){
        
    }
    //用于存储单个解决方案对象
    public static Map<String, SglSolutions> sglSltnMap = new HashMap<String, SglSolutions>();
    

}

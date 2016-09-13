package com.xtx.itbook.entity;

import java.io.Serializable;
/**
 * 产品术语服务器返回结果实体类
 * @author caocong
 * 2013.11.19
 *
 */
public class TerminologyResponse implements Serializable
{
    private boolean succ;
    
    private String msg;

    private String language;
    
    private long time;

    private String list;

    public boolean isSucc()
    {
        return succ;
    }

    public void setSucc(boolean succ)
    {
        this.succ = succ;
    }

    
    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public String getList()
    {
        return list;
    }

    public void setList(String list)
    {
        this.list = list;
    }

}

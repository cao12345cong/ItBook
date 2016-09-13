package com.xtx.itbook.entity;
/**
 * Faq服务返回结果
 * @author caocong
 * 2013.11.19
 */
public class FaqResponse
{
    private boolean succ;
    
    private String msg;

    private String language;

    private int total;

    private long time;

    private int state;

    private int number;// 追加的

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

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
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

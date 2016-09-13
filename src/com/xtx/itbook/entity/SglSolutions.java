package com.xtx.itbook.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.transform.Source;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 解决方案实体类
 * @author Administrator
 * 2013.10.31
 */
public class SglSolutions implements Serializable
{
    /**
     * 解决方案实体类
     */
    //解决方案pid
    private long pid;
    //解决方案更新时间
    private long time;
    //解决方案语言
    private String language;
    //排序
    private int sort;
    //上级目录id
    private long upId;
    //解决方案标题
    private String sltnTitle;
    
    //解决方案内容
    private String sltnContent;
    
   //解决方案图片
    private String sltnImageUrl;
    
    //解决方案相关内容集合
    private  List<SltnRltd>sltnRltdList;
    
    public List<SltnRltd> getSltnRltdList()
    {
        return sltnRltdList;
    }
    public void setSltnRltdList(List<SltnRltd> sltnRltdList)
    {
        this.sltnRltdList = sltnRltdList;
    }
    public String getSltnTitle()
    {
        return sltnTitle;
    }
    public void setSltnTitle(String sltnTitle)
    {
        this.sltnTitle = sltnTitle;
    }
    public String getSltnContent()
    {
        return sltnContent;
    }
    public void setSltnContent(String sltnContent)
    {
        this.sltnContent = sltnContent;
    }
    public String getSltnImageUrl()
    {
        return sltnImageUrl;
    }
    public void setSltnImageUrl(String sltnImageUrl)
    {
        this.sltnImageUrl = sltnImageUrl;
    }
    public long getPid()
    {
        return pid;
    }
    public void setPid(long pid)
    {
        this.pid = pid;
    }
    public long getTime()
    {
        return time;
    }
    public void setTime(long time)
    {
        this.time = time;
    }
    public String getLanguage()
    {
        return language;
    }
    public void setLanguage(String language)
    {
        this.language = language;
    }
    public int getSort()
    {
        return sort;
    }
    public void setSort(int sort)
    {
        this.sort = sort;
    }
    public long getUpId()
    {
        return upId;
    }
    public void setUpId(long upId)
    {
        this.upId = upId;
    }
 
   
    
}
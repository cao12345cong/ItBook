package com.xtx.itbook.entity;

import java.util.List;

/**
 * 解决方案详情实体类
 * @author ChenHuigang
 *
 */
public class SltnDtl
{
    //数据是否请求ok
    private boolean succ;
    //数据请求失败错误信息
    private String errorMsg;
    
    //语言类型
    private String language;
    //详情id
    private long id;
    //状态1或0
    private int state;
    //最新更新时间
    private long updateTime;
    //名称
    private String name;
    //简介
    private String intro;
    //标题大图,具体哪个图片
    private String imageUrl;
    //存放pdf的官网url
    private String colorpage;
    //子栏目条数
    private int total;
    //上级id
    private long upId;
    //排序
    private int sort;
    //相关内容集合
    private List<SltnRltd> sltnRltdList;
    public boolean isSucc()
    {
        return succ;
    }
    public void setSucc(boolean succ)
    {
        this.succ = succ;
    }
    public String getErrorMsg()
    {
        return errorMsg;
    }
    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
    public String getLanguage()
    {
        return language;
    }
    public void setLanguage(String language)
    {
        this.language = language;
    }
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }
    public int getState()
    {
        return state;
    }
    public void setState(int state)
    {
        this.state = state;
    }
    public long getUpdateTime()
    {
        return updateTime;
    }
    public void setUpdateTime(long updateTime)
    {
        this.updateTime = updateTime;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getIntro()
    {
        return intro;
    }
    public void setIntro(String intro)
    {
        this.intro = intro;
    }
    public String getImageUrl()
    {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }
    public String getColorpage()
    {
        return colorpage;
    }
    public void setColorpage(String colorpage)
    {
        this.colorpage = colorpage;
    }
    public int getTotal()
    {
        return total;
    }
    public void setTotal(int total)
    {
        this.total = total;
    }
    public long getUpId()
    {
        return upId;
    }
    public void setUpId(long upId)
    {
        this.upId = upId;
    }
    public int getSort()
    {
        return sort;
    }
    public void setSort(int sort)
    {
        this.sort = sort;
    }
    public List<SltnRltd> getSltnRltdList()
    {
        return sltnRltdList;
    }
    public void setSltnRltdList(List<SltnRltd> sltnRltdList)
    {
        this.sltnRltdList = sltnRltdList;
    }
    
    
}

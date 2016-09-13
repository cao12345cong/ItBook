package com.xtx.itbook.entity;

import java.util.List;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * 即 目录请求的 记录. 若没有更新则是最新的 一种语言 只有一条数据记录.
 * 目录列表实体类
 * @author caocong
 * 2013.10.24
 */
@Table(name="directory")
public class Directory
{   
    /**
     * 构造方法 .
     */
    public Directory()
    {
        super();
    }
    @Id
    private int _id;
    
    private int kind;          // 产品|解决方案|faq|It术语|故障案例        
    
    private String succ;       //成功与否.
    
    private String language;   // 语言
    
    private String state;      // 0 不可更新   1可更新.  当前请求返回的目录 是否需要更新.
    
    private long time;         // 时间
    
    private List<ListMenuEntry> list;
     
    

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}
	
	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getSucc()
    {
        return succ;
    }

    public void setSucc(String succ)
    {
        this.succ = succ;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public List<ListMenuEntry> getList()
    {
        return list;
    }

    public void setList(List<ListMenuEntry> list)
    {
        this.list = list;
    }

    
   

}

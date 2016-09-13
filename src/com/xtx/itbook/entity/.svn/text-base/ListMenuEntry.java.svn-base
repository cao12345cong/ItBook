/*
 * 文件名：ListMenuEntry.java
 * 描述：TODO
 * 修改人：王鹏
 * 修改时间：下午3:44:12
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.entity;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;


/**
 * 菜单属性.
 *  数据库中 表名 为 menu
 * @author Abelart.
 */
@Table(name="menu")
public class ListMenuEntry
{   
    /*
     *  通过注解表明 此字段为 主键.  使用afinal 一个类对应必须有一个主键.  
     *  @Id(column="name") 此写法表明 数据库中 name列 作为 主键
     */
	@Id
    private int _id;          //主键
    
    private int kind;        // 所属模块类型 产品？ faq? It 术语
    
    private long id;         //   目录的ID
    
    private String name;     //   目录的名字
    
    private int level;       //   目录层级
    
    private String imageurl; // 图片地址
    
    private int sort;        //排序
    
    private int node;        //1 为最终子节点.
    
    private String language; //语言
    
    private long upid;      //上级目录结构.
    
    /**
     * 构造方法 .
     */
    public ListMenuEntry()
    {
        super();
    }
    
    
 
    public int getKind() {
		return kind;
	}



	public void setKind(int kind) {
		this.kind = kind;
	}



	public int get_id()
    {
        return _id;
    }



    public void set_id(int _id)
    {
        this._id = _id;
    }



    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public String getImageurl()
    {
        return imageurl;
    }

    public void setImageurl(String imageurl)
    {
        this.imageurl = imageurl;
    }

    public int getSort()
    {
        return sort;
    }

    public void setSort(int sort)
    {
        this.sort = sort;
    }

    public int getNode()
    {
        return node;
    }

    public void setNode(int node)
    {
        this.node = node;
    }

    public long getUpid()
    {
        return upid;
    }

    public void setUpid(long upid)
    {
        this.upid = upid;
    }



    public String getLanguage()
    {
        return language;
    }



    public void setLanguage(String language)
    {
        this.language = language;
    }
    
    
    
    
}

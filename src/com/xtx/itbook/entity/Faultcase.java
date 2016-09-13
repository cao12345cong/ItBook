package com.xtx.itbook.entity;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * 故障案例实体类
 * @author caocong
 * 2013.10.31
 */
@Table(name = "faultcase")
public class Faultcase implements Serializable
{
    @Id
    private Integer _id; // 主键

    private Long id; // 故障案例的id

    private String name; // 故障案例原因

    private String resolve; // 解决方案具体内容

    private String upid; // 上级目录的id

    private String sort; // 排序

    private Long time; // 时间

    public Faultcase()
    {
        super();
    }

    public Integer get_id()
    {
        return _id;
    }

    public void set_id(Integer _id)
    {
        this._id = _id;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
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

    public String getResolve()
    {
        return resolve;
    }

    public void setResolve(String resolve)
    {
        this.resolve = resolve;
    }

    public String getUpid()
    {
        return upid;
    }

    public void setUpid(String upid)
    {
        this.upid = upid;
    }

    public String getSort()
    {
        return sort;
    }

    public void setSort(String sort)
    {
        this.sort = sort;
    }

    public Long getTime()
    {
        return time;
    }

    public void setTime(Long time)
    {
        this.time = time;
    }

}

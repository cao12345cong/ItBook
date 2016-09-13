package com.xtx.itbook.entity;

import java.io.Serializable;
import java.util.Date;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.ManyToOne;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * FAQ实体类
 * @author caocong
 *
 */
@Table(name = "faq")
public class Faq implements Serializable
{
    @Id(column = "_id")
    private Integer _id; // 主键

    private Long id; // faq的id

    private String question; // 问题

    private String answer; // 答案

    private Integer upid; // 上级目录Id

    private Integer sort; // 排序

    private Long time; // 时间

    public Faq()
    {
        super();
    }

    public Faq(String question, String answer)
    {
        super();
        this.question = question;
        this.answer = answer;
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

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public Integer getUpid()
    {
        return upid;
    }

    public void setUpid(Integer upid)
    {
        this.upid = upid;
    }

    public Integer getSort()
    {
        return sort;
    }

    public void setSort(Integer sort)
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

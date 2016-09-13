package com.xtx.itbook.entity;

import java.io.Serializable;
import java.util.Date;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * 产品术语实体类
 * @author caocong
 * 2013.10.25
 */

@Table(name = "terminology")
public class Terminology implements Serializable
{
    @Id
    private Integer _id; // 主键

    private Long id; // 产品术语id

    private Integer upid; // 上级目录

    private Long last_update_time; // 最新更新时间

    private Integer sort; // 排序

    private String word; // 术语名称

    private String explain; // 解释

    private String abbreviation; // 缩略词

    private String sysnonyms; // 同义词

    private String author; // 作者

    private String remark; // 备注

    private String illustrate; // 举例

    private String provenance; // 出处

    private String confidentiality;// 机密性 public公开， internal内部公开

    private String property; // 词性 词性 n 名称 V动词

    private Integer is_delete; // 是否删除 0 未删除 1 已删除

    private String firstLetter;// word第一个字母

    private String firstSpell; // word汉字串拼音首字母

    private String fullSpell;// word汉字串全拼

    public Terminology()
    {
        super();
    }

    public Terminology(String word)
    {
        super();
        this.word = word;
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation)
    {
        this.abbreviation = abbreviation;
    }

    public String getIllustrate()
    {
        return illustrate;
    }

    public void setIllustrate(String illustrate)
    {
        this.illustrate = illustrate;
    }

    public String getProvenance()
    {
        return provenance;
    }

    public void setProvenance(String provenance)
    {
        this.provenance = provenance;
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

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getSysnonyms()
    {
        return sysnonyms;
    }

    public void setSysnonyms(String sysnonyms)
    {
        this.sysnonyms = sysnonyms;
    }

    public Long getLast_update_time()
    {
        return last_update_time;
    }

    public void setLast_update_time(Long last_update_time)
    {
        this.last_update_time = last_update_time;
    }

    public Integer getSort()
    {
        return sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Integer getUpid()
    {
        return upid;
    }

    public void setUpid(Integer upid)
    {
        this.upid = upid;
    }

    public String getConfidentiality()
    {
        return confidentiality;
    }

    public void setConfidentiality(String confidentiality)
    {
        this.confidentiality = confidentiality;
    }

    public Integer getIs_delete()
    {
        return is_delete;
    }

    public void setIs_delete(Integer is_delete)
    {
        this.is_delete = is_delete;
    }

    public String getProperty()
    {
        return property;
    }

    public void setProperty(String property)
    {
        this.property = property;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public String getExplain()
    {
        return explain;
    }

    public void setExplain(String explain)
    {
        this.explain = explain;
    }

    public String getFirstLetter()
    {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter)
    {
        this.firstLetter = firstLetter;
    }

    public String getFirstSpell()
    {
        return firstSpell;
    }

    public void setFirstSpell(String firstSpell)
    {
        this.firstSpell = firstSpell;
    }

    public String getFullSpell()
    {
        return fullSpell;
    }

    public void setFullSpell(String fullSpell)
    {
        this.fullSpell = fullSpell;
    }

    @Override
    public String toString()
    {
        return "Terminology [_id=" + _id + ", id=" + id + ", author=" + author
                + ", sysnonyms=" + sysnonyms + ", last_update_time="
                + last_update_time + ", sort=" + sort + ", remark=" + remark
                + ", upid=" + upid + ", confidentiality=" + confidentiality
                + ", is_delete=" + is_delete + ", property=" + property
                + ", word=" + word + ", explain=" + explain + "]";
    }

}

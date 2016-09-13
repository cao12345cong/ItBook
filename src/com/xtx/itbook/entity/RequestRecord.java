package com.xtx.itbook.entity;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * 请求记录 .
 * @author Abelart
 *
 */
@Table(name="request_record")
public class RequestRecord
 {
	@Id
	private int _id;
	
	private int kind;        //类型.

	private long pid;        //下拉列表的目录ID

	private String language; //语言

	private long time = 0;   //时间.
	
	

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}

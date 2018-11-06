package com.label.common.model;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * 分页Model
 * 
 * @author Mmmmm
 *
 * @param <T>
 */
public class PageModel<T> {

	private Integer draw;
	private Integer start;//起始行
	private Integer length;//每页需展示的数据条数
	private Integer recordsTotal;//总记录数
	private Integer recordsFiltered;
	private List<T> data;
	
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	public void initData(Page<T> page) {
		
		this.data = page.getResult();
		this.recordsTotal = new Long(page.getTotal()).intValue();
		this.recordsFiltered = this.recordsTotal;
	}
}

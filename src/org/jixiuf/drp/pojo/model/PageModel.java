package org.jixiuf.drp.pojo.model;

public class PageModel {
	private Integer pageNow = 1;// 当前页号
	private Integer rowCount;// 总行数
	private Integer pageSize = 3;// 每页行数

	public PageModel() {
		super();
	}

	public PageModel(Integer pageNow, Integer rowCount, Integer pageSize) {
		super();
		this.pageNow = pageNow;
		this.rowCount = rowCount;
		this.pageSize = pageSize;
	}

	public PageModel(Integer pageNow, Integer pageSize) {
		super();
		this.pageNow = pageNow;
		this.pageSize = pageSize;
	}

	public Integer getPageNow() {
		if (pageNow <= 0) {
			this.pageNow = 1;
			return pageNow;
		}
		if (rowCount != null && pageNow > getPageCount()) {
			this.pageNow = getPageCount();
		}
		return pageNow;
	}

	public void setPageNow(Integer pageNow) {
		this.pageNow = pageNow;
	}

	// 返回页数
	public Integer getPageCount() {
		try {
			return getRowCount() % getPageSize() == 0 ? getRowCount()
					/ getPageSize() : getRowCount() / getPageSize() + 1;
		} catch (Exception e) {
			return 1;
		}
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public Integer getPageSize() {
		if (pageSize < 0)
			return 3;
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	// 当前页的第一行的行号,
	// from =0, to=3 ; [0,1,2] ,包括前不包括后
	public Integer getFrom() {
		return (getPageNow() - 1) * getPageSize();
	}

	// 当前页的最后一行行号(从0始计),此数据一般用不到
	public Integer getTo() {
		return getPageNow() * getPageSize();
	}

	@Override
	public String toString() {
		return "pageNow=" + getPageNow() +
		",pageCount=" + getPageCount()
				+ ",rowCount=" + getRowCount()
				+ ",pageSize=" + getPageSize()
				+ ",from=" + getFrom() +
				",to=" + getTo();
	}
}

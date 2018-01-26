package cn.fjlcx.application.bean;

public class SubmitPaging {
	private int pageIndex;
	private int pageSize;
	private String sortName;
	private String sortorder;//asc  desc
	private String keyWord;
	private String infos;
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getSortorder() {
		return sortorder;
	}
	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getInfos() {
		return infos;
	}
	public void setInfos(String infos) {
		this.infos = infos;
	}
	public SubmitPaging() {
		super();
	}
	public SubmitPaging(int pageIndex, int pageSize, String sortName, String sortorder, String keyWord, String infos) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.sortName = sortName;
		this.sortorder = sortorder;
		this.keyWord = keyWord;
		this.infos = infos;
	}
	
}

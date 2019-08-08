package com.movitech.mbox.modules.base.vo;


/**
 * gorden 提供给datatable
 * @param <T>
 */
public class DataTableResult<T> {
	private Boolean result;
	private String message;
	private T data;
	private int recordsFiltered;
    private int draw;
    private Long recordsTotal;

	public DataTableResult(Boolean result, String message) {
		this.result = result;
		this.message = message;
	}
	public DataTableResult() {
	}
	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public Long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
}

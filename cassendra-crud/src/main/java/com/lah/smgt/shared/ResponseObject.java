package com.lah.smgt.shared;

import java.util.List;
import java.util.Map;

public class ResponseObject {

	private List<Error> errList;
	private List<?> data;
	private Map<String, String> msg;
	
	public List<Error> getErrList() {
		return errList;
	}
	public void setErrList(List<Error> errList) {
		this.errList = errList;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	public Map<String, String> getMsg() {
		return msg;
	}
	public void setMsg(Map<String, String> msg) {
		this.msg = msg;
	}
	

}

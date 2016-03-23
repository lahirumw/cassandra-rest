package com.lah.smgt.shared;

/*
 * initiate by LahiruA
 * This is the response object for every HTTP method
 * 
 */

import java.util.List;
import java.util.Map;

public class ResponseObject {

	//for listing errors
	private List<Error> errList;
	
	//for listing requested data
	private List<?> data;
	
	//for bring successful message
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

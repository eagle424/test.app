package com.ba.test.app.vo;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonSaveVo {
	private String service;
	private String method;
	private String queryId;
	private String cpUsrLngDv;
	private String cpIp;
	private String cpUid;
	private String cpnId;
	private HashMap<String, Object> mapData;
	private ArrayList<HashMap<String, Object>> listData;
	private ArrayList<HashMap<String, Object>> listData2;
	private ArrayList<HashMap<String, Object>> listData3;

	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}

	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getCpUsrLngDv() {
		return cpUsrLngDv;
	}
	public void setCpUsrLngDv(String cpUsrLngDv) {
		this.cpUsrLngDv = cpUsrLngDv;
	}

	public String getCpIp() {
		return cpIp;
	}
	public void setCpIp(String cpIp) {
		this.cpIp = cpIp;
	}

	public String getCpUid() {
		return cpUid;
	}
	public void setCpUid(String cpUid) {
		this.cpUid = cpUid;
	}

	public String getCpnId() {
		return cpnId;
	}
	public void setCpnId(String cpnId) {
		this.cpnId = cpnId;
	}

	public HashMap<String, Object> getMapData() {
		return mapData;
	}
	public void setMapData(HashMap<String, Object> mapData) {
		this.mapData = mapData;
	}

	public ArrayList<HashMap<String, Object>> getListData() {
		return listData;
	}
	public void setListData(ArrayList<HashMap<String, Object>> listData) {
		this.listData = listData;
	}

	public ArrayList<HashMap<String, Object>> getListData2() {
		return listData2;
	}
	public void setListData2(ArrayList<HashMap<String, Object>> listData2) {
		this.listData2 = listData2;
	}

	public ArrayList<HashMap<String, Object>> getListData3() {
		return listData3;
	}
	public void getListData3(ArrayList<HashMap<String, Object>> listData3) {
		this.listData3 = listData3;
	}
}

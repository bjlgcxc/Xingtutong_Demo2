package com.framework.domain;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class DeviceInfo implements Serializable{
	
	public Integer id;
	public String imei;
	public String name;
	public Timestamp connectTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getConnectTime() {
		return connectTime;
	}
	public void setConnectTime(Timestamp connectTime) {
		this.connectTime = connectTime;
	}
}

package com.framework.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SystemInfo implements Serializable{

	public Integer id;
	public Integer locationInterval;
	public Integer locationUpload;
	public Integer locateInterval;
	public Integer locateTimes;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLocationInterval() {
		return locationInterval;
	}
	public void setLocationInterval(Integer locationInterval) {
		this.locationInterval = locationInterval;
	}
	public Integer getLocationUpload() {
		return locationUpload;
	}
	public void setLocationUpload(Integer locationUpload) {
		this.locationUpload = locationUpload;
	}
	public Integer getLocateInterval() {
		return locateInterval;
	}
	public void setLocateInterval(Integer locateInterval) {
		this.locateInterval = locateInterval;
	}
	public Integer getLocateTimes() {
		return locateTimes;
	}
	public void setLocateTimes(Integer locateTimes) {
		this.locateTimes = locateTimes;
	}

}

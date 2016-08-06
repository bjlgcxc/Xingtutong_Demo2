package com.framework.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.ConfigInfo;

@Repository
public class ConfigDao{
		
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public int queryConfigMatchCount(int deviceId){
		String sql = " select count(*) from t_config where deviceId=? ";
		Object args[] = new Object[]{deviceId};
		return jdbcTemplate.queryForInt(sql,args);
	}
	
	public ConfigInfo queryConfigInfo(int deviceId){
		String sql = "select * from t_config where deviceId=? ";
		Object args[] = new Object[]{deviceId};
		List<ConfigInfo> result = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<ConfigInfo>(ConfigInfo.class));
		if(result.size()!=0)
			return result.get(0);
		else
			return null;
	}
	
	public void insertConfigInfo(ConfigInfo configInfo){
		String sql = " insert into t_config(deviceId,braceletInterval,braceletUpload,locationInterval,locationUpload" +
				  ",locateInterval,locateTimes) values(?,?,?,?,?,?,?) ";
		Object args[] = new Object[]{configInfo.getDeviceId(),configInfo.getBraceletInterval(),configInfo.getBraceletUpload(),
				configInfo.getLocationInterval(),configInfo.getLocationUpload(),configInfo.getLocateInterval(),
				configInfo.getLocateTimes()};
		jdbcTemplate.update(sql, args);
	}
	
	public void updateConfigInfo(ConfigInfo configInfo){
	
		String sql = " update t_config set deviceId=" + configInfo.getDeviceId();
		
		if(configInfo.getBraceletInterval()!=null){
			sql += ",braceletInterval=" + configInfo.getBraceletInterval();
		}
		if(configInfo.getBraceletUpload()!=null){
			sql += ",braceletUpload=" + configInfo.getBraceletUpload();
		}
		if(configInfo.getLocationInterval()!=null){
			sql += ",locationInterval=" + configInfo.getLocationInterval();
		}
		if(configInfo.getLocationUpload()!=null){
			sql += ",locationUpload=" + configInfo.getLocationUpload();
		}
		if(configInfo.getLocateInterval()!=null){
			sql += ",locateInterval=" + configInfo.getLocateInterval();
		}
		if(configInfo.getLocateTimes()!=null){
			sql += ",locateTimes=" + configInfo.getLocateTimes();
		}
		sql += " where deviceId=" + configInfo.getDeviceId();
	
		jdbcTemplate.update(sql);	
	}
	
}

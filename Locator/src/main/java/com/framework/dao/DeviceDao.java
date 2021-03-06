package com.framework.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.DeviceInfo;

@Repository
public class DeviceDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int queryDeviceMatchCount(int id){
		String sql = " select count(*) from t_device where id=? ";
		return jdbcTemplate.queryForInt(sql,new Object[]{id});
	}
	
	// 查询设备imei匹配个数
	public int queryDeviceMatchCount(String imei){
		String sqlStr = " select count(*) from t_device where imei=? ";
		return jdbcTemplate.queryForInt(sqlStr,new Object[]{imei});
	}
	
	// 根据imei查询设备id号
	public int queryDeviceId(String imei){
		String sqlStr = " select id from t_device where imei=? ";
		return jdbcTemplate.queryForInt(sqlStr,new Object[]{imei});
	}
	
	// 插入设备信息 imei,connectTime
	public void insertDeviceInfo(DeviceInfo deviceInfo){
		String sqlStr = " insert into t_device(imei,connectTime) values(?,?) ";
		Object args[] = new Object[]{deviceInfo.getImei(),deviceInfo.getConnectTime()};	
		jdbcTemplate.update(sqlStr,args);
	}
	
	// 更新手机连接后台的时间
	public void updateConnectTime(DeviceInfo deviceInfo){
		String sqlStr = " update t_device set connectTime=? where id=? ";
		Object args[] = new Object[]{deviceInfo.getConnectTime(),deviceInfo.getId()};
		jdbcTemplate.update(sqlStr, args);
	}
	
    // 获取设备信息
	public List<DeviceInfo> getDeviceInfo(String deviceId,String name){
		String sql = " select * from t_device where 1=1 ";
		if(deviceId!=null && deviceId!=""){
			sql += " and id=" + deviceId;
		}
		if(name!=null && name!=""){ 
			sql += " and name=" + "'" + name + "'";
		}	
		List<DeviceInfo> result = jdbcTemplate.query(sql,new BeanPropertyRowMapper<DeviceInfo>(DeviceInfo.class));
		if(result.size()!=0)
			return result;
		else
			return null;
	}
	
	//更新设备名
	public void updateDeviceName(int deviceId,String deviceName){
		String sql = " update t_device set name=? where id=? ";
		Object args[] = new Object[]{deviceName,deviceId};
		jdbcTemplate.update(sql, args);
	}
	
}

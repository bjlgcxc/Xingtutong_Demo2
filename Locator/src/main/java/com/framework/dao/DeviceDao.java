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
	
	// ��ѯ�豸imeiƥ�����
	public int queryDeviceMatchCount(String imei){
		String sqlStr = " select count(*) from t_device where imei=? ";
		return jdbcTemplate.queryForInt(sqlStr,new Object[]{imei});
	}
	
	// ����id��ѯmac��ַ
	public String queryDeviceMac(int id){
		String sqlStr = " select mac from t_device where id=? ";
		return jdbcTemplate.queryForObject(sqlStr, new Object[]{id},java.lang.String.class);
	}
	
	// ����imei��ѯ�豸id��
	public int queryDeviceId(String imei){
		String sqlStr = " select id from t_device where imei=? ";
		return jdbcTemplate.queryForInt(sqlStr,new Object[]{imei});
	}
	
	// �����豸��Ϣ imei,connectTime
	public void insertDeviceInfo(DeviceInfo deviceInfo){
		String sqlStr = " insert into t_device(imei,connectTime) values(?,?) ";
		Object args[] = new Object[]{deviceInfo.getImei(),deviceInfo.getConnectTime()};	
		jdbcTemplate.update(sqlStr,args);
	}
	
	// �����豸��
	public void updateDeviceName(int deviceId,String deviceName){
		String sql = " update t_device set name=? where id=? ";
		Object args[] = new Object[]{deviceName,deviceId};
		jdbcTemplate.update(sql, args);
	}
	
	// �����ֻ����Ӻ�̨��ʱ��
	public void updateConnectTime(DeviceInfo deviceInfo){
		String sqlStr = " update t_device set connectTime=? where id=? ";
		Object args[] = new Object[]{deviceInfo.getConnectTime(),deviceInfo.getId()};
		jdbcTemplate.update(sqlStr, args);
	}
	
    // ��ȡ�豸��Ϣ
	public List<DeviceInfo> getDeviceInfo(String deviceId,String imei,String deviceName){
		String sql = " select * from t_device where 1=1 ";
		if(deviceId!=null && deviceId!=""){
			sql += " and id=" + deviceId;
		}
		if(imei!=null && imei!=""){ 
			sql += " and imei=" + "'" + imei + "'";
		}
		if(deviceName!=null && deviceName!=""){ 
			sql += " and name=" + "'" + deviceName + "'";
		}
		
		List<DeviceInfo> result = jdbcTemplate.query(sql,new BeanPropertyRowMapper<DeviceInfo>(DeviceInfo.class));
		if(result.size()!=0)
			return result;
		else
			return null;
	}
	
}

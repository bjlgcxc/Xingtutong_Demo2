package com.framework.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.Instruction;

@Repository
public class InstructionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//��ѯָ����Ϣ
	public List<Instruction> queryInstructions(int deviceId){
		String sql = "select * from t_instruction where deviceId=? and isSend=0";
		Object args[] = {deviceId};	
		try{
			List<Instruction> instructions = jdbcTemplate.query(sql, args,
											new BeanPropertyRowMapper<Instruction>(Instruction.class));
			return instructions;
		}
		catch(DataAccessException e){
			return null;      //��������Ҫ���͵�ָ��
		}
	}
	
	//����ָ��ͱ��
	public void updateInstructionSendInfo(Instruction instruction){
		String sql = " update t_instruction set isSend=1 where deviceId=? and timestamp=? ";
		Object args[] = new Object[]{instruction.getDeviceId(),instruction.getTimestamp()};
		jdbcTemplate.update(sql, args);
	}
	
	//�������ݲɼ����
	public void insertSampleInterval(int deviceId,int sampleInterval){
		String sql = " insert into t_instruction(deviceId,sampleInterval) values(?,?) ";
		Object args[] = new Object[]{deviceId,sampleInterval};
		jdbcTemplate.update(sql, args);
	}
	
	//����ÿ���ϴ�����������
	public void insertUploadEverytime(int deviceId,int uploadEverytime){
		String sql = " insert into t_instruction(deviceId,uploadEverytime) values(?,?) ";
		Object args[] = new Object[]{deviceId,uploadEverytime};
		jdbcTemplate.update(sql, args);
	}
	
	//���������λ��Ϣ
	public void insertLocateInfo(int deviceId,int locateInterval,int locateTimes){
		String sql = " insert into t_instruction(deviceId,locateInterval,locateTimes) value(?,?,?) ";
		Object args[] = new Object[]{deviceId,locateInterval,locateTimes};
		jdbcTemplate.update(sql,args);
	}
	
	//���������Ϣ
	public void insertBasicInfo(int deviceId,String teleNumber){
		String sql = " insert into t_instruction(deviceId,teleNumber) values(?,?)"; 
		Object args[] = new Object[]{deviceId,teleNumber};
		jdbcTemplate.update(sql,args);
	}
	
}

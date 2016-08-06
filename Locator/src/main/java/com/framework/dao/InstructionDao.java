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
	
	//����ָ��
	public void insertInstruction(Instruction instruction){
		String sql = " insert t_instruction(deviceId,braceletInterval,braceletUpload,locationInterval,locationUpload," +
				"locateInterval,locateTimes) values(?,?,?,?,?,?,?) ";
		Object args[] = new Object[]{instruction.getDeviceId(),instruction.getBraceletInterval(),instruction.getBraceletUpload(),
				instruction.getLocationInterval(),instruction.getLocationUpload(),instruction.getLocateInterval(),instruction.getLocateTimes(),
				};
		jdbcTemplate.update(sql, args);
	}	
	
	//����ָ��ͱ��
	public void updateInstructionSendInfo(Instruction instruction){
		String sql = " update t_instruction set isSend=1 where deviceId=? and timestamp=? ";
		Object args[] = new Object[]{instruction.getDeviceId(),instruction.getTimestamp()};
		jdbcTemplate.update(sql, args);
	}

}

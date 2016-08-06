package com.framework.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.domain.ConfigInfo;
import com.framework.domain.Instruction;
import com.framework.service.ConfigService;
import com.framework.service.InstructionService;

@Controller
public class InstructionController {
	
	private static final Log log =  LogFactory.getLog(InstructionController.class);
	
	@Autowired
	InstructionService instructionService;
	@Autowired
	ConfigService configService;
	
	
	/*
	 * ����ָ�app(josnArray)
	 */
	@ResponseBody
	@RequestMapping(value="/app/instruction/{deviceId}/returnJsonArray")
	public List<Instruction> returnInstructions(@PathVariable int deviceId){
		 List<Instruction> instructions = instructionService.getInstructions(deviceId);
		 for(Instruction instruction:instructions){
			 instructionService.updateInstructionSendInfo(instruction);
		 } 
		 
		 log.info("return instructions,size=" + instructions.size());
		 return instructions;
	}
	
	
	/*
	 * �����ֻ����ݲɼ�ָ��
	 */
	@ResponseBody
	@RequestMapping(value="/instruction/{deviceId}/saveBraceletSample",method=RequestMethod.POST)
	public void saveSampleInterval(HttpServletRequest request,@PathVariable int deviceId,Instruction instruction){
		instruction.setDeviceId(deviceId);		
		if(instruction.getBraceletInterval()!=null){
			instruction.setBraceletInterval(60*instruction.getBraceletInterval());
		}
		instructionService.addInstruction(instruction);
		
		//����������Ϣ
		ConfigInfo configInfo = new ConfigInfo();
		configInfo.setDeviceId(deviceId);
		configInfo.setBraceletInterval(instruction.getBraceletInterval());
		configInfo.setBraceletUpload(instruction.getBraceletUpload());
		configService.updateConfigInfo(configInfo);
	}

	
	/*
	 * ����λ�òɼ�ָ��
	 */
	@ResponseBody
	@RequestMapping(value="/instruction/{deviceId}/saveLocationSample",method=RequestMethod.POST)
	public void saveUploadEverytime(HttpServletRequest request,@PathVariable int deviceId,Instruction instruction){
		instruction.setDeviceId(deviceId);		
		if(instruction.getLocationInterval()!=null){
			instruction.setLocationInterval(60*instruction.getLocationInterval());
		}
		instructionService.addInstruction(instruction);
		
		//����������Ϣ
		ConfigInfo configInfo = new ConfigInfo();
		configInfo.setDeviceId(deviceId);
		configInfo.setLocationInterval(instruction.getLocationInterval());
		configInfo.setLocationUpload(instruction.getLocationUpload());
		configService.updateConfigInfo(configInfo);
	}
	
	
	/*
	 * ���������λָ��
	 */
	@ResponseBody
	@RequestMapping(value="/instruction/{deviceId}/saveLocateInfo",method=RequestMethod.POST)
	public void saveLocateInfo(HttpServletRequest request,@PathVariable int deviceId,Instruction instruction){
		instruction.setDeviceId(deviceId);
		instructionService.addInstruction(instruction);
		
		//����������Ϣ
		ConfigInfo configInfo = new ConfigInfo();
		configInfo.setDeviceId(deviceId);
		configInfo.setLocateInterval(instruction.getLocateInterval());
		configInfo.setLocateTimes(instruction.getLocateTimes());
		configService.updateConfigInfo(configInfo);
	}
	

	/*
	 * �����������(ָ��)
	 */
	@ResponseBody
	@RequestMapping(value="/instruction/{deviceId}/saveBasicInfo",method=RequestMethod.POST)
	public void saveBasicInfo(HttpServletRequest request,@PathVariable int deviceId,Instruction instruction){
		instruction.setDeviceId(deviceId);
		instructionService.addInstruction(instruction);
		
		//����������Ϣ
		ConfigInfo configInfo = new ConfigInfo();
		configInfo.setDeviceId(deviceId);
		configService.updateConfigInfo(configInfo);
	}
	
}

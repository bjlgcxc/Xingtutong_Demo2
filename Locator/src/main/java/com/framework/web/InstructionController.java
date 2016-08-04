package com.framework.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 * ����ɼ����(ָ��)
	 */
	@RequestMapping(value="/instruction/{deviceId}/saveSampleInterval")
	public void saveSampleInterval(HttpServletRequest request,@PathVariable int deviceId){
		int sampleInterval = 0;
		String minutes = request.getParameter("minutes");
		sampleInterval += 60*Integer.parseInt(minutes);
		instructionService.addSampleInterval(deviceId, sampleInterval);
		
		//����������Ϣ
		ConfigInfo configInfo = new ConfigInfo();
		configInfo.setDeviceId(deviceId);
		configInfo.setSampleInterval(sampleInterval);
		configService.updateConfigInfo(configInfo);
	}

	
	/*
	 * ����ÿ���ϴ���������(ָ��)
	 */
	@RequestMapping(value="/instruction/{deviceId}/saveUploadEverytime")
	public void saveUploadEverytime(HttpServletRequest request,@PathVariable int deviceId){
		int uploadEverytime = Integer.parseInt(request.getParameter("uploadEverytime"));
		instructionService.addUploadEverytime(deviceId, uploadEverytime);
		
		//����������Ϣ
		ConfigInfo configInfo = new ConfigInfo();
		configInfo.setDeviceId(deviceId);
		configInfo.setUploadEverytime(uploadEverytime);
		configService.updateConfigInfo(configInfo);
	}
	
	
	/*
	 * ���������λ(ָ��)
	 */
	@RequestMapping(value="/instruction/{deviceId}/saveLocateInfo")
	public void saveLocateInfo(HttpServletRequest request,@PathVariable int deviceId){
		int locateInterval = Integer.parseInt(request.getParameter("locateInterval"));
		int locateTimes = Integer.parseInt(request.getParameter("locateTimes"));
		instructionService.addLocateInfo(deviceId, locateInterval, locateTimes);
		
		//����������Ϣ
		ConfigInfo configInfo = new ConfigInfo();
		configInfo.setDeviceId(deviceId);
		configInfo.setLocateInterval(locateInterval);
		configInfo.setLocateTimes(locateTimes);
		configService.updateConfigInfo(configInfo);
	}
	

	/*
	 * �����������(ָ��)
	 */
	@RequestMapping(value="/instruction/{deviceId}/saveBasicInfo")
	public void saveBasicInfo(HttpServletRequest request,@PathVariable int deviceId){
		String teleNumber = request.getParameter("teleNumber");
		instructionService.addBasicInfo(deviceId, teleNumber);
		
		//����������Ϣ
		ConfigInfo configInfo = new ConfigInfo();
		configInfo.setDeviceId(deviceId);
		configInfo.setTeleNumber(teleNumber);
		configService.updateConfigInfo(configInfo);
	}
	
}

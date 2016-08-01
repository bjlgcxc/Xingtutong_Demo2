package com.framework.web;

import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.domain.DeviceInfo;
import com.framework.service.DeviceService;
import com.framework.service.InstructionService;

@Controller
public class DeviceController {
	
	private static final Log log =  LogFactory.getLog(DeviceController.class);
	
	@Autowired
	DeviceService deviceService;
	@Autowired
	InstructionService instructionService;

	
	/*
	 * �ֻ���¼app,�����ֻ���Ϣ(imei)����̨,��̨����app��Ӧ���豸id
	 */
	@ResponseBody
	@RequestMapping(value="/app/device/{imei}/appLogin")
	public DeviceInfo appLogin(@PathVariable String imei){
		
		//imei�Ƿ��Ѿ�ע��
		boolean isDeviceExist = deviceService.hasMatchDevice(imei);
		
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setImei(imei);
		deviceInfo.setConnectTime(new Timestamp(System.currentTimeMillis()));
		//��δע�ᣬ���ע�᣻����ע�ᣬ��������ʱ��
		if(!isDeviceExist)
			deviceService.addDeviceInfo(deviceInfo);
		else{
			deviceInfo.setId(deviceService.getDeviceId(imei));
			deviceService.updateConnectTime(deviceInfo);
		}
		
		//����imei��id�Ķ�Ӧ��Ϣ
		int id = deviceService.getDeviceId(imei);
		deviceInfo = new DeviceInfo();
		deviceInfo.setId(id);
		deviceInfo.setImei(imei);
		
		log.info("get imei and return id");
		return deviceInfo;
	}
	

	/*
	 * app���̨ͨ��ʱ,����ͨ��ʱ��
	 */
	@RequestMapping(value="/app/device/{deviceId}/refreshTime")
	public void connect(@PathVariable int deviceId){
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setId(deviceId);
		deviceInfo.setConnectTime(new Timestamp(System.currentTimeMillis()));
		deviceService.updateConnectTime(deviceInfo);
	}
	
}

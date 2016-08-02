package com.framework.web;

import java.sql.Timestamp;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.domain.ConfigInfo;
import com.framework.domain.DeviceInfo;
import com.framework.service.ConfigService;
import com.framework.service.DeviceService;
import com.framework.service.InstructionService;

@Controller
public class DeviceController {
	
	private static final Log log =  LogFactory.getLog(DeviceController.class);
	
	@Autowired
	DeviceService deviceService;	
	@Autowired
	InstructionService instructionService;
	@Autowired
	ConfigService configService;
	
	/*
	 * �ֻ���¼app,�����ֻ���Ϣ(imei)����̨,��̨����app��Ӧ���豸id
	 */
	@ResponseBody
	@RequestMapping(value="/app/device/{imei}/appLogin")
	public DeviceInfo appLogin(@PathVariable String imei){
		
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setImei(imei);
		deviceInfo.setConnectTime(new Timestamp(System.currentTimeMillis()));
		//��δע�ᣬ���ע�᣻����ע�ᣬ��������ʱ��
		if(!deviceService.hasMatchDevice(imei))
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
		
		//��û��������Ϣ�������Ĭ������
		if(!configService.hasMatchConfig(id)){
			ConfigInfo configInfo = new ConfigInfo();
			configInfo.setSampleInterval(60);
			configInfo.setUploadEverytime(1);
			configService.insertConfigInfo(configInfo);
		}
				
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
	
	
	/*
	 * ��ȡ�豸��Ϣ�б�
	 */
	@RequestMapping(value="device.html")
	public String getDeviceInfo(HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
		String deviceName = (String) request.getParameter("deviceName");
	
		JSONArray jsonArray = new JSONArray();
		List<DeviceInfo> deviceInfo = deviceService.getDeviceInfo(deviceId,null,deviceName);
		for(DeviceInfo df:deviceInfo){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", df.getId());
			jsonObj.put("name", df.getName());
			jsonObj.put("connectTime", df.getConnectTime());
			jsonArray.add(jsonObj);
		}

		request.setAttribute("deviceInfo", jsonArray);
		return "device";
	}
}

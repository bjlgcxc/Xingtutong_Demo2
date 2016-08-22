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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.domain.ConfigInfo;
import com.framework.domain.DeviceInfo;
import com.framework.domain.SystemInfo;
import com.framework.service.ConfigService;
import com.framework.service.DeviceService;
import com.framework.service.InstructionService;
import com.framework.service.SystemService;

@Controller
public class DeviceController {
	
	private static final Log log =  LogFactory.getLog(DeviceController.class);
	
	@Autowired
	DeviceService deviceService;	
	@Autowired
	InstructionService instructionService;
	@Autowired
	ConfigService configService;
	@Autowired
	SystemService systemService;
	
	/*
	 * �ֻ���¼app,�����ֻ���Ϣ(imei)����̨,��̨����app��Ӧ���豸id
	 */
	@ResponseBody
	@RequestMapping(value="/app/device/{imei}/appLogin")
	public synchronized JSONObject appLogin(@PathVariable String imei){
		
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
		
		//configInfo
		int id = deviceService.getDeviceId(imei);
		ConfigInfo configInfo = null;
		if(!configService.hasMatchConfig(id)){
			//��û��������Ϣ�������Ĭ������
			SystemInfo sysDefault = systemService.getSysDefault();
			configInfo = new ConfigInfo();
			configInfo.setDeviceId(id);
			configInfo.setLocationInterval(sysDefault.getLocationInterval());
			configInfo.setLocationUpload(sysDefault.getLocationUpload());
			configInfo.setLocateInterval(sysDefault.getLocateInterval());
			configInfo.setLocateTimes(sysDefault.getLocateTimes());
			configService.insertConfigInfo(configInfo);
		}
		else{
			configInfo = configService.getConfigInfo(id);
		}
				
		log.info("send device_id and config_info to device");
		JSONObject jsonObj = JSONObject.fromObject(configInfo);
		jsonObj.put("id",configInfo.getDeviceId());
		return jsonObj;
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
		List<DeviceInfo> deviceInfoList = deviceService.getDeviceInfo(deviceId,deviceName);
		if(deviceInfoList!=null){
			for(DeviceInfo deviceInfo:deviceInfoList){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", deviceInfo.getId());
				jsonObj.put("name", deviceInfo.getName());
				jsonObj.put("connectTime", deviceInfo.getConnectTime().getTime());
				jsonArray.add(jsonObj);
			}
		}
		request.setAttribute("deviceInfo", jsonArray);
		return "device";
	}
	
	/*
	 * �޸��豸��
	 */
	@ResponseBody
	@RequestMapping(value="/device/{deviceId}/updateDeviceName",method = RequestMethod.POST)
	public void updateBraceletAlias(HttpServletRequest request,@PathVariable int deviceId){
		String deviceName = request.getParameter("deviceName");
		deviceService.updateDeviceName(deviceId,deviceName);
	}
}

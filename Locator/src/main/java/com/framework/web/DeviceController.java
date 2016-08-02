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
	 * 手机登录app,发送手机信息(imei)到后台,后台返给app相应的设备id
	 */
	@ResponseBody
	@RequestMapping(value="/app/device/{imei}/appLogin")
	public DeviceInfo appLogin(@PathVariable String imei){
		
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setImei(imei);
		deviceInfo.setConnectTime(new Timestamp(System.currentTimeMillis()));
		//若未注册，添加注册；若已注册，更新连接时间
		if(!deviceService.hasMatchDevice(imei))
			deviceService.addDeviceInfo(deviceInfo);
		else{
			deviceInfo.setId(deviceService.getDeviceId(imei));
			deviceService.updateConnectTime(deviceInfo);
		}
		
		//返回imei和id的对应信息
		int id = deviceService.getDeviceId(imei);
		deviceInfo = new DeviceInfo();
		deviceInfo.setId(id);
		deviceInfo.setImei(imei);
		
		//若没有配置信息，则插入默认配置
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
	 * app与后台通信时,更新通信时间
	 */
	@RequestMapping(value="/app/device/{deviceId}/refreshTime")
	public void connect(@PathVariable int deviceId){
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setId(deviceId);
		deviceInfo.setConnectTime(new Timestamp(System.currentTimeMillis()));
		deviceService.updateConnectTime(deviceInfo);
	}
	
	
	/*
	 * 获取设备信息列表
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

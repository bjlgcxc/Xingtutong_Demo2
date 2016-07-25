package com.framework.web;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.framework.domain.BraceletInfo;
import com.framework.service.BraceletService;
import com.framework.service.DeviceService;

@Controller
public class SpecialController {
	
	private static final Log log =  LogFactory.getLog(SpecialController.class);
	
	@Autowired
	DeviceService deviceService;
	@Autowired
	BraceletService braceletService;
	
	/*
	 * 保存手环状态信息、健康信息
	 */
	@RequestMapping(value="app/special/{deviceId}/saveJsonArray",method = RequestMethod.POST)
	public String specialJson(@RequestBody JSONObject[] jsonArray,@PathVariable int deviceId){
		
		for(JSONObject jsonObj:jsonArray){
			String mac = deviceService.getDeviceMac(deviceId);
		
			//手环状态信息更新	
			BraceletInfo braceletInfo = new BraceletInfo();
			braceletInfo.setMac(mac);
			braceletInfo.setBatteryLevel(jsonObj.getInt("batteryLevel"));
			braceletInfo.setBatteryState(jsonObj.getInt("batteryState"));
			braceletService.updateBraceletStateInfo(braceletInfo);
			log.info("get braceStateInfo");
	
		}
		
		return "redirect:/app/instruction/" + deviceId + "/returnJsonArray";
		
	}
	
}

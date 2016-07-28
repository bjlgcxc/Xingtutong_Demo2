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
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.domain.ConfigInfo;
import com.framework.service.ConfigService;

@Controller
public class ConfigController {
	
	private static final Log log =  LogFactory.getLog(ConfigController.class);
	
	@Autowired
	ConfigService configService;
	
	
	/*
	 * ��������(json)
	 */
	@ResponseBody
	@RequestMapping(value="/app/config/{deviceId}/saveJson")
	public void saveJson(@RequestBody JSONObject jsonObj,@PathVariable int deviceId){
		ConfigInfo configInfo = (ConfigInfo)JSONObject.toBean(jsonObj,ConfigInfo.class);
		configInfo.setDeviceId(deviceId);
		
		//��������ڣ������
		if(!configService.hasMatchConfig(deviceId)){
			configService.insertConfigInfo(configInfo);
		}
		//���ڣ������
		else{
			configService.updateConfigInfo(configInfo);
		}
		
		log.info("save config info");
	}

	
	/*
	 * ��ȡ������Ϣ
	 */
	@ResponseBody
	@RequestMapping(value="/config/{deviceId}/getConfigInfo",method = RequestMethod.GET)
	public ConfigInfo getConfigInfo(@PathVariable int deviceId){
		ConfigInfo configInfo;
		configInfo = configService.getConfigInfo(deviceId);
		
		return configInfo;
	}
	
}
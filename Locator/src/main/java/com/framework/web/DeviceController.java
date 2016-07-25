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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.domain.BraceletInfo;
import com.framework.domain.DeviceInfo;
import com.framework.service.BraceletService;
import com.framework.service.DeviceService;
import com.framework.service.InstructionService;

@Controller
public class DeviceController {
	
	private static final Log log =  LogFactory.getLog(DeviceController.class);
	
	@Autowired
	DeviceService deviceService;
	@Autowired
	BraceletService braceletService;
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
	 * �ֻ���������,app�����ֻ���Ϣ����̨
	 */
	@RequestMapping(value="/app/device/{deviceId}/bluetoothConn",method = RequestMethod.POST)
	public String bluetoothConn(@RequestBody JSONObject jsonObj,@PathVariable int deviceId){
		
		//�����豸(�ֻ�)��mac��ַ
		String mac = jsonObj.getString("mac");
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setId(deviceId);
		deviceInfo.setMac(mac);
		deviceInfo.setConnectTime(new Timestamp(System.currentTimeMillis()));
		deviceService.updateDevicetInfo(deviceInfo);
		
		//����ֻ�����Ϣ
		BraceletInfo braceletInfo = (BraceletInfo)JSONObject.toBean(jsonObj,BraceletInfo.class);
		boolean isBraceletExist = braceletService.hasMatchBracelet(mac);
		if(!isBraceletExist)
			braceletService.addBraceletInfo(braceletInfo);
		else{
			braceletService.updateBraceletInfo(braceletInfo);
		}
		
		log.info("get bracelet info");
		return "redirect:/app/instruction/" + deviceId + "/returnJsonArray";
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
		String deviceAlias = request.getParameter("deviceAlias");
		
		JSONArray jsonArray = new JSONArray();
		if(deviceId!=null && deviceId!=""){
			List<DeviceInfo> deviceInfo = deviceService.getDeviceInfo(deviceId,null, null);
			if(deviceInfo!=null){
				List<BraceletInfo> braceletInfoList = braceletService.getBraceletInfo(deviceInfo.get(0).getMac(),deviceName, deviceAlias); 
				if(braceletInfoList!=null){			
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", deviceId);
					jsonObj.put("name",braceletInfoList.get(0).getName());
					jsonObj.put("alias",braceletInfoList.get(0).getAlias());
					jsonObj.put("connectTime",deviceInfo.get(0).getConnectTime().getTime());
					jsonArray.add(jsonObj);
				}
			}
		}
		else{
			List<BraceletInfo> braceletInfoList = braceletService.getBraceletInfo(null,deviceName, deviceAlias); 
			if(braceletInfoList!=null){
				for(BraceletInfo braceletInfo:braceletInfoList){
					JSONObject jsonObj = new JSONObject();
					String mac = braceletInfo.getMac();
					List<DeviceInfo> deviceInfoList = deviceService.getDeviceInfo(null,null,mac);
					if(deviceInfoList!=null){
						jsonObj.put("id",deviceInfoList.get(0).getId());
						jsonObj.put("name",braceletInfo.getName());
						jsonObj.put("alias", braceletInfo.getAlias());
						jsonObj.put("connectTime",deviceInfoList.get(0).getConnectTime().getTime());
						jsonArray.add(jsonObj);
				    }
				}
			}
		}
		
		request.setAttribute("deviceInfo", jsonArray);
		return "device";
	}
}

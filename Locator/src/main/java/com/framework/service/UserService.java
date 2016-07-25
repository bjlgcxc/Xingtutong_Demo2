package com.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.*;
import com.framework.domain.*;

@Service  
public class UserService {

	@Autowired
	private UserDao userDao;
	
	//1.����û���/�������ȷ��
	public boolean hasMatchUser(String userName,String password){
		int matchCount = userDao.getMatchCount(userName, password);
		return matchCount > 0;
	}
	
	//2.���û���Ϊ��������User����
	public UserInfo findUserByUserName(String userName){
		return userDao.findUserByUserName(userName);
	}
	
	//3.�����û���¼��Ϣ
	public void loginSuccess(UserInfo userInfo){
		userDao.updateLoginInfo(userInfo);
	}
	
}

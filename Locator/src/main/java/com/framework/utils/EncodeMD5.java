package com.framework.utils;

import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;

/**
 * MD5�����㷨
 * @author caixiaocong
 *
 */
public class EncodeMD5 {
	
	private final static String[] hexDigits = {"0", "1", "2", "3", "4",   
        "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};   
	
	
	public static String encode(String message) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
	    byte[] result = md.digest(message.getBytes());
	    String resultString = byteArrayToHexString(result);

	    return resultString;
	}
	 
	private static String byteArrayToHexString(byte[] b){   
         StringBuffer resultSb = new StringBuffer();   
        for (int i = 0; i < b.length; i++){   
             resultSb.append(byteToHexString(b[i]));   
         }   
        return resultSb.toString();   
    }   
       
      
    private static String byteToHexString(byte b){   
        int n = b;   
        if (n < 0)   
             n = 256 + n;   
        int d1 = n / 16;   
        int d2 = n % 16;   
        return hexDigits[d1] + hexDigits[d2];   
    }   
	

}

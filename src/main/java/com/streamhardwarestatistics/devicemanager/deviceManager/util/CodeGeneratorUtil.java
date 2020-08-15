package com.streamhardwarestatistics.devicemanager.deviceManager.util;

import java.security.SecureRandom;

public class CodeGeneratorUtil {

	static final int codeLength = 6;
	static SecureRandom random = new SecureRandom();
	static final String characterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String generateCode() {
	   StringBuilder sb = new StringBuilder(codeLength);
	   for(int i = 0; i < codeLength; i++) 
	      sb.append(characterSet.charAt(random.nextInt(characterSet.length())));
	   return sb.toString();
	}

}

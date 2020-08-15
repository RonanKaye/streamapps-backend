package com.streamhardwarestatistics.devicemanager.deviceManager.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.streamhardwarestatistics.devicemanager.deviceManager.models.Code;
import com.streamhardwarestatistics.devicemanager.deviceManager.services.CodeHandlerService;
import com.streamhardwarestatistics.devicemanager.deviceManager.util.CodeGeneratorUtil;

@Service
public class CodeHandlerServiceImpl implements CodeHandlerService {

	private Map<String, Code> codesByCodeMap;

	private final Long expirationTimeOncePaired = 60000L; // 30 Seconds
	private final Long cleanupExpiredInterval = 1000L; // 1 second

	public CodeHandlerServiceImpl() {
		codesByCodeMap = new ConcurrentHashMap<String, Code>();

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(() -> codesByCodeMap.values().removeIf(code -> code.isExpired()), cleanupExpiredInterval, cleanupExpiredInterval, TimeUnit.MILLISECONDS);
	}

	@Override
	public Code generateUniqueCode() {
		String shortCode = null;

		do {
			shortCode = CodeGeneratorUtil.generateCode();
		} while(codesByCodeMap.containsKey(shortCode));

		Code generatedCode = new Code(shortCode, expirationTimeOncePaired);
		codesByCodeMap.put(generatedCode.getShortCode(), generatedCode);

		return generatedCode;
	}

	@Override
	public String linkCode(String shortCode) {
		if(codesByCodeMap.containsKey(shortCode))
			return codesByCodeMap.remove(shortCode).getDeviceId();
		return null;
	}

}

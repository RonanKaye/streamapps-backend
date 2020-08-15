package com.streamhardwarestatistics.devicemanager.deviceManager.models;

import java.util.UUID;

public class Code {

	private String shortCode;
	private String deviceId;
	private Long creationTime;
	private Long expirationTime;

	public Code(String code, Long duration) {
		this.shortCode = code;
		this.deviceId = UUID.randomUUID().toString();
		this.creationTime = System.currentTimeMillis();
		this.expirationTime = creationTime + duration;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String code) {
		this.shortCode = code;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public boolean isExpired() {
		return System.currentTimeMillis() > expirationTime;
	}
}

package com.streamhardwarestatistics.devicemanager.deviceManager.services;

import com.streamhardwarestatistics.devicemanager.deviceManager.models.Code;

public interface CodeHandlerService {

	/**
	 * Generates a unique code that is not currently being used in the matching system.
	 * @param duration the length of time the code should stay valid for
	 * @return a code that is not currently in use
	 */
	public Code generateUniqueCode();

	/**
	 * Used to remove a code once the match as been made. This ensures that a code can
	 * not be used to register a device on two different accounts.
	 * @param code the code to invalidate
	 * @return the deviceId(UUID) associated with the short code provided
	 */
	public String linkCode(String shortCode);

}

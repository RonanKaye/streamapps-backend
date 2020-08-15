package com.streamhardwarestatistics.devicemanager.deviceManager.services;

import com.github.twitch4j.TwitchClient;

public interface TwitchClientService {

	/**
	 * Gets the twitch client. Used to make Twitch API calls.
	 */
	public TwitchClient getClient();
}

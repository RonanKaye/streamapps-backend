package com.streamhardwarestatistics.devicemanager.deviceManager.impl;

import org.springframework.stereotype.Service;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.streamhardwarestatistics.devicemanager.deviceManager.services.TwitchClientService;

@Service("TwitchClientService")
public class TwitchClientServiceImpl implements TwitchClientService {

	TwitchClient client;

	TwitchClientServiceImpl() {
		client = TwitchClientBuilder.builder()
				.withEnableHelix(true)
			    .withClientId("clientId")
			    .withClientSecret("clientSecret")
			    .build();
	}

	@Override
	public TwitchClient getClient() {
		return client;
	}
	
}
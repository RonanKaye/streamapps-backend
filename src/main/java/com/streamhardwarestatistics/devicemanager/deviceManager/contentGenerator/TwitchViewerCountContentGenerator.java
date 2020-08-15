package com.streamhardwarestatistics.devicemanager.deviceManager.contentGenerator;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.helix.domain.StreamList;

@Entity
@Table(name = "twitch_viewer_count_content_generators")
public class TwitchViewerCountContentGenerator extends ContentGenerator {

	private static final long serialVersionUID = -8730839529766081447L;

	@Column(name = "apiKey")
	private String apiKey;

	@Column(name = "twitchUsername")
	private String twitchUsername;

	public TwitchViewerCountContentGenerator() {
		super();
	}

	public TwitchViewerCountContentGenerator(String apiKey, String twitchUsername, String displayName) {
		super();
		super.setType("twitchViewerCount");
		setApiKey(apiKey);
		setTwitchUsername(twitchUsername);
		setDisplayName(displayName);
	}

	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setTwitchUsername(String twitchUsername) {
		this.twitchUsername = twitchUsername;
	}

	public String getTwitchUsername() {
		return this.twitchUsername;
	}

	@Override
	public String generateContent(TwitchClient twitchClient) {
		List<String> loginToGet = new ArrayList<String>();
		loginToGet.add(getTwitchUsername());

		int count = 0;

		StreamList resultList = twitchClient.getHelix().getStreams(getApiKey(), null, null, null, null, null, null, null, loginToGet).execute();
		if(!resultList.getStreams().isEmpty())
			count = resultList.getStreams().get(0).getViewerCount();

		String generatedContent = getTwitchUsername() + " View Count: " + count;
		this.setContent(generatedContent);
		return generatedContent;
	}
}

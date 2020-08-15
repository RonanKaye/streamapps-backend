package com.streamhardwarestatistics.devicemanager.deviceManager.contentGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.helix.domain.SubscriptionList;

@Entity
@Table(name = "twitch_subscriber_count_content_generators")
public class TwitchSubscriberCountContentGenerator extends ContentGenerator {

	private static final long serialVersionUID = 8655271892038573566L;

	@Column(name = "apiKey")
	private String apiKey;

	@Column(name = "channelId")
	private String channelId;

	public TwitchSubscriberCountContentGenerator() {
		super();
		channelId = null;
	}

	public TwitchSubscriberCountContentGenerator(String apiKey, String channelId, String displayName) {
		super();
		super.setType("twitchSubscriberCount");
		setApiKey(apiKey);
		setChannelId(channelId);
		setDisplayName(displayName);
	}

	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Override
	public String generateContent(TwitchClient twitchClient) {
		int count = 0;
		String nextPageCursor = null;
		SubscriptionList resultList;
		do {
			resultList = twitchClient.getHelix().getSubscriptions(getApiKey(), getChannelId(), null, nextPageCursor, 100).execute();
			count+= resultList.getSubscriptions().size();
			nextPageCursor = resultList.getPagination().getCursor();
		} while(nextPageCursor != null);

		String generatedContent = "Sub Count: " + count;
		this.setContent(generatedContent);
		return generatedContent;
	}
}

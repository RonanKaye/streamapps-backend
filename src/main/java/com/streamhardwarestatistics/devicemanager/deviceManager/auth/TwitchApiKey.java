package com.streamhardwarestatistics.devicemanager.deviceManager.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "twitch_api_keys")
public class TwitchApiKey extends ApiKey implements Serializable {

	private static final long serialVersionUID = 1849060219940222909L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "api_service")
	private String service = "Twitch";

	@Column(name = "api_key")
	private String key;

	@Column(name = "api_username")
	private String apiUsername;

	@Column(name = "api_channel_id")
	private String channelId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String authority) {
		this.key = authority;
	}

	public String getApiUsername() {
		return apiUsername;
	}

	public void setApiUsername(String apiUsername) {
		this.apiUsername = apiUsername;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}

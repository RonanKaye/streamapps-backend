package com.streamhardwarestatistics.devicemanager.deviceManager.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.streamhardwarestatistics.devicemanager.deviceManager.contentGenerator.ContentGenerator;

@Entity
@Table(name = "devices")
public class Device implements Serializable {

	private static final long serialVersionUID = 6197370098889171193L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "userId")
	private int userId;

	@Column(name = "displayName")
	private String displayName;

	@Column(name = "deviceId")
	private String deviceId;

	@ManyToOne
	@JoinColumn(name="assigned_content_generator_id", nullable=true)
	private ContentGenerator assignedContentGenerator;

	@Column(name = "lastFetchedContent")
	private String lastFetchedContent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public ContentGenerator getAssignedContentGenerator() {
		return assignedContentGenerator;
	}

	public void setAssignedContentGenerator(ContentGenerator assignedContentGenerator) {
		this.assignedContentGenerator = assignedContentGenerator;
	}

	public String getLastFetchedContent() {
		return lastFetchedContent;
	}

	public void setLastFetchedContent(String content) {
		this.lastFetchedContent = content;
	}
}

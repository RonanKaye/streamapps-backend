package com.streamhardwarestatistics.devicemanager.deviceManager.contentGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.github.twitch4j.TwitchClient;

@Entity
@Table(name = "content_generators")
@Inheritance(strategy = InheritanceType.JOINED)
public class ContentGenerator implements Serializable {

	private static final long serialVersionUID = 6197370098889171193L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "type")
	private String type;

	@Column(name = "content")
	private String content;

	@Column(name = "displayName")
	private String displayName;

	public ContentGenerator() {
		setType("generic");
		setDisplayName("default");
	}

	protected void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	protected void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String generateContent(TwitchClient twitchClient) {
		String generatedContent = "Default_Content";
		this.setContent(generatedContent);
		return generatedContent;
	}
}

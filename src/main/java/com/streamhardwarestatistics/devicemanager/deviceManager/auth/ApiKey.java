package com.streamhardwarestatistics.devicemanager.deviceManager.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "api_keys")
@Inheritance(strategy = InheritanceType.JOINED)
public class ApiKey implements Serializable {

	private static final long serialVersionUID = 1849060219940222909L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "api_service")
	private String service;

	@Column(name = "api_key")
	private String key;

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
}

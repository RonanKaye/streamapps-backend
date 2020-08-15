package com.streamhardwarestatistics.devicemanager.deviceManager.auth;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.userdetails.UserDetails;

import com.streamhardwarestatistics.devicemanager.deviceManager.contentGenerator.ContentGenerator;

@Entity
@Table(name = "user")
public class User implements UserDetails {

	private static final long serialVersionUID = 5145494635497403345L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	@Column(name = "email")
	private String email;

	@Column(name = "username")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "enabled")
	private boolean isEnabled = true;

	@Column(name = "accountExpired")
	private boolean isAccountExpired = false;

	@Column(name = "accountLocked")
	private boolean isAccountLocked = false;

	@Column(name = "credentialsExpired")
	private boolean isCredentialsExpired = false;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
	private Set<Authority> authorities = new HashSet<Authority>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ApiKey> apiKeys = new HashSet<ApiKey>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Device> devices = new HashSet<Device>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ContentGenerator> contentGenerators = new HashSet<ContentGenerator>();

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public boolean isAccountExpired() {
		return isAccountExpired;
	}

	public void setAccountExpired(boolean isAccountExpired) {
		this.isAccountExpired = isAccountExpired;
	}

	public boolean isAccountLocked() {
		return isAccountLocked;
	}

	public void setAccountLocked(boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	public boolean isCredentialsExpired() {
		return isCredentialsExpired;
	}

	public void setCredentialsExpired(boolean isCredentialsExpired) {
		this.isCredentialsExpired = isCredentialsExpired;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void addApiKey(String apiKey, String apiService) {
		ApiKey toAdd = new ApiKey();
		toAdd.setKey(apiKey);
		toAdd.setService(apiService);
		this.apiKeys.add(toAdd);
	}

	public void deleteApiKey(int apiKeyId) {
		apiKeys.removeIf(key -> (key.getId() == apiKeyId));
	}

	public Collection<ApiKey> getApiKeys() {
		return this.apiKeys;
	}

	public void addDevice(Device device) {
		this.devices.add(device);
	}

	public void deleteDevice(String deviceId) {
		devices.removeIf(device -> (device.getDeviceId().equals(deviceId)));
	}

	public Collection<Device> getDevices() {
		return this.devices;
	}

	public void addContentGenerator(ContentGenerator contentGenerator) {
		this.contentGenerators.add(contentGenerator);
	}

	public void deleteContentGenerator(int contentGeneratorId) {
		contentGenerators.removeIf(contentGenerator -> (contentGenerator.getId() == contentGeneratorId));
	}

	public Collection<ContentGenerator> getContentGenerators() {
		return this.contentGenerators;
	}

	public void addAuthority(Authority newAuthority) {
		this.authorities.add(newAuthority);
	}

	public void setAuthorities(Collection<Authority> collection) {
		this.authorities.addAll(collection);
	}

	@Override
	public Collection<Authority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !isAccountExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !isAccountLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !isCredentialsExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
}

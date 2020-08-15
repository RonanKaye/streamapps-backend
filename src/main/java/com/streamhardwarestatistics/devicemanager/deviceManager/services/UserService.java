package com.streamhardwarestatistics.devicemanager.deviceManager.services;

import java.util.Collection;

import com.streamhardwarestatistics.devicemanager.deviceManager.auth.ApiKey;
import com.streamhardwarestatistics.devicemanager.deviceManager.auth.Device;
import com.streamhardwarestatistics.devicemanager.deviceManager.auth.User;
import com.streamhardwarestatistics.devicemanager.deviceManager.contentGenerator.ContentGenerator;

public interface UserService {

	public User saveUser(User user);

	public User getUser(int id);

	public Collection<ApiKey> addApiKey(int userId, ApiKey key);

	public Collection<ApiKey> deleteApiKey(int userId, int keyId);

	public Collection<ApiKey> getApiKeys(int userId);

	public Collection<Device> addDevice(int userId, Device device);

	public Collection<Device> deleteDevice(int userId, String deviceId);

	public Collection<Device> editDevice(int userId, String deviceId, String displayName, int assignedContentGenerator);

	public Collection<Device> getDevices(int userId);

	public Collection<ContentGenerator> addContentGenerator(int userId, ContentGenerator contentGenerator);

	public Collection<ContentGenerator> deleteContentGenerator(int userId, int contentGeneratorId);

	public Collection<ContentGenerator> getContentGenerators(int userId);
}
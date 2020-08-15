package com.streamhardwarestatistics.devicemanager.deviceManager.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.streamhardwarestatistics.devicemanager.deviceManager.auth.ApiKey;
import com.streamhardwarestatistics.devicemanager.deviceManager.auth.Device;
import com.streamhardwarestatistics.devicemanager.deviceManager.auth.User;
import com.streamhardwarestatistics.devicemanager.deviceManager.contentGenerator.ContentGenerator;
import com.streamhardwarestatistics.devicemanager.deviceManager.repository.UserRepository;
import com.streamhardwarestatistics.devicemanager.deviceManager.services.UserService;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public User saveUser(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	public User getUser(int id) {
		return userRepository.findById(id).get();
	}

	@Override
	@Transactional
	public User loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUserName(username);

		if (null == user) {
			throw new UsernameNotFoundException("No user present with username: " + username);
		}

		user.getAuthorities().size();
		user.getApiKeys().size();

		return user;
	}

	@Override
	@Transactional
	public Collection<ApiKey> addApiKey(int userId, ApiKey key) {
		User user = userRepository.findById(userId).get();
		Collection<ApiKey> apiKeys = null;
		if(user != null) {
			user.getApiKeys().add(key);
			userRepository.saveAndFlush(user);
			apiKeys = user.getApiKeys();
		}
		return apiKeys;
	}

	@Override
	@Transactional
	public Collection<ApiKey> deleteApiKey(int userId, int keyId) {
		User user = userRepository.findById(userId).get();
		Collection<ApiKey> apiKeys = null;
		if(user != null) {
			user.getApiKeys().removeIf(key -> key.getId() == keyId);
			userRepository.saveAndFlush(user);
			apiKeys = user.getApiKeys();
		}
		return apiKeys;
	}

	@Override
	public Collection<ApiKey> getApiKeys(int userId) {
		User user = userRepository.findById(userId).get();
		Collection<ApiKey> apiKeys = null;
		if(user != null) {
			apiKeys = user.getApiKeys();
		}
		return apiKeys;
	}

	@Override
	@Transactional
	public Collection<Device> addDevice(int userId, Device device) {
		User user = userRepository.findById(userId).get();
		Collection<Device> devices = null;
		if(user != null) {
			user.getDevices().add(device);
			userRepository.saveAndFlush(user);
			devices = user.getDevices();
		}
		return devices;
	}

	@Override
	@Transactional
	public Collection<Device> deleteDevice(int userId, String deviceId) {
		User user = userRepository.findById(userId).get();
		Collection<Device> devices = null;
		if(user != null) {
			user.getDevices().removeIf(key -> key.getDeviceId().equals(deviceId));
			userRepository.saveAndFlush(user);
			devices = user.getDevices();
		}
		return devices;
	}

	@Override
	@Transactional
	public Collection<Device> editDevice(int userId, String deviceId, String displayName,
			int assignedContentGeneratorId) {
		User user = userRepository.findById(userId).get();
		Collection<Device> devices = null;
		if(user != null) {
			Collection<Device> originalDevices = user.getDevices();
			Collection<ContentGenerator> contentGenerators = user.getContentGenerators();

			ContentGenerator contentGeneratorToAssign = getContentGeneratorById(assignedContentGeneratorId, contentGenerators);
			if(contentGeneratorToAssign == null)
				return originalDevices;

			Device foundDevice = findDeviceById(deviceId, originalDevices);
			if(foundDevice == null)
				return originalDevices;

			foundDevice.setDisplayName(displayName);
			foundDevice.setAssignedContentGenerator(contentGeneratorToAssign);
			userRepository.saveAndFlush(user);

			devices = user.getDevices();
		}
		return devices;
	}

	@Override
	public Collection<Device> getDevices(int userId) {
		User user = userRepository.findById(userId).get();
		Collection<Device> devices = null;
		if(user != null) {
			devices = user.getDevices();
		}
		return devices;
	}

	@Override
	public Collection<ContentGenerator> addContentGenerator(int userId, ContentGenerator contentGenerator) {
		User user = userRepository.findById(userId).get();
		Collection<ContentGenerator> contentGenerators = null;
		if(user != null) {
			user.getContentGenerators().add(contentGenerator);
			userRepository.saveAndFlush(user);
			contentGenerators = user.getContentGenerators();
		}
		return contentGenerators;
	}

	@Override
	public Collection<ContentGenerator> deleteContentGenerator(int userId, int contentGeneratorId) {
		User user = userRepository.findById(userId).get();
		Collection<ContentGenerator> contentGenerators = null;
		if(user != null) {

			// Clear all existing references of the ContentGenerator that is to be deleted.
			for(Device device : user.getDevices()) {
				if(device.getAssignedContentGenerator() != null && device.getAssignedContentGenerator().getId() == contentGeneratorId)
					device.setAssignedContentGenerator(null);
			}
			userRepository.saveAndFlush(user);

			// Remove the ContentGenerator once all references to it have been cleared.
			user.getContentGenerators().removeIf(contentGenerator -> contentGenerator.getId() == contentGeneratorId);
			userRepository.saveAndFlush(user);
			contentGenerators = user.getContentGenerators();
		}
		return contentGenerators;
	}

	@Override
	public Collection<ContentGenerator> getContentGenerators(int userId) {
		User user = userRepository.findById(userId).get();
		Collection<ContentGenerator> contentGenerators = null;
		if(user != null) {
			contentGenerators = user.getContentGenerators();
		}
		return contentGenerators;
	}

	private Device findDeviceById(String deviceId, Collection<Device> deviceList) {
		for(Device device : deviceList){
			if(device.getDeviceId().equals(deviceId))
				return device;
		}
		return null;
	}

	private ContentGenerator getContentGeneratorById(int contentGeneratorId, Collection<ContentGenerator> contentGeneratorList) {
		for(ContentGenerator contentGenerator : contentGeneratorList) {
			if(contentGenerator.getId() == contentGeneratorId)
				return contentGenerator;
		}
		return null;
	}
}
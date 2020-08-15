package com.streamhardwarestatistics.devicemanager.deviceManager.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.streamhardwarestatistics.devicemanager.deviceManager.auth.ApiKey;
import com.streamhardwarestatistics.devicemanager.deviceManager.auth.Device;
import com.streamhardwarestatistics.devicemanager.deviceManager.auth.TwitchApiKey;
import com.streamhardwarestatistics.devicemanager.deviceManager.auth.User;
import com.streamhardwarestatistics.devicemanager.deviceManager.contentGenerator.ContentGenerator;
import com.streamhardwarestatistics.devicemanager.deviceManager.contentGenerator.TwitchSubscriberCountContentGenerator;
import com.streamhardwarestatistics.devicemanager.deviceManager.contentGenerator.TwitchViewerCountContentGenerator;
import com.streamhardwarestatistics.devicemanager.deviceManager.services.CodeHandlerService;
import com.streamhardwarestatistics.devicemanager.deviceManager.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	CodeHandlerService codeHandlerService;

	@GetMapping("/me")
	public UserDetails getCurrentUser() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails)principal).getUsername();
			return userDetailsService.loadUserByUsername(username);
		} else {
		  return null;
		}

	}

	@PostMapping("/apiKey/add")
	public Collection<ApiKey> addApiToken(@RequestParam String apiKey, @RequestParam String apiService) {

		Collection<ApiKey> ret = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			int userId = ((User)principal).getId();
			ApiKey newKey = new ApiKey();
			newKey.setKey(apiKey);
			newKey.setService(apiService);
			ret = userService.addApiKey(userId, newKey);
		}

		return ret;
	}

	@PostMapping("/apiKey/twitch/add")
	public Collection<ApiKey> addTwitchApiToken(@RequestParam String apiKey, @RequestParam String channelId, @RequestParam String twitchUsername) {

		Collection<ApiKey> ret = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			int userId = ((User)principal).getId();
			TwitchApiKey newKey = new TwitchApiKey();
			newKey.setKey(apiKey);
			newKey.setChannelId(channelId);
			newKey.setApiUsername(twitchUsername);
			ret = userService.addApiKey(userId, newKey);
		}

		return ret;
	}

	@PostMapping("/apiKey/delete")
	public Collection<ApiKey> deleteApiToken(@RequestParam Integer keyId) {

		Collection<ApiKey> ret = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			int userId = ((User)principal).getId();
			ret = userService.deleteApiKey(userId, keyId);
		}

		return ret;
	}

	@GetMapping("/apiKey")
	public Collection<ApiKey> getApiKeys() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			int userId = ((User)principal).getId();
			return userService.getApiKeys(userId);
		}

		return null;
	}

	@PostMapping("/device/add")
	public Collection<Device> addDevice(@RequestParam String shortCode, @RequestParam String displayName) {

		Collection<Device> ret = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			int userId = ((User)principal).getId();
			String deviceId = codeHandlerService.linkCode(shortCode);
			if(deviceId != null) {
				Device newDevice = new Device();
				newDevice.setUserId(userId);
				newDevice.setDeviceId(deviceId);
				newDevice.setDisplayName(displayName);
				newDevice.setLastFetchedContent("Content_Never_Set");
				ret = userService.addDevice(userId, newDevice);
			}
			else {
				ret = userService.getDevices(userId);
			}
		}

		return ret;
	}

	@PostMapping("/device/delete")
	public Collection<Device> deleteDevice(@RequestParam String deviceId) {

		Collection<Device> ret = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			int userId = ((User)principal).getId();
			ret = userService.deleteDevice(userId, deviceId);
		}

		return ret;
	}

	@PostMapping("/device/edit")
	public Collection<Device> editDevice(@RequestParam String deviceId, @RequestParam int contentGeneratorId, @RequestParam String displayName) {

		Collection<Device> ret = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			int userId = ((User)principal).getId();
			ret = userService.editDevice(userId, deviceId, displayName, contentGeneratorId);
		}

		return ret;
	}

	@GetMapping("/device")
	public Collection<Device> getDevices() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			int userId = ((User)principal).getId();
			return userService.getDevices(userId);
		}

		return null;
	}

	@PostMapping("/contentGenerator/add/twitchSubscriberCount")
	public Collection<ContentGenerator> addContentGenerator_TwitchSubscriberCount(@RequestParam String apiKey, @RequestParam String channelId, @RequestParam String displayName) {

		Collection<ContentGenerator> ret = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			int userId = ((User)principal).getId();

			TwitchSubscriberCountContentGenerator newContentGenerator = new TwitchSubscriberCountContentGenerator(apiKey, channelId, displayName);

			ret = userService.addContentGenerator(userId, newContentGenerator);
		}

		return ret;
	}

	@PostMapping("/contentGenerator/add/twitchViewerCount")
	public Collection<ContentGenerator> addContentGenerator_TwitchViewerCount(@RequestParam String apiKey, @RequestParam String twitchUsername, @RequestParam String displayName) {

		Collection<ContentGenerator> ret = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			int userId = ((User)principal).getId();

			TwitchViewerCountContentGenerator newContentGenerator = new TwitchViewerCountContentGenerator(apiKey, twitchUsername, displayName);

			ret = userService.addContentGenerator(userId, newContentGenerator);
		}

		return ret;
	}

	@PostMapping("/contentGenerator/delete")
	public Collection<ContentGenerator> deleteContentGenerator(@RequestParam int contentGeneratorId) {

		Collection<ContentGenerator> ret = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			int userId = ((User)principal).getId();
			ret = userService.deleteContentGenerator(userId, contentGeneratorId);
		}

		return ret;
	}

	@GetMapping("/contentGenerator")
	public Collection<ContentGenerator> getContentGenerators() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			int userId = ((User)principal).getId();
			return userService.getContentGenerators(userId);
		}

		return null;
	}

	@GetMapping("/login")
	public String login() {
		return "Login Successful!";
	}

	@GetMapping("/isAuthenticated")
	public ResponseEntity<Boolean> isLoggedIn() {
		String currentUsername = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUsername = authentication.getName();
		}

		boolean isAuthenticated = currentUsername != null;

		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(isAuthenticated, HttpStatus.OK);
		return response;
	}
}

package com.streamhardwarestatistics.devicemanager.deviceManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streamhardwarestatistics.devicemanager.deviceManager.auth.Device;
import com.streamhardwarestatistics.devicemanager.deviceManager.contentGenerator.ContentGenerator;
import com.streamhardwarestatistics.devicemanager.deviceManager.repository.DeviceRepository;

@RestController
@RequestMapping("/device")
public class DeviceController {

	@Autowired
	DeviceRepository deviceRepository;

	@GetMapping("/get/content/{deviceId}")
	public ResponseEntity<String> requestCode(@PathVariable String deviceId) {

		Device deviceFetched = deviceRepository.findByDeviceId(deviceId);
		if(deviceFetched == null)
			return new ResponseEntity<>("Device does not exist.", HttpStatus.OK);

		ContentGenerator contentGeneratorFetched = deviceFetched.getAssignedContentGenerator();
		if(contentGeneratorFetched == null)
			return new ResponseEntity<>("No content generator assigned.", HttpStatus.OK);

		String content = contentGeneratorFetched.getContent();
		deviceFetched.setLastFetchedContent(content);
		deviceRepository.saveAndFlush(deviceFetched);

		return new ResponseEntity<>(content, HttpStatus.OK);
	}
}

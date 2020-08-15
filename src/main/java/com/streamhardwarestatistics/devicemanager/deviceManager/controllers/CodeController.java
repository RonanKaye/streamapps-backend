package com.streamhardwarestatistics.devicemanager.deviceManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streamhardwarestatistics.devicemanager.deviceManager.models.Code;
import com.streamhardwarestatistics.devicemanager.deviceManager.services.CodeHandlerService;

@RestController
@RequestMapping("/code")
public class CodeController {

	@Autowired
	CodeHandlerService codeHandlerService;

	@GetMapping("/requestcode")
	public Code requestCode() {
		return codeHandlerService.generateUniqueCode();
	}
}

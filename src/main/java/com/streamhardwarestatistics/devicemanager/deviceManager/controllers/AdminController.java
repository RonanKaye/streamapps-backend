package com.streamhardwarestatistics.devicemanager.deviceManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streamhardwarestatistics.devicemanager.deviceManager.auth.User;
import com.streamhardwarestatistics.devicemanager.deviceManager.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService userService;

	@Autowired
	UserDetailsService userDetailsService;

	@GetMapping("/lookup/id/{id}")
	public User loadById(@PathVariable int id) {
		return userService.getUser(id);
	}

	@GetMapping("/lookup/username/{username}")
	public UserDetails loadByUsername(@PathVariable String username) {
		return userDetailsService.loadUserByUsername(username);
	}

}

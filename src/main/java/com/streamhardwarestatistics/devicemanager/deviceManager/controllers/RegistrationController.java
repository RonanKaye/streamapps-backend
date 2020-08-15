package com.streamhardwarestatistics.devicemanager.deviceManager.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.streamhardwarestatistics.devicemanager.deviceManager.auth.Authority;
import com.streamhardwarestatistics.devicemanager.deviceManager.auth.User;
import com.streamhardwarestatistics.devicemanager.deviceManager.services.UserService;

@RestController
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserService userService;

	@Autowired
	UserDetailsService userDetailsService;

	@PostMapping("/")
	public ResponseEntity<Object> registerAccount(@RequestParam String username, @RequestParam String email,
			@RequestParam String password) {
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setUsername(username);
		newUser.setPassword(bCryptPasswordEncoder.encode(password));
		Authority userAuthority = new Authority();
		userAuthority.setAuthority("USER");
		newUser.addAuthority(userAuthority);
		userService.saveUser(newUser);
		return new ResponseEntity<Object>(newUser, HttpStatus.OK);
	}

	@GetMapping("/checkavailability/{username}")
	public boolean isUsernameAvailable(@PathVariable String username) {
		try {
			userDetailsService.loadUserByUsername(username);
		}
		catch (UsernameNotFoundException unf) {
			return true;
		}
		return false;
	}

	@GetMapping("/testdatasource")
    public String retrievePrincipal(Principal principal) {
        return "ALSKDJAOIDWMNOIDMAW";
    }

}

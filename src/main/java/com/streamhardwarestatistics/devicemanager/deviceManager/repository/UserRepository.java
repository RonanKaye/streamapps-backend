package com.streamhardwarestatistics.devicemanager.deviceManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.streamhardwarestatistics.devicemanager.deviceManager.auth.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUserName(String username);
}

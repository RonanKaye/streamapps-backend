package com.streamhardwarestatistics.devicemanager.deviceManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.streamhardwarestatistics.devicemanager.deviceManager.auth.Device;

@Repository("deviceRepository")
public interface DeviceRepository extends JpaRepository<Device, Integer> {
	public Device findByDeviceId(String deviceId);
}

package com.streamhardwarestatistics.devicemanager.deviceManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.streamhardwarestatistics.devicemanager.deviceManager.services.ContentGeneratorExecutorService;

@SpringBootApplication
public class DeviceManagerApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(DeviceManagerApplication.class, args);

		// Startup services that are supposed to run all the time.
		ContentGeneratorExecutorService contentGrneratorExecutorService = applicationContext.getBean(ContentGeneratorExecutorService.class);
		contentGrneratorExecutorService.start();
	}

}

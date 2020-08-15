package com.streamhardwarestatistics.devicemanager.deviceManager.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.streamhardwarestatistics.devicemanager.deviceManager.contentGenerator.ContentGenerator;
import com.streamhardwarestatistics.devicemanager.deviceManager.repository.ContentGeneratorRepository;
import com.streamhardwarestatistics.devicemanager.deviceManager.services.ContentGeneratorExecutorService;
import com.streamhardwarestatistics.devicemanager.deviceManager.services.TwitchClientService;

@Service("ContentGeneratorExecutorService")
public class ContentGeneratorExecutorServiceImpl implements ContentGeneratorExecutorService {

	@Autowired
	ContentGeneratorRepository contentGeneratorRepository;

	@Autowired
	TwitchClientService twitchClientServicel;

	private ScheduledExecutorService scheduledExecutorService;
	private ExecutorService executorService;

	private Long startUpDelay = 1L; // In seconds
	private Long queryInterval = 5L; // In seconds

	@Override
	public void start() {
		System.out.println("Starting " + this.getClass().getCanonicalName());

		scheduledExecutorService = Executors.newScheduledThreadPool(1);
		executorService = Executors.newFixedThreadPool(3);
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				for(ContentGenerator contentGenerator : contentGeneratorRepository.findAll()) {
					executorService.execute(new Runnable() {
						@Override
						public void run() {
							System.out.println("Generating content for: " + contentGenerator.getType() + " : " + contentGenerator.getId());
							contentGenerator.generateContent(twitchClientServicel.getClient());
							contentGeneratorRepository.saveAndFlush(contentGenerator);
							System.out.println("Done generating content for: " + contentGenerator.getType() + " : " + contentGenerator.getId());
						}
					});
				}
			}
		}, startUpDelay, queryInterval, TimeUnit.SECONDS);
	}

	@Override
	public void stop() {
		System.out.println("Stopping " + this.getClass().getCanonicalName());

		scheduledExecutorService.shutdown();
		executorService.shutdown();
		scheduledExecutorService = null;
		executorService = null;
	}

}
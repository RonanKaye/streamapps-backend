package com.streamhardwarestatistics.devicemanager.deviceManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.streamhardwarestatistics.devicemanager.deviceManager.contentGenerator.ContentGenerator;

@Repository("contentGeneratorRepository")
public interface ContentGeneratorRepository extends JpaRepository<ContentGenerator, Integer> {
}

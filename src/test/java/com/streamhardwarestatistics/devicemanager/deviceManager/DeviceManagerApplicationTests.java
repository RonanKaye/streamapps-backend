package com.streamhardwarestatistics.devicemanager.deviceManager;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
  DeviceManagerApplication.class,
  H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
class DeviceManagerApplicationTests {

	@Test
	void contextLoads() {
	}

}

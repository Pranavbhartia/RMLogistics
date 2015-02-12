package com.nexera.newfi.core.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.nexera.newfi.core.service")
public class CoreConfiguration {

	static{
		System.out.println("Reading core configuration files");
	}
}

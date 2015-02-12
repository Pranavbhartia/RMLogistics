package com.nexeracore.newfi.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.nexeracore.newfi.service")
public class CoreConfiguration {

	static{
		System.out.println("Reading core configuration files");
	}
}

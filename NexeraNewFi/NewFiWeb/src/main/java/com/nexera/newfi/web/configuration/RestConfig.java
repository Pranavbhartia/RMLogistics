package com.nexera.newfi.web.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan("com.nexera.newfi.web.rest.controller" )
public class RestConfig {

}

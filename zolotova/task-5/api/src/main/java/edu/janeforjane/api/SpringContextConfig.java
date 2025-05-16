package edu.janeforjane.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.janeforjane.api", "com.janeforjane.core", "com.janeforjane.provider", "com.janeforjane.service", "com.janeforjane.entities"})
public class SpringContextConfig {
}

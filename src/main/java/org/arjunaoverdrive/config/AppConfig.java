package org.arjunaoverdrive.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.arjunaoverdrive")
@PropertySource("classpath:application.properties")
public class AppConfig {
}

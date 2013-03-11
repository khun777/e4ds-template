package ch.rasc.e4ds.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "ch.ralscha.extdirectspring", "ch.rasc.e4ds.config", "ch.rasc.e4ds.schedule",
		"ch.rasc.e4ds.security", "ch.rasc.e4ds.service", "ch.rasc.e4ds.web" })
@PropertySource({ "version.properties" })
public class ComponentConfig {
	// nothing here
}
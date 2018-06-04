package com.github.paulerpen.mypush;

import com.github.paulerpen.mypush.storage.StorageProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig {
	
	@Autowired
	private Environment env;

	@Bean
	public StorageProperties storageProperties() {
		StorageProperties ret = new StorageProperties();
		ret.setLocation(env.getProperty("mypush.paths.uploaded"));
		return ret;
	}
}

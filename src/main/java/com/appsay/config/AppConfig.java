package com.appsay.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by appsay02 on 10-05-2017.
 */
@Configuration
@ComponentScan(basePackages = "com.appsay.*")
@ImportResource({"classpath:applicationContext.xml"})
public class AppConfig {


}

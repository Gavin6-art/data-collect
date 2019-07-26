package com.iwhale.congestion.index.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "third.datasource")
@Getter
@Setter
public class OracleSgConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}

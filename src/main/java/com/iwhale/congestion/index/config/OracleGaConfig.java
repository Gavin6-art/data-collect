package com.iwhale.congestion.index.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sll on 2019/4/21.
 */
@Configuration
@ConfigurationProperties(prefix = "second.datasource")
@Getter
@Setter
public class OracleGaConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}

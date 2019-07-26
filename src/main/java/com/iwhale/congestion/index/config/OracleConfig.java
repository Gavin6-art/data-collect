package com.iwhale.congestion.index.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sll on 2019/4/21.
 */
@Configuration
@ConfigurationProperties(prefix = "first.datasource")
@Data
public class OracleConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}

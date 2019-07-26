package com.iwhale.congestion.index;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * The type Web application.
 *
 * @author shilili
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@MapperScan({"com.iwhale.congestion.index.dao.gadao","com.iwhale.congestion.index.dao.spdao","com.iwhale.congestion.index.dao.sgdao"})
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}

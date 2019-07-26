package com.iwhale.congestion.index.config;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;

@Slf4j
@Configuration
@MapperScan(basePackages = "com.iwhale.congestion.index.dao.sgdao", sqlSessionTemplateRef = "sgSqlSessionTemplate")
public class OracleSgConfiguration {

    @Bean(name = "sgDataSource")
    public DataSource setDataSource(OracleSgConfig dbc) {
        log.info("当前数据源:sgorcl");
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(dbc.getUrl());
        ds.setUsername(dbc.getUsername());
        ds.setPassword(dbc.getPassword());
        ds.setDriverClassName(dbc.getDriverClassName());
        return ds;
    }

    @Bean(name = "sgSqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("sgDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/sgmapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "sgSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("sgSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

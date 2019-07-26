package com.iwhale.congestion.index.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Created by sll on 2019/4/21.
 */
@Slf4j
@Configuration
@MapperScan(basePackages = "com.iwhale.congestion.index.dao.gadao", sqlSessionTemplateRef = "slaveSqlSessionTemplate")
public class OracleGaConfiguration {

    @Bean(name = "slaveDataSource")
    public DataSource setDataSource(OracleGaConfig dbc) {
        log.info("当前数据源:second");
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(dbc.getUrl());
        ds.setUsername(dbc.getUsername());
        ds.setPassword(dbc.getPassword());
        ds.setDriverClassName(dbc.getDriverClassName());
        return ds;
    }

    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("slaveDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/gamapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "slaveSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("slaveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

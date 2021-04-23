package com.inl.rest.inlDB;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.inl.rest.inlDB",
    entityManagerFactoryRef = "inlEntityManagerFactory",
    transactionManagerRef= "inlTransactionManager")
public class InlDataSourceConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties inlDataSourceProperties() {
        return new DataSourceProperties();
    }
    @Bean   
    @Primary 
    @ConfigurationProperties("spring.datasource.configuration")
    public DataSource inlDataSource() {
        return inlDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }
    @Bean(name = "inlEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean inlEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(inlDataSource())
                .packages("com.inl.rest.inlDB")
                .build();
    }
    @Bean
    @Primary
    public PlatformTransactionManager inlTransactionManager(
        final @Qualifier("inlEntityManagerFactory"
    )
    LocalContainerEntityManagerFactoryBean inlEntityManagerFactory) {
        return new JpaTransactionManager(inlEntityManagerFactory.getObject());
    }
}
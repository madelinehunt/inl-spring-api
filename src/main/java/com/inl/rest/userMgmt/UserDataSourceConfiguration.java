package com.inl.rest.userMgmt;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.inl.rest.userMgmt",
    entityManagerFactoryRef = "userEntityManagerFactory",
    transactionManagerRef= "userTransactionManager")
public class UserDataSourceConfiguration {
    @Autowired
	private Environment env;
    
    @Bean
    @ConfigurationProperties("spring.user-datasource")
    public DataSourceProperties userDataSourceProperties() {
        return new DataSourceProperties();
    }
    @Bean    
    @ConfigurationProperties("spring.user-datasource.configuration")
    public DataSource userDataSource() {
        return userDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }
    @Bean(name = "userEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(userDataSource())
                .packages("com.inl.rest.userMgmt")
                .build();
    }
    @Bean
    public PlatformTransactionManager userTransactionManager(
        final @Qualifier("userEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean userEntityManagerFactory) {
        return new JpaTransactionManager(userEntityManagerFactory.getObject());
    }
    
    @Bean
    @ConfigurationProperties("emailer.configuration")
    public Emailer emailer() {
        return new Emailer();
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
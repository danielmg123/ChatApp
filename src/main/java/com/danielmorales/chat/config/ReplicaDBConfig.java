package com.danielmorales.chat.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EntityScan(basePackages = {"com.danielmorales.chat.entity"})
@EnableJpaRepositories(
        basePackages = {"com.danielmorales.chat.repository.replica"},
        entityManagerFactoryRef = "replicaEntityManagerFactory",
        transactionManagerRef = "replicaTransactionManager"
)
public class ReplicaDBConfig {

    @Bean(name = "replicaDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.replica")
    public DataSource replicaDataSource() {
        return org.springframework.boot.jdbc.DataSourceBuilder.create().build();
    }

    @Bean(name = "replicaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean replicaEntityManagerFactory(
            @Qualifier("replicaDataSource") DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.danielmorales.chat.entity");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // can add any custom JPA/Hibernate properties
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        jpaProperties.put("hibernate.show_sql", "true");
        factoryBean.setJpaPropertyMap(jpaProperties);

        return factoryBean;
    }

    @Bean(name = "replicaTransactionManager")
    public PlatformTransactionManager replicaTransactionManager(
            @Qualifier("replicaEntityManagerFactory") LocalContainerEntityManagerFactoryBean replicaEmFactory
    ) {
        return new DataSourceTransactionManager(replicaEmFactory.getDataSource());
    }
}
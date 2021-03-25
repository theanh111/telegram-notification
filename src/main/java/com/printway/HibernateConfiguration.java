package com.printway;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager",
        basePackages = {"com.printway"})
@EntityScan(basePackages = "com.printway")
@PropertySource("classpath:config/${spring.profiles.active}/database.properties")
public class HibernateConfiguration {
    @Value("${db.password}")
    private String DB_PASSWORD;

    @Value("${db.url}")
    private String DB_URL;

    @Value("${db.username}")
    private String DB_USERNAME;

    @Value("${hibernate.dialect}")
    private String HIBERNATE_DIALECT;

    @Value("${hibernate.show_sql}")
    private String HIBERNATE_SHOW_SQL;

    @Value("${hibernate.jdbc.batch_size}")
    private String BATCH_SIZE;
    @Value("${hibernate.order_inserts}")
    private String BATCH_INSERTS;
    @Value("${hibernate.order_updates}")
    private String BATCH_UPDATES;
    @Value("${hibernate.jdbc.batch_versioned_data}")
    private String BATCH_VERSION_DATA;

    @Value("${hibernate.hbm2ddl.auto}")
    private String HIBERNATE_HBM2DDL_AUTO;
    @Value("${entitymanager.packagesToScan}")
    private String ENTITYMANAGER_PACKAGES_TO_SCAN;
    @Value("${current_session_context_class}")
    private String ENTITYMANAGER_SESSION_CONFIG;

    @Value("${dbcp2.initial-size}")
    private int DBCP_INIT_SIZE;
    @Value("${dbcp2.max-total}")
    private int DBCP_MAX_TOTAL;
    @Value("${dbcp2.pool-prepared-statements}")
    private boolean DBCP_POOL_PREPARED_STATEMENTS;

    @Primary
    @Bean(name = "printwayDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource printwayDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setInitialSize(DBCP_INIT_SIZE);
        dataSource.setMaxTotal(DBCP_MAX_TOTAL);
        dataSource.setPoolPreparedStatements(DBCP_POOL_PREPARED_STATEMENTS);
        return dataSource;
    }

    @Primary
    @Bean(name = "printwayEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(printwayDataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
        hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        hibernateProperties.put("hibernate.jdbc.batch_size", BATCH_SIZE);
        hibernateProperties.put("hibernate.order_inserts", BATCH_INSERTS);
        hibernateProperties.put("hibernate.order_updates", BATCH_UPDATES);
        hibernateProperties.put("hibernate.jdbc.batch_versioned_data", BATCH_VERSION_DATA);
        hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
        hibernateProperties.put("hibernate.current_session_context_class", ENTITYMANAGER_SESSION_CONFIG);
        entityManagerFactoryBean.setJpaProperties(hibernateProperties);
        return entityManagerFactoryBean;
    }

    @Primary
    @Bean(name = "printwayTransactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("printwayEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean(name = "hibernate5AnnotatedSessionFactory")
    public LocalSessionFactoryBuilder getLocalSessionFactoryBean() {
        LocalSessionFactoryBuilder localSessionFactoryBean = new LocalSessionFactoryBuilder(printwayDataSource());
        localSessionFactoryBean.scanPackages(
                "com.printway"
        );
        localSessionFactoryBean.buildSessionFactory();

        return localSessionFactoryBean;
    }
}

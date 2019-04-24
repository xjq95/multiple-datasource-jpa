package com.github.xjq95.multipledatasourcejpa.config;

import java.util.Calendar;
import java.util.stream.Stream;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description: todo complete the comment.
 * User: Administrator
 * Date: 2019-02-15 15:31
 */
@Slf4j
@ConfigurationProperties(prefix = "datasource-config")
@Configuration
@EnableTransactionManagement
@Data
public class DatasourceConfig {

    private DataSourceProp db1;

    private DataSourceProp db2;

    @Bean("db1DataSource")
    @Primary
    public DataSource db1DataSource() {
        return db1.dsp.initializeDataSourceBuilder().build();
    }

    @Bean("db2DataSource")
    public DataSource db2DataSource() {
        return db2.dsp.initializeDataSourceBuilder().build();
    }

    @Profile("test")
    @EventListener
    public void populateDatabase(ApplicationReadyEvent ready) {
        log.info("schema init");
        log.info(Calendar.getInstance().getTimeInMillis() + "");
        DatabasePopulator databasePopulator1 = new ResourceDatabasePopulator(
                Stream.concat(db1.dsp.getSchema().stream(), db1.dsp.getData().stream())
                      .map(ClassPathResource::new).toArray(Resource[]::new));
        DatabasePopulatorUtils.execute(databasePopulator1, ready.getApplicationContext().getBean("db1DataSource", DataSource.class));

        DatabasePopulator databasePopulator2 = new ResourceDatabasePopulator(
                Stream.concat(db2.dsp.getSchema().stream(), db2.dsp.getData().stream())
                      .map(ClassPathResource::new).toArray(Resource[]::new));
        DatabasePopulatorUtils.execute(databasePopulator2, ready.getApplicationContext().getBean("db2DataSource", DataSource.class));
    }

    @Data
    public static class DataSourceProp {
        private DataSourceProperties dsp;

        private String entityPackages;
    }

    @EnableJpaRepositories(
            entityManagerFactoryRef = Db1Config.ENTITY_MANAGER_FACTORY_REF,
            transactionManagerRef = Db1Config.TRANSACTION_MANAGER_REF,
            basePackages = {"com.github.xjq95.multipledatasourcejpa.dao.db1"}
    )
    @Configuration
    public static class Db1Config {

        private static final String ENTITY_MANAGER_FACTORY_REF = "db1EntityManagerFactory";
        private static final String TRANSACTION_MANAGER_REF = "db1TransactionManager";
        private static final String DATASOURCE_REF = "db1DataSource";

        @Autowired
        private DatasourceConfig datasourceConfig;

        @Primary
        @Bean(ENTITY_MANAGER_FACTORY_REF)
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(
                EntityManagerFactoryBuilder builder, @Qualifier(DATASOURCE_REF) DataSource dataSource, JpaProperties jpaProperties) {
            return builder.dataSource(dataSource).properties(jpaProperties.getProperties()).packages(datasourceConfig.getDb1().getEntityPackages())
                          .persistenceUnit(ENTITY_MANAGER_FACTORY_REF).build();
        }

        @Primary
        @Bean(TRANSACTION_MANAGER_REF)
        public PlatformTransactionManager transactionManager(@Qualifier(ENTITY_MANAGER_FACTORY_REF) EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }
    }

    @Slf4j
    @EnableJpaRepositories(
            entityManagerFactoryRef = Db2Config.ENTITY_MANAGER_FACTORY_REF,
            transactionManagerRef = Db2Config.TRANSACTION_MANAGER_REF,
            basePackages = {"com.github.xjq95.multipledatasourcejpa.dao.db2"}
    )
    @Configuration
    public static class Db2Config {

        private static final String ENTITY_MANAGER_FACTORY_REF = "db2EntityManagerFactory";
        private static final String TRANSACTION_MANAGER_REF = "db2TransactionManager";
        private static final String DATASOURCE_REF = "db2DataSource";

        @Autowired
        private DatasourceConfig datasourceConfig;

        @Primary
        @Bean(ENTITY_MANAGER_FACTORY_REF)
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(
                EntityManagerFactoryBuilder builder, @Qualifier(DATASOURCE_REF) DataSource db2DataSource, JpaProperties jpaProperties) {
            return builder.dataSource(db2DataSource).properties(jpaProperties.getProperties()).packages(datasourceConfig.getDb2().getEntityPackages())
                          .persistenceUnit(ENTITY_MANAGER_FACTORY_REF).build();
        }

        @Primary
        @Bean(TRANSACTION_MANAGER_REF)
        public PlatformTransactionManager transactionManager(@Qualifier(ENTITY_MANAGER_FACTORY_REF) EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }
    }
}
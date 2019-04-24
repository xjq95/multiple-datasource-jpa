package com.github.xjq95.multipledatasourcejpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class MultipleDatasourceJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipleDatasourceJpaApplication.class, args);
    }
}

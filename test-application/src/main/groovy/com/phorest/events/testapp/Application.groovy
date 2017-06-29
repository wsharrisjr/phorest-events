package com.phorest.events.testapp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@ComponentScan(basePackages = ['com.phorest.events'])
@EnableTransactionManagement
class Application {

    static void main(String[] args) {
        SpringApplication.run(Application.class, args)
    }

}

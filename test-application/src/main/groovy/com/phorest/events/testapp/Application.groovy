package com.phorest.events.testapp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ['com.phorest.events'])
class Application {

    static void main(String[] args) {
        SpringApplication.run(Application.class, args)
    }

}

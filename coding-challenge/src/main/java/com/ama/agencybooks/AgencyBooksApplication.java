package com.ama.agencybooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {"com.ama.agencybooks.model"})
@SpringBootApplication
public class AgencyBooksApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgencyBooksApplication.class, args);
    }

}

package com.descenty.work_in_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class WorkInSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkInSpringApplication.class, args);
	}

}

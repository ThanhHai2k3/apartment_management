package com.example.apartmentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class ApartmentmanagementApplication {

	public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone
        Date date = new Date();
        SpringApplication.run(ApartmentmanagementApplication.class, args);
	}
}

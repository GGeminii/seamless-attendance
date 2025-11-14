package com.gemini.labsense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SeamlessAttendanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeamlessAttendanceApplication.class, args);
    }

}

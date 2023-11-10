package com.scaler.notification;

import com.scaler.notification.utils.DotenvUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class NotificationApplication {

        static {
            DotenvUtil.loadEnvironmentVariables();
        }

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
      }
}



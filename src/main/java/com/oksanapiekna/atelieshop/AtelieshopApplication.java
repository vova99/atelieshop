package com.oksanapiekna.atelieshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class AtelieshopApplication {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        SpringApplication.run(AtelieshopApplication.class, args);
//        ViberBotConfig.init();
    }

}

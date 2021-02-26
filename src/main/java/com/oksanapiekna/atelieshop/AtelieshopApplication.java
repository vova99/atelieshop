package com.oksanapiekna.atelieshop;

import com.oksanapiekna.atelieshop.viberBot.ViberBotConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class AtelieshopApplication {
    public static void main(String[] args) {
        SpringApplication.run(AtelieshopApplication.class, args);
    }

//    @Bean
//    public ViberBotConfig viberBotConfig() throws InterruptedException, ExecutionException, IOException {
//      return new ViberBotConfig();
//    }
}

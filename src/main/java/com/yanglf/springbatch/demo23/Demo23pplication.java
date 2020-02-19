package com.yanglf.springbatch.demo23;
import com.yanglf.springbatch.demo22.Demo22pplication;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Demo23pplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo23pplication.class, args);
    }

}

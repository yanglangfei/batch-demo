package com.yanglf.springbatch.demo22;
import com.yanglf.springbatch.demo21.Demo21pplication;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Demo22pplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo22pplication.class, args);
    }

}

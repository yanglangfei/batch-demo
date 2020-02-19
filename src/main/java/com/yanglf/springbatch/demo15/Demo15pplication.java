package com.yanglf.springbatch.demo15;
import com.yanglf.springbatch.demo14.Demo14pplication;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Demo15pplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo15pplication.class, args);
    }

}

package com.yanglf.springbatch.demo16;
import com.yanglf.springbatch.demo15.Demo15pplication;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Demo16pplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo16pplication.class, args);
    }

}

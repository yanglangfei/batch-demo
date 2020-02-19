package com.yanglf.springbatch.demo21;
import com.yanglf.springbatch.demo20.Demo20pplication;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Demo21pplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo21pplication.class, args);
    }

}

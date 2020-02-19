package com.yanglf.springbatch.demo17;
import com.yanglf.springbatch.demo16.Demo16pplication;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Demo17pplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo17pplication.class, args);
    }

}

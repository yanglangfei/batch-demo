package com.yanglf.springbatch.demo20;
import com.yanglf.springbatch.demo19.Demo19pplication;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Demo20pplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo20pplication.class, args);
    }

}

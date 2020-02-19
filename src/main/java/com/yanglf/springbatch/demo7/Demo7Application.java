package com.yanglf.springbatch.demo7;
import com.yanglf.springbatch.demo6.Demo6Application;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Demo7Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo7Application.class, args);
    }

}

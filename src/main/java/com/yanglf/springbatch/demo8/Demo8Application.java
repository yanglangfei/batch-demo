package com.yanglf.springbatch.demo8;
import com.yanglf.springbatch.demo7.Demo7Application;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Demo8Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo8Application.class, args);
    }

}

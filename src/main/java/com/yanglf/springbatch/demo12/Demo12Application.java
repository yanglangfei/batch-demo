package com.yanglf.springbatch.demo12;
import com.yanglf.springbatch.demo11.Demo11Application;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Demo12Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo12Application.class, args);
    }

}

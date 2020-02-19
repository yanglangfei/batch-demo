package com.yanglf.springbatch.demo13;
import com.yanglf.springbatch.demo12.Demo12Application;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Demo13Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo13Application.class, args);
    }

}

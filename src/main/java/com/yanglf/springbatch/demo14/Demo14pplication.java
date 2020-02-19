package com.yanglf.springbatch.demo14;
import com.yanglf.springbatch.demo13.Demo13Application;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Demo14pplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo14pplication.class, args);
    }

}

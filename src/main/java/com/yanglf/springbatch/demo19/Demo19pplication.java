package com.yanglf.springbatch.demo19;
import com.yanglf.springbatch.demo18.Demo18pplication;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Demo19pplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo19pplication.class, args);
    }

}

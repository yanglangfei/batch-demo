package com.yanglf.springbatch.demo18;
import com.yanglf.springbatch.demo17.Demo17pplication;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Demo18pplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo18pplication.class, args);
    }

}

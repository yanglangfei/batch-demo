package com.yanglf.springbatch.demo21;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 错误跳过 以及   跳过 监听
 *
 * @author yanglf
 * @sine 2020.02.16
 * @descriptipon
 * @see
 */
@Configuration
public class JobConfiguration {
    /**
     * 注入 创建job 对象
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    /**
     * 注入 创建step 的对象
     */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job demo21Job() {
        return jobBuilderFactory.get("demo21Job1")
                .start(step1())
                .build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(2)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                // 容错
                .faultTolerant()
                // 跳过 异常
                .skip(CustomRetryException.class)
                // 重试次数 超过次数  任务  失败
                .skipLimit(5)
                .listener(new MySkipListener())
                .build();
    }

    @Bean
    public ItemProcessor<String, String> processor() {
        return new ItemProcessor<String, String>() {
            private int attemptCount;

            @Override
            public String process(String s) throws Exception {
                System.out.println("processor item---" + s);
                if (s.equalsIgnoreCase("26")) {
                    attemptCount++;
                    if (attemptCount >= 3) {
                        System.out.println("Retried " + attemptCount + " times success");
                        return String.valueOf(Integer.valueOf(s) * -1);
                    } else {
                        System.out.println("Processor the " + attemptCount + " times fail");
                        throw new CustomRetryException("Processor fail. Attempt :" + attemptCount);
                    }
                }
                return String.valueOf(Integer.valueOf(s) * -1);
            }
        };
    }

    @Bean
    public ItemWriter<String> writer() {
        return new ItemWriter<String>() {
            @Override
            public void write(List<? extends String> list) throws Exception {
                for (String s : list) {
                    System.out.println("-------" + s + "---------");
                }
            }
        };
    }

    @Bean
    public ItemReader<String> reader() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            datas.add(String.valueOf(i));
        }
        return new ListItemReader<>(datas);
    }


}

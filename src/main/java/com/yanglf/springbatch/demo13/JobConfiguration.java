package com.yanglf.springbatch.demo13;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.validation.BindException;

import java.util.List;

/**
 * reader 异常  和 重启
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
    public Job demo13Job() {
        return jobBuilderFactory.get("demo13Job2")
                .start(step1())
                .build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Custom, Custom>chunk(2)
                .reader(new RestartReader())
                .writer(new ItemWriter<Custom>() {
                    @Override
                    public void write(List<? extends Custom> list) throws Exception {
                        for (Custom custom : list) {
                            System.out.println("------" + custom.toString() + "----------");
                        }
                    }
                })
                .build();
    }

}

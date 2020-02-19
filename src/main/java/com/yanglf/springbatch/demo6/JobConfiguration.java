package com.yanglf.springbatch.demo6;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.AbstractTaskletStepBuilder;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 监听器
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

    /**
     * 监听  chunk
     */
    @Autowired
    private MyChunkListener myChunkListener;


    @Bean
    public Job demo6Job() {
        return jobBuilderFactory.get("demo6Job1")
                .start(step1())
                .listener(new MyJobListener())
                .next(step2())
                .build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                // 每读完 两个数据 进行  处理   读写 数据
                .<String, String>chunk(2)
                .faultTolerant()
                .listener(myChunkListener)
                .reader(reader())
                .writer(writer())
                .build();

    }

    @Bean
    public ItemReader<String> reader() {
        // 读取数据
        List<String> data = Arrays.asList("java", "php", "python", "c", "go");
        return new ListItemReader<>(data);
    }


    @Bean
    public ItemWriter<String> writer() {
        return new ItemWriter<String>() {
            @Override
            public void write(List<? extends String> list) throws Exception {
                for (String s : list) {
                    System.out.println("--------------"+s+"----------");
                }
            }
        };
    }


    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("demo6 STEP2 event");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }


}

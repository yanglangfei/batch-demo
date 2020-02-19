package com.yanglf.springbatch.demo3;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * split 实现 job 并发
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
    public Job demo3Job() {
        return jobBuilderFactory.get("demo3Job")
                .start(demo3Flow1())
                .split(new SimpleAsyncTaskExecutor())
                .add(demo3Flow2())
                .end()
                .build();

    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("demo3 STEP1");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }


    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("demo3 STEP2");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }


    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("demo3 step3");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }


    @Bean
    public Flow demo3Flow1() {
        return new FlowBuilder<Flow>("demo3Flow1")
                .start(step1())
                .next(step2())
                .build();
    }


    @Bean
    public Flow demo3Flow2() {
        return new FlowBuilder<Flow>("demo3Flow2")
                .start(step2())
                .next(step3())
                .build();
    }


}

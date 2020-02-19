package com.yanglf.springbatch.demo4;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * 决策器： 满足条件时 在执行下边的流程
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
    public Job demo4Job() {
        return jobBuilderFactory.get("demo4Job")
                .start(step1())
                .next(myDecider())
                .from(myDecider()).on("event").to(step2())
                .from(myDecider()).on("odd").to(step3())
                // 执行完决策器 后  无论返回什么 都要回到 决策器  执行 另外的条件
                .from(step3()).on("*").to(myDecider())
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
                        System.out.println("demo3 STEP2 event");
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
                        System.out.println("demo3 odd");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }


    @Bean
    public JobExecutionDecider myDecider(){
        return new MyDecider();
    }


}

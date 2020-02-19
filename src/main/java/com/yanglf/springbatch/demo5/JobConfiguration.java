package com.yanglf.springbatch.demo5;

import com.yanglf.springbatch.demo4.MyDecider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 父子  job
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

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;


    @Bean
    public Job parentJob() {
        return jobBuilderFactory.get("parentJob")
                .start(childJob1())
                .next(childJob2())
                .build();
    }



    @Bean
    public Step childJob1() {
        return new  JobStepBuilder(new StepBuilder("childJob1"))
                .job(child1Job())
                .launcher(jobLauncher)
                .repository(jobRepository)
                .build();
    }


    @Bean
    public Step childJob2() {
        return new  JobStepBuilder(new StepBuilder("childJob2"))
                .job(child2Job())
                .launcher(jobLauncher)
                .repository(jobRepository)
                .build();
    }


    @Bean
    public Job child2Job() {
        return jobBuilderFactory.get("child2Job")
                .start(step3())
                .next(step1())
                .build();
    }


    @Bean
    public Job child1Job() {
        return jobBuilderFactory.get("child1Job")
                .start(step2())
                .next(step1())
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


}

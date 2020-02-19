package com.yanglf.springbatch.demo22;

import com.yanglf.springbatch.demo21.MySkipListener;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JobLauncher 手动启动 任务
 *
 * @author yanglf
 * @sine 2020.02.16
 * @descriptipon
 * @see
 */
@Configuration
public class JobConfiguration  implements StepExecutionListener {
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

    private Map<String, JobParameter> parameters;

    @Bean
    public Job demo21Job() {
        return jobBuilderFactory.get("demo21Job1")
                .start(step1())
                .build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("parameter:"+parameters.get("msg").getValue());
                        return RepeatStatus.FINISHED;
                    }
                })
                .listener(this)
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


    @Override
    public void beforeStep(StepExecution stepExecution) {
        parameters = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}

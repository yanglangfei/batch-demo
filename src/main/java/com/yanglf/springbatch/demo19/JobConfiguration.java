package com.yanglf.springbatch.demo19;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 错误处理
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


    @Autowired
    public DataSource dataSource;


    @Bean
    public Job demo19Job() {
        return jobBuilderFactory.get("demo19Job1")
                .start(step1())
                .next(step2())
                .build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(errorHandling())
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(errorHandling())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet errorHandling() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
                if (executionContext.containsKey("qingdao")) {
                    System.out.println("the second run will success");
                    return RepeatStatus.FINISHED;
                } else {
                    System.out.println("the second run will fail");
                    chunkContext.getStepContext().getStepExecution().getExecutionContext().put("qingdao", "2");
                    throw new RuntimeException("error-------");
                }
            }
        };
    }


}

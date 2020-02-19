package com.yanglf.springbatch.demo14;

import com.yanglf.springbatch.demo10.Custom;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

import java.util.List;

/**
 * 数据库   writer
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
    @Qualifier("writer")
    private JdbcBatchItemWriter<User> writer;


    @Bean
    public Job demo14Job() {
        return jobBuilderFactory.get("demo14Job2")
                .start(step1())
                .build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<User, User>chunk(2)
                .reader(reader())
                .writer(writer)
                .build();
    }


    @Bean
    @StepScope
    public ItemReader<User> reader() {
        FlatFileItemReader<User> reader = new FlatFileItemReader<>();
        // 读取文件路径
        reader.setResource(new ClassPathResource("user.txt"));
        // 跳过第一行
        //reader.setLinesToSkip(1);
        // 数据解析
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("id", "username", "password", "age");
        // 数据映射对象
        DefaultLineMapper<User> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(delimitedLineTokenizer);
        mapper.setFieldSetMapper(new FieldSetMapper<User>() {
            @Override
            public User mapFieldSet(FieldSet fieldSet) throws BindException {
                return User.builder()
                        .id(fieldSet.readLong("id"))
                        .username(fieldSet.readString("username"))
                        .password(fieldSet.readRawString("password"))
                        .age(fieldSet.readInt("age"))
                        .build();
            }
        });
        mapper.afterPropertiesSet();
        reader.setLineMapper(mapper);
        reader.open(new ExecutionContext());
        return reader;
    }


}

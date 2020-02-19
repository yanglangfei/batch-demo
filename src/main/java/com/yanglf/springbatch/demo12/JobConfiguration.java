package com.yanglf.springbatch.demo12;

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
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.validation.BindException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取 多文件
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

    @Value("classpath:/custom*.txt")
    private Resource[] resources;


    @Bean
    public Job demo12Job() {
        return jobBuilderFactory.get("demo12Job1")
                .start(step1())
                .build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Custom, Custom>chunk(2)
                .reader(reader())
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


    @Bean
    @StepScope
    public ItemReader<Custom> multiResourceReader() {
        MultiResourceItemReader<Custom> reader=new MultiResourceItemReader<>();
        reader.setDelegate(reader());
        reader.setResources(resources);
        reader.open(new ExecutionContext());
        return reader;
    }


    public FlatFileItemReader<Custom> reader() {
        FlatFileItemReader<Custom> reader = new FlatFileItemReader<>();
        // 读取文件路径
        reader.setResource(new ClassPathResource("custom.txt"));
        // 跳过第一行
        reader.setLinesToSkip(1);
        // 数据解析
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("id", "username", "password", "age");
        // 数据映射对象
        DefaultLineMapper<Custom> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(delimitedLineTokenizer);
        mapper.setFieldSetMapper(new FieldSetMapper<Custom>() {
            @Override
            public Custom mapFieldSet(FieldSet fieldSet) throws BindException {
                return Custom.builder()
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

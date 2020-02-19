package com.yanglf.springbatch.demo11;

import com.sun.xml.internal.stream.buffer.XMLStreamBufferMark;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.validation.BindException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取 xml文件
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
    public Job demo11Job() {
        return jobBuilderFactory.get("demo11Job2")
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
    public ItemReader<Custom> reader() {
        StaxEventItemReader<Custom> reader=new StaxEventItemReader<>();
        // 读取路径
        reader.setResource(new ClassPathResource("custom.xml"));
        // 指定需要处理的跟标签
        reader.setFragmentRootElementName("customer");
        // 数据转化为 对象
        XStreamMarshaller xStreamMarshaller=new XStreamMarshaller();
        Map<String,Class> map=new HashMap<>();
        map.put("customer",Custom.class);
        xStreamMarshaller.setAliases(map);
        reader.setUnmarshaller(xStreamMarshaller);

        reader.open(new ExecutionContext());
        return reader;
    }


}

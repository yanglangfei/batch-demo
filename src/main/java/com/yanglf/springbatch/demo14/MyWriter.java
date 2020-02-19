package com.yanglf.springbatch.demo14;

import org.springframework.batch.item.*;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author yanglf
 * @sine 2020.02.16
 * @descriptipon
 * @see
 */
@Component
public class MyWriter{

    @Autowired
    private DataSource dataSource;

    @Bean(value = "writer")
    public JdbcBatchItemWriter<User> writer()  {
        JdbcBatchItemWriter<User> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("insert into user (id,username,password,age) values (:id,:username,:password,:age)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }
}

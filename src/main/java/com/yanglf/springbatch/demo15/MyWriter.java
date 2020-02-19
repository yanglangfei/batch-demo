package com.yanglf.springbatch.demo15;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author yanglf
 * @sine 2020.02.16
 * @descriptipon
 * @see
 */
@Component
public class MyWriter {

    @Autowired
    private DataSource dataSource;

    @Bean(value = "writer")
    public FlatFileItemWriter<User> writer() throws Exception {
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<>();
        // 指定 写入 路径
        String path = "f:\\user.txt";
        writer.setResource(new FileSystemResource(path));
        // 对象转换 为 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        writer.setLineAggregator(new LineAggregator<User>() {
            @Override
            public String aggregate(User user) {
                String value = null;
                try {
                    value = objectMapper.writeValueAsString(user);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return value;
            }
        });
        writer.afterPropertiesSet();
        return writer;
    }
}

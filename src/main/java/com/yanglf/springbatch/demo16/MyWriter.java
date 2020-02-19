package com.yanglf.springbatch.demo16;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

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
    public StaxEventItemWriter<User> writer() throws Exception {
        StaxEventItemWriter<User> writer = new StaxEventItemWriter<>();
        // 对象转换 为 字符串
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        Map<String, Class> map = new HashMap<>(1);
        map.put("user", User.class);
        xStreamMarshaller.setAliases(map);
        writer.setRootTagName("user");
        writer.setMarshaller(xStreamMarshaller);
        // 指定 写入 路径
        String path = "f:\\user.xml";
        writer.setResource(new FileSystemResource(path));
        writer.afterPropertiesSet();
        return writer;
    }
}

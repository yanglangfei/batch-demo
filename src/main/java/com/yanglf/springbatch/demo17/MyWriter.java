package com.yanglf.springbatch.demo17;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Arrays;
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

    @Bean(value = "writerToXml")
    public StaxEventItemWriter<User> writerToXml() throws Exception {
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


    @Bean(value = "writerToTxt")
    public FlatFileItemWriter<User> writerToTxt() throws Exception {
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


    /**
     * 写入 多文件 不分类  全部写
     *
     * @return
     * @throws Exception
     */
  /*  @Bean(value = "writer")
    public CompositeItemWriter<User> writer() throws Exception {
        CompositeItemWriter<User> writer = new CompositeItemWriter<>();
        writer.setDelegates(Arrays.asList(writerToXml(), writerToTxt()));
        writer.afterPropertiesSet();
        writer.open(new ExecutionContext());
        return writer;
    }*/


    /**
     * 将数据分类  写入  不同的 文件
     *
     * @return
     * @throws Exception
     */
    @Bean(value = "writer")
    public ClassifierCompositeItemWriter<User> writer() throws Exception {
        ClassifierCompositeItemWriter<User> writer = new ClassifierCompositeItemWriter<>();
        // 写入 多文件
        writer.setClassifier(new Classifier<User, ItemWriter<? super User>>() {
            @Override
            public ItemWriter<? super User> classify(User user) {
                ItemWriter<User> write = null;
                try {
                    Long id = user.getId();
                    write = id % 2 == 0 ? writerToTxt() : writerToXml();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return write;
            }
        });
        return writer;
    }


}

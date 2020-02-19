package com.yanglf.springbatch.demo13;

import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

/**
 * @author yanglf
 * @sine 2020.02.16
 * @descriptipon
 * @see
 */
public class RestartReader implements ItemStreamReader<Custom> {

    private FlatFileItemReader<Custom> reader;
    private int curLine;
    private boolean restart;
    private ExecutionContext executionContext;

    public RestartReader() {
        reader = new FlatFileItemReader<>();
        // 读取文件路径
        reader.setResource(new ClassPathResource("custom.txt"));
        // 跳过第一行
       // reader.setLinesToSkip(1);
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
    }

    @Override
    public Custom read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Custom customer = null;
        curLine++;
        if (restart) {
            reader.setLinesToSkip(curLine - 1);
            restart = true;
            System.out.println("restart read from line" + curLine);
        }
        reader.open(executionContext);
        customer = reader.read();

        if (customer != null && customer.getUsername().equals("wrong")) {
            throw new RuntimeException("error--------"+customer.getId());
        }


        return customer;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.executionContext = executionContext;
        if(executionContext.containsKey("curLine")){
            curLine=executionContext.getInt("curLine");
            System.out.println("open line"+curLine);
            restart=true;
        }else {
            curLine=0;
            executionContext.put("curLine",curLine);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put("curLine",curLine);
        System.out.println("update----"+curLine);
    }

    @Override
    public void close() throws ItemStreamException {

    }
}

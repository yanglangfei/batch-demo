package com.yanglf.springbatch.demo17;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *  多文件  writer
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
    private ItemWriter<User> writer;

    @Autowired
    @Qualifier("writerToXml")
    private ItemStreamWriter<User> writerToXml;

    @Autowired
    @Qualifier("writerToTxt")
    private ItemStreamWriter<User> writerToTxt;


    @Autowired
    public DataSource dataSource;


    @Bean
    public Job demo17Job() {
        return jobBuilderFactory.get("demo17Job1")
                .start(step1())
                .build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<User, User>chunk(2)
                .reader(reader())
                .writer(writer)
                // ClassifierCompositeItemWriter 必须加   stream
                .stream(writerToXml)
                .stream(writerToTxt)
                .build();
    }


    @Bean
    @StepScope
    public ItemReader<User> reader() {
        JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        // 每次读取  2  条
        reader.setFetchSize(2);
        // 将读取的数据转换为 对象
        reader.setRowMapper(new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                return User.builder().id(resultSet.getLong(1))
                        .username(resultSet.getString(2))
                        .password(resultSet.getString(3))
                        .age(resultSet.getInt(4))
                        .build();
            }
        });
        // 指定  sql 语句
        MySqlPagingQueryProvider sql = new MySqlPagingQueryProvider();
        // 指定查询字段
        sql.setSelectClause("id,username,password,age");
        // 指定  表名
        sql.setFromClause("from user");
        // 指定 排序字段
        Map<String, Order> sort = new HashMap<>(1);
        sort.put("id", Order.DESCENDING);
        sql.setSortKeys(sort);
        // where 条件
        // sql.setWhereClause("");
        reader.setQueryProvider(sql);
        return reader;
    }


}

package com.yanglf.springbatch.demo8;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;
import java.util.List;

/**
 * @author yanglf
 * @sine 2020.02.16
 * @descriptipon
 * @see
 */
public class MyReader implements ItemReader<String> {


    private Iterator<String> data;

    public MyReader(List<String> data) {
        this.data = data.iterator();
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (data.hasNext()) {
            return data.next();
        }
        return null;
    }
}

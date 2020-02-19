package com.yanglf.springbatch.demo21;

import org.springframework.batch.core.SkipListener;

/**
 * @author yanglf
 * @sine 2020.02.18
 * @descriptipon
 * @see
 */
public class MySkipListener  implements SkipListener<String,String> {

    @Override
    public void onSkipInRead(Throwable throwable) {
        System.out.println("Read 发生异常 异常类型:"+throwable);
    }

    @Override
    public void onSkipInWrite(String s, Throwable throwable) {
        System.out.println("Write 发生异常  :"+s+"  异常类型:"+throwable);
    }

    @Override
    public void onSkipInProcess(String s, Throwable throwable) {
        System.out.println("Processor 发生异常  :"+s+"  异常类型:"+throwable);
    }
}

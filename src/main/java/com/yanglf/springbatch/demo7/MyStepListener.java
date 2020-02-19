package com.yanglf.springbatch.demo7;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * @author yanglf
 * @sine 2020.02.16
 * @descriptipon
 * @see
 */
public class MyStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        stepExecution.getJobParameters().getParameters().put("info",new JobParameter("zhangsan"));
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}

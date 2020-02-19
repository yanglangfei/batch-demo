package com.yanglf.springbatch.demo4;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * @author yanglf
 * @sine 2020.02.16
 * @descriptipon
 * @see
 */
public class MyDecider implements JobExecutionDecider {

    private int count;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        count++;
        if (count % 2 == 0) {
            return new FlowExecutionStatus("event");
        } else {
            return new FlowExecutionStatus("odd");
        }
    }
}

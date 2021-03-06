package com.yanglf.springbatch.demo22;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanglf
 * @sine 2020.02.18
 * @descriptipon
 * @see
 */
@RestController
public class MyController {


    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "demo21Job")
    public Job job;

    @RequestMapping("/job/{msg}")
    public String jobRun1(@PathVariable String msg) {
        try {
            // 将接收到的 参数  传给 任务
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("msg", msg)
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
            return "Job success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Job fail";
    }
}

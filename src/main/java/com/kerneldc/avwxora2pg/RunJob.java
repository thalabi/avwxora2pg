package com.kerneldc.avwxora2pg;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RunJob implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job metarJob;

    public RunJob(JobLauncher jobLauncher, @Qualifier("metarJob") Job metarJob) {
        this.jobLauncher = jobLauncher;
        this.metarJob = metarJob;
    }

    @Override
    public void run(String... args) throws Exception {
    	
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("run.id", LocalDateTime.now().toString()) // unique parameter to rerun the job
                
                .addString("yearMonth", "2025-02")
                
                .toJobParameters();

//        LOGGER.info("Starting metarJob");
//        JobExecution execution = jobLauncher.run(metarJob, jobParameters);
//        LOGGER.info("MetarJob status: [{}]", execution.getStatus());
    }
}
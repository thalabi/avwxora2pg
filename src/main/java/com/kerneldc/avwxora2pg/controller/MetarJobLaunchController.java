package com.kerneldc.avwxora2pg.controller;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/metarJobLaunchController")
@RequiredArgsConstructor
@Slf4j
public class MetarJobLaunchController {

	private final JobLauncher jobLauncher;
    private final Job metarJob;
    private Map<String, JobExecution> jobInfoMap = new ConcurrentHashMap<>();
    
    // curl -X POST "http://localhost:6007/metarJobLaunchController/launchMetarJob?yearMonth=2025-03"

    @PostMapping("/launchMetarJob")
    public ResponseEntity<String> launchMetarJob(@RequestParam String yearMonth) {
    	String jobId = UUID.randomUUID().toString();
    	JobParameters jobParameters = new JobParametersBuilder()
    			.addDate("runDate", new Date())  // to ensure uniqueness
    			.addString("yearMonth", yearMonth)
    			.addString("jobId", jobId)
    			.toJobParameters();
    	
    	 new Thread(() -> {
    	        try {
    	        	jobInfoMap.put(jobId, new JobExecution(0l)); // put a placeholder
    	        	var jobExecution = jobLauncher.run(metarJob, jobParameters);
    	        	jobInfoMap.put(jobId, jobExecution);
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
    	    }).start();
    	 return ResponseEntity.ok(jobId);
    }

    // curl http://localhost:6007/metarJobLaunchController/metarJobStatus?jobId=463c4d72-ff82-4343-aba5-ff93ac78e655
    
    @GetMapping("/metarJobStatus")
    public ResponseEntity<String> metarJobStatus(@RequestParam String jobId) {
    	LOGGER.info("jobInfoMap size [{}]", jobInfoMap.size());
        var execution = jobInfoMap.get(jobId);
        if (execution == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No job found with ID: " + jobId);
        }
        if (execution.getJobId() == null || execution.getJobId() == 0l) {
        	return ResponseEntity.ok("Job status not available");
        } else {
        	return ResponseEntity.ok("Job status: " + execution.getStatus());
        }
    }

}

package com.kerneldc.avwxora2pg.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class MetarReadStepListener extends MetarReadListener implements StepExecutionListener {

	@Override
    public void beforeStep(StepExecution stepExecution) {
        // No action needed
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logFinalCount();
        return stepExecution.getExitStatus();
    }
}

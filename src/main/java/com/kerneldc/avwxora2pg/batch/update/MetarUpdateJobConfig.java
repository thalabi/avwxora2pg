package com.kerneldc.avwxora2pg.batch.update;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.kerneldc.avwxora2pg.batch.MetarReadStepListener;
import com.kerneldc.avwxora2pg.domain.Metar;

@Configuration
@EnableBatchProcessing
public class MetarUpdateJobConfig {

	@Value("${write.chunk.size}")
	private int chunkSize;

    @Bean
    public Step metarUpdateLoadStep(JobRepository jobRepository,
                          PlatformTransactionManager transactionManager,
                          ItemReader<Metar> metarUpdateReader,
                          MetarReadStepListener metarReadStepListener, 
                          ItemProcessor<Metar, Metar> metarProcessor,
                          ItemWriter<Metar> metarUpdateWriter,
                          ChunkListener loggingChunkListener) {

        return new StepBuilder("metarUpdateLoadStep", jobRepository)
                .<Metar, Metar>chunk(chunkSize, transactionManager)
                .reader(metarUpdateReader)
                .processor(metarProcessor)
                .writer(metarUpdateWriter)
                .listener((ItemReadListener<Metar>) metarReadStepListener) // metarReadStepListener logs count of read rows
                .listener((StepExecutionListener) metarReadStepListener) // have to register twice because it implements ItemReadListener & StepExecutionListener
                .listener(loggingChunkListener)
                .build();
    }

    @Bean
    public Job metarUpdateJob(JobRepository jobRepository, Step truncateMetarMigrationUpdateTableStep, Step metarUpdateLoadStep, Step updateMetarMigrationStep) {
    	return new JobBuilder("metarUpdateJob", jobRepository)
    			.start(truncateMetarMigrationUpdateTableStep)
    			.next(metarUpdateLoadStep)
    			.next(updateMetarMigrationStep)
    			.build();
    }
}

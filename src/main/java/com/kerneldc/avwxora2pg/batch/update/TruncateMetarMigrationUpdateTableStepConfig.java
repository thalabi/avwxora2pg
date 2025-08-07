package com.kerneldc.avwxora2pg.batch.update;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TruncateMetarMigrationUpdateTableStepConfig {

	@Bean
	public Step truncateMetarMigrationUpdateTableStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, DataSource postgresDataSource) {
	    return new StepBuilder("truncateMetarMigrationUpdateTableStep", jobRepository)
	            .tasklet((contribution, chunkContext) -> {
	                try (Connection conn = postgresDataSource.getConnection();
	                     Statement stmt = conn.createStatement()) {
	                    stmt.execute("TRUNCATE TABLE metar_migration_update");
	                }
	                return RepeatStatus.FINISHED;
	            }, transactionManager)
	            .build();
	}
}

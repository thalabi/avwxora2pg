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
public class UpdateMetarMigrationStepConfig {

	@Bean
	public Step updateMetarMigrationStep(JobRepository jobRepository,
	                                    PlatformTransactionManager transactionManager,
	                                    DataSource postgresDataSource) {
	    return new StepBuilder("updateMetarMigrationStep", jobRepository)
	            .tasklet((contribution, chunkContext) -> {
	                try (Connection conn = postgresDataSource.getConnection();
	                     Statement stmt = conn.createStatement()) {

	                    stmt.executeUpdate("""
							update metar t
							   set sky_cover_1 = s.sky_cover_1,
								   cloud_base_ft_agl_1 = s.cloud_base_ft_agl_1,
								   sky_cover_2 = s.sky_cover_2,
								   cloud_base_ft_agl_2 = s.cloud_base_ft_agl_2,
								   sky_cover_3 = s.sky_cover_3,
								   cloud_base_ft_agl_3 = s.cloud_base_ft_agl_3,
								   sky_cover_4 = s.sky_cover_4,
								   cloud_base_ft_agl_4 = s.cloud_base_ft_agl_4
							  from metar_migration_update s
							 where t.station_id = s.station_id 
							   and t.observation_time = s.observation_time;
	                    """);
	                }

	                return RepeatStatus.FINISHED;
	            }, transactionManager)
	            .build();
	}

}

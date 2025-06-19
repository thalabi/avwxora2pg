package com.kerneldc.avwxora2pg.batch;

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
public class MergeMetarMigrationStepConfig {

	@Bean
	public Step mergeMetarMigrationStep(JobRepository jobRepository,
	                                    PlatformTransactionManager transactionManager,
	                                    DataSource postgresDataSource) {
	    return new StepBuilder("mergeMetarMigrationStep", jobRepository)
	            .tasklet((contribution, chunkContext) -> {
	                try (Connection conn = postgresDataSource.getConnection();
	                     Statement stmt = conn.createStatement()) {

	                    stmt.executeUpdate("""
	                        INSERT INTO metar (
	                            raw_text, station_id, observation_time,
	                            latitude, longitude, temp_c, dewpoint_c, wind_dir_degrees,
	                            wind_speed_kt, wind_gust_kt, visibility_statute_mi, altim_in_hg,
	                            sea_level_pressure_mb, corrected, auto, auto_station, 
	                            maintenance_indicator_on, no_signal, lightning_sensor_off, 
	                            freezing_rain_sensor_off, present_weather_sensor_off, wx_string,
	                            sky_cover_1, cloud_base_ft_agl_1, sky_cover_2, cloud_base_ft_agl_2,
	                            sky_cover_3, cloud_base_ft_agl_3, sky_cover_4, cloud_base_ft_agl_4,
	                            flight_category, three_hr_pressure_tendency_mb, maxt_c, mint_c,
	                            maxt24hr_c, mint24hr_c, precip_in, pcp3hr_in, pcp6hr_in,
	                            pcp24hr_in, snow_in, vert_vis_ft, metar_type, elevation_m
	                        )
	                        SELECT
	                            raw_text, station_id, observation_time,
	                            latitude, longitude, temp_c, dewpoint_c, wind_dir_degrees,
	                            wind_speed_kt, wind_gust_kt, visibility_statute_mi, altim_in_hg,
	                            sea_level_pressure_mb, corrected, auto, auto_station, 
	                            maintenance_indicator_on, no_signal, lightning_sensor_off, 
	                            freezing_rain_sensor_off, present_weather_sensor_off, wx_string,
	                            sky_cover_1, cloud_base_ft_agl_1, sky_cover_2, cloud_base_ft_agl_2,
	                            sky_cover_3, cloud_base_ft_agl_3, sky_cover_4, cloud_base_ft_agl_4,
	                            flight_category, three_hr_pressure_tendency_mb, maxt_c, mint_c,
	                            maxt24hr_c, mint24hr_c, precip_in, pcp3hr_in, pcp6hr_in,
	                            pcp24hr_in, snow_in, vert_vis_ft, metar_type, elevation_m
	                        FROM metar_migration
	                        ON CONFLICT (station_id, observation_time) DO NOTHING
	                    """);
	                }

	                return RepeatStatus.FINISHED;
	            }, transactionManager)
	            .build();
	}

}

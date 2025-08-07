package com.kerneldc.avwxora2pg.batch.update;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kerneldc.avwxora2pg.domain.Metar;

@Configuration
public class MetarUpdateWriterConfig {

    @Bean
    public JdbcBatchItemWriter<Metar> metarUpdateWriter(@Qualifier("postgresDataSource") DataSource postgresDataSource) {
        return new JdbcBatchItemWriterBuilder<Metar>()
                .dataSource(postgresDataSource)
                .sql("""
                        INSERT INTO metar_migration_update (
                          station_id, observation_time, 
                          sky_cover_1, cloud_base_ft_agl_1, sky_cover_2, cloud_base_ft_agl_2,
                          sky_cover_3, cloud_base_ft_agl_3, sky_cover_4, cloud_base_ft_agl_4)
                        VALUES (
                          :stationId, :observationTime, 
                          :skyCover1, :cloudBaseFtAgl1, :skyCover2, :cloudBaseFtAgl2,
                          :skyCover3, :cloudBaseFtAgl3, :skyCover4, :cloudBaseFtAgl4
                        )
                        """)
                .beanMapped()
                .build();
    }
}
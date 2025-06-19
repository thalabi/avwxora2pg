package com.kerneldc.avwxora2pg.batch;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kerneldc.avwxora2pg.domain.Metar;

@Configuration
public class MetarWriterConfig {

    @Bean
    public JdbcBatchItemWriter<Metar> metarWriter(@Qualifier("postgresDataSource") DataSource postgresDataSource) {
        return new JdbcBatchItemWriterBuilder<Metar>()
                .dataSource(postgresDataSource)
                .sql("""
                        INSERT INTO metar_migration (
                          raw_text, station_id, observation_time, latitude, longitude,
                          temp_c, dewpoint_c, wind_dir_degrees, wind_speed_kt, wind_gust_kt,
                          visibility_statute_mi, altim_in_hg, sea_level_pressure_mb, corrected, auto,
                          auto_station, maintenance_indicator_on, no_signal, lightning_sensor_off,
                          freezing_rain_sensor_off, present_weather_sensor_off, wx_string,
                          sky_cover_1, cloud_base_ft_agl_1, sky_cover_2, cloud_base_ft_agl_2,
                          sky_cover_3, cloud_base_ft_agl_3, sky_cover_4, cloud_base_ft_agl_4,
                          flight_category, three_hr_pressure_tendency_mb, maxt_c, mint_c,
                          maxt24hr_c, mint24hr_c, precip_in, pcp3hr_in, pcp6hr_in,
                          pcp24hr_in, snow_in, vert_vis_ft, metar_type, elevation_m)
                        VALUES (
                          :rawText, :stationId, :observationTime, :latitude, :longitude,
                          :tempC, :dewpointC, :windDirDegrees, :windSpeedKt, :windGustKt,
                          :visibilityStatuteMi, :altimInHg, :seaLevelPressureMb, :corrected, :auto,
                          :autoStation, :maintenanceIndicatorOn, :noSignal, :lightningSensorOff,
                          :freezingRainSensorOff, :presentWeatherSensorOff, :wxString,
                          :skyCover1, :cloudBaseFtAgl1, :skyCover2, :cloudBaseFtAgl2,
                          :skyCover3, :cloudBaseFtAgl3, :skyCover4, :cloudBaseFtAgl4,
                          :flightCategory, :threeHrPressureTendencyMb, :maxtC, :mintC,
                          :maxt24hrC, :mint24hrC, :precipIn, :pcp3hrIn, :pcp6hrIn,
                          :pcp24hrIn, :snowIn, :vertVisFt, :metarType, :elevationM
                        )
                        """)
                .beanMapped()
                .build();
    }
}
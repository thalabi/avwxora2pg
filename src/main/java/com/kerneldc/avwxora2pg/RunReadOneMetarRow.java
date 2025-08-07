package com.kerneldc.avwxora2pg;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.kerneldc.avwxora2pg.domain.Metar;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class RunReadOneMetarRow /*implements CommandLineRunner*/ {

private final JdbcTemplate oracleJdbcTemplate;

    public RunReadOneMetarRow(@Qualifier("oracleJdbcTemplate") JdbcTemplate oracleJdbcTemplate) {
        this.oracleJdbcTemplate = oracleJdbcTemplate;
    }

//    @Override
    public void run(String... args) throws Exception {
    	
        LOGGER.info("Starting RunReadOneMetarRow");
        
        LOGGER.info("oracleJdbcTemplate != null: [{}]", oracleJdbcTemplate != null);
        
        //var sql = "select m.* from metar m where observation_time = to_date('2025/06/01','yyyy/mm/dd') and station_id ='AYMH' and rownum=1";
        var sql = """
			SELECT
			    RAW_TEXT AS rawText,
			    STATION_ID AS stationId,
			    OBSERVATION_TIME AS observationTime,
			    LATITUDE AS latitude,
			    LONGITUDE AS longitude,
			    TEMP_C AS tempC,
			    DEWPOINT_C AS dewpointC,
			    WIND_DIR_DEGREES AS windDirDegrees,
			    WIND_SPEED_KT AS windSpeedKt,
			    WIND_GUST_KT AS windGustKt,
			    VISIBILITY_STATUTE_MI AS visibilityStatuteMi,
			    ALTIM_IN_HG AS altimInHg,
			    SEA_LEVEL_PRESSURE_MB AS seaLevelPressureMb,
			    CORRECTED AS corrected,
			    AUTO AS auto,
			    AUTO_STATION AS autoStation,
			    MAINTENANCE_INDICATOR_ON AS maintenanceIndicatorOn,
			    NO_SIGNAL AS noSignal,
			    LIGHTNING_SENSOR_OFF AS lightningSensorOff,
			    FREEZING_RAIN_SENSOR_OFF AS freezingRainSensorOff,
			    PRESENT_WEATHER_SENSOR_OFF AS presentWeatherSensorOff,
			    WX_STRING AS wxString,
			    SKY_COVER_1 AS skyCover1,
			    CLOUD_BASE_FT_AGL_1 AS cloudBaseFtAgl1,
			    SKY_COVER_2 AS skyCover2,
			    CLOUD_BASE_FT_AGL_2 AS cloudBaseFtAgl2,
			    SKY_COVER_3 AS skyCover3,
			    CLOUD_BASE_FT_AGL_3 AS cloudBaseFtAgl3,
			    SKY_COVER_4 AS skyCover4,
			    CLOUD_BASE_FT_AGL_4 AS cloudBaseFtAgl4,
			    FLIGHT_CATEGORY AS flightCategory,
			    THREE_HR_PRESSURE_TENDENCY_MB AS threeHrPressureTendencyMb,
			    MAXT_C AS maxtC,
			    MINT_C AS mintC,
			    MAXT24HR_C AS maxt24hrC,
			    MINT24HR_C AS mint24hrC,
			    PRECIP_IN AS precipIn,
			    PCP3HR_IN AS pcp3hrIn,
			    PCP6HR_IN AS pcp6hrIn,
			    PCP24HR_IN AS pcp24hrIn,
			    SNOW_IN AS snowIn,
			    VERT_VIS_FT AS vertVisFt,
			    METAR_TYPE AS metarType,
			    ELEVATION_M AS elevationM
			FROM METAR
			WHERE OBSERVATION_TIME = TO_DATE('2025/06/01','yyyy/mm/dd')
			  AND STATION_ID = 'VEAT'
			  AND ROWNUM = 1
        		""";
        var m = oracleJdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Metar.class));
        LOGGER.info("m != null: [{}]", m != null);
        if (m == null) {
        	return;
        }
        LOGGER.info("m: [{}]", m);
        
    }
}
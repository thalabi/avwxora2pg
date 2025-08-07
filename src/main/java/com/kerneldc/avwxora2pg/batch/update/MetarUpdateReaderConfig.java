package com.kerneldc.avwxora2pg.batch.update;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.kerneldc.avwxora2pg.domain.Metar;

@Configuration
public class MetarUpdateReaderConfig {

	@Value("${read.page.size}")
	private int pageSize;

    @StepScope
    @Bean
    public JdbcPagingItemReader<Metar> metarUpdateReader(
            @Qualifier("oracleDataSource") DataSource dataSource,
            @Value("#{jobParameters['yearMonth']}") String yearMonthStr
    ) {
        YearMonth targetMonth = YearMonth.parse(yearMonthStr);
        LocalDate start = targetMonth.atDay(1);
        LocalDate end = targetMonth.atEndOfMonth().plusDays(1); // exclusive

        JdbcPagingItemReader<Metar> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setPageSize(pageSize);
        reader.setRowMapper(new BeanPropertyRowMapper<>(Metar.class));

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("startDate", start);
        parameterValues.put("endDate", end);
        reader.setParameterValues(parameterValues);

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        var selectClause = """
			select
			    STATION_ID ,
			    OBSERVATION_TIME ,
			    SKY_COVER_1 as \"skyCover1\",
			    CLOUD_BASE_FT_AGL_1 as \"cloudBaseFtAgl1\",
			    SKY_COVER_2 as \"skyCover2\",
			    CLOUD_BASE_FT_AGL_2 as \"cloudBaseFtAgl2\",
			    SKY_COVER_3 as \"skyCover3\",
			    CLOUD_BASE_FT_AGL_3 as \"cloudBaseFtAgl3\",
			    SKY_COVER_4 as \"skyCover4\",
			    CLOUD_BASE_FT_AGL_4 as \"cloudBaseFtAgl4\"
        		""";
        queryProvider.setSelectClause(selectClause);
        queryProvider.setFromClause("FROM metar");
        queryProvider.setWhereClause("observation_time >= :startDate AND observation_time < :endDate");

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("station_id", Order.ASCENDING);
        sortKeys.put("observation_time", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);

        reader.setQueryProvider(queryProvider);

        return reader;
    }
}

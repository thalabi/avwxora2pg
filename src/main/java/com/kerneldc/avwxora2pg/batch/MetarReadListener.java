package com.kerneldc.avwxora2pg.batch;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kerneldc.avwxora2pg.domain.Metar;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MetarReadListener implements ItemReadListener<Metar> {

	@Value("${read.page.size}")
	private int pageSize;

	private int count = 0;

	public void resetCount() {
		count = 0;
	}
	
    @Override
    public void beforeRead() {
        // optional: log start of reading
    }

    @Override
    public void afterRead(Metar metar) {
        count++;
        if (count % pageSize == 0) {
        	LOGGER.info("Read count: [{}]", count);
        }
    }

    @Override
    public void onReadError(Exception ex) {
        LOGGER.error("Error reading item", ex);
    }

    public void logFinalCount() {
    	LOGGER.info("Total metar rows read: [{}]", count);
    }
}

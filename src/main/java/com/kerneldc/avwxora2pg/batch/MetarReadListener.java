package com.kerneldc.avwxora2pg.batch;

import org.springframework.batch.core.ItemReadListener;

import com.kerneldc.avwxora2pg.domain.Metar;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MetarReadListener implements ItemReadListener<Metar> {

	private int count = 0;

    @Override
    public void beforeRead() {
        // optional: log start of reading
    }

    @Override
    public void afterRead(Metar metar) {
        count++;
    }

    @Override
    public void onReadError(Exception ex) {
        LOGGER.error("Error reading item", ex);
    }

    public void logFinalCount() {
    	LOGGER.info("Total metar rows read: [{}]", count);
    }
}

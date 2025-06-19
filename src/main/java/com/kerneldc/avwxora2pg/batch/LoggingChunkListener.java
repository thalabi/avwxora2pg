package com.kerneldc.avwxora2pg.batch;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggingChunkListener implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
        // Optional: log before chunk
    }

    @Override
    public void afterChunk(ChunkContext context) {
    	LOGGER.info("✅ Chunk committed successfully.");
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        LOGGER.error("Error during chunk execution.");
    }
}

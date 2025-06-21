package com.kerneldc.avwxora2pg.batch;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggingChunkListener implements ChunkListener {

	@Value("${write.chunk.size}")
	private int chunkSize;

    @Override
    public void beforeChunk(ChunkContext context) {
        // Optional: log before chunk
    }

    @Override
    public void afterChunk(ChunkContext context) {
    	LOGGER.info("✅ Chunk committed [{}] rows successfully", chunkSize);
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        LOGGER.error("Error during chunk execution.");
    }
}

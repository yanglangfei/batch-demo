package com.yanglf.springbatch.demo6;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

/**
 * @author yanglf
 * @sine 2020.02.16
 * @descriptipon
 * @see
 */
@Component
public class MyChunkListener {

    @BeforeChunk
    public void beforeChunk(ChunkContext chunkContext) {
        System.out.println("beforeChunk------------"+chunkContext.getStepContext().getStepName());
    }

    @AfterChunk
    public void afterChunk(ChunkContext chunkContext) {
        System.out.println("afterChunk------------"+chunkContext.getStepContext().getStepName());
    }

    @AfterChunkError
    public void chunkError(){
        System.out.println("chunkError------------");
    }
}

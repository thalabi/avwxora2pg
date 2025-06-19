package com.kerneldc.avwxora2pg.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.kerneldc.avwxora2pg.domain.Metar;

@Component
public class MetarProcessor implements ItemProcessor<Metar, Metar> {

    @Override
    public Metar process(Metar item) {
        // No transformation; pass-through
        return item;
    }
}
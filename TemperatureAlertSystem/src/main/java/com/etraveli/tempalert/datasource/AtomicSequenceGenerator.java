package com.etraveli.tempalert.datasource;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Generate sequence with incremental manner.
 * 
 * @author VBhosale
 *
 */
public class AtomicSequenceGenerator implements SequenceGenerator {

	private AtomicLong value = new AtomicLong(1);

    @Override
    public long getNext() {
        return value.getAndIncrement();
    }

}

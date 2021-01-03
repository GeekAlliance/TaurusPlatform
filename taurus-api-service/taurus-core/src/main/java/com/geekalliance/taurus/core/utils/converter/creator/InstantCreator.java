package com.geekalliance.taurus.core.utils.converter.creator;

import java.time.Instant;

/**
 * @author maxuqiang
 */
public class InstantCreator implements Creator<Instant> {
    @Override
    public Instant create(Object source) {
        Instant srcObject = (Instant) source;
        return Instant.from(srcObject);
    }
}
